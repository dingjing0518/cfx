package cn.cf.task.schedule.marketing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.OrderExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bManageRegionDao;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dao.MarketingOrderMemberExtDao;
import cn.cf.dao.MarketingPersonnelExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bManageRegionDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.RegionJson;
import cn.cf.model.ManageAccount;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class MkOrderRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(MkOrderRunnable.class);
    private String name;

    private String fileName;

    private String accountPk;

    private String accountName;

    private String rolePk;

    private String uuid;

    private Date insertTime;
    private SysExcelStoreExtDto storeDtoTemp;

    private MarketingOrderMemberExtDao orderMemberDao;

    private B2bOrderExtDao b2bOrderExtDao;

    private B2bManageRegionDao b2bManageRegionDao;

    private ManageAuthorityExtDao manageAuthorityExtDao;
    private SysExcelStoreExtDao storeDao;
    public MkOrderRunnable() {
    }



    public MkOrderRunnable(String name,String uuid) {
        this.name = name;
        this.uuid = uuid;
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

        B2bOrderExtDao orderDao = (B2bOrderExtDao) BeanUtils.getBean("b2bOrderExtDao");
        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");

        this.orderMemberDao = (MarketingOrderMemberExtDao) BeanUtils.getBean("marketingOrderMemberExtDao");
        this.b2bOrderExtDao = (B2bOrderExtDao) BeanUtils.getBean("b2bOrderExtDao");
        this.b2bManageRegionDao = (B2bManageRegionDao) BeanUtils.getBean("b2bManageRegionDao");
        this.manageAuthorityExtDao = (ManageAuthorityExtDao) BeanUtils.getBean("manageAuthorityExtDao");

        if (storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.TWO,"exportOrderM_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    this.fileName = "营销中心-订单管理-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                    OrderDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), OrderDataTypeParams.class);
                        this.accountPk = storeDto.getAccountPk();
                        this.rolePk = storeDto.getRolePk();
                        this.accountName = storeDto.getAccountName();
                        this.insertTime = storeDto.getInsertTime();
                    }
                    if (orderDao != null) {
                        Map<String, Object> orderMap = getExportObjectMap(params,storeDto.getAccountPk());
                        MarketingPersonnelDto dto = this.getAccountName(storeDto.getAccountPk());
                        String ossPath = "";
                        int counts = checkNumbers(orderMap,dto);
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于5000，分页查询数据
                            ossPath = setLimitPages(orderMap, counts,dto);
                        } else {
                            //如果小于或等于5000条直接上传
                            ossPath = setNotLimitPages(orderMap,dto);
                        }
                        //更新订单导出状态
                        ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
                    }
                }
            }
        }
    }
    private int checkNumbers(Map<String, Object> orderMap,MarketingPersonnelDto dto){
        //查找业务员
        // 查询登陆账户信息
        int count = 0;
        if(null != dto){
            if (dto.getType() == Constants.ONE) {//业务员账号登录
                orderMap.put("businessPk", this.accountPk);
                count = orderMemberDao.searchOrderCount(orderMap);
            }else if (dto.getType() == Constants.TWO){ //平台交易员登录,看所有的订单
                count = b2bOrderExtDao.searchOrderMCount(orderMap);
            }else if (dto.getType() == Constants.THREE){//区域经理
                B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                if (regionDto != null&&regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                    List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                    orderMap.put("regionList", regionJsons);
                    orderMap.put("loginPk", this.accountPk);
                    count = orderMemberDao.searchOrderCount(orderMap);
                }else{//区域总监看见所有
                    count = b2bOrderExtDao.searchOrderMCount(orderMap);
                }
            }
        }
        return count;
    }


    public List<B2bOrderExtDto> exportOrderMList(Map<String, Object> orderMap,MarketingPersonnelDto dto) {
        //查找业务员
        // 查询登陆账户信息
        List<B2bOrderExtDto> list = new ArrayList<B2bOrderExtDto>();
        if(null != dto){
            if (dto.getType() == 1) {//业务员账号登录
                orderMap.put("businessPk", this.accountPk);
                list = orderMemberDao.exportOrderList(orderMap);
            }else if (dto.getType() == 2){ //平台交易员登录,看所有的订单
                list = b2bOrderExtDao.exportOrderGoodsMList(orderMap);
            }else if (dto.getType() == 3){//区域经理
                B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                if (regionDto != null&&regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                    List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                    orderMap.put("regionList", regionJsons);
                    orderMap.put("loginPk", this.accountPk);
                    list = orderMemberDao.exportOrderList(orderMap);
                }else{//区域总监看见所有
                    list = b2bOrderExtDao.exportOrderGoodsMList(orderMap);
                }
            }
        }
            ManageAccount account  = new ManageAccount();
            account.setPk(this.accountPk);
            account.setRolePk(this.rolePk);
            if (list != null && list.size() > 0){
                List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(this.rolePk);
                Map<String,String> checkMap = CommonUtil.checkColAuth(account,setDtoList);
                for (B2bOrderExtDto extDto:list){
                    OrderExportUtil.setMarketOrderJsonInfoCheckMap(extDto,checkMap);
                    OrderExportUtil.setExportParams(extDto);
                    String plantName="";
                    if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_ORDER_COL_PLANTNAME))) {
                        plantName = extDto.getPlantName() == null ? "" : CommonUtil.hideCompanyName(extDto.getPurchaserName());
                    } else {
                        plantName = extDto.getPlantName();
                    }
                    extDto.setPlantName(plantName);
                }
                checkMap.clear();
            }
        return list;
    }

    private String setNotLimitPages(Map<String, Object> orderMap,MarketingPersonnelDto dto) {
        String ossPath = "";
        List<B2bOrderExtDto> orderGoodsList = exportOrderMList(orderMap,dto);
        if (orderGoodsList != null && orderGoodsList.size() > Constants.ZERO) {
            ExportUtil<B2bOrderExtDto> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("orderM",this.fileName, orderGoodsList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts, MarketingPersonnelDto dto) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            orderMap.put("start", start);
            List<B2bOrderExtDto> orderGoodsList = exportOrderMList(orderMap,dto);
            orderMap.remove("start");
            if (orderGoodsList != null && orderGoodsList.size() > Constants.ZERO) {
                //设置权限
                ExportUtil<B2bOrderExtDto> exportUtil = new ExportUtil<>();
                String excelName = "营销中心-订单管理-"+this.accountName+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                String path = exportUtil.exportDynamicUtil("orderM",excelName, orderGoodsList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    private MarketingPersonnelDto getAccountName(String accountPk) {
        MarketingPersonnelExtDao marketingPersonnelDao = (MarketingPersonnelExtDao) BeanUtils.getBean("marketingPersonnelExtDao");
        Map<String, Object> mapAccount = new HashMap<>();
        mapAccount.put("accountPk", accountPk);
        mapAccount.put("isDelete", 1);
        mapAccount.put("isVisable", 1);
        MarketingPersonnelDto dto = null;
         if (marketingPersonnelDao != null) {
            dto = marketingPersonnelDao.getByMap(mapAccount);
         }
        return dto;
    }

    private Map<String, Object> getExportObjectMap(OrderDataTypeParams params,String accountPk) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (params != null) {
            map.put("orderNumber", params.getOrderNumber());
            map.put("purchaserName", params.getPurchaserName());
            map.put("storeName", params.getStoreName());
            map.put("insertStartTime", params.getInsertStartTime());
            map.put("insertEndTime", params.getInsertEndTime());
            map.put("orderStatus", params.getOrderStatus() .equals("") ? null : params.getOrderStatus());
            map.put("memberPk", params.getMemberPk());
            map.put("block", params.getBlock());
            map.put("source", params.getSource());
            map.put("paymentStartTime", params.getPaymentStartTime());
            map.put("paymentEndTime", params.getPaymentEndTime());
            map.put("paymentType", params.getPaymentType());
            map.put("receivablesStartTime", params.getReceivablesStartTime());
            map.put("receivablesEndTime", params.getReceivablesEndTime());
        }
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        map.put("colPurchaserName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_PURCHASERNAME));
        map.put("colStoreName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_STORENAME));
        map.put("colPurPerson", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_PURPERSON));
        map.put("colSupPerson", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_SUPPERSON));
        map.put("colAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_ADDRESS));
        map.put("colContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_CONTACTS));
        map.put("colContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_CONTACTSTEL));
        map.put("colSupComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_SUPCOMNAME));
        map.put("colPlantName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_PLANTNAME));
        map.put("colMemberName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_ORDER_COL_MEMBERNAME));
        return map;
    }
}
