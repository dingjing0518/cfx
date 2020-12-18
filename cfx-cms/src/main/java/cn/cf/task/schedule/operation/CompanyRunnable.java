package cn.cf.task.schedule.operation;

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
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bCompanyExtDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class CompanyRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(CompanyRunnable.class);

    private String name;

    private String fileName;

    private String modelType = "";

    private String companyType = "";

    private Date insertTime;

    private String uuid;

    private SysExcelStoreExtDto storeDtoTemp;

    private SysExcelStoreExtDao storeDao;

    public CompanyRunnable() {
    }

    public CompanyRunnable(String name,String uuid) {
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

        B2bCompanyExtDao b2bCompanyExtDao = (B2bCompanyExtDao) BeanUtils.getBean("b2bCompanyExtDao");

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");

        if (storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.ONE,"exportB2bCompany_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    CustomerDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), CustomerDataTypeParams.class);
                        this.companyType = params.getCompanyType();
                        this.modelType = params.getModelType();
                        this.insertTime = storeDto.getInsertTime();
                        if (CommonUtil.isNotEmpty(this.companyType) && "2".equals(this.companyType)) {
                            if (CommonUtil.isNotEmpty(this.modelType) && "1".equals(this.modelType)) {
                                this.fileName = "运营中心-客户管理-供应商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                            }else{
                                this.fileName = "营销中心-客户管理-供应商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                            }
                        } else {
                            if (CommonUtil.isNotEmpty(this.modelType) && "1".equals(this.modelType)) {
                                this.fileName = "运营中心-客户管理-采购商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                            }else{
                                this.fileName = "营销中心-客户管理-采购商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                            }
                        }
                    }
                    if (b2bCompanyExtDao != null) {
                        //设置查询参数
                        Map<String, Object> companyMap = companyParams(params);
                        //判断不同公司类型，获取不同权限
                        int counts = 0;
                        if ("1".equals(this.companyType)) {
                            //不同模块下获取不同权限
                            if ("1".equals(params.getModelType())) {
                                setOperColAuthPurchaer(companyMap, storeDto.getAccountPk());
                            } else {
                                setMkColAuthPurchaer(companyMap, storeDto.getAccountPk());
                            }
                            counts = b2bCompanyExtDao.searchGridCountExt(companyMap);
                        } else {
                            if ("1".equals(params.getModelType())) {
                                setOperColAuthSupplier(companyMap, storeDto.getAccountPk());
                            } else {
                                setMkColAuthSupplier(companyMap, storeDto.getAccountPk());
                            }
                            counts = b2bCompanyExtDao.searchGridSupplierExtCounts(companyMap);
                        }
                        String ossPath = "";
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于10000，分页查询数据
                            ossPath = setLimitPages(companyMap, counts, b2bCompanyExtDao, storeDto);
                        } else {
                            //如果小于或等于10000条直接上传
                            ossPath = setNotLimitPages(b2bCompanyExtDao, companyMap);
                        }
                        //更新订单导出状态
                        ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
                    }
                }
            }
        }
    }

    private String setNotLimitPages(B2bCompanyExtDao b2bCompanyExtDao, Map<String, Object> companyMap) {
        String ossPath = "";
        List<B2bCompanyExtDto> companyList = null;
        if ("1".equals(this.companyType)) {
            companyList = b2bCompanyExtDao.searchGridExt(companyMap);
        } else {
            companyList = b2bCompanyExtDao.searchGridSupplierExt(companyMap);
        }
        if (companyList != null && companyList.size() > Constants.ZERO) {
            ExportUtil<B2bCompanyExtDto> exportUtil = new ExportUtil<>();
            String templateName = "";
            if ("2".equals(this.companyType)) {
                templateName = "supplier";
            } else {
                templateName = "purchaser";
            }
            String path = exportUtil.exportDynamicUtil(templateName, this.fileName, companyList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> companyMap, double counts, B2bCompanyExtDao b2bCompanyExtDao, SysExcelStoreExtDto storeDto) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        companyMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = (i * Constants.EXCEL_NUMBER_TENHOUSAND);
            }
            companyMap.put("start", start);
            List<B2bCompanyExtDto> companyList = null;
            //判断不同公司类型，获取不同数据
            if ("1".equals(this.companyType)) {
                companyList = b2bCompanyExtDao.searchGridExt(companyMap);
            } else {
                companyList = b2bCompanyExtDao.searchGridSupplierExt(companyMap);
            }
            companyMap.remove("start");
            if (companyList != null && companyList.size() > Constants.ZERO) {
                ExportUtil<B2bCompanyExtDto> exportUtil = new ExportUtil<>();
                String excelName = "";
                if (CommonUtil.isNotEmpty(this.companyType) && "2".equals(this.companyType)) {
                    if (CommonUtil.isNotEmpty(this.modelType) && "1".equals(this.modelType)){
                        excelName = "运营中心-客户管理-供应商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                    }else{
                        excelName = "营销中心-客户管理-供应商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                    }
                } else {
                    if (CommonUtil.isNotEmpty(this.modelType) && "1".equals(this.modelType)){
                        excelName = "运营中心-客户管理-采购商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                    }else{
                        excelName = "营销中心-客户管理-采购商管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                    }
                }
                String templateName = "";
                if ("2".equals(this.companyType)) {
                    templateName = "supplier";
                } else {
                    templateName = "purchaser";
                }
                String path = exportUtil.exportDynamicUtil(templateName, excelName, companyList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }


    private Map<String, Object> companyParams(CustomerDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (params != null) {
            map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
            map.put("companyType", params.getCompanyType());
            map.put("auditStatus", params.getAuditStatus());
            map.put("auditSpStatus", params.getAuditSpStatus());
            map.put("parentPk", params.getParentPk());
            map.put("organizationCode", params.getOrganizationCode());
            map.put("name", params.getName());
            map.put("insertStartTime", params.getInsertStartTime());
            map.put("insertEndTime", params.getInsertEndTime());
            map.put("updateStartTime", params.getUpdateStartTime());
            map.put("updateEndTime", params.getUpdateEndTime());
            map.put("auditStartTime", params.getAuditStartTime());
            map.put("auditEndTime", params.getAuditEndTime());
            map.put("isVisable", params.getIsVisable());
            map.put("mobile", params.getMobile());
            map.put("contactsTel", params.getContactsTel());
            map.put("province", params.getProvinceName());
            map.put("city", params.getCityName());
            map.put("area", params.getAreaName());
        }
        return map;
    }

    /**
     * 运营中心-采购商管理
     *
     * @param map
     * @param accountPk
     */
    private void setOperColAuthPurchaer(Map<String, Object> map, String accountPk) {
        if (CommonUtil.isNotEmpty(accountPk)) {
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_TEL)) {
                map.put("contactsTelCol", ColAuthConstants.OPER_CM_PURCHASER_COL_TEL);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_MOBILE)) {
                map.put("mobileCol", ColAuthConstants.OPER_CM_PURCHASER_COL_MOBILE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_CONTACTS)) {
                map.put("contactsCol", ColAuthConstants.OPER_CM_PURCHASER_COL_CONTACTS);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.OPER_CM_PURCHASER_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_REGION)) {
                map.put("regionCol", ColAuthConstants.OPER_CM_PURCHASER_COL_REGION);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_ORGCODE)) {
                map.put("orgCodeCol", ColAuthConstants.OPER_CM_PURCHASER_COL_ORGCODE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_BUSSLICENSE)) {
                map.put("bussLicenseCol", ColAuthConstants.OPER_CM_PURCHASER_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 营销中心-采购商管理
     *
     * @param map
     * @param accountPk
     */
    private void setMkColAuthPurchaer(Map<String, Object> map, String accountPk) {
        if (CommonUtil.isNotEmpty(accountPk)) {
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_TEL)) {
                map.put("contactsTelCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_TEL);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_MOBILE)) {
                map.put("mobileCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_MOBILE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_CONTACTS)) {
                map.put("contactsCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_CONTACTS);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_REGION)) {
                map.put("regionCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_REGION);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_ORGCODE)) {
                map.put("orgCodeCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_ORGCODE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_BUSSLICENSE)) {
                map.put("bussLicenseCol", ColAuthConstants.MARKET_CM_PURCHASER_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 运营中心-供应商管理
     *
     * @param map
     * @param accountPk
     */
    private void setOperColAuthSupplier(Map<String, Object> map, String accountPk) {
        if (CommonUtil.isNotEmpty(accountPk)) {
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_TEL)) {
                map.put("contactsTelCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_TEL);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_MOBILE)) {
                map.put("mobileCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_MOBILE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_CONTACTS)) {
                map.put("contactsCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_CONTACTS);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_REGION)) {
                map.put("regionCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_REGION);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_ORGCODE)) {
                map.put("orgCodeCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_ORGCODE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_BUSSLICENSE)) {
                map.put("bussLicenseCol", ColAuthConstants.OPER_CM_SUPPLIER_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 营销中心-供应商管理
     *
     * @param map
     * @param accountPk
     */
    private void setMkColAuthSupplier(Map<String, Object> map, String accountPk) {
        if (CommonUtil.isNotEmpty(accountPk)) {
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_TEL)) {
                map.put("contactsTelCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_TEL);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_MOBILE)) {
                map.put("mobileCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_MOBILE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_CONTACTS)) {
                map.put("contactsCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_CONTACTS);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_REGION)) {
                map.put("regionCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_REGION);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_ORGCODE)) {
                map.put("orgCodeCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_ORGCODE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_BUSSLICENSE)) {
                map.put("bussLicenseCol", ColAuthConstants.MARKET_CM_SUPPLIER_COL_BUSSLICENSE);
            }
        }
    }
}
