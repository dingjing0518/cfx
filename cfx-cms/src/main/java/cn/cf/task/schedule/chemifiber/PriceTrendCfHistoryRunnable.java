package cn.cf.task.schedule.chemifiber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;

import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bPriceMovementExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.B2bPriceMovementEntity;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class PriceTrendCfHistoryRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(PriceTrendCfHistoryRunnable.class);
    private String name;

    private String fileName;

    private String uuid;

    private Date insertTime;
    private SysExcelStoreExtDto storeDtoTemp;

    private B2bPriceMovementExtDao b2bPriceMovementExtDao;

    private MongoTemplate mongoTemplate;

    private SysExcelStoreExtDao storeDao;


    public PriceTrendCfHistoryRunnable() {
    }


    public PriceTrendCfHistoryRunnable(String name,String uuid) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public void run() {
        //上传数据
        ScheduledFuture future = null;
        if (CommonUtil.isNotEmpty(this.name)) {
            future = ScheduledFutureMap.map.get(this.name);
        }
        try {
            upLoadFile();
        } catch (Exception e) {
            ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao,this.storeDtoTemp,e.getMessage());
        } finally {
            ScheduledFutureMap.stopFuture(future, this.name);
        }
    }

    //上传到OSS
    public void upLoadFile() throws Exception {

        this.b2bPriceMovementExtDao = (B2bPriceMovementExtDao) BeanUtils.getBean("b2bPriceMovementExtDao");
        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
        this.mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");

        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.THREE,"exportPriceTrendHistoryList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    String ossPath = "";
                    GoodsDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                         params = JSON.parseObject(storeDto.getParams(), GoodsDataTypeParams.class);
                         this.fileName = "化纤中心-运营管理-首页显示管理-价格趋势历史-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                         this.insertTime = storeDto.getInsertTime();
                    }
                    if (this.b2bPriceMovementExtDao != null) {
                        long counts = countsCfPriceTrendHistory(params);
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于5000，分页查询数据
                            ossPath = setCfLimitPages(params, counts, storeDto);
                        } else {
                            //如果小于或等于5000条直接上传
                            ossPath = setCfNotLimitPages(params);
                        }
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }

    //化纤
    private String setCfNotLimitPages(GoodsDataTypeParams params) {
        String ossPath = "";
        List<B2bPriceMovementEntity> priceHistoryList = exportPriceTrendHistoryList(params, 0, 0);
        if (priceHistoryList != null && priceHistoryList.size() > Constants.ZERO) {
            ExportUtil<B2bPriceMovementEntity> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("priceTrendHistory", this.fileName, priceHistoryList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 化纤
    private String setCfLimitPages(GoodsDataTypeParams params, double counts, SysExcelStoreExtDto storeDto){
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            List<B2bPriceMovementEntity> priceHistoryList = exportPriceTrendHistoryList(params, start, Constants.EXCEL_NUMBER_TENHOUSAND);
            if (priceHistoryList != null && priceHistoryList.size() > Constants.ZERO) {
                String excelName = "化纤中心-运营管理-首页显示管理-价格趋势历史-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                ExportUtil<B2bPriceMovementEntity> exportUtil = new ExportUtil<>();
                String path = exportUtil.exportDynamicUtil("priceTrendHistory", excelName, priceHistoryList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }


    public List<B2bPriceMovementEntity> exportPriceTrendHistoryList(GoodsDataTypeParams dto, int skip, int limit) {
        Query query = setMongoQuery(dto);
        if (skip > 0 && limit > 0) {
            query.skip(skip);
            query.limit(limit);
        }
        return this.mongoTemplate.find(query, B2bPriceMovementEntity.class);
    }

    public long countsCfPriceTrendHistory(GoodsDataTypeParams dto) {
        Query query = setMongoQuery(dto);
        return this.mongoTemplate.count(query, B2bPriceMovementEntity.class);
    }

    private Query setMongoQuery(GoodsDataTypeParams dto) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria = Criteria.where("goodsPk").nin("");
        if (dto != null) {
            String start = dto.getDateHistoryStart();
            String end = dto.getDateHistoryEnd();

            if (start != null && !"".equals(start) && end != null && !"".equals(end)) {
                criteria.and("date").gte(start).lte(end);
            }
            if (start != null && !"".equals(start) && (end == null || "".equals(end))) {
                criteria.and("date").gte(start);
            }
            if (end != null && !"".equals(end) && (start == null || "".equals(start))) {
                criteria.and("date").lte(end);
            }
            criteria.and("insertTime").lte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));

            if (CommonUtil.isNotEmpty(dto.getIsHome()) && Integer.valueOf(dto.getIsHome()) > 0) {
                criteria.and("isShow").is( Integer.valueOf(dto.getIsHome()));
            }
        }
        query.addCriteria(criteria);
        return query;
    }

}
