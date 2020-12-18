package cn.cf.service.enconmics.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.entity.CreditInfo;
import cn.cf.entity.CreditInvoiceDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.*;
import cn.cf.property.PropertyConfig;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.service.enconmics.CustomerService;
import cn.cf.service.enconmics.EconomicsCreditService;
import cn.cf.util.KeyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "economicsCreditService")
public class EconomicsCreditServiceImpl implements EconomicsCreditService {

	@Autowired
	private B2bOrderExtDao orderDao;
	
	@Autowired
	private B2bCreditExtDao b2bCreditDao;

	@Autowired
	private B2bCreditInvoiceExtDao b2bCreditInvoiceExtDao;

	@Autowired
	private B2bBillCustomerExtDao b2bBillCustomerExtDao;
	
	@Autowired
	private B2bCreditGoodsExtDao b2bCreditGoodsDao;
	
	@Autowired
	private B2bEconomicsBankCompanyDao economicsBankCompanyDao;
	
	@Autowired
	private B2bEconomicsBankCompanyExtDao b2bEconomicsBankCompanyExtDao;
	
	@Autowired
	private B2bEconomicsBankExtDao economicsBankExDao;

	@Autowired
	private B2bEconomicsCustomerExtDao economicsCustomerExtDao;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bEconomicsCustomerGoodsExDao economicsCustomerGoodsExDao;

	@Autowired
	private B2bBillCusgoodsExtDao b2bBillCusgoodsExtDao;

	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;



	@Override
	public PageModel<B2bEconomicsBankExtDto> economicsBankList(QueryModel<B2bEconomicsBankExtDto> qm) {
		PageModel<B2bEconomicsBankExtDto> pm = new PageModel<B2bEconomicsBankExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("bankName", qm.getEntity().getBankName());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("gateway", qm.getEntity().getGateway());
		//map.put("creditPk", qm.getEntity().getCreditPk());
		int totalCount = economicsBankExDao.searchEconomicsBankCounts(map);
		List<B2bEconomicsBankExtDto> list = economicsBankExDao.searchEconomicsBankList(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int inserOrUpdateEconomicsBank(B2bEconomicsBank bank) {
		// 有传名称的时候判断是否唯一
		if (bank.getBankName() != null && !"".equals(bank.getBankName())) {
			B2bEconomicsBank ecnomicsBank = economicsBankExDao.searchEconomicsBankByName(bank);
			if (ecnomicsBank != null) {
				return 2;
			}
		}
		int result = 0;
		if (bank.getPk() != null && !"".equals(bank.getPk())) {
			result = economicsBankExDao.updateEconomicsBank(bank);
		} else {
			bank.setPk(KeyUtils.getUUID());
			bank.setIsVisable(1);
			result = economicsBankExDao.insertEconomicsBank(bank);
		}
		return result;
	}

	@Override
	public PageModel<B2bEconomicsBankCompanyDto> searchAuthorizedBanks(Map<String, Object> map) {
		PageModel<B2bEconomicsBankCompanyDto> pm = new PageModel<B2bEconomicsBankCompanyDto>();
		List<B2bEconomicsBankCompanyDto> list = economicsBankExDao.searchAuthorizedBanks(map);
		int counts = economicsBankExDao.searchAuthorizedBankCounts(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public List<B2bEconomicsBankCompanyDto> getEconomicsBankCompanyByPk(String companyPk) {
		if (companyPk != null) {
			return b2bEconomicsBankCompanyExtDao.getByCompanyPk(companyPk);
		}
		return null;
	}

	@Override
	public List<B2bEconomicsBankCompanyDto> getEconomicsBankCompanyByMap(Map<String, Object> map) {
		return b2bEconomicsBankCompanyExtDao.getEconomicsBankCompanyByMap(map);
	}

//	@Override
//	public int auditCreditCompay(B2bCreditExtDto credit, List<EconomicsBank> list) {
//		int result = 0;
//		try {
//
//			// 删除所有已经选择的银行
//			// Map<String,Object> map = new HashMap<String,Object>();
//			// map.put("creditPk", credit.getPk());
//			// economicsBankDao.delCompanyBank(map);
//
//			if (list != null && list.size() > 0) {
//				// 重新新增现选的银行
//				for (int i = 0; i < list.size(); i++) {
//					B2bEconomicsBankCompanyDto bankCompanyDto = b2bEconomicsBankCompanyExtDao
//							.getByCreditPk(credit.getPk());
//					if (bankCompanyDto != null) {
//						bankCompanyDto.setCreditPk(credit.getPk());
//						bankCompanyDto.setCompanyPk(credit.getCompanyPk());
//						bankCompanyDto.setBankPk(list.get(i).getPk());
//						bankCompanyDto.setBankName(list.get(i).getBankName());
//						B2bEconomicsBankCompany bankCompany = new B2bEconomicsBankCompany();
//						b2bEconomicsBankCompanyExtDao.update(bankCompanyDto);
//					} else {
//						Map<String, Object> mp = new HashMap<String, Object>();
//						mp.put("pk", KeyUtils.getUUID());
//						mp.put("creditPk", credit.getPk());
//						mp.put("companyPk", credit.getCompanyPk());
//						mp.put("bankPk", list.get(i).getPk());
//						mp.put("bankName", list.get(i).getBankName());
//						economicsBankExDao.insertCompanyBank(mp);
//					}
//				}
//				// 如果是审核通过 置空原因
//				// company.setCreditReason("");
//			}
//			// 修改授信表状态
//			credit.setCreditAuditTime(new Date());// 授信审核时间
//			if (credit.getCreditReason() == null) {
//				credit.setCreditReason("");
//			}
//			b2bCreditDao.update(credit);
//			result = 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	@Override
	public int updateOrder(B2bOrder order) {
		int retVal = 0;
		if (order.getOrderNumber() != null && order.getOrderNumber() != "") {
			retVal = orderDao.updateOrder(order);
		}
		return retVal;
	}

	@Override
	public B2bCreditDto getB2bcreditByPk(String pk) {
		return b2bCreditDao.getCreditPhoneByPk(pk);
	}


	@Override
	public int updateCreditCustomer(B2bCreditGoodsExtDto creditGoodsExtDto) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (creditGoodsExtDto.getEconomicsBankCompanyPk() != null
				&& !creditGoodsExtDto.getEconomicsBankCompanyPk().equals("")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pk", creditGoodsExtDto.getEconomicsBankCompanyPk());
			map.put("creditStartTime", creditGoodsExtDto.getBankCreditStartTime());
			map.put("creditEndTime", creditGoodsExtDto.getBankCreditEndTime());
			map.put("creditAmount", creditGoodsExtDto.getBankContractAmount());
			b2bEconomicsBankCompanyExtDao.updateCreditTime(map);
		}
		updateCreditAudit(creditGoodsExtDto.getPk());
		try {
			creditGoodsExtDto.setCreditEndTime(format.parse(creditGoodsExtDto.getEndTime()));
			creditGoodsExtDto.setCreditStartTime(format.parse(creditGoodsExtDto.getStartTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b2bCreditGoodsDao.updatePlatformAmount(creditGoodsExtDto);
	}

	@Override
	public PageModel<B2bCreditExtDto> searchCreditList(QueryModel<B2bCreditExtDto> qm) {
		PageModel<B2bCreditExtDto> pm = new PageModel<B2bCreditExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("creditStatus", qm.getEntity().getCreditStatus());
		
	     map.put("colCompanyName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CREDITCUST_COL_COM_NAME));
         map.put("colApplicants",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CREDITCUST_COL_APPLICANTS));
         map.put("colContactsTel",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_CREDITCUST_COL_CONTACTSTEL));

		int totalCount = b2bCreditDao.searchGridCountEx(map);
		List<B2bCreditExtDto> list = b2bCreditDao.searchGridEx(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<B2bBillCustomerExtDto> searchBillList(QueryModel<B2bBillCustomerExtDto> qm) {
		PageModel<B2bBillCustomerExtDto> pm = new PageModel<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("companyName", qm.getEntity().getCompanyName());
		map.put("status", qm.getEntity().getStatus());

		map.put("colCompanyName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_BILLCUST_COL_COM_NAME));
		map.put("colApplicants",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_BILLCUST_COL_APPLICANTS));
		map.put("colContactsTel",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.EM_BILLCUST_COL_CONTACTSTEL));
		int totalCount = b2bBillCustomerExtDao.searchGridExtCount(map);
		List<B2bBillCustomerExtDto> list = b2bBillCustomerExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<B2bBillCusgoodsExtDto> searchBillCustGoodsList(QueryModel<B2bBillCusgoodsExtDto> qm) {
		PageModel<B2bBillCusgoodsExtDto> pm = new PageModel<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("customerPk", qm.getEntity().getCustomerPk());
		List<B2bBillCusgoodsExtDto> list = b2bBillCusgoodsExtDao.searchGridExt(map);
		int count = b2bBillCusgoodsExtDao.searchGridExtCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<B2bCreditInvoiceExtDto> searchCreditInvoiceList(QueryModel<B2bCreditInvoiceExtDto> qm) {
		PageModel<B2bCreditInvoiceExtDto> pm = new PageModel<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("companyPk", qm.getEntity().getCompanyPk());
		map.put("dataType", qm.getEntity().getDataType());
		map.put("billingDateStart", qm.getEntity().getBillingDateStart());
		map.put("billingDateEnd", qm.getEntity().getBillingDateEnd());
		List<B2bCreditInvoiceExtDto> list = b2bCreditInvoiceExtDao.searchGridExt(map);
		int count = b2bCreditInvoiceExtDao.searchGridExtCount(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public PageModel<B2bCreditGoodsExtDto> searchCreditGoods(Map<String, Object> map) {
		PageModel<B2bCreditGoodsExtDto> pm = new PageModel<B2bCreditGoodsExtDto>();
		List<B2bCreditGoodsExtDto> list = b2bCreditGoodsDao.searchGrid(map);
		int counts = b2bCreditGoodsDao.searchGridCount(map);
		pm.setTotalCount(counts);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public B2bCreditDto getcreditByPk(String pk) {
		return b2bCreditDao.getByPk(pk);
	}

	@Override
	public int saveCreditInvioceExcelToOss(CreditInvoiceDataTypeParams params, ManageAccount account) {
		String paramsStr = "";
		try {
			if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getBillingDateStart(),params.getBillingDateEnd()))) {
				paramsStr += "查询时间："+CommonUtil.checkUpdateExportTime(params.getBillingDateStart(),params.getBillingDateEnd())+";";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("1".equals(params.getDataType())){
			paramsStr += "发票类型：进项;";
		}
		if ("2".equals(params.getDataType())){
			paramsStr += "发票类型：销项;";
		}
		Date time = new Date();
		String json = JsonUtils.convertToString(params);
		SysExcelStore store = new SysExcelStore();
		store.setPk(KeyUtils.getUUID());
		store.setMethodName("creditInvoice_"+params.getUuid());
		store.setParams(json);
		store.setIsDeal(Constants.TWO);
		store.setInsertTime(time);
		store.setName("金融中心-授信客户管理-发票查询");
		store.setParamsName(paramsStr);
		store.setType(Constants.ONE);
		store.setAccountPk(account.getPk());
		return sysExcelStoreExtDao.insert(store);
	}

	@Override
	public int updateCreditStatusAndRemarks(String companyPk, int status, String remarks) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		map.put("creditStatus", status);
		map.put("remarks", remarks);
		return b2bCreditDao.updateCreditStatusAndRemarks(map);
	}

	@Override
	public B2bCreditDto getByCompanyPk(String companyPk) {
		return b2bCreditDao.getByCompanyPk(companyPk);
	}

	@Override
	public void insertCreditInfo(B2bEconomicsCustomerExtDto dto, int status) {
		B2bCreditDto credit = b2bCreditDao.getByCompanyPk(dto.getCompanyPk());
		String creditPk = "";
		Date creditAuditTime =  new Date();
		Date creditInsertTime =  (null == dto.getInsertTime())? new Date() : dto.getInsertTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyPk", dto.getCompanyPk());
		map.put("companyName", dto.getCompanyName());
		map.put("creditStatus", Constants.ONE);
		map.put("creditContacts", dto.getContacts());
		map.put("creditContactsTel", dto.getContactsTel());
		map.put("creditAuditTime",null);
		map.put("creditInsertTime", creditInsertTime);
		map.put("isDelete", Constants.ONE);
		map.put("financeContacts", dto.getAssidirName());
		map.put("financePk", dto.getAssidirPk());
		map.put("source", dto.getSource());
		map.put("processInstanceId", dto.getProcessInstanceId());
		if (credit!=null) {
			creditPk = credit.getPk();
			map.put("pk",creditPk);
			b2bCreditDao.creditUpdate(map);
		}else{
			 creditPk = KeyUtils.getUUID();
			 map.put("pk",creditPk);
			 b2bCreditDao.insertCreditInfo(map) ;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("economicsCustomerPk", dto.getPk());
		List<B2bEconomicsCustomerGoodsDto> list = economicsCustomerGoodsExDao.searchList(param);
		if (list.size()>0) {
			for (B2bEconomicsCustomerGoodsDto customerGoodsDto : list) {
				B2bCreditGoods b2bCreditGoods = new B2bCreditGoods();
				map = new HashMap<String, Object>();
				map.put("creditPk", creditPk);
				map.put("economicsGoodsPk", customerGoodsDto.getEconomicsGoodsPk());
				B2bCreditGoodsDto goodsDto = b2bCreditGoodsDao. getByMap(map);
				if (goodsDto!=null) {
					b2bCreditGoods.setPk(goodsDto.getPk());
					//当银行，产品都相同，更新建议额度
					if(goodsDto.getBankPk().equals(customerGoodsDto.getBankPk())){
						b2bCreditGoods.setSuggestAmount(customerGoodsDto.getSuggestAmount());
						b2bCreditGoodsDao.updateB2bCreditGoods(b2bCreditGoods);
					}else{//产品相同，银行不同，删除，重新添加
						b2bCreditGoodsDao.deleteByPk(goodsDto.getPk());
						insertCreditGoods(dto, status, creditPk, creditAuditTime, creditInsertTime, customerGoodsDto,
								b2bCreditGoods);
					}
				}else{
					insertCreditGoods(dto, status, creditPk, creditAuditTime, creditInsertTime, customerGoodsDto,
							b2bCreditGoods);
				}
			}
		}
	}

	/**
	 * 新增金融产品
	 * @param dto
	 * @param status
	 * @param creditPk
	 * @param creditAuditTime
	 * @param creditInsertTime
	 * @param customerGoodsDto
	 * @param b2bCreditGoods
	 */
	private void insertCreditGoods(B2bEconomicsCustomerExtDto dto, int status, String creditPk, Date creditAuditTime,
			Date creditInsertTime, B2bEconomicsCustomerGoodsDto customerGoodsDto, B2bCreditGoods b2bCreditGoods) {
		b2bCreditGoods.setPk(KeyUtils.getUUID());
		b2bCreditGoods.setCompanyPk(dto.getCompanyPk());
		b2bCreditGoods.setCompanyName(dto.getCompanyName());
		b2bCreditGoods.setEconomicsGoodsPk(customerGoodsDto.getEconomicsGoodsPk());
		b2bCreditGoods.setEconomicsGoodsName(customerGoodsDto.getEconomicsGoodsName());
		b2bCreditGoods.setStatus(status);
		b2bCreditGoods.setCreditInsertTime(creditInsertTime);
		b2bCreditGoods.setCreditAuditTime(creditAuditTime);
		b2bCreditGoods.setCreditPk(creditPk);
		b2bCreditGoods.setGoodsType(customerGoodsDto.getGoodsType());
		b2bCreditGoods.setPlatformAmount(null);
		b2bCreditGoods.setPledgeUsedAmount(0.0d);
		b2bCreditGoods.setSuggestAmount(customerGoodsDto.getSuggestAmount());
		b2bCreditGoods.setBank(customerGoodsDto.getBankName());
		b2bCreditGoods.setBankPk(customerGoodsDto.getBankPk());
		//b2bCreditGoods.setCreditEndTime(customerGoodsDto.getEffectEndTime());
		//b2bCreditGoods.setCreditStartTime(customerGoodsDto.getEffectStartTime());
		b2bCreditGoods.setIsVisable(Constants.ONE);
		//计息利率初始化
		if (customerGoodsDto.getGoodsType()!=null&&customerGoodsDto.getGoodsType()==1) {
			b2bCreditGoods.setBankRate(6.0);
			b2bCreditGoods.setTotalRate(6.0);
			b2bCreditGoods.setTerm(30);
		}else if (customerGoodsDto.getGoodsType()!=null&&customerGoodsDto.getGoodsType()==2) {
			b2bCreditGoods.setSevenRate(6.8);
			b2bCreditGoods.setBankRate(7.0);
			b2bCreditGoods.setTotalRate(7.8);
			b2bCreditGoods.setTerm(60);
		}
		b2bCreditGoodsDao.insert(b2bCreditGoods);
	}

	@Override
	@Transactional
	public void updateEconCustomerToCredit(String companyPk) {
		// 金融申请记录中最新的申请通过的数据
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("companyPk").is(companyPk), Criteria.where("auditStatus").is(3));
		Query query = new Query(criteria);
		query.with(new Sort(new Order(Direction.DESC, "approveTime")));
		EconCustomerMongo econCustomerMongo = mongoTemplate.findOne(query, EconCustomerMongo.class);
		if (econCustomerMongo != null && !econCustomerMongo.equals("")) {
			Map<String, Object> map = new HashMap<>();
			map.put("creditStatus", Constants.TWO);
			map.put("creditContacts", econCustomerMongo.getContacts());
			map.put("creditContactsTel", econCustomerMongo.getContactsTel());
			map.put("source", econCustomerMongo.getSource());
			map.put("financeContacts", econCustomerMongo.getAssidirName());
			map.put("financePk", econCustomerMongo.getAssidirPk());
			map.put("processInstanceId", econCustomerMongo.getProcessInstanceId());
			map.put("companyPk", companyPk);
			map.put("companyName", econCustomerMongo.getCompanyName());
			map.put("creditAuditTime", econCustomerMongo.getApproveTime());
			map.put("creditInsertTime", econCustomerMongo.getInsertTime());
			b2bCreditDao.updateCreditInfo(map);
			
			// 删除b2bCreditGoods，插入新数据
			B2bCreditDto creditDto = b2bCreditDao.getByCompanyPk(companyPk);
			List<B2bCreditGoodsDto> creditGoodslist =b2bCreditGoodsDao. getByCompanyPk(companyPk);
			b2bCreditGoodsDao.deleteByCompanyPk(companyPk);
			String content = econCustomerMongo.getContent();
			List<B2bEconomicsCustomerGoodsDto> lst = JSON.parseArray(content, B2bEconomicsCustomerGoodsDto.class);
			if (null != lst) {
				for (B2bEconomicsCustomerGoodsDto customerGoodsDto : lst) {
					B2bCreditGoods b2bCreditGoods = new B2bCreditGoods();
					b2bCreditGoods.setPk(KeyUtils.getUUID());
					b2bCreditGoods.setCompanyPk(creditDto.getCompanyPk());
					b2bCreditGoods.setCompanyName(creditDto.getCompanyName());
					b2bCreditGoods.setEconomicsGoodsPk(customerGoodsDto.getEconomicsGoodsPk());
					b2bCreditGoods.setEconomicsGoodsName(customerGoodsDto.getEconomicsGoodsName());
					b2bCreditGoods.setStatus(2);//?
					b2bCreditGoods.setCreditInsertTime(econCustomerMongo.getInsertTime());
					b2bCreditGoods.setCreditAuditTime(econCustomerMongo.getApproveTime());
					b2bCreditGoods.setCreditPk(creditDto.getPk());
					b2bCreditGoods.setGoodsType(customerGoodsDto.getGoodsType());
					b2bCreditGoods.setPlatformAmount(null);
					b2bCreditGoods.setPledgeUsedAmount(0.0d);
					b2bCreditGoods.setSuggestAmount(customerGoodsDto.getSuggestAmount());
					b2bCreditGoods.setBank(customerGoodsDto.getBankName());
					b2bCreditGoods.setBankPk(customerGoodsDto.getBankPk());
//					b2bCreditGoods.setCreditEndTime(customerGoodsDto.getEffectEndTime());
//					b2bCreditGoods.setCreditStartTime(customerGoodsDto.getEffectStartTime());
					
					//计息利率初始化
					if (customerGoodsDto.getGoodsType()!=null&&(customerGoodsDto.getGoodsType()==1||customerGoodsDto.getGoodsType()==3)) {
						b2bCreditGoods.setTerm(30);
						b2bCreditGoods.setBankRate(6.0);
						b2bCreditGoods.setTotalRate(6.0);
						
					}else if (customerGoodsDto.getGoodsType()!=null&&(customerGoodsDto.getGoodsType()==2||customerGoodsDto.getGoodsType()==4)) {
						b2bCreditGoods.setSevenRate(6.8);
						b2bCreditGoods.setBankRate(7.0);
						b2bCreditGoods.setTotalRate(7.8);
						b2bCreditGoods.setTerm(60);
					}
					if (creditGoodslist.size()>0) {
						for (B2bCreditGoodsDto creditGoods : creditGoodslist) {
							if (customerGoodsDto.getBankPk()!=null&&customerGoodsDto.getBankPk().equals(creditGoods.getBankPk())) {
								b2bCreditGoods.setBankAccountNumber(creditGoods.getBankAccountNumber());
								break;
							}
						}
					}
					b2bCreditGoodsDao.insert(b2bCreditGoods);
				}
			}
		}
	}

	@Override
	public int updateCreditStatus(String companyPk, Integer status) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("companyPk", companyPk);
		 map.put("creditStatus", status);
		 int result = b2bCreditDao.updateCreditStatus(map) + b2bCreditGoodsDao.updateCreditGoodsStatus(map);
		if (result>1) {
			B2bEconomicsCustomerDto dto = economicsCustomerExtDao.getByCompanyPk(companyPk);
			if (status==2) {//通过
				dto.setAuditStatus(3);
			}
			if (status==3) {//不通过
				dto.setAuditStatus(4);
			}
			result = customerService.updateEconomics(dto);
			customerService.updateEconomicsMongo(dto.getPk());
		}
		return result ;
	}

	@Override
	public List<B2bCreditGoodsExtDto> searchCreditGoodsByCreditPk(Map<String, Object> map) {
		return b2bCreditGoodsDao.searchCreditGoodsByCreditPk(map);
	}

	@Override
	public int updateCreditIsVisable(B2bCreditGoodsExtDto cExtDto) {
		
		//授信更新为待审核
		updateCreditAudit(cExtDto.getPk());
		return b2bCreditGoodsDao.updateCreditIsVisable(cExtDto);
	}
	
	/**
	 * 授信修改、禁用、启用，要提交待审核
	 * @param pk
	 */
	private void updateCreditAudit(String pk) {
		B2bCreditGoodsDto dto = b2bCreditGoodsDao.getByPk(pk);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", dto.getCreditPk());
		map.put("creditStatus", Constants.ONE);
		map.put("creditAuditTime", "");
		b2bCreditDao.updateCreditInfo(map);
	}

	@Override
	public int updateBillAndGoods(B2bBillCustomerExtDto billExtDto) {
		return b2bBillCustomerExtDao.updateStatusByCompanyPk(billExtDto);
	}

	@Override
	public int updateCustGoodsStatus(B2bBillCusgoodsExtDto billExtDto) {
		return b2bBillCusgoodsExtDao.updateObj(billExtDto);
	}

	@Override
	public List<B2bTaxAuthorityDto> getB2bTaxAuthority(String taxAuthorityName) {
		Map<String,Object> map = new HashMap<>();
		map.put("taxAuthorityName",taxAuthorityName);
		return b2bCreditDao.getTaxAuthorityCode(map);
	}

	@Override
	public String importTaxAuthority(String pk,String taxNature, String taxAuthorityName,String taxAuthorityCode, MultipartFile file) {

		String backPath = "";
		String tempPath = PropertyConfig.getProperty("FILE_PATH");
		String fileName = KeyUtils.getUUID() + ".pdf";
		String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
		File files = new File(destFileNamePath);
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), files);
			backPath = OSSUtils.ossUploadXls(files, Constants.FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int result = 0;
		if (CommonUtil.isNotEmpty(backPath)){

			B2bCreditDto dto = new B2bCreditDto();
			dto.setPk(pk);
			if (CommonUtil.isNotEmpty(taxNature)){
				dto.setTaxNature(Integer.valueOf(taxNature));
			}
			dto.setTaxAuthorityCode(taxAuthorityCode);
			dto.setTaxAuthorityName(taxAuthorityName);
			dto.setDelegateCertUrl(backPath);
			result = b2bCreditDao.updateCreditOfBillTax(dto);
		}
		if (result > 0){
			return backPath;
		} else{
			return "";
		}
	}
	
	
	@Override
	public int updateCreditApply(CreditInfo info, String pk) {
		B2bCreditExtDto model = new  B2bCreditExtDto();
		model.setPk(pk);
		model.setCreditInfo(JsonUtils.convertToString(info));
		return b2bCreditDao.update(model);
	}
}
