package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.guangda.MacUtils;
import cn.cf.common.creditpay.guangda.PostUtils;
import cn.cf.common.creditpay.guangda.XmlUtils;
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
import cn.cf.service.creditpay.BankGuangdaService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
/**
 * 
 * @description:光大银行业务层实现
 * @author FJM
 * @date 2018-5-17 下午3:26:22
 */
@Service
public class BankGuangdaServiceImpl implements BankGuangdaService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void appMacKeyValue() {
		XmlUtils gdXml = new XmlUtils();
		String	id = "";
		String xmlStrs = gdXml.guangda9000();
		try {
			// 存前置通知
			id = this.saveBankInfo("9000", null,xmlStrs, null, "active",null);
			//获取密钥值
			String responseBody = PostUtils.post(PropertyConfig.getProperty("gd_bank_url"), "POST", gdXml.guangda9000());
			if(null != responseBody){
				//存后置通知
				this.saveBankInfo("9000", id, xmlStrs, responseBody, "active",null);
				try {
					//此responseBody不会返回mac 随机拼接16位数字
					JSONObject json = gdXml.getJsonByXml(responseBody+KeyUtils.getRandom(16));
					//写入密钥值
					JSONObject body = json.getJSONObject("Body");
					MacUtils.updateWorkKey(body.getString("macKeyValue"), body.getString("macVerifyValue"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,B2bCreditGoodsDto dto) {
		BankBaseResp resp =  null;
		XmlUtils xml = new XmlUtils();
		try {
			//发送报文请求
			String xmlStrs = xml.guangdaG337(company.getName(), company.getOrganizationCode());
			// 存前置通知
			xmlStrs = addMac(xmlStrs);
			String id = this.saveBankInfo("G337", null,xmlStrs, null, "active","customer");
			String responseBody = PostUtils.post(PropertyConfig.getProperty("gd_bank_url"), "POST", xmlStrs);
			if(null != responseBody){
				resp = new BankBaseResp();
				//存后置通知
				this.saveBankInfo("G337", id, xmlStrs, responseBody, "active","customer");
				JSONObject json = xml.getJsonByXml(responseBody);
				//业务回调
				JSONObject body = json.getJSONObject("Body");
				if(!body.isNull("RESULT_FLAG") && 1 == body.getInt("RESULT_FLAG")){
					JSONArray array = new JSONArray();
					if(body.getInt("Z_BISHU") == 1){//总笔数
						array.put(body.getJSONObject("LIST_OBJ"));
					}
					if(body.getInt("Z_BISHU") > 1){//总笔数
						array = body.getJSONArray("LIST_OBJ");
					}
					if(array.length()>0){
						List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
						for (int a = 0; a < array.length(); a++) {
							JSONObject j = array.getJSONObject(a);
							String amountType =  j.get("BUSI_ClASS").toString();	//业务类型
							String startDate = j.get("EDSXRQ").toString();//生效日期
							String endDate = j.get("EDDQRQ").toString();//生效日期
							Double creditAmount = j.getDouble("SXZED");//总额度
							Double contractAmount = j.getDouble("CPSXED");//产品额度
							Double availableAmount = j.getDouble("CPKYED");//产品可用额度
							B2bEconomicsBankCompanyDto ebc = new B2bEconomicsBankCompanyDto();
							ebc.setPk(KeyUtils.getUUID());
							ebc.setCreditAmount(creditAmount);
//						ebc.setCreditPk(company.getCreditPk());
							ebc.setCompanyPk(company.getPk());
							ebc.setBankPk(dto.getBankPk());
							ebc.setBankName(dto.getBank());
							ebc.setCreditStartTime(DateUtil.parseDateFormatYmd(startDate));
							ebc.setCreditEndTime(DateUtil.parseDateFormatYmd(endDate));
							ebc.setContractAmount(contractAmount);
							ebc.setAvailableAmount(availableAmount);
							ebc.setAmountType(amountType);
//						ebc.setCreditPk(company.getCreditPk());
							ebc.setType(1);//化纤白条类型
							ebcList.add(ebc);
						}
						resp.setEbcList(ebcList);
					}
				}else{
//				resp.setMsg(json.get("MSG").toString());
				}
			}
		} catch (Exception e) {
			logger.error("errorG337",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		XmlUtils xml = new XmlUtils();
		String xmlStrs = xml.guangdaG330(loanNumber.getOrderNumber(),loanNumber.getPurchaserName(), loanNumber.getOrganizationCode());
		// 存前置通知
		xmlStrs = addMac(xmlStrs);
		String id = this.saveBankInfo("G330", null,xmlStrs, null, "active","loan");
		BankBaseResp resp =null;
		//发送报文请求
		String responseBody = PostUtils.post(PropertyConfig.getProperty("gd_bank_url"), "POST",xmlStrs);
		if(null != responseBody && !"".equals(responseBody)){
			resp = new BankBaseResp();
			//存后置通知
			this.saveBankInfo("G330", id, xmlStrs, responseBody, "active","loan");
			JSONObject json = xml.getJsonByXml(responseBody);
			//业务回调
			JSONObject body = json.getJSONObject("Body");
			//已放款
			if(!body.isNull("LOAN_STATUS") && 4 == body.getInt("LOAN_STATUS")){
				//放款开始日期
				String loanStartTime = body.get("LOAN_START_DATE").toString();
				//放款结束日期
				String loanEndTime = body.get("LOAN_END_DATE").toString();
				//借据编号
				String iouNumber = body.get("LOUS_NO").toString();
				BankCreditDto cdto = new BankCreditDto();
				cdto.setLoanStartDate(DateUtil.parseDateFormatYMD(loanStartTime));
				cdto.setLoanEndDate(DateUtil.parseDateFormatYMD(loanEndTime));
				cdto.setIouNumber(iouNumber);
				cdto.setPayStatus(1);
				resp.setBankCreditDto(cdto);
			}
			//已取消
			if(!body.isNull("LOAN_STATUS") && 6 == body.getInt("LOAN_STATUS")){
				BankCreditDto cdto = new BankCreditDto();
				cdto.setLoanStatus(4);
				resp.setBankCreditDto(cdto);
			}
		}
		return resp;
	}

	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		List<B2bRepaymentRecord> brpdNewList = new ArrayList<B2bRepaymentRecord>();
		Double repaymentAmount = 0d;
		BankBaseResp resp = new BankBaseResp();
		Integer start = 1;
		Integer limit = 19;
		//分页查询还款记录
		while(limit >= 19){
			List<B2bRepaymentRecord> brpdList  = this.addB2bRepaymentRecordList(loanNumber, start);
			limit = brpdList.size();
			start +=20;
			brpdNewList.addAll(brpdList);
		}
		if(brpdNewList.size()>0){
			for(B2bRepaymentRecord o : brpdNewList){
				repaymentAmount = ArithUtil.add(repaymentAmount, o.getAmount());
			}
			resp.setB2bRepaymentRecordList(brpdNewList);
			if(loanNumber.getLoanAmount().toString().equals(ArithUtil.round(repaymentAmount, 2)+"")){
				resp.setLoanStatus(5);//5全部还款 
			}else{
				resp.setLoanStatus(6);//6部分还款
			}
			resp.setRepaymentDate(DateUtil.numberToString(brpdNewList.get(0).getCreateTime()));
		}
		return resp;
	}
	
	private  List<B2bRepaymentRecord>  addB2bRepaymentRecordList(B2bLoanNumberDto loanNumber,Integer start){
		XmlUtils xml = new XmlUtils();
		List<B2bRepaymentRecord> brpdList = new ArrayList<B2bRepaymentRecord>();
		String xmlStrs = xml.guangdaG335(loanNumber.getOrderNumber(), loanNumber.getPurchaserName(), loanNumber.getOrganizationCode(), loanNumber.getLoanNumber(),start);
		//存前置通知
		xmlStrs = addMac(xmlStrs);
		String id = this.saveBankInfo("G335", null,xmlStrs, null, "active","repayment");
		//发送报文请求
		String responseBody = PostUtils.post(PropertyConfig.getProperty("gd_bank_url"), "POST",xmlStrs);
		if(null != responseBody && !"".equals(responseBody)){
			//存后置通知
			this.saveBankInfo("G335", id, xmlStrs, responseBody, "active","repayment");
			JSONObject json = xml.getJsonByXml(responseBody);
			//业务回调
			JSONObject body = json.getJSONObject("Body");
			JSONArray array =new JSONArray();
			if(!body.isNull("Z_BISHU")){
				if(body.getInt("Z_BISHU") >1){
					array = body.getJSONArray("LIST_OBJ");
				}else{
					JSONObject obj = body.getJSONObject("LIST_OBJ");
					array.put(obj);
				}
//				limit = body.getInt("Z_BISHU");
				if(null != array && array.length() >0){
					for (int i = 0; i < array.length(); i++) {
						JSONObject j = array.getJSONObject(i);
						//"1"表示借
						if( "1".equals(j.get("CREDIT_FLAG").toString())){
							B2bRepaymentRecord brpr = new B2bRepaymentRecord();
							brpr.setOrderNumber(loanNumber.getOrderNumber());
							brpr.setIouNumber(loanNumber.getLoanNumber());
							brpr.setStatus(1);
							brpr.setCompanyPk(loanNumber.getPurchaserPk());
							brpr.setCompanyName(loanNumber.getPurchaserName());
							brpr.setBankPk(loanNumber.getBankPk());
							brpr.setBankName(loanNumber.getBankName());
							brpr.setAmount(j.getDouble("TRAN_AMOUNT"));
							brpr.setCreateTime(j.get("TRAN_DATE").toString());
							brpr.setOrganizationCode(loanNumber.getOrganizationCode());
							brpdList.add(brpr);
						}
					}
				}
			}
		 }
		return brpdList;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order,B2bCompanyDto company,SysCompanyBankcardDto card,B2bCreditGoodsDtoMa cgDto) {
		XmlUtils xml = new XmlUtils();
		String xmlStrs = xml.guangdaG332(order.getOrderNumber(), order.getPurchaser().getPurchaserName(), company.getOrganizationCode(), order.getActualAmount(), order.getSupplier().getSupplierName(), card.getBankAccount(), card.getBankNo(), card.getBankName());
		//存前置通知
		xmlStrs = addMac(xmlStrs);
		String id = this.saveBankInfo("G332", null,xmlStrs, null, "active","pay");
		//发送报文请求
		String responseBody = PostUtils.post(PropertyConfig.getProperty("gd_bank_url"), "POST",xmlStrs);
		BankBaseResp resp = null;
		if(null != responseBody && !"".equals(responseBody)){
			resp = new BankBaseResp();
			//存后置通知
			this.saveBankInfo("G332", id, xmlStrs, responseBody, "active","pay");
			JSONObject json = xml.getJsonByXml(responseBody);
			//业务回调
			JSONObject body = json.getJSONObject("Body");
			if(!body.isNull("RESULT_FLAG") && 1 == body.getInt("RESULT_FLAG")){
				resp.setCode(RestCode.CODE_0000.getCode());
			}else{
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg(body.get("ErrorInfo").toString());
			}
		}
		return resp;
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		XmlUtils xml = new XmlUtils();
		String xmlStrs = xml.guangdaG336(loanNumber.getOrderNumber(), loanNumber.getPurchaserName(), loanNumber.getOrganizationCode());
		//存前置通知
		xmlStrs = addMac(xmlStrs);
		String id = this.saveBankInfo("G336", null,xmlStrs, null, "active","cancel");
		//发送报文请求
		String responseBody = PostUtils.post(PropertyConfig.getProperty("gd_bank_url"), "POST",xmlStrs);
		BankBaseResp resp = null;
		if(null != responseBody && !"".equals(responseBody)){
			resp = new BankBaseResp();
			//存后置通知
			this.saveBankInfo("G336", id, xmlStrs, responseBody, "active","cancel");
			JSONObject json = xml.getJsonByXml(responseBody);
			//业务回调
			JSONObject body = json.getJSONObject("Body");
			if(!body.isNull("RESULT_FLAG") && 1 == body.getInt("RESULT_FLAG")){
				resp.setCode(RestCode.CODE_0000.getCode());
			}else{
				resp.setMsg(body.get("ErrorInfo").toString());
			}
		}
		return resp;
	}
	
	

	
	
	public String saveBankInfo(String code, String id, String requestValue,
			 String responseValue,String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("guangdaBank");
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
	
	private String addMac(String value){
		 String  macCode = MacUtils.toMAC(value);
		 if(null == macCode || "".equals(macCode)){
			 this.appMacKeyValue();
			 macCode = MacUtils.toMAC(value);
		 }
		 XmlUtils gdXml = new XmlUtils();
		 value = gdXml.getXml(value+macCode);
		return value;
	}
}
