package cn.cf.service.marketing.impl;

import cn.cf.PageModel;
import cn.cf.common.*;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.RegionJson;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bManageRegion;
import cn.cf.model.ManageAccount;
import cn.cf.model.MarketingCompany;
import cn.cf.model.SysExcelStore;
import cn.cf.service.marketing.McustomerManageService;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class McustomerManageServiceImpl implements McustomerManageService {

    @Autowired
    private B2bCompanyExtDao b2bCompanyExtDao;

    @Autowired
    private ManageAccountExtDao manageAccountExtDao;

    @Autowired
    private MarketingPersonnelExtDao personnelDao;

    @Autowired
    private B2bPaymentDao b2bPaymentDao;

    @Autowired
    private B2bOrderExtDao b2bOrderExtDao;

    @Autowired
    private B2bManageRegionExtDao b2bManageRegionExtDao;

    @Autowired
    private MarketingPersonnelExtDao marketingPersonnelDao;

    @Autowired
    private B2bManageRegionDao b2bManageRegionDao;

    @Autowired
    private MarketingOrderMemberExtDao orderMemberDao;

    @Autowired
    private  MongoTemplate  mongoTemplate;
    @Autowired
    private ManageAuthorityExtDao manageAuthorityExtDao;

    @Autowired
    private SysExcelStoreExtDao sysExcelStoreExtDao;

    @Override
    public List<MarketingPersonnelDto> getPersonByType(Integer type) {
        return personnelDao.getPersonByType(type);
    }

    @Override
    public PageModel<B2bCompanyExtDto> searchCompanyListByMarrieddealOrder(QueryModel<B2bCompanyExtDto> qm, ManageAccount account) {
        PageModel<B2bCompanyExtDto> pm = new PageModel<B2bCompanyExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("companyType", qm.getEntity().getCompanyType());
        map.put("name", qm.getEntity().getName());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("distributeMemberStartTime", qm.getEntity().getDistributeMemberStartTime());
        map.put("distributeMemberEndTime", qm.getEntity().getDistributeMemberEndTime());
        map.put("accountPk", qm.getEntity().getAccountPk());
        map.put("mobile", qm.getEntity().getMobile());
        map.put("isDistribute", qm.getEntity().getIsDistribute());
        map.put("province", qm.getEntity().getProvinceName());
        map.put("city", qm.getEntity().getCityName());
        map.put("area", qm.getEntity().getAreaName());
        // 设置隐藏字段
        setPurCol(account, map);
        // 查询登陆账户信息
        MarketingPersonnelDto dto =this.getAccountName(account.getPk());

        int totalCount = 0;
        List<B2bCompanyExtDto> list = new ArrayList<B2bCompanyExtDto>();
        List<MarketingPersonnelDto> regions = null;
        if (null != dto) {
            if (dto.getType() == 1) { // 业务经理:查看分配名下的公司
                map.put("businessPk", account.getPk());
                totalCount = b2bCompanyExtDao.searchCompanyCount(map);
                list = b2bCompanyExtDao.searchAuditCompayList(map);
                // 查找区域经理
                if (totalCount > 0) {
                    // 匹配区域经理
                    matchRegionName(list, dto.getType());
                }
            } else if (dto.getType() == 2) {// 平台交易员:未分配所属大区查看所有公司；已分配所属大区，查看分配下的所属大区
                if (dto.getRegionPk() != null && !dto.getRegionPk().equals("")) {
                    B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                    if (regionDto != null) {
                        if (regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                            List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                            map.put("regionList", regionJsons);
                         
                            // 查询所属区域经理
                            Map<String, Object> param = new HashMap<String, Object>();
                            param.put("isDelete", 1);
                            param.put("isVisable", 1);
                            param.put("type", 3);
                            param.put("regionPk", dto.getRegionPk());
                            regions = marketingPersonnelDao.searchList(param);
                        }
                    }
                    
                }
                totalCount = b2bCompanyExtDao.searchCompanyCount(map);
                list = b2bCompanyExtDao.searchAuditCompayList(map);
                // 查找区域经理:有分配区域，查找改区域是否有区域经理，反之匹配
                if (totalCount > 0) {
                    if (regions != null && regions.size() > 0) {
                        for (B2bCompanyExtDto b2bCompanyExtDto : list) {
                            b2bCompanyExtDto.setRegionName(regions.get(0).getAccountName());
                            b2bCompanyExtDto.setType(dto.getType());
                        }
                    } else {
                        matchRegionName(list, dto.getType());
                    }
                }
            } else if (dto.getType() == 3) {// 区域主管
                B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                if (regionDto != null) {
                    if (regionDto.getArea() != "" && !regionDto.getArea().equals("")) {
                        List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                        map.put("regionList", regionJsons);
                        map.put("loginPk",account.getPk());
                    }
                }
                totalCount = b2bCompanyExtDao.searchCompanyCount(map);
                list = b2bCompanyExtDao.searchAuditCompayList(map);
                // 查找区域经理
                if (totalCount > 0 && regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                    for (B2bCompanyExtDto b2bCompanyExtDto : list) {
                        b2bCompanyExtDto.setRegionName(account.getName());
                        b2bCompanyExtDto.setType(dto.getType());
                    }
                }else{
                    matchRegionName(list, dto.getType());
                }
            }
        }
        for(B2bCompanyExtDto b2bCompanyExtDto : list){
            if(!(boolean) map.get("colRegionName")){
                b2bCompanyExtDto.setRegionName(CommonUtil.hideContacts(b2bCompanyExtDto.getRegionName()));
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);

        return pm;
    }

    private void setSupCol(ManageAccount account, Map<String, Object> map) {
        map.put("colCompanyName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_STORE_NAME));
        map.put("colAccountName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_ACCOUNTNAME));
        map.put("colPersonnelName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_PERSONALNAME));
        map.put("colContacts", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_CONTACTS));
        map.put("colContactsTel", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_CONTACTSTEL));
        map.put("colBusinessLicense", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_BUSSINESSLICENSE));
        
        map.put("colRegionName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_SUPPLIER_COL_REGIONNAME));
    }

    private void setPurCol(ManageAccount account, Map<String, Object> map) {
        map.put("colCompanyName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_COM_NAME));
        map.put("colAccountName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_ACCOUNTNAME));
        map.put("colPersonnelName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_PERSONALNAME));
        map.put("colContacts", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_CONTACTS));
        map.put("colContactsTel", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_CONTACTSTEL));
        map.put("colBusinessLicense", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_BUSSINESSLICENSE));
        
        
        map.put("colRegionName", CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CUST_PURCHASER_COL_REGIONNAME));
    }

    private void matchRegionName(List<B2bCompanyExtDto> list, Integer type) {
        for (B2bCompanyExtDto companyExt : list) {
            // 获取区域经理全部的区域地址
            List<MarketingPersonnelExtDto> marketingPersonnelExtDaos = marketingPersonnelDao.getRegions();
            // 省市区
            String accountName = getAccountName(companyExt, marketingPersonnelExtDaos, 1);
            if (accountName.equals("")) {// 省市
                accountName = getAccountName(companyExt, marketingPersonnelExtDaos, 2);
            }
            companyExt.setRegionName(accountName);
            companyExt.setType(type);
        }
    }

    private String getAccountName(B2bCompanyExtDto companyExt, List<MarketingPersonnelExtDto> marketingPersonnelExtDaos, int i) {
        String province = companyExt.getProvince();
        String city = companyExt.getCity();
        String area = companyExt.getArea();
        String accountName = "";
        if (null != province && !province.equals("") && null != city && !city.equals("") && null != area && !area.equals("")) {
            if (i == 2) {
                area = "";
            }
            if (marketingPersonnelExtDaos.size() > 0) {
                for (MarketingPersonnelExtDto m : marketingPersonnelExtDaos) {
                    if (null != m.getArea() && !m.getArea().equals("")) {
                        List<RegionJson> regionJsons = JSON.parseArray(m.getArea(), RegionJson.class);
                        if (regionJsons.size() > 0) {
                            for (RegionJson region : regionJsons) {
                                if (region.getProvince().equals(province) && region.getCity().equals(city) && region.getArea().equals(area)) {
                                    accountName = m.getAccountName();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return accountName;
    }

    @Override
    public List<ManageAuthorityDto> getBtnRoleList(String rolePk, String btnName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rolePk", rolePk);
        map.put("name", btnName);
        return b2bCompanyExtDao.getBtnManageAuthorityByRolePk(map);
    }

    @Override
    public int insertOrUpdateMarketingCompany(MarketingCompany marketingCompany) {
        Date date = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyPk", marketingCompany.getCompanyPk());
        map.put("companyType", marketingCompany.getCompanyType());
        int counts = b2bCompanyExtDao.marketingCompanyCountsByPk(map);

        marketingCompany.setDistributeMemberTime(date);
        if (counts > 0) {
            int i = b2bCompanyExtDao.updateMarketingCompany(marketingCompany);
            return i;
        } else {
            marketingCompany.setPk(KeyUtils.getUUID());
            int j = b2bCompanyExtDao.insertMarketingCompany(marketingCompany);
            return j;
        }
    }

    @Override
    public List<ManageAccountDto> getOnline() {
        return manageAccountExtDao.getOnlineAccount();
    }

    @Override
    public List<B2bPaymentDto> getPaymentList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisable", 1);
        return b2bPaymentDao.searchList(map);
    }

    @Override
    public PageModel<B2bOrderExtDto> searchOrderMList(QueryModel<B2bOrderExtDto> qm) {
        PageModel<B2bOrderExtDto> pm = new PageModel<B2bOrderExtDto>();
        Map<String, Object> map = getStringObjectMap(qm);
        // 查询登陆账户信息
        MarketingPersonnelDto dto =this.getAccountName(qm.getEntity().getAccountPk());
        int totalCount = 0;
        List<B2bOrderExtDto> list = new ArrayList<B2bOrderExtDto>();
        if(null != dto){
            if (dto.getType() == 1) {//业务员账号登录
                map.put("businessPk", qm.getEntity().getAccountPk());
                totalCount = orderMemberDao.searchOrderCount(map);
                list = orderMemberDao.searchOrderList(map);
            }else if (dto.getType() == 2){ //平台交易员登录,看所有的订单
                totalCount = b2bOrderExtDao.searchOrderMCount(map);
                list = b2bOrderExtDao.searchOrderMList(map);
            }else if (dto.getType() == 3){//区域经理
                B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                if (regionDto != null && regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                        List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                        map.put("regionList", regionJsons);
                        map.put("loginPk", qm.getEntity().getAccountPk());
                        totalCount = orderMemberDao.searchOrderCount(map);
                        list = orderMemberDao.searchOrderList(map);
                }else{//区域总监看见所有
                   totalCount = b2bOrderExtDao.searchOrderMCount(map);
                    list = b2bOrderExtDao.searchOrderMList(map);
                }
            }
        }
        if(totalCount>0){
        	for (B2bOrderExtDto d : list) {
                Criteria criatira = new Criteria();
                criatira.andOperator(Criteria.where("orderNumber").is(d.getOrderNumber()), Criteria.where("type").is(1));
                List<B2bPayVoucher> imgUrls = mongoTemplate.find(new Query(criatira), B2bPayVoucher.class);
                if (null != imgUrls && imgUrls.size() != 0) {
                    if (!CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_VOUCHERLIST)) {
                        d.setVoucherList(null);
                    } else {
                        d.setVoucherList(imgUrls);
                    }
                }
            }
        }
        ManageAccount account = new ManageAccount();
        account.setPk(qm.getEntity().getAccountPk());
        if(list != null && list.size() > 0){
            for (B2bOrderExtDto extDto:list) {
                OrderExportUtil.setMarketOrderJsonInfo(extDto,account);
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    private MarketingPersonnelDto getAccountName(String accountPk) {

        Map<String, Object> mapAccount = new HashMap<>();
        mapAccount.put("accountPk", accountPk);
        mapAccount.put("isDelete", 1);
        mapAccount.put("isVisable", 1);
        return  marketingPersonnelDao.getByMap(mapAccount);
    }

    private Map<String, Object> getStringObjectMap(QueryModel<B2bOrderExtDto> qm) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        map.put("purchaserName", qm.getEntity().getPurchaserName());
        map.put("storeName", qm.getEntity().getStoreName());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("orderStatus", qm.getEntity().getOrderStatus());
        map.put("block", qm.getEntity().getBlock());
        map.put("memberPk", qm.getEntity().getMemberPk());
        map.put("source", qm.getEntity().getSource());
        map.put("paymentStartTime", qm.getEntity().getPaymentStartTime());
        map.put("paymentEndTime", qm.getEntity().getPaymentEndTime());
        map.put("paymentType", qm.getEntity().getPaymentType());
        map.put("receivablesStartTime", qm.getEntity().getReceivablesStartTime());
        map.put("receivablesEndTime", qm.getEntity().getReceivablesEndTime());
        map.put("colPurchaserName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_PURCHASERNAME));
        map.put("colInvoiceName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_INVOICENAME));
        map.put("colStoreName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_STORENAME));
        map.put("colPurPerson", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_PURPERSON));
        map.put("colSupPerson", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_SUPPERSON));
        map.put("colAddress", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_ADDRESS));
        map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_CONTACTS));
        return map;
    }

    @Override
    public List<B2bOrderExtDto> exportOrderMList(QueryModel<B2bOrderExtDto> qm,int type) {
    	 Map<String, Object> map = getExportObjectMap(qm);
         //查找业务员
         // 查询登陆账户信息
         MarketingPersonnelDto dto =this.getAccountName(qm.getEntity().getAccountPk());
         List<B2bOrderExtDto> list = new ArrayList<B2bOrderExtDto>();
         if(null != dto){
             if (dto.getType() == 1) {//业务员账号登录
                 map.put("businessPk", qm.getEntity().getAccountPk());
                 list = orderMemberDao.exportOrderList(map);
             }else if (dto.getType() == 2){ //平台交易员登录,看所有的订单
            	 list = b2bOrderExtDao.exportOrderGoodsMList(map);
             }else if (dto.getType() == 3){//区域经理
                 B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                 if (regionDto != null&&regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                         List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                         map.put("regionList", regionJsons);
                         map.put("loginPk", qm.getEntity().getAccountPk());
                         list = orderMemberDao.exportOrderList(map);
                 }else{//区域总监看见所有
                	 list = b2bOrderExtDao.exportOrderGoodsMList(map);
                 }
             }
         }
         if (type==1) {
        	 ManageAccount account  = new ManageAccount();
             account.setPk(qm.getEntity().getAccountPk());
             account.setRolePk(qm.getEntity().getRolePk());
             
              if (list != null && list.size() > 0){
     				List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(account.getRolePk());					
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
		}
        return list;
    }

    @Override
    public int saveOrderMExcelToOss(OrderDataTypeParams params, ManageAccount account) {
        Date time = new Date();
        try {
            Map<String,String> insertMap = CommonUtil.checkExportTime(params.getInsertStartTime(), params.getInsertEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
            Map<String,String> payMap = CommonUtil.checkExportTime(params.getPaymentStartTime(), params.getPaymentEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
            Map<String,String> receMap = CommonUtil.checkExportTime(params.getReceivablesStartTime(), params.getReceivablesEndTime(), new SimpleDateFormat("yyyy-MM-dd").format(time));
            params.setInsertStartTime(insertMap.get("startTime"));
            params.setInsertEndTime(insertMap.get("endTime"));
            params.setPaymentStartTime(payMap.get("startTime"));
            params.setPaymentEndTime(payMap.get("endTime"));
            params.setReceivablesStartTime(receMap.get("startTime"));
            params.setReceivablesEndTime(receMap.get("endTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtils.convertToString(params);
        SysExcelStore store = new SysExcelStore();
        store.setPk(KeyUtils.getUUID());
        store.setMethodName("exportOrderM_"+params.getUuid());
        store.setParams(json);
        store.setIsDeal(Constants.TWO);

        store.setInsertTime(time);
        store.setParamsName(ExportDoJsonParams.doOrderRunnableParams(params,time));
        store.setName("营销中心-订单管理");
        store.setType(Constants.TWO);
        store.setAccountPk(account.getPk());
        return sysExcelStoreExtDao.insert(store);
    }

    private Map<String, Object> getExportObjectMap(QueryModel<B2bOrderExtDto> qm) {
    	   Map<String, Object> map = new HashMap<String, Object>();
           map.put("orderName", qm.getFirstOrderName());
           map.put("orderType", qm.getFirstOrderType());
           map.put("orderNumber", qm.getEntity().getOrderNumber());
           map.put("purchaserName", qm.getEntity().getPurchaserName());
           map.put("storeName", qm.getEntity().getStoreName());
           map.put("insertStartTime", qm.getEntity().getInsertStartTime());
           map.put("insertEndTime", qm.getEntity().getInsertEndTime());
           map.put("orderStatus", qm.getEntity().getOrderStatus());
           map.put("memberPk", qm.getEntity().getMemberPk());
           map.put("block", qm.getEntity().getBlock());
           map.put("source", qm.getEntity().getSource());
           map.put("paymentStartTime", qm.getEntity().getPaymentStartTime());
           map.put("paymentEndTime", qm.getEntity().getPaymentEndTime());
           map.put("loginPk", qm.getEntity().getLoginPk());
           map.put("paymentType", qm.getEntity().getPaymentType());
           map.put("receivablesStartTime", qm.getEntity().getReceivablesStartTime());
           map.put("receivablesEndTime", qm.getEntity().getReceivablesEndTime());
           
           map.put("colPurchaserName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_PURCHASERNAME));
           map.put("colStoreName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_STORENAME));
           map.put("colPurPerson", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_PURPERSON));
           map.put("colSupPerson", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_SUPPERSON));
           map.put("colAddress", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_ADDRESS));
           map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_CONTACTS));
           map.put("colContactsTel", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_CONTACTSTEL));
           map.put("colSupComName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_SUPCOMNAME));
           map.put("colPlantName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_PLANTNAME));
           map.put("colMemberName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.MARKET_ORDER_COL_MEMBERNAME));
		return map;
	}

	@Override
    public PageModel<B2bManageRegionExtDto> searchManageRegionList(QueryModel<B2bManageRegionExtDto> qm) {
        PageModel<B2bManageRegionExtDto> pm = new PageModel<B2bManageRegionExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("provinceName", qm.getEntity().getProvinceName());
        map.put("cityName", qm.getEntity().getCityName());
        map.put("areaName", qm.getEntity().getAreaName());
        int totalCount = b2bManageRegionExtDao.searchGridExtCount(map);
        List<B2bManageRegionExtDto> list = b2bManageRegionExtDao.searchGridExt(map);
        if (list != null && list.size() > 0) {
            for (B2bManageRegionExtDto dtos : list) {
                if(dtos.getArea() != null && !"".equals(dtos.getArea())){
                    JSONArray array = JSONArray.fromObject(dtos.getArea());
                    String area = "";
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String provinceName = obj.get("provinceName") == null ? "" : obj.get("provinceName").toString();
                        String cityName = obj.get("cityName") == null ? "" : obj.get("cityName").toString();
                        String areaName = obj.get("areaName") == null ? "" : obj.get("areaName").toString();
                        area += provinceName + "-" + cityName;
                        if (!"".equals(areaName)) {
                            area += "-" + areaName + "；";
                        } else {
                            area += "；";
                        }
                    }
                        dtos.setArea(area);
                }
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public B2bManageRegionDto getSearchManageRegionByPk(String pk) {

        return b2bManageRegionExtDao.getByPk(pk);
    }

    @Override
    public int updateManageRegion(B2bManageRegionExtDto dto) {

        int result = 0;
        if (dto != null) {
            // 修改
            if (dto.getPk() != null && !"".equals(dto.getPk())) {
                dto.setUpdateTime(new Date());
                if (dto.getIsVisable() != null && dto.getIsVisable() == Constants.TWO) {
                    // 删除也分配到的人员数据
                    result = personnelDao.updateRegion(dto.getPk());
                }
                result = b2bManageRegionExtDao.updateObj(dto);
            } else {
                // 新增
                B2bManageRegion region = new B2bManageRegion();
                region.setPk(KeyUtils.getUUID());
                region.setIsVisable(Constants.ONE);
                region.setUpdateTime(new Date());
                region.setInsertTime(new Date());
                region.setName(dto.getName());
                region.setArea(dto.getArea());
                result = b2bManageRegionExtDao.insert(region);
            }
        }
        return result;
    }

    @Override
    public int delManageRegion(String pk) {
        // 删除也分配到的人员数据
        personnelDao.updateRegion(pk);
        return b2bManageRegionExtDao.delete(pk);
    }

    @Override
    public PageModel<B2bCompanyExtDto> searchStoreListByAccount(QueryModel<B2bCompanyExtDto> qm, ManageAccount account) {
        PageModel<B2bCompanyExtDto> pm = new PageModel<B2bCompanyExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("companyType", qm.getEntity().getCompanyType());
        map.put("name", qm.getEntity().getName());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("distributeMemberStartTime", qm.getEntity().getDistributeMemberStartTime());
        map.put("distributeMemberEndTime", qm.getEntity().getDistributeMemberEndTime());
        map.put("accountPk", qm.getEntity().getAccountPk());
        map.put("mobile", qm.getEntity().getMobile());
        map.put("isDistribute", qm.getEntity().getIsDistribute());
        map.put("province", qm.getEntity().getProvinceName());
        map.put("city", qm.getEntity().getCityName());
        map.put("area", qm.getEntity().getAreaName());
        map.put("block", Constants.BLOCK_CF);
        // 设置隐藏字段
            setSupCol(account, map);
        // 查询登陆账户信息
        Map<String, Object> mapAccount = new HashMap<>();
        mapAccount.put("accountPk", account.getPk());
        mapAccount.put("isDelete", 1);
        mapAccount.put("isVisable", 1);
        MarketingPersonnelDto dto = marketingPersonnelDao.getByMap(mapAccount);
        int totalCount = 0;
        List<B2bCompanyExtDto> list = new ArrayList<B2bCompanyExtDto>();
        List<MarketingPersonnelDto> regions = null;
        if (null != dto) {
            if (dto.getType() == 1) { // 业务经理:查看分配名下的公司
                map.put("businessPk", account.getPk());
                totalCount = b2bCompanyExtDao.searchStoreCount(map);
                list = b2bCompanyExtDao.searchAuditStoreList(map);
                // 查找区域经理
                if (totalCount > 0) {
                    // 匹配区域经理
                    matchRegionName(list, dto.getType());
                }
            } else if (dto.getType() == 2) {// 平台交易员:未分配所属大区查看所有公司；已分配所属大区，查看分配下的所属大区
                if (dto.getRegionPk() != null && !dto.getRegionPk().equals("")) {
                    B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                    if (regionDto != null) {
                        if (regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                            List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                            map.put("regionList", regionJsons);
                            // 查询所属区域经理
                            Map<String, Object> param = new HashMap<String, Object>();
                            param.put("isDelete", 1);
                            param.put("isVisable", 1);
                            param.put("type", 3);
                            param.put("regionPk", dto.getRegionPk());
                            regions = marketingPersonnelDao.searchList(param);
                        }
                    }
                    
                }
                totalCount = b2bCompanyExtDao.searchStoreCount(map);
                list = b2bCompanyExtDao.searchAuditStoreList(map);
                // 查找区域经理:有分配区域，查找改区域是否有区域经理，反之匹配
                if (totalCount > 0) {
                    if (regions != null && regions.size() > 0) {
                        for (B2bCompanyExtDto b2bCompanyExtDto : list) {
                            b2bCompanyExtDto.setRegionName(regions.get(0).getAccountName());
                            b2bCompanyExtDto.setType(dto.getType());
                        }
                    } else {
                        matchRegionName(list, dto.getType());
                    }
                }
            } else if (dto.getType() == 3) {// 区域主管
                B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
                if (regionDto != null) {
                    if (regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                        List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
                        map.put("regionList", regionJsons);
                        map.put("loginPk", account.getPk());
                    }
                }
                totalCount = b2bCompanyExtDao.searchStoreCount(map);
                list = b2bCompanyExtDao.searchAuditStoreList(map);
                // 查找区域经理
                if (totalCount > 0 && regionDto.getArea() != null && !regionDto.getArea().equals("")) {
                    for (B2bCompanyExtDto b2bCompanyExtDto : list) {
                        b2bCompanyExtDto.setRegionName(account.getName());
                        b2bCompanyExtDto.setType(dto.getType());
                    }
                }else{
                    matchRegionName(list, dto.getType());
                }
            }
        }
        for(B2bCompanyExtDto b2bCompanyExtDto : list){
            if(!(boolean) map.get("colRegionName")){
                b2bCompanyExtDto.setRegionName(CommonUtil.hideContacts(b2bCompanyExtDto.getRegionName()));
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public List<MarketingPersonnelDto> getDistributePerson() {
        
        return personnelDao.getDistributePerson();
    }

	@Override
	public ManageAccountDto getAccountByMap(Map<String, Object> map) {
		return personnelDao.getAccountByMap(map);
	}

	@Override
	public List<ManageAccountExtDto> getRegionalAccountByMap() {
		return personnelDao.getRegionalAccountByMap();
	}

}
