	package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.xingye.DesUtils;
import cn.cf.common.creditpay.xingye.PostUtils;
import cn.cf.dao.B2bEconomicsBankCompanyDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.property.PropertyConfig;
import cn.cf.service.creditpay.BankXingyeService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class BankXingyeServiceImpl implements BankXingyeService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bEconomicsBankCompanyDaoEx bankCompanyDaoEx;

	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,B2bCreditGoodsDto dto) {
		BankBaseResp resp =  null;
		String id =null;
		try {
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("service", "cib.netpay.hxh.loan");
			map.put("ver", "01");
			map.put("mrch_no", PropertyConfig.getProperty("xy_shop_no"));
			map.put("uscc", company.getOrganizationCode());
			map.put("oper", "1");
			id = this.saveBankInfo(null, null,
					map.toString(), null,
					"active","customer");
			String rest  =  PostUtils.post(PostUtils.getPostParams(map));
			this.saveBankInfo(null, id, map.toString(),
					rest, "active","customer");
			if(null != rest && !"".equals(rest)){
				if(!getJsonByXml(rest).isNull("LoanInfo")){
					JSONObject json = getJsonByXml(rest).getJSONObject("LoanInfo");
					resp = new BankBaseResp();
					List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
					B2bEconomicsBankCompanyDto ebc = new B2bEconomicsBankCompanyDto();
					String customerNumber = json.get("ncid").toString();//银行客户号
//					String companyName = json.getString("firmName");//公司名称
//					String currency = json.getString("currency");//币种
					Double contractAmount = json.getDouble("contractAmount");//贷款额度
					Double useAmount = json.getDouble("usedAmount");//已用额度
					String startDate = json.get("effectdate").toString();//生效开始日期
					String endDate = json.get("maturitydate").toString();//生效结束日期
					ebc.setPk(KeyUtils.getUUID());
					ebc.setCreditAmount(contractAmount);
//					ebc.setCreditPk(company.getCreditPk());
					ebc.setCompanyPk(company.getPk());
					ebc.setBankPk(dto.getBankPk());
					ebc.setBankName(dto.getBank());
					ebc.setCreditStartTime(DateUtil.parseDateFormatYMD(startDate));
					ebc.setCreditEndTime(DateUtil.parseDateFormatYMD(endDate));
					ebc.setContractAmount(contractAmount);
					//可用额度=贷款额度-已用额度
					ebc.setAvailableAmount(ArithUtil.round(ArithUtil.sub(contractAmount, useAmount), 2));
					ebc.setAmountType("1");//目前写死
					ebc.setCompanyPk(company.getPk());
//					ebc.setCreditPk(company.getCreditPk());
					ebc.setCustomerNumber(customerNumber);
					ebc.setType(1);//化纤白条类型
					ebcList.add(ebc);
					resp.setEbcList(ebcList);	
				}
			}
		} catch (Exception e) {
			logger.error("errorSearchAmount",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp =  null;
		String id = null;
		try {
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("service", "cib.netpay.hxh.loan");
			map.put("ver", "01");
			map.put("mrch_no", PropertyConfig.getProperty("xy_shop_no"));
			map.put("ord_no", loanNumber.getOrderNumber());
			map.put("oper", "2");
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","loan");
			String rest  =  PostUtils.post(PostUtils.getPostParams(map));
			this.saveBankInfo(null, id, map.toString(),
					 rest, "active","loan");
			if(null != rest && !"".equals(rest)){
				JSONObject json = getJsonByXml(rest);
				if(!json.isNull("LoanInfo")){
					json = json.getJSONObject("LoanInfo");
					if(!json.isNull("loanNo")){
						resp = new BankBaseResp();
						//放款开始日期
						String loanStartTime = json.get("openTime").toString();
						//放款结束日期
						String loanEndTime = json.get("utilTime").toString();
						//借据编号
						String iouNumber = json.get("loanNo").toString();
						//贷款账号
						String accountNo = json.get("accountNo").toString();
						BankCreditDto cdto = new BankCreditDto();
						cdto.setLoanStartDate(DateUtil.parseDateFormatYMD(loanStartTime));
						cdto.setLoanEndDate(DateUtil.parseDateFormatYMD(loanEndTime));
						cdto.setIouNumber(iouNumber);
						cdto.setPayStatus(1);
						cdto.setLoanAccount(accountNo);
						resp.setBankCreditDto(cdto);	
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorSearchLoan",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp =  null;
		String id = null;
		Map<String,Object> smap = new HashMap<String,Object>();
		smap.put("companyPk", loanNumber.getPurchaserPk());
		List<B2bEconomicsBankCompanyDto> economicsCompanys = bankCompanyDaoEx.searchList(smap);
		try {
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("service", "cib.netpay.hxh.loan");
			map.put("ver", "01");
			map.put("mrch_no", PropertyConfig.getProperty("xy_shop_no"));
			map.put("ord_no", loanNumber.getOrderNumber());
			if(null != economicsCompanys && economicsCompanys.size()>0 && null !=economicsCompanys.get(0).getCreditStartTime()){
				map.put("begin_time", DateUtil.formatYearMonthDayTwo(economicsCompanys.get(0).getCreditStartTime()));
			}else{
				map.put("begin_time", DateUtil.formatYearMonthDayTwo(new Date()));
			}
			map.put("end_time", DateUtil.formatYearMonthDayTwo(new Date()));
			map.put("oper", "3");
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","repayment");
			String rest  =  PostUtils.post(PostUtils.getPostParams(map));
			this.saveBankInfo(null, id, map.toString(),
					 rest, "active","repayment");
			Object o = getJsonByXml(rest).get("list");
			if(null != o && !"".equals(o)){
				JSONArray array = new JSONArray();
				try {
					array = getJsonByXml(rest).getJSONObject("list").getJSONArray("RepaymentLog");
				} catch (Exception e) {
					JSONObject j = getJsonByXml(rest).getJSONObject("list").getJSONObject("RepaymentLog");
					array.put(j);
				}
				if(null != array && array.length()>0){
					List<B2bRepaymentRecord> brpdList = new ArrayList<B2bRepaymentRecord>();
					Double repaymentAmount = 0d;
					resp = new BankBaseResp();
					for (int i = 0; i < array.length(); i++) {
						JSONObject json = array.getJSONObject(i);
						B2bRepaymentRecord brpr = new B2bRepaymentRecord();
						brpr.setOrderNumber(loanNumber.getOrderNumber());
						brpr.setIouNumber(loanNumber.getLoanNumber());
						brpr.setStatus(1);
						brpr.setCompanyPk(loanNumber.getPurchaserPk());
						brpr.setCompanyName(loanNumber.getPurchaserName());
						brpr.setBankPk(loanNumber.getBankPk());
						brpr.setBankName(loanNumber.getBankName());
						brpr.setAmount(json.getDouble("transAmount"));
						brpr.setCreateTime(json.get("transTime").toString());
						brpr.setOrganizationCode(loanNumber.getOrganizationCode());
						brpdList.add(brpr);
						repaymentAmount = ArithUtil.add(repaymentAmount, brpr.getAmount());
						if(i == array.length()-1){
							resp.setRepaymentDate(DateUtil.numberToString(brpr.getCreateTime()));
						}
					}
					if(loanNumber.getLoanAmount().toString().equals(ArithUtil.round(repaymentAmount, 2)+"")){
						resp.setLoanStatus(5);//5全部还款 
					}else{
						resp.setLoanStatus(6);//6部分还款
					}
					resp.setB2bRepaymentRecordList(brpdList);
				}
			}
		} catch (Exception e) {
			logger.error("errorRepayment",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card,B2bCreditGoodsDtoMa cgDto) {
		BankBaseResp resp = null;
		String id = null;
		try {
			LinkedHashMap<String,String> map = new  LinkedHashMap<String,String>();
			map.put("service", "cib.netpay.hxh.pay");//服务名称
			map.put("ver", "01");//接口版本号
			map.put("mrch_no", PropertyConfig.getProperty("xy_shop_no"));
			map.put("ord_no", order.getOrderNumber());
			map.put("ord_date", DateUtil.formatYearMonthDayTwo(order.getInsertTime()));
			map.put("cur", "01");
			map.put("ord_amt", order.getActualAmount()+"");
			map.put("ncid", cgDto.getBankAccountNumber());
			map.put("payee_platNo", order.getSupplierPk());
			map.put("payee_name", order.getSupplier().getSupplierName());
			map.put("payee_acctNo", DesUtils.encryptByDES(PropertyConfig.getProperty("xy_secret_key"),card.getBankAccount()));
			map.put("payee_bankName", card.getBankName());
			map.put("payee_bankNo", card.getBankNo());
			map.put("plat_name", "化纤汇");
			Map<String,String> requestMap = PostUtils.getPostParams(map);
			requestMap.put("desc", "采购");
			id = this.saveBankInfo(null, null,
					requestMap.toString(), null,
						"active","pay");
			String rest  =  PostUtils.post(requestMap);
			this.saveBankInfo(null, id, map.toString(),
					 rest, "active","pay");
			if(null != rest && !"".equals(rest)){
				resp = new BankBaseResp();
				JSONObject json = getJsonByXml(rest);
				if(!getJsonByXml(rest).isNull("PayInfo")){
					json = getJsonByXml(rest).getJSONObject("PayInfo");
					if(!json.isNull("orderStatus") && ("2".equals(json.get("orderStatus").toString()) || "0".equals(json.get("orderStatus").toString()) || "1".equals(json.get("orderStatus").toString()))){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg("银行信息数据异常");
					}
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg(json.getString("msg"));
				}
			}
		} catch (Exception e) {
			logger.error("payOrder",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		String id = null;
		try {
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("service", "cib.netpay.hxh.que");//服务名称
			map.put("ver", "01");//接口版本号
			map.put("mrch_no", PropertyConfig.getProperty("xy_shop_no"));
			map.put("ord_no", loanNumber.getOrderNumber());
			map.put("oper", "2");
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","cancel");
			String rest  =  PostUtils.post(PostUtils.getPostParams(map));
			this.saveBankInfo(null, id, map.toString(),
					 rest, "active","cancel");
			if(null != rest && !"".equals(rest)){
				resp = new BankBaseResp();
				if(!getJsonByXml(rest).isNull("PayInfo")){
					JSONObject json = getJsonByXml(rest).getJSONObject("PayInfo");
					if(!json.isNull("orderStatus")&&"5".equals(json.get("orderStatus").toString())){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(json.getString("msg"));
					}
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg(getJsonByXml(rest).getString("msg"));
				}
			}	
		}catch (Exception e){
			logger.error("errorCancelOrder",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchOrder(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		String id = null;
		try {
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("service", "cib.netpay.hxh.que");//服务名称
			map.put("ver", "01");//接口版本号
			map.put("mrch_no", PropertyConfig.getProperty("xy_shop_no"));
			map.put("ord_no", loanNumber.getOrderNumber());
			map.put("oper", "1");
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","order");
			String rest  =  PostUtils.post(PostUtils.getPostParams(map));
			this.saveBankInfo(null, id, map.toString(),
					 rest, "active","order");
			if(null != rest && !"".equals(rest)){
				if(!getJsonByXml(rest).isNull("PayInfo")){
					JSONObject json = getJsonByXml(rest).getJSONObject("PayInfo");
					if(null != json){
						resp = new BankBaseResp();
						if(!json.isNull("orderStatus")&&"1".equals(json.get("orderStatus").toString())){
							resp.setPayStatus(1);
						}else{
							resp.setPayStatus(2);
						}
					}
				}
			}	
		}catch (Exception e){
			logger.error("errorCancelOrder",e);
		}
		return resp;
	}
	
	public  JSONObject getJsonByXml(String xml) throws JSONException{
		JSONObject xmlJSONObj = null;
		try {
			xmlJSONObj = XML.toJSONObject(xml);
		} catch (JSONException e) {
			logger.error("errorJson",e);
		}
		//失败
		if(xmlJSONObj.isNull("netpay_resp")){
			xmlJSONObj = xmlJSONObj.getJSONObject("netpay_err");
		//成功
		}else{
			xmlJSONObj = xmlJSONObj.getJSONObject("netpay_resp");
		}
       return xmlJSONObj;
	}
	
	private String saveBankInfo(String code, String id, String requestValue,
			 String responseValue, String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("xingyeBank");
			bank.setRequestValue(requestValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setRequestMode(requestMode);
			bank.setHxhCode(hxhCode);
			mongoTemplate.save(bank);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, BankInfo.class);
			return id;
		}
	}
}
