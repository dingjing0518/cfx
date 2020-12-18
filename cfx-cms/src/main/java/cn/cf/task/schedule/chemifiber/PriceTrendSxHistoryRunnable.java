package cn.cf.task.schedule.chemifiber;

import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bPriceMovementExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bPriceMovementExtDto;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.*;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

public class PriceTrendSxHistoryRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(PriceTrendSxHistoryRunnable.class);

    private String name;

    private String fileName;

    private String uuid;

    private Date insertTime;

    private SysExcelStoreExtDto storeDtoTemp;
    private B2bPriceMovementExtDao b2bPriceMovementExtDao;

    private MongoTemplate mongoTemplate;

    private SysExcelStoreExtDao storeDao;


    public PriceTrendSxHistoryRunnable() {
    }


    public PriceTrendSxHistoryRunnable(String name,String uuid) {
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
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.THREE,"exportSxPriceTrendHistoryList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    String ossPath = "";
                    GoodsDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                         params = JSON.parseObject(storeDto.getParams(), GoodsDataTypeParams.class);
                         this.fileName = "纱线中心-运营管理-首页显示管理-价格趋势历史-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                         this.insertTime = storeDto.getInsertTime();
                    }
                    if (this.b2bPriceMovementExtDao != null) {
                        long counts = countsSxPriceTrendHistory(params);

                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于5000，分页查询数据
                           ossPath = setSxLimitPages(params, counts, storeDto);
                        } else {
                            //如果小于或等于5000条直接上传
                            ossPath = setSxNotLimitPages(params);
                        }
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }

    //纱线
    private String setSxNotLimitPages(GoodsDataTypeParams params) {
        String ossPath = "";
        List<SxPriceMovementEntity> priceHistoryList = exportSxPriceTrendHistoryList(params, 0, 0);
        if (priceHistoryList != null && priceHistoryList.size() > Constants.ZERO) {
            String templateName = "";
            ExportUtil<SxPriceMovementEntity> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("yarnPriceTrendHistory", this.fileName, priceHistoryList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS 纱线
    private String setSxLimitPages(GoodsDataTypeParams params, double counts, SysExcelStoreExtDto storeDto) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            List<SxPriceMovementEntity> priceHistoryList = exportSxPriceTrendHistoryList(params, start, Constants.EXCEL_NUMBER_TENHOUSAND);
            if (priceHistoryList != null && priceHistoryList.size() > Constants.ZERO) {
                String excelName = "纱线中心-运营管理-首页显示管理-价格趋势历史-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                ExportUtil<SxPriceMovementEntity> exportUtil = new ExportUtil<>();
                String path = exportUtil.exportDynamicUtil("yarnPriceTrendHistory", excelName, priceHistoryList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    public List<SxPriceMovementEntity> exportSxPriceTrendHistoryList(GoodsDataTypeParams dto, int skip, int limit) {
        Query query = setMongoQuery(dto);
        if (skip > 0 && limit > 0) {
            query.skip(skip);
            query.limit(limit);
        }
        return this.mongoTemplate.find(query, SxPriceMovementEntity.class);
    }

    public long countsSxPriceTrendHistory(GoodsDataTypeParams dto) {
        Query query = setMongoQuery(dto);
        return this.mongoTemplate.count(query, SxPriceMovementEntity.class);
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
                criteria.and("isShow").is(Integer.valueOf(dto.getIsHome()));
            }
        }
        query.addCriteria(criteria);
        return query;
    }

}
