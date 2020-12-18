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
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CfGoods;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.SxGoods;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

public class PriceTrendRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(PriceTrendRunnable.class);
    private String name;

    private String fileName;

    private String block;

    private String uuid;

    private Date insertTime;

    private SysExcelStoreExtDto storeDtoTemp;

    private B2bPriceMovementExtDao b2bPriceMovementExtDao;

    private SysExcelStoreExtDao storeDao;


    public PriceTrendRunnable() {
    }


    public PriceTrendRunnable(String name,String uuid) {
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
            ScheduledFutureMap.stopFuture(future,this.name);
        }
    }

    //上传到OSS
    public void upLoadFile() throws Exception {

        this.b2bPriceMovementExtDao = (B2bPriceMovementExtDao) BeanUtils.getBean("b2bPriceMovementExtDao");
        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");

        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.THREE,"exportPriceTrend_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    String ossPath = "";
                    GoodsDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), GoodsDataTypeParams.class);
                        this.block = params.getBlock();
                        this.insertTime = storeDto.getInsertTime();
                        if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)){
                            this.fileName = "化纤中心-运营管理-首页显示管理-价格趋势-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }else{
                            this.fileName = "纱线中心-运营管理-首页显示管理-价格趋势-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }
                    }
                    if (this.b2bPriceMovementExtDao != null) {
                        //设置查询参数
                        Map<String, Object> orderMap = orderParams(params);
                        //设置权限
                        int counts = b2bPriceMovementExtDao.searchGridCountExt(orderMap);

                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于5000，分页查询数据
                            ossPath = setLimitPages(orderMap, counts,storeDto);
                        } else {
                            //如果小于或等于5000条直接上传
                            ossPath = setNotLimitPages(orderMap);
                        }

                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }

    private String setNotLimitPages(Map<String, Object> orderMap) {
        String ossPath = "";

        List<B2bPriceMovementExtDto>  priceList = b2bPriceMovementExtDao.searchGridExt(orderMap);
        if (priceList != null && priceList.size() > Constants.ZERO) {
            //解析商品属性
            setGoodsParams(priceList);
            String templateName = "";
            if (Constants.BLOCK_CF.equals(this.block)){
                templateName = "priceTrend";
            }else{
                templateName = "yarnPriceTrend";
            }
            ExportUtil<B2bPriceMovementExtDto> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil(templateName,this.fileName, priceList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts,SysExcelStoreExtDto storeDto){
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            orderMap.put("start", start);
            List<B2bPriceMovementExtDto>  priceList = b2bPriceMovementExtDao.searchGridExt(orderMap);
            orderMap.remove("start");
            if (priceList != null && priceList.size() > Constants.ZERO) {
                //解析商品属性
                setGoodsParams(priceList);

                String excelName = "";
                if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)){
                    excelName = "化纤中心-运营管理-首页显示管理-价格趋势-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                }else{
                    excelName = "纱线中心-运营管理-首页显示管理-价格趋势-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                }
                String templateName = "";
                if (Constants.BLOCK_CF.equals(this.block)){
                    templateName = "priceTrend";
                }else{
                    templateName = "yarnPriceTrend";
                }
                ExportUtil<B2bPriceMovementExtDto> exportUtil = new ExportUtil<>();
                String path = exportUtil.exportDynamicUtil(templateName,excelName, priceList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    private void setGoodsParams(List<B2bPriceMovementExtDto>  priceList){
        for (B2bPriceMovementExtDto extDto : priceList) {
            String goodsInfo = extDto.getGoodsInfo();
            if (goodsInfo != null && !"".equals(goodsInfo)){
                if (Constants.BLOCK_CF.equals(this.block)){
                    CfGoods cf = JSON.parseObject(goodsInfo,CfGoods.class);
                    if (cf != null) {
                        extDto.setProductName(cf.getProductName());
                        extDto.setVarietiesName(cf.getVarietiesName());
                        extDto.setSpecName(cf.getSpecName());
                        extDto.setSpecifications(cf.getSpecifications());
                        extDto.setBatchNumber(cf.getBatchNumber());
                        extDto.setGradeName(cf.getGradeName());
                        extDto.setSeriesName(cf.getSeriesName());
                    }
                }
                if (Constants.BLOCK_SX.equals(this.block)){
                    SxGoods sx = JSON.parseObject(goodsInfo, SxGoods.class);
                    if (sx != null) {
                        extDto.setTechnologyName(sx.getTechnologyName());
                        extDto.setFirstMaterialName(sx.getRawMaterialParentName());
                        extDto.setSecondMaterialName(sx.getRawMaterialName());
                        extDto.setSpecifications(sx.getSpecName());
                    }
                }
            }
        }
    }

    private Map<String, Object> orderParams(GoodsDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isDelete", Constants.ONE);
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        if (params != null) {
            map.put("productName", params.getProductName());
            map.put("varietiesName", params.getVarietiesName());
            map.put("specName", params.getSpecName());
            map.put("seriesName", params.getSeriesName());
            map.put("specifications", params.getSpecifications());
            map.put("batchNumber", params.getBatchNumber());
            map.put("brandName", params.getBrandName());
            map.put("block", params.getBlock());
            map.put("platformUpdateTimeStart", params.getPlatformUpdateTimeStart());
            map.put("platformUpdateTimeEnd", params.getPlatformUpdateTimeEnd());
            map.put("updateTimeStart", params.getUpdateTimeStart());
            map.put("updateTimeEnd", params.getUpdateTimeEnd());
            map.put("dateStart", params.getDateStart());
            map.put("dateEnd", params.getDateEnd());
            map.put("isHome", params.getIsHome());
            if(CommonUtil.isNotEmpty(params.getIsShowHome()) && !"-1".equals(params.getIsShowHome())){
                map.put("isHome", params.getIsShowHome());
            }
            map.put("rawMaterialPk", params.getSecondMaterialName());//二级原料
            map.put("rawMaterialParentPk", params.getFirstMaterialName());//一级原料
            map.put("technologyPk", params.getTechnologyName());
        }
        return map;
    }
}
