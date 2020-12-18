package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import cn.cf.common.creditpay.suzhou.PostUtils;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.OrderPaymentLimitDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.property.PropertyConfig;
import cn.cf.service.creditpay.BankSuzhouService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
@Service
public class BankSuzhouServiceImpl implements BankSuzhouService{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public static final String SUCCESS = "00000000";
	private  static final String SZ_LOAN_CODE = "11101";//化纤白条
	private  static final String SZ_PLEDGE_CODE = "11102";//化纤贷



	private String saveBankInfo(String code, String id, String requestValue,
			String requestDesValue, String responseValue,
			String responseDesValue, String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("suzhouBank");
			bank.setRequestValue(requestValue);
			bank.setRequestDesValue(requestDesValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setRequestMode(requestMode);
			bank.setHxhCode(hxhCode);
			mongoTemplate.save(bank);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			update.set("responseDesValue", responseDesValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, BankInfo.class);
			return id;
		}
	}
	
	private JSONObject getBody(String str){
		JSONObject j = new JSONObject(str);
		return j.getJSONObject("body");
	}
	
	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,B2bCreditGoodsDto dto) {
		BankBaseResp resp = null;
		String id =null;
		try {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("transCode", "CMS912");
			if(null != dto.getBankAccountNumber() && !"".equals(dto.getBankAccountNumber())){
				map.put("ECIFNO", dto.getBankAccountNumber());//银行客户号
			}else{
				map.put("zjlx", "01");//证件类型
				map.put("zjhm", company.getOrganizationCode());//统一信用代码
			}
			map.put("YWLX", "11101|11102");//业务类型
			id = this.saveBankInfo("CMS912", null,
					map.toString(), null, null, null,
					"active","customer");
			String rest  =  PostUtils.post(map);
			this.saveBankInfo("CMS912", id, map.toString(),
					null, rest, null, "active","customer");
			System.out.println("交易返回报文===================>>"+rest);
			//返回结果
			JSONObject body  = getBody(rest);
			//'2':表示合同启用
			if(!body.isNull("errorCode") && "000000".equals(body.getString("errorCode"))){
				resp = new BankBaseResp();
				if(!body.isNull("FLAG") && "1".equals(body.getString("FLAG"))){
					List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
					//合同数据集合
					JSONArray array = body.getJSONArray("CONTRACT_AGREE_ARRAY");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						String contractStatus = null;
						if(!object.isNull("HTZT")){
							contractStatus = object.getString("HTZT");//合同状态(030 已生效)
						}
						if(null != contractStatus && !"030".equals(contractStatus)){
							continue;
						}
						String contractNumber = null;
						Double creditAmount = null;
//						Double crediUsAmount = null;
//						Double otherAmount = null;
//						Double openAmount = null;
						String code = null;
//						String date = null;
						Double contractAmount = null;
//						Double contractUsAmount = null;
						Double contractOtherAmount = null;
						String contractStartDate = null;
						String contractEndDate = null;
						if(!object.isNull("BCSERIALNO")){
							contractNumber = object.getString("BCSERIALNO");//合同编号
						}
						if(!object.isNull("SXSUM") && !"".equals(object.getString("SXSUM"))){
							creditAmount = object.getDouble("SXSUM");//授信总额度
						}
//						if(!object.isNull("YSYSUM") && !"".equals(object.getString("YSYSUM"))){
//							crediUsAmount = object.getDouble("YSYSUM");//已使用额度		
//						}
//						if(!object.isNull("CYSUM") && !"".equals(object.getString("CYSUM"))){
//							otherAmount = object.getDouble("CYSUM");//剩余额度				
//						}
//						if(!object.isNull("QYSUM") && !"".equals(object.getString("QYSUM"))){
//							openAmount = object.getDouble("QYSUM");//启用额度
//						}
						if(!object.isNull("YWLX")){
							code = object.getString("YWLX");//业务类型				
						}
//						if(!object.isNull("FKQX")){
//							date = object.getString("FKQX");//放款期限				
//						}
						if(!object.isNull("HTJE") && !"".equals(object.getString("HTJE"))){
							contractAmount = object.getDouble("HTJE");//合同金额		
						}
//						if(!object.isNull("HTYYJE") && !"".equals(object.getString("HTYYJE"))){
//							contractUsAmount = object.getDouble("HTYYJE");//合同已用金额				
//						}
						if(!object.isNull("HTWYJE") && !"".equals(object.getString("HTWYJE"))){
							contractOtherAmount = object.getDouble("HTWYJE");//合同未用金额		
						}
						if(!object.isNull("HTQR")){
							contractStartDate =  object.getString("HTQR");//合同开始日
						}
						if(!object.isNull("HTJR")){
							contractEndDate =   object.getString("HTJR");//合同结束日
						}
						B2bEconomicsBankCompanyDto model = new B2bEconomicsBankCompanyDto();
						model.setPk(KeyUtils.getUUID());
						model.setCustomerNumber(dto.getBankAccountNumber());
						model.setContractNumber(contractNumber);
						model.setCreditAmount(creditAmount);
						model.setContractAmount(contractAmount);
						model.setAvailableAmount(contractOtherAmount);
						model.setCreditStartTime(DateUtil
								.numberToStringTwo(contractStartDate));
						model.setCreditEndTime(DateUtil
								.numberToStringTwo(contractEndDate));
						model.setAmountType(code);
						model.setCompanyPk(company.getPk());
//						model.setCreditPk(company.getCreditPk());
						model.setBankPk(dto.getBankPk());
						model.setBankName(dto.getBank());
						//"11101":白条额度;"11102":化纤贷额度
						if(SZ_LOAN_CODE.equals(code)){
							//对应金融产品类型化纤白条
							model.setType(1);
						}
						if(SZ_PLEDGE_CODE.equals(code)){
							//对应金融产品类型化纤贷
							model.setType(2);
						}
						if(!body.isNull("ECIFNO")){
							model.setCustomerNumber(body.getString("ECIFNO"));
						}
						ebcList.add(model);
					}
					resp.setEbcList(ebcList);
				}
			}
		} catch (Exception e) {
			logger.error("error-----------CMS912",e);
		}
		return resp;
	}
	
	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order,
			B2bCompanyDto company, SysCompanyBankcardDto card,
			B2bCreditGoodsDtoMa creditGoods) {
		BankBaseResp resp = null;
		String id = null;
		try {
			Map<String,Object> map = new  HashMap<String,Object>();
			//金融产品支付比例
			Double proportion = creditGoods.getProportion();
			if(null == proportion || proportion == 0d){
				proportion = 1d;
			}
			String supplierName=order.getSupplier().getSupplierName();
			Double totalAmount = ArithUtil.round(ArithUtil.mul(order.getActualAmount(), proportion), 2);
			map.put("transCode", "CMS911");
			map.put("ECIFNO", creditGoods.getBankAccountNumber());//银行客户号
			map.put("YWLX", creditGoods.getGoodsType() == 1?SZ_LOAN_CODE:SZ_PLEDGE_CODE);//业务类型
			map.put("DDBH", order.getOrderNumber());//订单号
			map.put("DDYT", "额度支付");//订单用途
			map.put("DDSU", totalAmount+"");//订单金额
			map.put("SKRXM", supplierName);//收款人姓名
			map.put("SKXNZH", card.getBankAccount());//收款人实体账户
			map.put("SKRHM", supplierName);//收款人户名
			map.put("SKRKHHH", card.getBankNo());//收款人开户行号
			map.put("SKRKHHM", card.getBankName());//收款人开户行名
			map.put("SKAMT", totalAmount+"");//收款金额
			List<OrderPaymentLimitDto> dtos = new ArrayList<OrderPaymentLimitDto>();
			OrderPaymentLimitDto pldto = new OrderPaymentLimitDto(null, null, totalAmount+"", 
					"额度支付", order.getSupplierPk(), supplierName,supplierName, card.getBankAccount(), 
					null, null, null, null, card.getBankName(), card.getBankNo(), null, null, null, null, null);
			if(null != PropertyConfig.getProperty("platform_amount") && !"".equals(PropertyConfig.getProperty("platform_amount"))){
				Double platformAmount = Double.parseDouble(PropertyConfig.getProperty("platform_amount"));
				if(totalAmount>platformAmount){
					OrderPaymentLimitDto pldtor = new OrderPaymentLimitDto(null, null, platformAmount+"", 
							"额度支付", PropertyConfig.getProperty("platform_companypk"), PropertyConfig.getProperty("platform_companyname"), PropertyConfig.getProperty("platform_accountname"), PropertyConfig.getProperty("platform_account"), 
							null, null, null, null, PropertyConfig.getProperty("platform_bankname"), PropertyConfig.getProperty("platform_bankno"), null, null, null, null, null);
					
					map.put("SKRXM2", pldtor.getPayeePlatName());//收款人姓名
					map.put("SKXNZH2", pldtor.getPayeeVirtualAccount());//收款人实体账户
					map.put("SKRHM2", pldtor.getPayeeName());//收款人户名
					map.put("SKRKHHH2", pldtor.getPayeeBankNo());//收款人开户行号
					map.put("SKRKHHM2", pldtor.getPayeeBankName());//收款人开户行名
					map.put("SKAMT2", platformAmount+"");//收款金额
					map.put("SKAMT", ArithUtil.sub(totalAmount, platformAmount)+"");//收款金额=(订单总金额-服务费)
					pldto.setOrderAmount(ArithUtil.sub(totalAmount, platformAmount)+"");
					dtos.add(pldtor);
				}
			}
			dtos.add(pldto);
			id = this.saveBankInfo("CMS911", null,
					map.toString(), null, null, null,
					"active","pay");
			String rest  = PostUtils.post(map);
			this.saveBankInfo("CMS911", id, map.toString(),
					null, rest, null, "active","pay");
			System.out.println("交易返回报文===================>>"+rest);
			JSONObject body = getBody(rest);
			resp = new BankBaseResp();
			if(!body.isNull("FLAG") && "1".equals(body.getString("FLAG"))){
				resp.setCode(RestCode.CODE_0000.getCode());
				resp.setOrderPaymentList(dtos);
			}else{
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg(body.getString("errorMsg"));
			}
		} catch (Exception e) {
			logger.error("error-----------CMS911",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		String id = null;
		try {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("transCode", "CMS918");
			map.put("ECIFNO", loanNumber.getCustomerNumber());//银行客户号
			map.put("YWLX", loanNumber.getEconomicsGoodsType() == 1?SZ_LOAN_CODE:SZ_PLEDGE_CODE);//业务类型
			map.put("DDBH", loanNumber.getOrderNumber());//订单号
			map.put("FLAG", "2");//根据订单标识查询
			id = this.saveBankInfo("CMS918", null,
					map.toString(),null, null, null,
					"active","loan");
			String rest  =  PostUtils.post(map);
			this.saveBankInfo("CMS918", id, map.toString(),
					null, rest, null, "active","loan");
			JSONObject body = getBody(rest);
			//借据数量大于0则表示已有放款记录
			 resp = new BankBaseResp();
			if(!body.isNull("JJS") && !"".equals(body.getString("JJS"))){
				JSONObject json = body.getJSONArray("DUEBILL_INFO_ARRAY").getJSONObject(0);
				BankCreditDto cdto = new BankCreditDto();
				if(!json.isNull("CZR") && !"".equals(json.getString("CZR"))){
					cdto.setLoanStartDate(DateUtil.numberToStringTwo(json.getString("CZR")));//出账日
				}
				if(!json.isNull("DQR") && !"".equals(json.getString("DQR"))){
					cdto.setLoanEndDate(DateUtil.numberToStringTwo(json.getString("DQR")));//到期日
				}
				if(!json.isNull("BDSERIALNO")){
					cdto.setIouNumber(json.getString("BDSERIALNO"));//借据编号
				}
				cdto.setPayStatus(2);//原来的支付状态不在此处处理 1代表处理成功 2代表未处理成功
				if(!json.isNull("BCSERIALNO")){
					cdto.setContractNumber(json.getString("BCSERIALNO"));//合同编号
				}
				resp.setBankCreditDto(cdto);
				if(!json.isNull("STATUS")){
					resp.setLoanStatus(Integer.parseInt(json.getString("STATUS")));//贷款状态
				}
			}
		} catch (Exception e) {
			logger.error("error-----------cms918",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		String id = null;
		try {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("transCode", "CMS927");
			map.put("ECIFNO", loanNumber.getCustomerNumber());//银行客户号
			map.put("BDSERIALNO", loanNumber.getLoanNumber());//借据号
			map.put("DDBH", loanNumber.getOrderNumber());//订单号
			id = this.saveBankInfo("CMS927", null,
					map.toString(), null, null, null,
					"active","repayment");
			String rest  =  PostUtils.post(map);
			this.saveBankInfo("CMS927", id, map.toString(),
					null, rest, null, "active","repayment");
			JSONObject body = getBody(rest);
			JSONArray array = body.getJSONArray("DETAIL_REPAY_INFO_ARRAY");
			//查询还款状态
			resp =  searchloan(loanNumber);
			Boolean isSettle = false;
			if(null != resp && null != resp.getLoanStatus()){
				switch (resp.getLoanStatus()) {
				case 2://正常结清
					isSettle = true;
					break;
				case 21://转让结清
					isSettle = true;
					break;
				case 3://提前结清
					isSettle = true;
					break;
				case 4://逾期结清
					isSettle = true;
					break;
				case 7://卖断结清
					isSettle = true;
				case 25://转出结清
					isSettle = true;
					break;
				default:
					//其余都算未结清
					break;
				}
			}
			if(isSettle){
				//全部还款
				resp.setLoanStatus(5);
			}else{
				//部分还款
				resp.setLoanStatus(6);
			}	
			String repaymentTime = null;
			List<B2bRepaymentRecord> repaymentRecordList = new ArrayList<B2bRepaymentRecord>();
			if(array.length()>0){
					for (int i = 0; i < array.length(); i++) {
						JSONObject j = array.getJSONObject(i);
						String hkrq = null;
						Double shbj = 0d;//实还本金
						Double shlx = 0d;//实还利息
						Double shfx = 0d;//实还罚息
						Double shfl = 0d;//实还复利
						if(!j.isNull("HKRQ")){
							hkrq = j.getString("HKRQ");
						}
						repaymentTime = hkrq;
						if(!j.isNull("SHBJ") && !"".equals(j.getString("SHBJ"))){
							shbj = j.getDouble("SHBJ");
						}
						if(!j.isNull("SHLX") && !"".equals(j.getString("SHLX"))){
							shlx = j.getDouble("SHLX");
						}
						if(!j.isNull("SHFX") && !"".equals(j.getString("SHFX"))){
							shfx = j.getDouble("SHFX");
						}
						if(!j.isNull("SHFL") && !"".equals(j.getString("SHFL"))){
							shfl = j.getDouble("SHFL");
						}
						// 还款记录
						B2bRepaymentRecord record = new B2bRepaymentRecord();
						record.setOrderNumber(loanNumber.getOrderNumber());
						record.setIouNumber(loanNumber.getLoanNumber());
						record.setCreateTime(hkrq);
						record.setAmount(ArithUtil.round(shbj, 2));
						record.setInterest(ArithUtil.round(shlx, 2));
						record.setPenalty(ArithUtil.round(shfx, 2));
						record.setCompound(ArithUtil.round(shfl, 2));
						record.setStatus(1);
						record.setCompanyPk(loanNumber.getPurchaserPk());
						record.setCompanyName(loanNumber.getPurchaserName());
						record.setBankPk(loanNumber.getBankPk());
						record.setBankName(loanNumber.getBankName());
						record.setOrganizationCode(loanNumber.getOrganizationCode());
						repaymentRecordList.add(record);
					}
					resp.setRepaymentDate(DateUtil.numberToStringTwo(repaymentTime));//最后一天还款日期
					resp.setB2bRepaymentRecordList(repaymentRecordList);
			//已经结清状态但是没有还款记录 则默认还款成功
			}else if(resp.getLoanStatus() == 5 && array.length() == 0){
				B2bRepaymentRecord record = new B2bRepaymentRecord();
				record.setId(KeyUtils.getUUID());
				record.setOrderNumber(loanNumber.getOrderNumber());
				record.setIouNumber(loanNumber.getLoanNumber());
				record.setCreateTime(DateUtil.formatYearMonthDay(new Date()));
				record.setAmount(loanNumber.getLoanAmount());
				record.setStatus(1);
				record.setCompanyPk(loanNumber.getPurchaserPk());
				repaymentRecordList.add(record);
				resp.setRepaymentDate(new Date());//还款日期为当前查询天数
				resp.setB2bRepaymentRecordList(repaymentRecordList);
			}
		} catch (Exception e) {
			logger.error("error---------------CMS927",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		String id = null;
		try {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("transCode", "CMS928");
			map.put("ECIFNO", loanNumber.getCustomerNumber());
			map.put("DDBH", loanNumber.getOrderNumber());
			id = this.saveBankInfo("CMS928", null,
					map.toString(), null, null, null,
					"active","cancel");
			String rest  =  PostUtils.post(map);
			this.saveBankInfo("cms928", id, map.toString(),
					null, rest, null, "active","cancel");
			JSONObject body = getBody(rest);
			resp = new BankBaseResp();
			if(!body.isNull("FLAG")&&"1".equals(body.getString("FLAG"))){
				resp.setCode(RestCode.CODE_0000.getCode());
			}else{
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg(body.getString("errorMsg"));
			}
		} catch (Exception e) {
			logger.error("error---------------CMS928",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchWithdrawalsByHttp(String orderNumber,
			String customerNumber, Integer goodsType) {
		BankBaseResp resp = null;
		String id = null;
		try {
			Map<String,Object> map = new  HashMap<String,Object>();
			map.put("transCode", "CMS924");
			map.put("ECIFNO", customerNumber);
			map.put("YWLX", goodsType == 1?SZ_LOAN_CODE:SZ_PLEDGE_CODE);
			map.put("DDBH", orderNumber);
			id = this.saveBankInfo("CMS924", null,
					map.toString(), null, null, null,
					"active","withdrawals");
			String rest  =  PostUtils.post(map);
			this.saveBankInfo("CMS924", id, map.toString(),
					null, rest, null, "active","withdrawals");
			JSONObject body = getBody(rest);
			resp = new BankBaseResp();
			JSONArray array = body.getJSONArray("LOAN_ORDER_ARRAY");
			if(array.length()>0 && !"".equals(array.getJSONObject(0).getString("ZFZT"))){
				//支付状态(1 成功 2处理中 3失败)
				resp.setPayStatus(Integer.parseInt(array.getJSONObject(0).getString("ZFZT")));
			}else{
				resp.setPayStatus(2);
			}
		} catch (Exception e) {
			logger.error("error---------------CMS924",e);
		}
		return resp;
	}
	@Override
	public BankBaseResp wzApplication(B2bCompanyDto company) {
		BankBaseResp resp = null;
		String id = null;
		Map<String,Object> map = new  HashMap<String,Object>();
		map.put("transCode", "hxhCreditReporting");
		map.put("tran_type", "1");
		map.put("query_mode", "1");
		map.put("employee_id",PropertyConfig.getStrValueByKey("wzsy_wz_employee_id"));
		map.put("query_reason","贷前审批");
		map.put("enterprise_name", company.getName());
		map.put("enterprise_code", company.getOrganizationCode());
		id = this.saveBankInfo("hxhCreditReporting", null,
				map.toString(), null, null, null,
				"active","wzApply");
		String rest  =  PostUtils.post(map);
		this.saveBankInfo("hxhCreditReporting", id, map.toString(),
				null, rest, null, "active","wzApply");
		if(null != rest && !"".equals(rest)){
			JSONObject json = getBody(rest);
			if(!json.isNull("returnCode")){
				resp = new BankBaseResp();
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg("申请成功,请稍后查询");
			}
		}
		return resp;
	}
	@Override
	public BankBaseResp wzSearch(B2bCompanyDto company) {
		BankBaseResp resp = null;
		String id = null;
		Map<String,Object> map = new  HashMap<String,Object>();
		map.put("transCode", "hxhCreditResult");
		map.put("tran_type", "1");
		map.put("query_mode", "1");
		map.put("employee_id",PropertyConfig.getStrValueByKey("wzsy_wz_employee_id"));
		map.put("query_reason","贷前审批");
		map.put("enterprise_name", company.getName());
		map.put("enterprise_code", company.getOrganizationCode());
		id = this.saveBankInfo("hxhCreditResult", null,
				map.toString(), null, null, null,
				"active","wzSearch");
		String rest  =  PostUtils.post(map);
		this.saveBankInfo("hxhCreditResult", id, map.toString(),
				null, rest, null, "active","wzSearch");
		if(null != rest && !"".equals(rest)){
			JSONObject json = getBody(rest);
			if(!json.isNull("file_name") && !"".equals(json.getString("file_name"))){
				resp = new BankBaseResp();
				resp.setWxFileName(json.getString("file_name"));
			}
		}
		return resp;
	}
	@Override
	public BankBaseResp wzDownload(String fileName) {
		Map<String,Object> map = new  HashMap<String,Object>();
		map.put("transCode", "BECP100001");
		map.put("transdate", DateUtil.formatYearMonthDay(new Date()));
		map.put("fileName", fileName);
		map.put("requestType", "02");
		map.put("filePath", PropertyConfig.getStrValueByKey("wzsy_wz_path"));
		map.put("appId", PropertyConfig.getStrValueByKey("wzsy_wz_appId"));
		BankBaseResp resp = new BankBaseResp();
		try {
			String	id = this.saveBankInfo("BECP100001", null,
					map.toString(), null, null, null,
					"active","wzDownload");
			String rest = PostUtils.post(map);
			this.saveBankInfo("BECP100001", id, map.toString(),
					null, rest, null, "active","wzDownload");
			resp.setCode(RestCode.CODE_0000.getCode());
		} catch (Exception e) {
			resp.setCode(RestCode.CODE_S999.getCode());
			logger.error("wzDownload",e);
		}
		return resp;
	}
	}
