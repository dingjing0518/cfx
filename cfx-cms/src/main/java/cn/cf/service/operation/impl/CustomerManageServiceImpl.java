package cn.cf.service.operation.impl;

import cn.cf.PageModel;
import cn.cf.common.*;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.constant.MemberSys;
import cn.cf.constant.SmsCode;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.entity.B2bInvoiceEntity;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.SysBankNameCode;
import cn.cf.json.JsonUtils;
import cn.cf.model.*;
import cn.cf.service.CuccSmsService;
import cn.cf.service.operation.CustomerManageService;
import cn.cf.util.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class CustomerManageServiceImpl implements CustomerManageService {

    private final static Logger logger = LoggerFactory.getLogger(CustomerManageServiceImpl.class);
    @Autowired
    private B2bCompanyExtDao b2bCompayExtDao;

    @Autowired
    private B2bTokenDao b2bTokenDao;

    @Autowired
    private B2bMemberExtDao b2bMemberExtDao;

    @Autowired
    private B2bStoreExtDao b2bStoreExtDao;

    @Autowired
    private B2bRoleExtDao b2bRoleExtDao;

    @Autowired
    private B2bCreditDao b2bCreditDao;

    @Autowired
    private SysCompanyBankcardExtDao sysCompanyBankcardExtDao;

    @Autowired
    private SysBankNamecodeExtDao sysBankNamecodeExtDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AddMemberSysPoint addMemberSysPoint;

    @Autowired
    private B2bInvitationRecordExtDao b2bInvitationRecordExtDao;

    @Autowired
    private CuccSmsService sysService;

    @Autowired
    private SysExcelStoreExtDao sysExcelStoreExtDao;

    @Autowired
    private SysSmsTemplateDao sysSmsTemplateDao;

    @Override
    public PageModel<B2bCompanyExtDto> searchCompanyList(
            QueryModel<B2bCompanyExtDto> qm,ManageAccount account) {
        PageModel<B2bCompanyExtDto> pm = new PageModel<B2bCompanyExtDto>();
        Map<String, Object> map = companyListParams(qm);

        int totalCount = b2bCompayExtDao.searchGridCountExt(map);
        List<B2bCompanyExtDto> list = new ArrayList<>();
        //采购商和供应商权限分别控制，所以查询时分别查询
        String modelType = qm.getEntity().getModelType();
        if (qm.getEntity().getCompanyType() == Constants.ONE){

            if ("1".equals(modelType)){
                setOperColAuthPurchaer(map,account);
            } else {
                setMkColAuthPurchaer(map,account);
            }
            list = b2bCompayExtDao.searchGridExt(map);
        }else{
            if ("1".equals(modelType)){
                setOperColAuthSupplier(map,account);
            } else {
                setMkColAuthSupplier(map,account);
            }
            list = b2bCompayExtDao.searchGridSupplierExt(map);
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public PageModel<B2bCompanyExtDto> searchCompanySubList(QueryModel<B2bCompanyExtDto> qm, ManageAccount account) {
        PageModel<B2bCompanyExtDto> pm = new PageModel<B2bCompanyExtDto>();
        Map<String, Object> map = companyListParams(qm);

        int totalCount = b2bCompayExtDao.searchGridCountExt(map);

        String modelType = qm.getEntity().getModelType();
        if("1".equals(modelType)){
            setOperColAuthSupplierSub(map , account);
        }else{
            setMkColAuthSupplierSub( map , account);
        }

        List<B2bCompanyExtDto> list = b2bCompayExtDao.searchGridSupplierSubExt(map);

        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    /**
     * 运营中心-采购商管理
     * @param map
     * @param account
     */
    private void setOperColAuthPurchaer(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_TEL)){
                map.put("contactsTelCol",ColAuthConstants.OPER_CM_PURCHASER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_MOBILE)){
                map.put("mobileCol",ColAuthConstants.OPER_CM_PURCHASER_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_CONTACTS)){
                map.put("contactsCol",ColAuthConstants.OPER_CM_PURCHASER_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.OPER_CM_PURCHASER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_REGION)){
                map.put("regionCol",ColAuthConstants.OPER_CM_PURCHASER_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_ORGCODE)){
                map.put("orgCodeCol",ColAuthConstants.OPER_CM_PURCHASER_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCHASER_COL_BUSSLICENSE)){
                map.put("bussLicenseCol",ColAuthConstants.OPER_CM_PURCHASER_COL_BUSSLICENSE);
            }
        }
    }
    /**
     * 营销中心-采购商管理
     * @param map
     * @param account
     */
    private void setMkColAuthPurchaer(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_TEL)){
                map.put("contactsTelCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_MOBILE)){
                map.put("mobileCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_CONTACTS)){
                map.put("contactsCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_REGION)){
                map.put("regionCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_ORGCODE)){
                map.put("orgCodeCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCHASER_COL_BUSSLICENSE)){
                map.put("bussLicenseCol",ColAuthConstants.MARKET_CM_PURCHASER_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 运营中心-供应商管理
     * @param map
     * @param account
     */
    private void setOperColAuthSupplier(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_TEL)){
                map.put("contactsTelCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_MOBILE)){
                map.put("mobileCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_CONTACTS)){
                map.put("contactsCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_REGION)){
                map.put("regionCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_ORGCODE)){
                map.put("orgCodeCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPPLIER_COL_BUSSLICENSE)){
                map.put("bussLicenseCol",ColAuthConstants.OPER_CM_SUPPLIER_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 营销中心-供应商管理
     * @param map
     * @param account
     */
    private void setMkColAuthSupplier(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_TEL)){
                map.put("contactsTelCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_MOBILE)){
                map.put("mobileCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_CONTACTS)){
                map.put("contactsCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_REGION)){
                map.put("regionCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_ORGCODE)){
                map.put("orgCodeCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPPLIER_COL_BUSSLICENSE)){
                map.put("bussLicenseCol",ColAuthConstants.MARKET_CM_SUPPLIER_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 运营中心-采购商公司管理子类表页面
     * @param map
     * @param account
     */
    private void setOperColAuthPurchaerMg(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_TEL)){
                map.put("contactsTelSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_MOBILE)){
                map.put("mobileSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_CONTACTS)){
                map.put("contactsSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_COMPANYNAME)){
                map.put("companyNameSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_REGION)){
                map.put("regionSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_ORGCODE)){
                map.put("orgCodeSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_PURCH_MG_COL_BUSSLICENSE)){
                map.put("bussLicenseSubCol",ColAuthConstants.OPER_CM_PURCH_MG_COL_BUSSLICENSE);
            }
        }
    }


    /**
     * 营销中心-采购商公司管理子类表页面
     * @param map
     * @param account
     */
    private void setMkColAuthPurchaerMg(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_TEL)){
                map.put("contactsTelSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_MOBILE)){
                map.put("mobileSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_CONTACTS)){
                map.put("contactsSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_COMPANYNAME)){
                map.put("companyNameSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_REGION)){
                map.put("regionSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_ORGCODE)){
                map.put("orgCodeSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_PURCH_MG_COL_BUSSLICENSE)){
                map.put("bussLicenseSubCol",ColAuthConstants.MARKET_CM_PURCH_MG_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 运营中心-供应商子公司
     * @param map
     * @param account
     */
    private void setOperColAuthSupplierSub(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_TEL)){
                map.put("contactsTelSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_MOBILE)){
                map.put("mobileSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_CONTACTS)){
                map.put("contactsSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_COMPANYNAME)){
                map.put("companyNameSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_REGION)){
                map.put("regionSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_ORGCODE)){
                map.put("orgCodeSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_SUPP_SUB_COL_BUSSLICENSE)){
                map.put("bussLicenseSubCol",ColAuthConstants.OPER_CM_SUPP_SUB_COL_BUSSLICENSE);
            }
        }
    }

    /**
     * 营销中心-供应商子公司
     * @param map
     * @param account
     */
    private void setMkColAuthSupplierSub(Map<String, Object> map,ManageAccount account){
        if(account != null){
            String accountPk = account.getPk();
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_TEL)){
                map.put("contactsTelSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_MOBILE)){
                map.put("mobileSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_MOBILE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_CONTACTS)){
                map.put("contactsSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_CONTACTS);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_COMPANYNAME)){
                map.put("companyNameSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_REGION)){
                map.put("regionSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_REGION);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_ORGCODE)){
                map.put("orgCodeSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_ORGCODE);
            }
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_SUPP_SUB_COL_BUSSLICENSE)){
                map.put("bussLicenseSubCol",ColAuthConstants.MARKET_CM_SUPP_SUB_COL_BUSSLICENSE);
            }
        }
    }

    private Map<String, Object> companyListParams(
            QueryModel<B2bCompanyExtDto> qm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("companyType", qm.getEntity().getCompanyType());
        map.put("auditStatus", qm.getEntity().getAuditStatus());
        map.put("auditSpStatus", qm.getEntity().getAuditSpStatus());
        map.put("organizationCode", qm.getEntity().getOrganizationCode());
        map.put("name", qm.getEntity().getName());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
        map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
        map.put("auditStartTime", qm.getEntity().getAuditStartTime());
        map.put("auditEndTime", qm.getEntity().getAuditEndTime());
        map.put("auditSpStartTime", qm.getEntity().getAuditSpStartTime());
        map.put("auditSpEndTime", qm.getEntity().getAuditSpEndTime());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("isDelete", qm.getEntity().getIsDelete());
        map.put("parentPk", qm.getEntity().getParentPk());
        map.put("contactsTel", qm.getEntity().getContactsTel());
        map.put("isPerfect", qm.getEntity().getIsPerfect());
        map.put("mobile", qm.getEntity().getMobile());
        map.put("province", qm.getEntity().getProvinceName());
        map.put("city", qm.getEntity().getCityName());
        map.put("area", qm.getEntity().getAreaName());

        return map;
    }

    @Override
    public PageModel<B2bCompanyExtDto> searchCompanyManageList(
            QueryModel<B2bCompanyExtDto> qm,ManageAccount account) {
        PageModel<B2bCompanyExtDto> pm = new PageModel<B2bCompanyExtDto>();
        Map<String, Object> map = companyListParams(qm);
        map.put("parentPk", qm.getEntity().getParentPk());
        map.put("pk", qm.getEntity().getParentPk());
        B2bCompanyDto company = b2bCompayExtDao.getByPk(qm.getEntity()
                .getParentPk());
        // 判断当总公司审核不通过时不显示子公司
        if (company.getAuditStatus() == 1 || company.getAuditStatus() == 3) {
            map.put("isShowSubCompany", company.getAuditStatus());
            map.put("start", 0);
        }
        int totalCount = b2bCompayExtDao.searchPurchaserCompanyCount(map);
        String modelType = qm.getEntity().getModelType();
        //判断某个模块
        if ("1".equals(modelType)){
            //采购商公司管理包含总公司包含在子公司子页面列表中
            setOperColAuthPurchaerMg(map,account);
            setOperColAuthPurchaer(map,account);
        } else{
            //采购商公司管理包含总公司包含在子公司子页面列表中
            setMkColAuthPurchaer(map,account);
            setMkColAuthPurchaerMg(map,account);
        }

        List<B2bCompanyExtDto> list = b2bCompayExtDao
                .searchPurchaserCompanyList(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public String getCreditByCompanyPk(String companyPk) {
        int result = 0;
        String json = "";
        B2bCompanyDto company = b2bCompayExtDao.getByPk(companyPk);
        SysCompanyBankcardDto cardDto =sysCompanyBankcardExtDao.getByCompanyPk(companyPk);
        if(null!=cardDto){

            json = JsonUtils.convertToString(cardDto);

        }else{
            SysCompanyBankcardDto cardDto1 = new SysCompanyBankcardDto();
            cardDto1.setCompanyPk(company.getPk());
            json = JsonUtils.convertToString(cardDto1);
        }
        return json;
    }

    private int insertCredit(B2bCreditDto creditDto) {
        B2bCredit credit = new B2bCredit();
        credit.setPk(creditDto.getPk());
        credit.setCompanyPk(creditDto.getCompanyPk());
        credit.setCompanyName(creditDto.getCompanyName());
        credit.setIsDelete(1);
        credit.setVirtualPayPassword(creditDto.getVirtualPayPassword());
        return b2bCreditDao.insert(credit);
    }

    @Override
    public PageModel<SysCompanyBankcardDtoEx> getBankCard(QueryModel<SysCompanyBankcardDtoEx> qm,ManageAccount account) {
        PageModel<SysCompanyBankcardDtoEx> pm = new PageModel<SysCompanyBankcardDtoEx>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", 1000);
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("companyPk", qm.getEntity().getCompanyPk());
        int counts = sysCompanyBankcardExtDao.searchGridCount(map);
        String type = qm.getEntity().getIsCompanyType();

        String modelType = qm.getEntity().getModelType();
        if("1".equals(modelType)){
            setOperBankparams(account, map, type);
        }
        if("2".equals(modelType)){
            setMkBankparams(account, map, type);
        }
        List<SysCompanyBankcardDtoEx> list = sysCompanyBankcardExtDao.searchGridEx(map);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        return pm;
    }

    /**
     * 运营中心账户银行列权限设置
     * @param account
     * @param map
     * @param type
     */
    private void setOperBankparams(ManageAccount account, Map<String, Object> map, String type) {
        if("1".equals(type)) {
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_BANKNUM)) {
                map.put("bankAccountCol", ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_BANKNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_ACCNAME)) {
                map.put("bankNameCol", ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_ACCNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_ACCNUM)) {
                map.put("bankNoCol", ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_ACCNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_BANKNAME)) {
                map.put("ecbankNameCol", ColAuthConstants.OPER_CM_SUPPLIER_ACCOUNT_COL_BANKNAME);
            }
        }else {

            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_BANKNUM)) {
                map.put("bankAccountCol", ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_BANKNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_ACCNAME)) {
                map.put("bankNameCol", ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_ACCNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_ACCNUM)) {
                map.put("bankNoCol", ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_ACCNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_BANKNAME)) {
                map.put("ecbankNameCol", ColAuthConstants.OPER_CM_SUPPLIER_SUBACCOUNT_COL_BANKNAME);
            }
        }
    }

    /**
     * 营销中心账户银行列权限设置
     * @param account
     * @param map
     * @param type
     */
    private void setMkBankparams(ManageAccount account, Map<String, Object> map, String type) {
        if("1".equals(type)) {
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_BANKNUM)) {
                map.put("bankAccountCol", ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_BANKNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_ACCNAME)) {
                map.put("bankNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_ACCNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_ACCNUM)) {
                map.put("bankNoCol", ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_ACCNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_BANKNAME)) {
                map.put("ecbankNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_ACCOUNT_COL_BANKNAME);
            }
        }else {

            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_COMPANYNAME)) {
                map.put("companyNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_COMPANYNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_BANKNUM)) {
                map.put("bankAccountCol", ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_BANKNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_ACCNAME)) {
                map.put("bankNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_ACCNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_ACCNUM)) {
                map.put("bankNoCol", ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_ACCNUM);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_BANKNAME)) {
                map.put("ecbankNameCol", ColAuthConstants.MARKET_CM_SUPPLIER_SUBACCOUNT_COL_BANKNAME);
            }
        }
    }


    @Override
    public List<SysBankNamecodeExtDto> getSysBankNameCode(
            SysBankNamecodeExtDto dto) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bankno", dto.getBankno());
        map.put("bankname", dto.getBankname());
        return sysBankNamecodeExtDao.getSysBankNameCodeExt(map);
    }

    @Override
    public SysBankNamecodeDto getSysBankName(String bankname) {
        return sysBankNamecodeExtDao.getByBankname(bankname);
    }

    @Override
    public int getAllSysBankNameCode() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysBankNamecodeDto> list = sysBankNamecodeExtDao.searchList(map);
        int result = 0;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        if (list != null && list.size() > 0) {
            mongoTemplate.dropCollection(SysBankNamecode.class);
            for (SysBankNamecodeDto sysBankNameCodeDto : list) {
                SysBankNameCode nameCode = new SysBankNameCode();
                nameCode.setId(KeyUtils.getUUID());
                nameCode.setBankname(sysBankNameCodeDto.getBankname());
                nameCode.setBankno(sysBankNameCodeDto.getBankno());
                nameCode.setInsertTime(date);
                try {
                    mongoTemplate.save(nameCode);
                    result = 1;
                } catch (Exception e) {
                    result = 0;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> updateCompanyBankCard(SysCompanyBankcardDto dto) {
        int result = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        if (dto.getPk() != null && !"".equals(dto.getPk())) {
            result = sysCompanyBankcardExtDao.updateSysCompanyBankcard(dto);
            map.put("pk", dto.getPk());
        } else {
            SysCompanyBankcard bankcard = new SysCompanyBankcard();
            bankcard.setBankAccount(dto.getBankAccount());
            bankcard.setBankName(dto.getBankName());
            bankcard.setBankNo(dto.getBankNo());
            bankcard.setPk(KeyUtils.getUUID());
            //TODO
            //字段已删除
            //bankcard.setStatus(Constants.ONE);
            bankcard.setCompanyPk(dto.getCompanyPk());
            bankcard.setEcbankPk(dto.getEcbankPk());
            bankcard.setEcbankName(dto.getEcbankName());
            bankcard.setBankclscode(dto.getBankclscode());
            result = sysCompanyBankcardExtDao.insert(bankcard);
            if (result > 0) {
                map.put("pk", bankcard.getPk());
            }
        }
        map.put("result", result);
        return map;
    }

    @Override
    public List<B2bCompanyExtDto> exportB2bCompany(
            QueryModel<B2bCompanyExtDto> qm,ManageAccount account) {
        Map<String, Object> map = companyListParams(qm);
        map.put("start", null);
        map.put("limit", null);
        map.put("orderName", null);
        map.put("orderType", null);
        String modelType = qm.getEntity().getModelType();
        //判断不同公司类型，获取不同权限
        if (qm.getEntity().getCompanyType() == Constants.ONE){
            //不同模块下获取不同权限
            if ("1".equals(modelType)){
                setOperColAuthPurchaer(map,account);
            }else{
                setMkColAuthPurchaer(map,account);
            }
            return b2bCompayExtDao.searchGridExt(map);
        }else{
            if ("1".equals(modelType)){
                setOperColAuthSupplier(map,account);
            }else{
                setMkColAuthSupplier(map,account);
            }

            return b2bCompayExtDao.searchGridSupplierExt(map);
        }
    }

    @Override
    public int saveCompanyExcelToOss(CustomerDataTypeParams params, ManageAccount account) {
        Date time = new Date();
        String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
        try {
            Map<String, String> map = CommonUtil.checkExportTime(params.getInsertStartTime(), params.getInsertEndTime(), timeStr);
            params.setInsertStartTime(map.get("startTime"));
            params.setInsertEndTime(map.get("endTime"));

            Map<String, String> upMap = CommonUtil.checkExportTime(params.getUpdateStartTime(), params.getUpdateEndTime(), timeStr);
            params.setUpdateStartTime(upMap.get("startTime"));
            params.setUpdateEndTime(upMap.get("endTime"));

            Map<String, String> auditMap = CommonUtil.checkExportTime(params.getAuditStartTime(), params.getAuditEndTime(), timeStr);
            params.setAuditStartTime(auditMap.get("startTime"));
            params.setAuditEndTime(auditMap.get("endTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtils.convertToString(params);
        SysExcelStore store = new SysExcelStore();
        store.setPk(KeyUtils.getUUID());
        store.setMethodName("exportB2bCompany_"+params.getUuid());
        store.setParamsName(ExportDoJsonParams.doCustomerRunnableParams(params,time));
        store.setParams(json);
        store.setIsDeal(Constants.TWO);
        store.setInsertTime(time);
        if (CommonUtil.isNotEmpty(params.getCompanyType()) && "2".equals(params.getCompanyType())) {
            if (CommonUtil.isNotEmpty(params.getModelType()) && "1".equals(params.getModelType())){
                store.setName("运营中心-客户管理-供应商管理");
            }else{
                store.setName("营销中心-客户管理-供应商管理");
            }
        }else{

            if (CommonUtil.isNotEmpty(params.getModelType()) && "1".equals(params.getModelType())){
                store.setName("运营中心-客户管理-采购商管理");
            }else{
                store.setName("营销中心-客户管理-采购商管理");
            }
        }
        store.setType(Constants.ONE);
        store.setAccountPk(account.getPk());
        return sysExcelStoreExtDao.insert(store);
    }

    @Override
    public int saveMemberExcelToOss(CustomerDataTypeParams params, ManageAccount account) {
        Date time = new Date();
        String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
        try {
            Map<String, String> map = CommonUtil.checkExportTime(params.getInsertStartTime(), params.getInsertEndTime(), timeStr);
            params.setInsertStartTime(map.get("startTime"));
            params.setInsertEndTime(map.get("endTime"));

            Map<String, String> auditMap = CommonUtil.checkExportTime(params.getAuditStartTime(), params.getAuditEndTime(), timeStr);
            params.setAuditStartTime(auditMap.get("startTime"));
            params.setAuditEndTime(auditMap.get("endTime"));

            Map<String, String> feedMap = CommonUtil.checkExportTime(params.getFeedStartTime(), params.getFeedEndTime(), timeStr);
            params.setFeedStartTime(feedMap.get("startTime"));
            params.setFeedEndTime(feedMap.get("endTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtils.convertToString(params);
        SysExcelStore store = new SysExcelStore();
        store.setPk(KeyUtils.getUUID());
        store.setMethodName("exportMembersList_"+params.getUuid());
        store.setParamsName(ExportDoJsonParams.doCustomerRunnableParams(params,time));
        store.setParams(json);
        store.setIsDeal(Constants.TWO);
        store.setInsertTime(time);
        if (CommonUtil.isNotEmpty(params.getModelType()) && "1".equals(params.getModelType())) {
            store.setName("营销中心-客户管理-会员管理");
        }else{
            store.setName("运营中心-客户管理-会员管理");
        }
        store.setType(Constants.ONE);
        store.setAccountPk(account.getPk());
        return sysExcelStoreExtDao.insert(store);
    }

    @Override
    public List<B2bCompanyExtDto> getB2bCompayDtoByType(Integer companyType,
                                                        String parentPk) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (companyType == 1) {
            map.put("auditStatus", 2);
        }
        if (companyType == 2) {
            map.put("auditSpStatus", 2);
        }
        map.put("isVisable", 1);
        map.put("parentPk", parentPk);
        return b2bCompayExtDao.searchGridExt(map);
    }


    @Override
    public List<B2bStoreExtDto> getB2bStore() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisable", 1);
        map.put("isDelete", 1);

        return b2bStoreExtDao.searchGridExt(map);
    }

    @Override
    public PageModel<B2bTokenDto> searchB2bToken(QueryModel<B2bTokenDto> qm) {

        PageModel<B2bTokenDto> pm = new PageModel<B2bTokenDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        //TODO
        map.put("storeName", qm.getEntity().getStoreName());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("isDelete", 1);
        List<B2bTokenDto> list = b2bTokenDao.searchGrid(map);
        int count = b2bTokenDao.searchGridCount(map);
        pm.setDataList(list);
        pm.setTotalCount(count);
        return pm;
    }

    @Override
    public String isExistOrgCode(B2bCompanyExtDto companyExtDto, boolean isAdd) {
        // 审核时判断营业执照号是否和其他公司重复
        String msg = "";
        if (companyExtDto.getOrganizationCode() != null
                && companyExtDto.getOrganizationCode() != "") {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isVisable", 1);
            map.put("isDelete", 1);
            map.put("pk", companyExtDto.getPk());
            map.put("organizationCode", companyExtDto.getOrganizationCode()
                    .trim());
            List<B2bCompanyExtDto> lst = b2bCompayExtDao.isExistOrganizationCode(map);
            if (isAdd) {
                if (null != lst && lst.size() > 0) {
                    msg = Constants.OrganizationCodeMsg;
                }

            } else {
                if (null != lst) {
                    if (lst.size() > 1) {
                        msg = Constants.OrganizationCodeMsg;
                    } else if (lst.size() == 1) {
                        if (!lst.get(0).getPk().equals(companyExtDto.getPk())) {
                            msg = Constants.OrganizationCodeMsg;
                        }
                    }

                }
            }

        }
        return msg;
    }

    @Override
    public String updateB2bCompany(B2bCompanyExtDto companyExtDto,
                                   ManageAccount adto) {
        int result = 0;
        String msg = Constants.RESULT_SUCCESS_MSG;
        // 如果是审核公司
        if ("1".equals(companyExtDto.getIsAudit())) {
            //判断子公司是已审核通过不可再次审核
            B2bCompanyDto company = b2bCompayExtDao.getByPk(companyExtDto.getPk());
            if ((!company.getParentPk().equals("-1"))
                    &&((companyExtDto.getAuditSpStatus()!=null
                    &&companyExtDto.getAuditSpStatus()==2
                    &&company.getAuditSpStatus()==2)
                    ||(companyExtDto.getAuditStatus()!=null
                    &&companyExtDto.getAuditStatus()==2
                    &&company.getAuditStatus()==2))) {
                result=1;
            }else {
                result = auditCompany(companyExtDto, result, adto);
            }
            try {
                //如果有邀请记录的把邀请公司Pk记录
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("companyPk", companyExtDto.getPk());
                map.put("companyName", companyExtDto.getName());
                b2bInvitationRecordExtDao.updatePkByCompanyName(map);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
                Map<String, String> smsMap = new HashMap<>();
                B2bCompanyDto b2bcompany = b2bCompayExtDao.getByPk(companyExtDto.getPk());
                if (companyExtDto.getAuditStatus() != null
                        && companyExtDto.getAuditStatus() == 2) {
                    sendMsgToUser(SmsCode.PASS_MEMBER.getValue(),smsMap,companyExtDto.getPk());
//                  sysService.sendToAdminOrRoleNotOrder("certification_passed", smsMap, companyExtDto.getPk());
                } else if (companyExtDto.getAuditStatus() != null
                        && companyExtDto.getAuditStatus() == 3) {

                    smsMap.put("uname", b2bcompany.getName());
                    smsMap.put("reason", b2bcompany.getRefuseReason());
                    sendMsgToUser(SmsCode.NO_PASS_PU.getValue(),smsMap,companyExtDto.getPk());
                    //sysService.sendToAdminOrRoleNotOrder("audit_not_through", smsMap, companyExtDto.getPk());
                }
                if (companyExtDto.getAuditSpStatus() != null
                        && companyExtDto.getAuditSpStatus() == 2) {
                    sendMsgToUser(SmsCode.SETTLED.getValue(),smsMap,companyExtDto.getPk());
                    //sysService.sendToAdminOrRoleNotOrder("settled", smsMap, companyExtDto.getPk());
                } else if (companyExtDto.getAuditSpStatus() != null
                        && companyExtDto.getAuditSpStatus() == 3) {
                    smsMap.put("Remark", b2bcompany.getRefuseReason());
                    sendMsgToUser(SmsCode.FAIL_SATTLE.getValue(),smsMap,companyExtDto.getPk());
                    //sysService.sendToAdminOrRoleNotOrder("supplier_audit_failure", smsMap, companyExtDto.getPk());
                }
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }


        } else {
            // 添加公司重名判断
            if (!"1".equals(companyExtDto.getIsUpdateRemarks())) {
                msg = isExtistEmpatyParams(companyExtDto);
            }
            if ("1".equals(companyExtDto.getIsUpdateRemarks())) {//修改备注
                msg = "";
            }
            if ("".equals(msg)) {
                if (companyExtDto.getFlag()!=null&&companyExtDto.getFlag()==1) {
                    companyExtDto.setInfoUpdateTime(new Date());
                }
                result = b2bCompayExtDao.updateCompany(companyExtDto);
                if(companyExtDto.getName()!=null &&!companyExtDto.getName().equals("")){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("companyPk",companyExtDto.getPk());
                    map.put("companyName", companyExtDto.getName());
                    b2bMemberExtDao.updateMemberByCompanyPk(map);
                }
                if(result>0){
                    //更新公司后更新店铺的板块状态
                    List<B2bStoreExtDto> storeList = b2bStoreExtDao.getByCompanyPkExt(companyExtDto.getPk());
                    if (storeList != null && storeList.size() > 0){
                        for (B2bStoreExtDto storeDto:storeList){
                            storeDto.setBlock(companyExtDto.getBlock());
                            b2bStoreExtDao.updateStore(storeDto);
                        }
                    }
                    msg = Constants.RESULT_SUCCESS_MSG;
                }else{
                    msg = Constants.RESULT_FAIL_MSG;
                }
            }
        }

        return msg;
    }

    /**
     * 公司审核后短信发送判断逻辑
     * @param smsName
     * @param smsMap
     * @param companyPk
     */
    public void sendMsgToUser(String smsName,Map<String, String> smsMap,String companyPk) {
        B2bMemberDto member =new B2bMemberDto();
        SysSmsTemplateDto smsDto= sysSmsTemplateDao.getByName(smsName);
        if(null!=smsDto&&!"".equals(smsDto)){
            if(null!=smsDto.getFlag()&&smsDto.getFlag()==2){
                Map<String, String> map=new HashMap<>();
                map.put("name", smsDto.getName());
                map.put("companyPk", companyPk);
                List<B2bMemberDto> members=	b2bMemberExtDao.getByRoleAndCompanyPk(map);
                if(members!=null&&members.size()>0){
                    for(B2bMemberDto m:members){
                        member.setMobile(m.getMobile());
                        sysService.sendMSM(member,m.getMobile(),smsName, smsMap);
                    }
                }
            }else{
                List<B2bMemberDto> bEntity= b2bMemberExtDao.searchAdminsByCompanyPk(companyPk);
                if(bEntity!=null&&bEntity.size()>0){
                    for(B2bMemberDto dto:bEntity){
                        member.setMobile(dto.getMobile());
                        sysService.sendMSM(member,dto.getMobile(),smsName, smsMap);
                    }
                }

            }
        }
    }



    /**
     * 查询子公司(前端添加的)是否已审核过
     * @param parentPk
     * @param i
     * @return
     */
    private boolean isFirstCheckCompany(String parentPk, int i) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyPk", parentPk);
        map.put("flag", i);
        map.put("dimenType", MemberSys.ACCOUNT_DIMEN_CHICOM.getValue());
        List<MangoMemberPoint> list = addMemberSysPoint.searchPointList(map);
        if (list!=null && list.size()>0) {
            return false;
        }else{
            return true;
        }
    }

    private String isExtistEmpatyParams(B2bCompanyExtDto companyExtDto) {
        String msg = "";
        if (companyExtDto != null && companyExtDto.getIsVisable() == null) {
            if ((companyExtDto.getBlUrl() == null || "".equals(companyExtDto
                    .getBlUrl()))
                    || (companyExtDto.getEcUrl() == null || ""
                    .equals(companyExtDto.getEcUrl()))) {
                msg = Constants.IS_EMPTY_ECURL;
            } else if ((companyExtDto.getEcUrl().contains("bgbg.png") || companyExtDto
                    .getBlUrl().contains("bgbg.png"))) {
                msg = Constants.IS_EMPTY_BLURL_ECURL;
            }

        }
        if (companyExtDto.getName() != null && companyExtDto.getName() != "") {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", companyExtDto.getName().trim());
            map.put("pk", companyExtDto.getPk());
            map.put("isDelete", 1);
            int counts = b2bCompayExtDao.isExistCompanyVaild(map);
            if (counts > 0) {
                msg = Constants.ISEXTIST_COMPANY_NAME;
            }
        }
        return msg;
    }

    private int auditCompany(B2bCompanyExtDto companyExtDto, int result,
                             ManageAccount adto) {
        //加分
        B2bCompanyDto	b2bcompany = b2bCompayExtDao.getByPk(companyExtDto.getPk());
        //首次注册加分
        if(b2bcompany.getParentPk().equals("-1")){
            String dimenType = "";
            if ((null != companyExtDto.getAuditStatus() && companyExtDto.getAuditStatus() == 2)
                    || (null != companyExtDto.getAuditSpStatus() && companyExtDto.getAuditSpStatus() == 2)) {
                updateMemberByCompanyPk(companyExtDto);
                if (null != companyExtDto.getAuditStatus() && companyExtDto.getAuditStatus() == 2) {
                    //添加注册成功的采购商的积分
                    dimenType =MemberSys.ACCOUNT_DIMEN_PURCHER.getValue();
                    addCompanyPoint(companyExtDto,dimenType);
                }
                //添加注册成功的供应商的积分
                if (null != companyExtDto.getAuditSpStatus() && companyExtDto.getAuditSpStatus() == 2) {
                    //供应商总公司审核通过，子公司更新enterTime
                    Map<String,Object>param=new HashMap<String,Object>();
                    param.put("parentPk", companyExtDto.getPk());
                    List<B2bCompanyDto>subcompany= b2bCompayExtDao.getCompanyDto(param);
                    if(null!=subcompany && subcompany.size()>0){
                        for(B2bCompanyDto b2bCompanyDto:subcompany){
                            B2bCompany b2bCompany=new B2bCompany();

                            BeanUtils.copyProperties(b2bCompanyDto, b2bCompany);
                            b2bCompany.setEnterTime(new Date());
                            b2bCompayExtDao.update(b2bCompany);
                        }
                    }

                    dimenType =MemberSys.ACCOUNT_DIMEN_SUPPLIER.getValue();
                    addCompanyPoint(companyExtDto,dimenType);
                }
            }
        }else {
            //添加首次添加子公司加分(只给前端添加的加)
            if (b2bcompany.getAddMemberPk()!=null&&!b2bcompany.getAddMemberPk().equals("")) {

                if (companyExtDto.getAuditStatus()!=null&&companyExtDto.getAuditStatus()==2) {//审核采购商子公司
                    //是否是首次审核
                    if (isFirstCheckCompany(b2bcompany.getParentPk(),1)) {
                        addMemberSysPoint.addPoint(b2bcompany.getAddMemberPk(), b2bcompany.getParentPk(), MemberSys.ACCOUNT_DIMEN_CHICOM.getValue(),1);
                    }
                }

                if (companyExtDto.getAuditSpStatus()!=null&&companyExtDto.getAuditSpStatus()==2) {//审核供应商子公司
                    if(b2bcompany.getAuditStatus()==1 || b2bcompany.getAuditStatus()==3){
                        if (isFirstCheckCompany(b2bcompany.getParentPk(),1)) {
                            addMemberSysPoint.addPoint(b2bcompany.getAddMemberPk(), b2bcompany.getParentPk(), MemberSys.ACCOUNT_DIMEN_CHICOM.getValue(),1);

                        }
                    }

                    //是否是首次审核
                    if (isFirstCheckCompany(b2bcompany.getParentPk(),2)) {
                        addMemberSysPoint.addPoint(b2bcompany.getAddMemberPk(), b2bcompany.getParentPk(), MemberSys.ACCOUNT_DIMEN_CHICOM.getValue(),2);
                    }
                }
            }
        }

        // 设置操作人
        if (companyExtDto.getAuditStatus() != null
                && companyExtDto.getAuditStatus() > 1) {
            companyExtDto.setAuditPk(adto.getPk());
            companyExtDto.setAuditTime(new Date());
        }
        if (companyExtDto.getAuditSpStatus() != null
                && companyExtDto.getAuditSpStatus() > 1) {
            companyExtDto.setAuditSpPk(adto.getPk());
            companyExtDto.setAuditSpTime(new Date());
            if (companyExtDto.getAuditSpStatus() == 2) {
                companyExtDto.setAuditStatus(2);
                companyExtDto.setAuditTime(new Date());
            }
        }
        String storePk = "";
        String storeName = "";
        // 如果是供应商商审核通过 默认给店铺表新增数据
        if (companyExtDto.getAuditSpStatus() != null
                && companyExtDto.getAuditSpStatus() == 2) {
            companyExtDto.setCompanyType(2);
            String companyPk = "";
            if (companyExtDto.getParentPk() != null
                    && !"-1".equals(companyExtDto.getParentPk())) {// 子公司审核
                companyPk = companyExtDto.getParentPk();
            }
            if (companyExtDto.getParentPk() != null
                    && "-1".equals(companyExtDto.getParentPk())) {// 总公司审核
                companyPk = companyExtDto.getPk();
            }
            List<B2bStoreExtDto> list = b2bStoreExtDao
                    .getByCompanyPkExt(companyPk);
            // 1.总共公司审核，如果是总公司审核查询是否存在店铺，如果总公司有店铺直接刷新到缓存；如果总公司没有店铺新增店铺后再刷新到缓存
            // 2. 子公司审核，查询总公司店铺,总公司存在店铺设置到子公司后刷入缓存，如果总公司没有店铺新增店铺后设置到子公司再刷入到缓存
            if (list != null && list.size() == 0) {
                B2bCompanyDto dto = b2bCompayExtDao.getByPk(companyPk);
                B2bStore store = new B2bStore();
                store.setPk(KeyUtils.getUUID());
                store.setCompanyPk(dto.getPk());
                store.setIsOpen(1);
                store.setName(dto.getName());
                store.setCustomerTel(dto.getContactsTel());
                store.setBlock(dto.getBlock());
                store.setStartTime("08:00");
                store.setEndTime("18:00");

                store.setContacts(dto.getContacts());
                store.setContactsTel(dto.getContactsTel());

                result = b2bStoreExtDao.insert(store);
                if (result > 0) {
                    storePk = store.getPk();
                    storeName = store.getName();
                }
            } else {
                storePk = list.get(0).getPk();
                storeName = list.get(0).getName();
            }
        }
        // 更新公司
        result = b2bCompayExtDao.updateCompany(companyExtDto);
        if (result > 0) {
            // 更新会员

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pk", companyExtDto.getPk());
            List<B2bCompanyExtDto> bctos = b2bCompayExtDao.searchGridExt(map);
            if (null != bctos && bctos.size() > 0) {
                B2bCompanyExtDto bcto = bctos.get(0);
                bcto.setStorePk(storePk);
                bcto.setStoreName(storeName);
                JedisMaterialUtils.set(companyExtDto.getPk(), bcto, 3600);
            }

//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("pk", companyExtDto.getPk());
//            List<B2bCompanyDto> bctos = b2bCompayExtDao.searchGrid(map);
//            if (null != bctos && bctos.size() > 0) {
//                B2bCompanyExtDto bcto = bctos.get(0);
//                bcto.setStorePk(storePk);
//                bcto.setStoreName(storeName);
////                B2bStoreDto store = new B2bStoreDto();
////                store.setPk(storePk);
////                store.setName(storeName);
////                Set<String> sessionIds = JedisMaterialUtils.getSet(companyExtDto.getPk());
////                //现在写法
////                if (sessionIds != null && sessionIds.size() > 0) {
////                    for (String sessionId : sessionIds) {
////                        Sessions session = JedisMaterialUtils.get(sessionId, Sessions.class);
////                        if(session != null) {
////                            session.setStoreDto(store);
////                            session.setCompanyDto(bcto);
////                            JedisMaterialUtils.set(sessionId, session, 3600);
////                        }
////                    }
////                }
//                //以前写法
//                JedisMaterialUtils.set(companyExtDto.getPk(), bcto, 3600);
//            }
        }
        return result;
    }

    private void addCompanyPoint(B2bCompanyExtDto companyExtDto,String dimenType) {
        B2bCompanyExtDto  companyDto = b2bCompayExtDao.getMemberCompanyByPk(companyExtDto.getPk());
        if (companyDto!=null) {
            try{
                //是否完善过
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("dimenType",dimenType);
                map.put("companyPk", companyDto.getParentPk());
                List<MangoMemberPoint> list = addMemberSysPoint.searchPointList(map);
                if (list==null ||list.size()==0) {
                    addMemberSysPoint.addPoint(companyDto.getMemberPk(),  companyExtDto.getPk(), dimenType,null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private int updateMemberByCompanyPk(B2bCompanyExtDto companyExtDto) {
        int result = 0;
        B2bCompanyDto company = b2bCompayExtDao.getByPk(companyExtDto.getPk());
        if (null != company) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyPk", company.getPk());
            map.put("isDelete", company.getIsDelete());
            map.put("isVisable", company.getIsVisable());
            if (companyExtDto.getAuditStatus() != null) {
                map.put("auditStatus", companyExtDto.getAuditStatus());
            }
            if (companyExtDto.getAuditSpStatus() != null) {
                map.put("auditStatus", companyExtDto.getAuditSpStatus());
            }
            result = b2bMemberExtDao.updateMemberByCompanyPk(map);
        }
        return result;
    }

    @Override
    public String insertB2bCompany(B2bCompany company, ManageAccount adto) {
        Date date =	new Date();
        String msg = "";
        msg = isExtistCompanyName(company, msg);
        if ("".equals(msg)) {
            company.setPk(KeyUtils.getUUID());
            company.setInsertTime(date);
            //供应商
            if(company.getCompanyType().intValue()==2){
                //供应商总公司
                if (company.getParentPk() == null
                        || "".equals(company.getParentPk())||"-1".equals(company.getParentPk())) {
                    company.setEnterTime(date);
                }else{
                    //判断总公司审核通过
                    B2bCompanyDto b2bCompanyDto=b2bCompayExtDao.getByPk(company.getParentPk());
                    if(null!=b2bCompanyDto && b2bCompanyDto.getAuditSpStatus().intValue()==2){
                        company.setEnterTime(date);
                    }
                }

            }
            //采购商
            if(company.getCompanyType().intValue()==1){

                if (company.getParentPk() == null
                        || "".equals(company.getParentPk()) || "-1".equals(company.getParentPk())) {

                }else{


                    //判断总公司审核通过
                    B2bCompanyDto b2bCompanyDto=b2bCompayExtDao.getByPk(company.getParentPk());
                    if(null!=b2bCompanyDto && b2bCompanyDto.getCompanyType().intValue()==2 && b2bCompanyDto.getAuditSpStatus().intValue()==2 ){
                        company.setEnterTime(date);
                    }
                }


            }
            company.setAuditStatus(Constants.ONE);
            company.setAuditSpStatus(Constants.ONE);
            company.setIsDelete(Constants.ONE);
            company.setIsVisable(Constants.ONE);
            company.setIsPerfect(2);
            company.setUpdateTime(date);
            company.setInfoUpdateTime(date);
            if (company.getParentPk() == null
                    || "".equals(company.getParentPk())) {
                company.setParentPk(Constants.MINUS_STR_ONE);
            }

            int result = b2bCompayExtDao.insert(company);
            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }
        }
        return msg;
    }

    private String isExtistCompanyName(B2bCompany company, String msg) {
        if ((company.getBlUrl() == null || "".equals(company.getBlUrl()))
                || (company.getEcUrl() == null || "".equals(company.getEcUrl()))) {
            msg = Constants.IS_EMPTY_ECURL;
        } else if ((company.getEcUrl().contains("bgbg.png") || company
                .getBlUrl().contains("bgbg.png"))) {
            msg = Constants.IS_EMPTY_BLURL_ECURL;
        }
        if (company.getName() != null && company.getName() != "") {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", company.getName().trim());
            map.put("pk", company.getPk());
            map.put("isDelete", 1);
            int counts = b2bCompayExtDao.isExistCompanyVaild(map);
            if (counts > 0) {
                msg = Constants.ISEXTIST_COMPANY_NAME;
            }
        }
        return msg;
    }

    @Override
    public PageModel<B2bMemberDto> getAllMemberList(QueryModel<B2bMemberExtDto> qm,ManageAccount account) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageModel<B2bMemberDto> pm = new PageModel<B2bMemberDto>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("companyPk", qm.getEntity().getCompanyPk());
        map.put("mobile", qm.getEntity().getMobile());
        //采购商和供应商分别控制手机号显示权限
        if(account != null){
            String modelType = qm.getEntity().getModelType();
            //判断属于那个模块
            int companyType = qm.getEntity().getCompanyType() == null?-1:qm.getEntity().getCompanyType();
            if ("1".equals(modelType)) {
                //判断公司类型
                if (companyType == Constants.ONE) {
                    if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_PURCHASER_COL_SETADMINMOBILE)) {
                        map.put("sAdminShowMobile", ColAuthConstants.OPER_CM_PURCHASER_COL_SETADMINMOBILE);
                    }
                }
                if (companyType == Constants.TWO) {
                    if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_SUPPLIER_COL_SETADMINMOBILE)) {
                        map.put("sAdminShowMobile", ColAuthConstants.OPER_CM_SUPPLIER_COL_SETADMINMOBILE);
                    }
                }
            }else{
                if (companyType == Constants.ONE) {
                    if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_PURCHASER_COL_SETADMINMOBILE)) {
                        map.put("sAdminShowMobile", ColAuthConstants.MARKET_CM_PURCHASER_COL_SETADMINMOBILE);
                    }
                }
                if (companyType == Constants.TWO) {
                    if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_SUPPLIER_COL_SETADMINMOBILE)) {
                        map.put("sAdminShowMobile", ColAuthConstants.MARKET_CM_SUPPLIER_COL_SETADMINMOBILE);
                    }
                }
            }
        }
        List<B2bMemberExtDto> list = b2bMemberExtDao.getMemberByAdminList(map);
        List<B2bMemberDto> listTemp = new ArrayList<B2bMemberDto>();
        if (list != null && list.size() > 0) {
            for (B2bMemberExtDto b2bMemberDto : list) {
                if (b2bMemberDto.getCompanyTypeArr() != null
                        && b2bMemberDto.getCompanyTypeArr().contains(";")) {
                    String[] array = b2bMemberDto.getCompanyTypeArr()
                            .split(";");
                    for (int i = 0; i < array.length; i++) {
                        String type = array[i];
                        if (type != null && "0".equals(type)) {
                            b2bMemberDto.setCompanyType(Integer.valueOf(type));
                        }
                    }
                }
                if ("0".equals(b2bMemberDto.getCompanyTypeArr())) {
                    b2bMemberDto.setCompanyType(Integer.valueOf(b2bMemberDto
                            .getCompanyTypeArr()));
                }
                listTemp.add(b2bMemberDto);
            }
        }
        int counts = b2bMemberExtDao.getMemberByAdminCount(map);
        pm.setTotalCount(counts);
        pm.setDataList(listTemp);
        return pm;
    }

    @Override
    public int updateSetAdmin(String memberPk, String oldMemberPk,
                              String companyPk) {
        Map<String, Object> mapMember = new HashMap<String, Object>();
        mapMember.put("companyPk", companyPk);
        List<B2bMemberExtDto> listMember = b2bMemberExtDao
                .getMemberByAdminList(mapMember);
        if (listMember != null && listMember.size() > 0) {
            Map<String, Object> roleMap = new HashMap<String, Object>();
            for (B2bMemberExtDto b2bMemberDto : listMember) {
                roleMap.put("memberPk", b2bMemberDto.getPk());
                // 如果是新选中的删除掉所有角色
                if (b2bMemberDto.getPk().equals(memberPk)) {
                    delSelectEdRole(roleMap, b2bMemberDto);
                } else {
                    findRoleAndDel(roleMap, b2bMemberDto);
                }
            }
        }
        List<B2bRoleExtDto> list = b2bRoleExtDao.searchRoleAdmin();
        if (list.size() > 0) {
            Map<String, Object> role = new HashMap<String, Object>();
            role.put("pk", KeyUtils.getUUID());
            role.put("memberPk", memberPk);
            role.put("rolePk", list.get(0).getPk());
            b2bRoleExtDao.insertMemberRole(role);
            return 1;
        } else {
            return 0;
        }
    }

    // 查找本公司设置过超级管理员的会员并删除(管理员时确保存在一个超级管理员)
    private void findRoleAndDel(Map<String, Object> roleMap,
                                B2bMemberExtDto b2bMemberDto) {
        List<B2bRoleExtDto> roleList = b2bRoleExtDao.searchRoleAdmin();
        if (roleList != null && roleList.size() > 0) {
            for (B2bRoleDto b2bRoleDto : roleList) {
                roleMap.put("memberPk", b2bMemberDto.getPk());
                if (b2bMemberDto.getRolePkArr() != null
                        && b2bMemberDto.getRolePkArr().contains(";")) {
                    String[] array = b2bMemberDto.getRolePkArr().split(";");
                    for (int i = 0; i < array.length; i++) {
                        String rolePk = array[i];
                        if (rolePk != null) {
                            if (rolePk.equals(b2bRoleDto.getPk())) {
                                roleMap.put("rolePk", rolePk);
                                b2bRoleExtDao
                                        .delMemberRoleByMemberPkAndRolePk(roleMap);
                            }
                        }
                    }
                }
                if (b2bMemberDto.getCompanyTypeArr() != null
                        && !b2bMemberDto.getRolePkArr().contains(";")) {
                    if (b2bRoleDto.getPk().equals(b2bMemberDto.getRolePkArr())) {
                        roleMap.put("rolePk", b2bMemberDto.getRolePkArr());
                        b2bRoleExtDao.delMemberRoleByMemberPkAndRolePk(roleMap);
                    }
                }
            }

        }
    }

    // 删除到已选择的超级管理员
    private void delSelectEdRole(Map<String, Object> roleMap,
                                 B2bMemberExtDto b2bMemberDto) {
        if (b2bMemberDto.getRolePkArr() != null
                && b2bMemberDto.getRolePkArr().contains(";")) {
            String[] array = b2bMemberDto.getRolePkArr().split(";");
            for (int i = 0; i < array.length; i++) {
                String rolePk = array[i];
                if (rolePk != null) {
                    roleMap.put("rolePk", rolePk);
                    b2bRoleExtDao.delMemberRoleByMemberPkAndRolePk(roleMap);
                }
            }
        }
        if (b2bMemberDto.getCompanyTypeArr() != null
                && !b2bMemberDto.getRolePkArr().contains(";")) {
            roleMap.put("rolePk", b2bMemberDto.getRolePkArr());
            b2bRoleExtDao.delMemberRoleByMemberPkAndRolePk(roleMap);
        }
    }

    @Override
    public PageModel<B2bMemberExtDto> searchb2bMemberExtList(
            QueryModel<B2bMemberExtDto> qm,ManageAccount account) {
        PageModel<B2bMemberExtDto> pm = new PageModel<B2bMemberExtDto>();
        Map<String, Object> map = memberListParams(qm);
        int totalCount = b2bMemberExtDao.searchB2bMemberCounts(map);
        String modelType = qm.getEntity().getModelType();
        if("1".equals(modelType)){
            setMkMemberParams(account, map);
        }else{
            setOperMemberParams(account, map);

        }
        List<B2bMemberExtDto> list = b2bMemberExtDao.searchB2bMemberList(map);
        // 查询所属角色
        for (int i = 0; i < list.size(); i++) {
            String roleNames = "";
            List<B2bRoleExtDto> roleList = b2bRoleExtDao
                    .searchRoleBymemberPk(list.get(i).getPk());
            boolean isSuperMember = false;
            if (roleList != null && roleList.size() > 0) {
                for (int j = 0; j < roleList.size(); j++) {
                    if (roleList.get(j) != null) {
                        roleNames += roleList.get(j).getName() + ",";
                        if (roleList.get(j).getCompanyType() != null
                                && roleList.get(j).getCompanyType() == 0) {
                            isSuperMember = true;
                        }
                    }
                }
            }
            if (isSuperMember) {
                list.get(i).setCompanyType(0);
            }
            if (!"".equals(roleNames)) {
                list.get(i).setRoleName(
                        roleNames.substring(0, roleNames.length() - 1));
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;

    }

    /**
     * 运营中心-权限显示配置
     * @param account
     * @param map
     */
    private void setOperMemberParams(ManageAccount account, Map<String, Object> map) {
        if(account!= null){
            if(!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CM_MEMBER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.OPER_CM_MEMBER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(account.getPk(),ColAuthConstants.OPER_CM_MEMBER_COL_TEL)){
                map.put("memberTelCol",ColAuthConstants.OPER_CM_MEMBER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(account.getPk(),ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERNAME)){
                map.put("memberNameCol",ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERNAME);
            }
            if(!CommonUtil.isExistAuthName(account.getPk(),ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERACC)){
                map.put("memberAccCol",ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERACC);
            }
        }
    }

    /**
     *  营销中心-权限显示配置
     * @param account
     * @param map
     */
    private void setMkMemberParams(ManageAccount account, Map<String, Object> map) {
        if(account!= null){
            if(!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CM_MEMBER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.MARKET_CM_MEMBER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(account.getPk(),ColAuthConstants.MARKET_CM_MEMBER_COL_TEL)){
                map.put("memberTelCol",ColAuthConstants.MARKET_CM_MEMBER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(account.getPk(),ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERNAME)){
                map.put("memberNameCol",ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERNAME);
            }
            if(!CommonUtil.isExistAuthName(account.getPk(),ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERACC)){
                map.put("memberAccCol",ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERACC);
            }
        }
    }

    private Map<String, Object> memberListParams(QueryModel<B2bMemberExtDto> qm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("auditStatus", qm.getEntity().getAuditStatus());
        map.put("companyName", qm.getEntity().getCompanyName());
        map.put("mobile", qm.getEntity().getMobile());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
        map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
        map.put("auditStartTime", qm.getEntity().getAuditStartTime());
        map.put("auditEndTime", qm.getEntity().getAuditEndTime());
        map.put("registerSource", qm.getEntity().getRegisterSource());
        map.put("type", qm.getEntity().getType());
        map.put("orderCounts", qm.getEntity().getOrderCounts());

        map.put("employeeNumber", qm.getEntity().getEmployeeNumber());
        map.put("employeeName", qm.getEntity().getEmployeeName());


        map.put("level", qm.getEntity().getLevel());
        map.put("levelName", qm.getEntity().getLevelName());
        map.put("feedStartTime", qm.getEntity().getFeedStartTime());
        map.put("feedEndTime", qm.getEntity().getFeedEndTime());
        return map;
    }

    @Override
    public List<B2bMemberExtDto> exportB2bMemberExtList(
            QueryModel<B2bMemberExtDto> qm,ManageAccount account) {
        Map<String, Object> map = memberListParams(qm);
        map.put("start", null);
        map.put("limit", null);
        map.put("orderName", null);
        map.put("orderType", null);
        String modelType = qm.getEntity().getModelType();
        if("1".equals(modelType)){
            setMkMemberParams(account, map);
        }else{
            setOperMemberParams(account, map);
        }
        List<B2bMemberExtDto> list = b2bMemberExtDao.searchB2bMemberList(map);
        // 查询所属角色
        for (int i = 0; i < list.size(); i++) {
            String roleNames = "";
            List<B2bRoleExtDto> roleList = b2bRoleExtDao
                    .searchRoleBymemberPk(list.get(i).getPk());
            boolean isSuperMember = false;
            if (roleList != null && roleList.size() > 0) {
                for (int j = 0; j < roleList.size(); j++) {
                    if (roleList.get(j) != null) {
                        roleNames += roleList.get(j).getName() + ",";
                        if (roleList.get(j).getCompanyType() != null
                                && roleList.get(j).getCompanyType() == 0) {
                            isSuperMember = true;
                        }
                    }
                }
            }
            if (isSuperMember) {
                list.get(i).setCompanyType(0);
            }
            if (!"".equals(roleNames)) {
                list.get(i).setRoleName(
                        roleNames.substring(0, roleNames.length() - 1));
            }
        }
        return list;
    }

    @Override
    public List<B2bRoleExtDto> getB2bRoleList(int companyType) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyType", companyType);
        List<B2bRoleExtDto> list = b2bRoleExtDao.searchRoles(map);
        return list;
    }

    @Override
    public int delSupplierBank(String pk) {

        return sysCompanyBankcardExtDao.delete(pk);
    }

    @Override
    public Boolean checkCompanyAndBank(SysCompanyBankcardDto dto) {
        Boolean flag ;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pk", dto.getPk());
        map.put("companyPk", dto.getCompanyPk());
        map.put("ecbankPk", dto.getEcbankPk());
        int num = sysCompanyBankcardExtDao.checkCompanyAndBank(map);
        if (num>0) {
            flag =  true;
        }else{
            flag =  false;
        }
        return flag;
    }

    @Override
    public int updateCompanyInvoice(B2bInvoiceEntity entity) {

        return  b2bCompayExtDao.updateCompanyInvoice(entity);
    }

    @Override
    public B2bCompanyDto getParentCompanyByParentPk(String parentPk) {
        return b2bCompayExtDao.getByPk(parentPk);
    }

    @Override
    public List<B2bCompanyExtDto> getPurCompanyList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyType", 1);
        map.put("parentPk",-1);
        map.put("auditStatus",2);
        map.put("isVisable",1);
        return  b2bCompayExtDao.searchGridExt(map);
    }


}
