package cn.cf.task.schedule.chemifiber;

import cn.cf.common.*;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.model.ManageAccount;
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

public class OrderRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(OrderRunnable.class);
    private String name;

    private String fileName;

    private String block;

    private String accountPk;

    private String rolePk;

    private Date insertTime;
    
    private String economicsGoodsType;

    private String uuid;
    private SysExcelStoreExtDto storeDtoTemp;

    private B2bOrderExtDao orderDao;

    private SysExcelStoreExtDao storeDao;

    private ManageAuthorityExtDao manageAuthorityExtDao;

    public OrderRunnable() {
    }


    public OrderRunnable(String name,String uuid) {
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

        this.orderDao = (B2bOrderExtDao) BeanUtils.getBean("b2bOrderExtDao");
        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
        this.manageAuthorityExtDao = (ManageAuthorityExtDao) BeanUtils.getBean("manageAuthorityExtDao");
        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.TWO,"exportOrder_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    String ossPath = "";
                    OrderDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), OrderDataTypeParams.class);
                        this.block = params.getBlock();
                        this.accountPk = storeDto.getAccountPk();
                        this.rolePk = storeDto.getRolePk();
                        this.insertTime = storeDto.getInsertTime();
                        this.economicsGoodsType = params.getEconomicsGoodsType();
                        if (economicsGoodsType==null) {//化纤中心,纱线中心的订单导出
	                        if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)){
	                            this.fileName = "化纤中心-订单管理-订单导出-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
	                        }else{
	                            this.fileName = "纱线中心-订单管理-订单导出-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
	                        }
                        }else if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)
                        		&&economicsGoodsType.equals("2")) {//财务中心-订单管理-导出
                            this.fileName = "财务中心-订单管理-订单导出-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());

						}
                    }
                    if (this.orderDao != null) {
                        //设置查询参数
                        Map<String, Object> orderMap = orderParams(params);
                        //设置权限
                        setColOrderParams(orderMap);
                        int counts = this.orderDao.searchGridExtCount(orderMap);
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于5000，分页查询数据
                            ossPath = setLimitPages(orderMap, counts,storeDto);
                        } else {
                            //如果小于或等于5000条直接上传
                            ossPath = setNotLimitPages(orderMap,storeDto);
                        }
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }

    private String setNotLimitPages(Map<String, Object> orderMap,SysExcelStoreExtDto storeExtDto) {
        String ossPath = "";
        ManageAccount account = new ManageAccount();
        account.setRolePk(this.rolePk);
        LinkedList<B2bOrderExtDto> orderlist = this.orderDao.exportOrderList(orderMap);
        if (orderlist != null && orderlist.size() > Constants.ZERO) {
            ExportUtil<B2bOrderExtDto> exportUtil = new ExportUtil<>();
            String templateName = "";
            if (Constants.BLOCK_CF.equals(this.block)){
                templateName = "order";
            }else{
                templateName = "yarnOrder";
            }
            //设置权限
            List<ManageAuthorityDto> setDtoList = this.manageAuthorityExtDao.getColManageAuthorityByRolePk(this.rolePk);
            Map<String,String> chckMap = CommonUtil.checkColAuth(account,setDtoList);
            for (B2bOrderExtDto order:orderlist) {
                OrderExportUtil.setOrderJsonInfoCheckMap(order,chckMap,this.block,this.economicsGoodsType);
            }
            chckMap.clear();
            String path = exportUtil.exportDynamicUtil(templateName,this.fileName, orderlist);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts,SysExcelStoreExtDto storeDto) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        ManageAccount account = new ManageAccount();
        account.setRolePk(this.rolePk);
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            orderMap.put("start", start);
            LinkedList<B2bOrderExtDto> orderlist = this.orderDao.exportOrderList(orderMap);
            orderMap.remove("start");
            if (orderlist != null && orderlist.size() > Constants.ZERO) {
                //设置权限
                List<ManageAuthorityDto> setDtoList = this.manageAuthorityExtDao.getColManageAuthorityByRolePk(this.rolePk);
                Map<String,String> chckMap = CommonUtil.checkColAuth(account,setDtoList);
                for (B2bOrderExtDto order:orderlist) {
                    OrderExportUtil.setOrderJsonInfoCheckMap(order,chckMap,this.block,this.economicsGoodsType);
                }
                chckMap.clear();
                ExportUtil<B2bOrderExtDto> exportUtil = new ExportUtil<>();
                String excelName = "";
                if (economicsGoodsType==null) {//化纤中心,纱线中心的订单导出
	                if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)){
	                    excelName = "化纤中心-订单管理-订单导出-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
	                }else{
	                    excelName = "纱线中心-订单管理-订单导出-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
	                }
                }else if (CommonUtil.isNotEmpty(this.block) && Constants.BLOCK_CF.equals(this.block)
                		&&economicsGoodsType.equals("2")){
                    excelName = "财务中心-订单管理-订单导出-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                }
                String templateName = "";
                if (Constants.BLOCK_CF.equals(this.block)){
                    templateName = "order";
                }else{
                    templateName = "yarnOrder";
                }
                String path = exportUtil.exportDynamicUtil(templateName,excelName, orderlist);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    private void setColOrderParams( Map<String, Object> orderMap){
        if(CommonUtil.isNotEmpty(this.accountPk)){
        	if (this.economicsGoodsType==null) {
        		 if (Constants.BLOCK_CF.equals(this.block)) {
                     if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.OPER_ORDERMG_COL_STORENAME)) {
                         orderMap.put("storeNameCol", ColAuthConstants.OPER_ORDERMG_COL_STORENAME);
                     }
                     if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.OPER_ORDERMG_COL_MEMBERNAME)) {
                         orderMap.put("memberNameCol", ColAuthConstants.OPER_ORDERMG_COL_MEMBERNAME);
                     }
                 }
                 if (Constants.BLOCK_SX.equals(block)) {
                     if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.YARN_ORDERMG_COL_STORENAME)) {
                         orderMap.put("storeNameCol", ColAuthConstants.YARN_ORDERMG_COL_STORENAME);
                     }
                     if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.YARN_ORDERMG_COL_MEMBERNAME)) {
                         orderMap.put("memberNameCol", ColAuthConstants.YARN_ORDERMG_COL_MEMBERNAME);
                     }
                 }
			}else if (this.economicsGoodsType.equals("2")&&Constants.BLOCK_CF.equals(this.block)) {
				 if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.FC_ODER_MGR_COL_STORENAME)) {
                     orderMap.put("storeNameCol", ColAuthConstants.FC_ODER_MGR_COL_STORENAME);
                 }
                 if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.FC_ODER_MGR_COL_MEMBERNAME)) {
                     orderMap.put("memberNameCol", ColAuthConstants.FC_ODER_MGR_COL_MEMBERNAME);
                 }
			}
        }
    }

    private Map<String, Object> orderParams(OrderDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        if (params != null) {
            map.put("orderNumber", params.getOrderNumber());
            map.put("purchaserName", params.getPurchaserName());
            map.put("block", params.getBlock());
            map.put("purchaseType", params.getPurchaseType());

            map.put("supplierName", params.getSupplierName());
            map.put("storeName", params.getStoreName());
            map.put("insertStartTime", params.getInsertStartTime());
            map.put("insertEndTime", params.getInsertEndTime());
            //TODO
            map.put("paymentType", params.getPaymentType());
            map.put("paymentName", params.getPaymentName());
            map.put("orderStatus", params.getOrderStatus());
            map.put("memberPk", params.getMemberPk());
            map.put("source", params.getSource());
            map.put("paymentStartTime", params.getPaymentStartTime());
            map.put("paymentEndTime", params.getPaymentEndTime());
            map.put("economicsGoodsType", params.getEconomicsGoodsType());
        }
        return map;
    }
}
