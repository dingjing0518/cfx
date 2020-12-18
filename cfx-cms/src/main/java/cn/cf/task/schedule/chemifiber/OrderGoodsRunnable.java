package cn.cf.task.schedule.chemifiber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

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
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.ManageAccountExtDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.model.ManageAccount;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class OrderGoodsRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(OrderGoodsRunnable.class);
    private String name;

    private String fileName;

    private Date insertTime;

    private String uuid;

    private SysExcelStoreExtDto storeDtoTemp;

    private SysExcelStoreExtDao storeDao;

    public OrderGoodsRunnable() {
    }
    private String block;

    public OrderGoodsRunnable(String name,String uuid) {

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

        B2bOrderExtDao orderDao = (B2bOrderExtDao) BeanUtils.getBean("b2bOrderExtDao");

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");

        if (storeDao != null) {

            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.TWO,"exportOrderGoods_"+StringUtils.defaultIfEmpty(this.uuid, ""));

            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    String ossPath = "";
                    OrderDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), OrderDataTypeParams.class);
                        this.block = params.getBlock();
                        this.insertTime = storeDto.getInsertTime();
                        if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)) {
                            this.fileName = "化纤中心-订单管理-导出-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }else{
                            this.fileName = "纱线中心-订单管理-导出-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }
                    }
                    if (orderDao != null) {
                        //设置查询参数
                        Map<String, Object> orderMap = orderParams(params);
                        //设置权限
                        setColOrderParams(orderMap,storeDto);
                        int counts = orderDao.searchOrderGoodsListCounts(orderMap);
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于5000，分页查询数据
                            ossPath = setLimitPages(orderMap, counts, orderDao,storeDto);
                        } else {
                            //如果小于或等于5000条直接上传
                            ossPath = setNotLimitPages(orderDao, orderMap,storeDto);
                        }
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
                }
            }
        }
    }

    private String setNotLimitPages(B2bOrderExtDao orderDao, Map<String, Object> orderMap,SysExcelStoreExtDto storeExtDto) {
        String ossPath = "";
        List<B2bOrderExtDto> orderGoodsList = orderDao.searchOrderGoodsList(orderMap);
        if (orderGoodsList != null && orderGoodsList.size() > Constants.ZERO) {
            ExportUtil<B2bOrderExtDto> exportUtil = new ExportUtil<>();
            //设置权限
            setColAuth(orderGoodsList,storeExtDto);
            String templateName = "";
            if (Constants.BLOCK_CF.equals(this.block)){
                templateName = "orderGoods";
            }else{
                templateName = "yarnOrderGoods";
            }
            String path = exportUtil.exportDynamicUtil(templateName,this.fileName, orderGoodsList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts, B2bOrderExtDao orderDao,SysExcelStoreExtDto storeDto) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            orderMap.put("start", start);
            List<B2bOrderExtDto> orderGoodsList = orderDao.searchOrderGoodsList(orderMap);
            orderMap.remove("start");
            if (orderGoodsList != null && orderGoodsList.size() > Constants.ZERO) {
                //设置权限
                setColAuth(orderGoodsList,storeDto);
                ExportUtil<B2bOrderExtDto> exportUtil = new ExportUtil<>();
                String excelName = "";
                if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)) {
                    excelName = "化纤中心-订单管理-导出-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                }else{
                    excelName = "纱线中心-订单管理-导出-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                }
                String templateName = "";
                if (Constants.BLOCK_CF.equals(this.block)){
                    templateName = "orderGoods";
                }else{
                    templateName = "yarnOrderGoods";
                }
                String path = exportUtil.exportDynamicUtil(templateName,excelName, orderGoodsList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    //设置解析json串数据，判断显示权限
    private void setColAuth(List<B2bOrderExtDto> list,SysExcelStoreExtDto storeDto) {
        ManageAuthorityExtDao manageAuthorityExtDao = (ManageAuthorityExtDao) BeanUtils.getBean("manageAuthorityExtDao");
        ManageAccountExtDao manageAccountExtDao = (ManageAccountExtDao) BeanUtils.getBean("manageAccountExtDao");
        ManageAccountDto manageAccountDto = manageAccountExtDao.getByPk(storeDto.getAccountPk());
        List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(manageAccountDto.getRolePk());
        if (list != null && list.size() > 0) {
            if (manageAccountDto != null) {
                ManageAccount account = new ManageAccount();
                org.springframework.beans.BeanUtils.copyProperties(manageAccountDto, account);
                Map<String, String> chckMap = CommonUtil.checkColAuth(account, setDtoList);
                for (B2bOrderExtDto order : list) {
                    OrderExportUtil.setOrderJsonInfoCheckMap(order, chckMap, this.block,null);
                    if (Constants.BLOCK_CF.equals(this.block)) {
                        OrderExportUtil.setExportParams(order);
                    }
                    if (Constants.BLOCK_SX.equals(this.block)) {
                        OrderExportUtil.setExportSxParams(order);
                    }
                }
                chckMap.clear();
            }
        }
    }

    private void setColOrderParams(Map<String, Object> map,SysExcelStoreExtDto storeDto) {
        if (Constants.BLOCK_CF.equals(this.block)) {
            if (!CommonUtil.isExistAuthName(storeDto.getAccountPk(), ColAuthConstants.OPER_ORDERMG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.OPER_ORDERMG_COL_STORENAME);
            }
            if (!CommonUtil.isExistAuthName(storeDto.getAccountPk(), ColAuthConstants.OPER_ORDERMG_COL_MEMBERNAME)) {
                map.put("memberNameCol", ColAuthConstants.OPER_ORDERMG_COL_MEMBERNAME);
            }
        }
        if (Constants.BLOCK_SX.equals(this.block)) {
            if (!CommonUtil.isExistAuthName(storeDto.getAccountPk(), ColAuthConstants.YARN_ORDERMG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.YARN_ORDERMG_COL_STORENAME);
            }
            if (!CommonUtil.isExistAuthName(storeDto.getAccountPk(), ColAuthConstants.YARN_ORDERMG_COL_MEMBERNAME)) {
                map.put("memberNameCol", ColAuthConstants.YARN_ORDERMG_COL_MEMBERNAME);
            }
        }
    }

    private Map<String, Object> orderParams(OrderDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        if (params != null) {
            if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
                map.put("purchaserName", params.getPurchaserName());
            }
            if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
                map.put("orderNumber", params.getOrderNumber());
            }
            if (CommonUtil.isNotEmpty(params.getBlock())) {
                map.put("block", params.getBlock());
            }
            if (CommonUtil.isNotEmpty(params.getStoreName())) {
                map.put("storeName", params.getStoreName());
            }
            if (CommonUtil.isNotEmpty(params.getInsertStartTime())) {
                map.put("insertStartTime", params.getInsertStartTime());
            }
            if (CommonUtil.isNotEmpty(params.getInsertEndTime())) {
                map.put("insertEndTime", params.getInsertEndTime());
            }
            //TODO
            if (CommonUtil.isNotEmpty(params.getPaymentType())) {
                map.put("paymentType", params.getPaymentType());
            }
            if (CommonUtil.isNotEmpty(params.getOrderStatus())) {
                map.put("orderStatus", params.getOrderStatus());
            }
            if (CommonUtil.isNotEmpty(params.getSource())) {
                map.put("source", params.getSource());
            }
            if (CommonUtil.isNotEmpty(params.getPaymentStartTime())) {
                map.put("paymentStartTime", params.getPaymentStartTime());
            }
            if (CommonUtil.isNotEmpty(params.getPaymentEndTime())) {
                map.put("paymentEndTime", params.getPaymentEndTime());
            }
        }
        return map;
    }
}
