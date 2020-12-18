package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cf.util.ArithUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.shanghai.PostUtils;
import cn.cf.common.creditpay.shanghai.XmlUtils;
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
import cn.cf.service.creditpay.BankShanghaiService;
import cn.cf.service.platform.B2bCreditGoodsService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.XmlTools;


@Service
public class BankShanghaiServiceImpl implements BankShanghaiService {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bCreditGoodsService b2bCreditGoodsService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	XmlUtils xml = new XmlUtils();
	PostUtils postUtils=new PostUtils();

	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,
			B2bCreditGoodsDto dto) {
		BankBaseResp resp =  null;
		String data = xml.searchLimitAmt(dto.getBankAccountNumber());
       //存前置通知
		try {
			String id = this.saveBankInfo("MCAgrmtContrQuery1_1Op", null, data, null, "active", "customer");
			//存后置通知
			String rest = postUtils.post("MCAgrmtContrQuery1_1Op", data);
			if (null != rest &&  rest.contains("loginFail")){
				this.saveBankInfo("MCAgrmtContrQuery1_1Op",null,data,rest,"active","login");
			}else {
				this.saveBankInfo("MCAgrmtContrQuery1_1Op", id, data, rest, "active", "customer");
				if (null != rest) {
					Document doc = null;
					try {
						doc = DocumentHelper.parseText(rest);
					} catch (DocumentException e) {
						logger.error("rest------",rest);
						logger.error("errorSearchBankCreditAmount",e);
					}
					if (null != doc) {
						Element root = doc.getRootElement();
						String retCode =root.element("opRep").element("retCode").getTextTrim();
						if (null != retCode && "0".equals(retCode)) {
							resp = new BankBaseResp();
							List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
							B2bEconomicsBankCompanyDto ebc = new B2bEconomicsBankCompanyDto();
							String limitAmt = root.element("opRep").element("opResultSet").element("opResult").element("LimitAmt").getTextTrim();//额度
							String startDate = root.element("opRep").element("opResultSet").element("opResult").element("LimitStart").getTextTrim();//生效开始日期
							String endDate = root.element("opRep").element("opResultSet").element("opResult").element("LimitEnd").getTextTrim();//生效结束日期
							List<?> elements = doc.selectNodes("//ContractId");
							String contractNumber = "";//合同编号
							String contractAmount = "";//合同额度
							String availableAmount = "";//合同可用额度
							String agreementNumber = "";//授信协议流水号
							for (Object mime : elements) {
								Element e = (Element) mime;
								contractNumber = e.getTextTrim();
							}
							elements = doc.selectNodes("//Amt");
							for (Object mime : elements) {
								Element e = (Element) mime;
								contractAmount = e.getTextTrim();
							}
							elements = doc.selectNodes("//CrmsContracValidAmt");
							for (Object mime : elements) {
								Element e = (Element) mime;
								availableAmount = e.getTextTrim();
							}
							elements = doc.selectNodes("//AgreementNo");
							for (Object mime : elements) {
								Element e = (Element) mime;
								agreementNumber = e.getTextTrim();
							}
							ebc.setPk(KeyUtils.getUUID());
							ebc.setCompanyPk(company.getPk());
							ebc.setBankPk(dto.getBankPk());
							ebc.setBankName(dto.getBank());
							ebc.setCreditAmount(Double.valueOf(limitAmt));//授信总额度
							ebc.setCreditStartTime(DateUtil.parseDateFormatYmd(startDate));
							ebc.setCreditEndTime(DateUtil.parseDateFormatYmd(endDate));
							ebc.setCustomerNumber(root.element("opRep").element("T24Id").getTextTrim());
							ebc.setContractNumber(contractNumber);
							ebc.setContractAmount(Double.valueOf(contractAmount));
							ebc.setAvailableAmount(Double.valueOf(availableAmount));
							ebc.setAgreementNumber(agreementNumber);
							ebc.setAmountType("1");//目前写死
							ebc.setCompanyPk(company.getPk());
							ebc.setType(1);//化纤白条类型
							ebcList.add(ebc);
							resp.setEbcList(ebcList);
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("searchBankCreditAmount",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp =  null;
		String data = xml.searchLoan(loanNumber.getLoanAccount());
		//存前置通知
		try {
			Date dateS=loanNumber.getLoanStartTime();
			Date dateE=loanNumber.getLoanEndTime();
			String startDate=  DateUtil.formatYearMonthDayTwo(dateS);
			String endDate=DateUtil.formatYearMonthDayTwo(dateE);
			String id = this.saveBankInfo("qryLoanApplyRes1_1Op", null, data, null, "active", "loan");
			//存后置通知
			String rest = postUtils.post("qryLoanApplyRes1_1Op", data);
			if (null != rest &&  rest.contains("loginFail")){
				this.saveBankInfo("qryLoanApplyRes1_1Op",null,data,rest,"active","login");
			}else{
				this.saveBankInfo("qryLoanApplyRes1_1Op", id, data, rest, "active", "loan");
				if (null != rest) {
					Document doc = null;
					try {
						doc = DocumentHelper.parseText(rest);
					} catch (DocumentException e) {
						logger.error("rest------",rest);
						logger.error("errorSearchloan",e);
					}
					if (null != doc) {
						Element root = doc.getRootElement();
						String retCode =root.element("opRep").element("retCode").getTextTrim();
						if (null != retCode && "0".equals(retCode)) {
							String loanNo = root.element("opRep").element("LoanNo").getTextTrim();//借据号
							if (null != loanNo && !"".equals(loanNo)) {
								String data2 = xml.searchloanApply(loanNo, "", "", startDate, endDate);
								String id1 = this.saveBankInfo("loanApplyResSync1_2Op", null, data, null, "active", "loanTwo");
								String rest2 = postUtils.post("loanApplyResSync1_2Op", data2);
								if (null != rest2 && rest2.contains("loginFail")) {
									this.saveBankInfo("loanApplyResSync1_2Op",null,data,rest2,"active","login");
								}else{
									this.saveBankInfo("loanApplyResSync1_2Op", id1, data, rest2, "active", "loanTwo");
									if(null != rest2){
										JSONObject json = XmlTools.xmltoJson(rest2);
										if (null != json.getJSONObject("BOSEBankData") &&
												null != json.getJSONObject("BOSEBankData").getJSONObject("opRep")
												&& "0".equals(json.getJSONObject("BOSEBankData").getJSONObject("opRep").get("retCode"))) {
											json = json.getJSONObject("BOSEBankData").getJSONObject("opRep").getJSONObject("opResultSet");
											if (null != json) {
												json = json.getJSONObject("opResult");
												//放款状态
			//									C - 已创建-线下放款才会有此状态
			//									W - 待审核-线下放款才会有此状态
			//									R - 已审核-线下放款才会有此状态
			//									T - 发送报文中：是否可以描述为业务已已受理
			//									J - 被拒绝
			//									H - 待信贷确认-线下放款才会有此状态
			//									X - 交易银行已撤销-即放款失败
			//									N - CRMS已撤销-即放款失败
			//									B - CRMS放款确认-线下放款才会有此状态
			//									M - 抹账-即放款失败
			//									Y - 预申请（待发库）-是否可以描述为业务已已受理
			//									S - 出账成功-表示放款成功
			//									F - 已结案-表示已全额还款
			//									P - 部分还款-表示已部分还款
			//									G - 票据回退-
			//									5 可疑-为系统之间通讯异常
												String loanStatus = json.getString("LoanStats");
												if (null != loanStatus && "S".equals(loanStatus)) {
													resp = new BankBaseResp();
													//放款开始日期
													String loanStartTime = json.getString("LoanValueDt");
													//放款结束日期
													String loanEndTime = json.getString("LoanDueDt");
													//生效利率
													Double intRate = json.getDouble("IntRate");
													BankCreditDto cdto = new BankCreditDto();
													cdto.setLoanStartDate(DateUtil.parseDateFormatYmd(loanStartTime));
													cdto.setLoanRate(intRate);
													cdto.setLoanEndDate(DateUtil.parseDateFormatYmd(loanEndTime));
													cdto.setIouNumber(loanNo);
													cdto.setPayStatus(1);
													resp.setBankCreditDto(cdto);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("searchloan",e);
		}
		return resp;
	}
	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp = null;
		List<B2bRepaymentRecord> repaymentRecordList = new ArrayList<B2bRepaymentRecord>();
		String startDate=  DateUtil.formatYearMonthDayTwo(loanNumber.getLoanStartTime());
		String endDate=DateUtil.formatYearMonthDayTwo(loanNumber.getLoanEndTime());
		String data = xml.searchRepayment(loanNumber.getLoanNumber(),loanNumber.getCustomerNumber(),startDate,endDate);
		String orderNumber=loanNumber.getOrderNumber();
		try {
			String id = this.saveBankInfo("repaymentResSync1_1Op", null, data, null, "active", "repayment");
			//存后置通知
			String rest = postUtils.post("repaymentResSync1_1Op", data);
			if (null != rest &&  rest.contains("loginFail")){
				this.saveBankInfo("repaymentResSync1_1Op",null,data,rest,"active","login");
			}else{
				this.saveBankInfo("repaymentResSync1_1Op", id, data, rest, "active", "repayment");
				if (null != rest) {
					Document doc = null;
					try {
						doc = DocumentHelper.parseText(rest);
					} catch (DocumentException e) {
						logger.error("rest------",rest);
						logger.error("errorSearchRepayment",e);
					}
					if (null != doc) {
						Element root = doc.getRootElement();
						String retCode =root.element("opRep").element("retCode").getTextTrim();
						if (null != retCode && "0".equals(retCode)) {
							resp = new BankBaseResp();
							List<?> elements = doc.selectNodes("//TotalNum");
							List<?> companyNames = doc.selectNodes("//CompanyName");
							List<?> repaymentNumbers = doc.selectNodes("//RepaymentId");
							List<?> createTimes = doc.selectNodes("//RepaymentDate");
							List<?> loanAmts = doc.selectNodes("//LoanAmt");
							if (null != elements && companyNames.size() > 0) {
								Element e = (Element) elements.get(0);
								int totalNum = Integer.valueOf(e.getTextTrim());//返回结果个数
								Double repaymentAmount = 0d;
								for (int i = 0; i < totalNum; i++) {
									e = (Element) companyNames.get(i);
									B2bRepaymentRecord record = new B2bRepaymentRecord();
									record.setCompanyName(e.getTextTrim());//公司名称
									e = (Element) repaymentNumbers.get(i);
									record.setRepaymentNumber(e.getTextTrim());//还款流水号
									e = (Element) createTimes.get(i);
									record.setCreateTime(e.getTextTrim());//还款日期
									e = (Element) loanAmts.get(i);
									Double loanAmt = Double.valueOf(e.getTextTrim());//本次还款
									record.setAmount(loanAmt);
									record.setStatus(1);
									record.setOrderNumber(orderNumber);
									repaymentAmount += record.getAmount();//实还本金(总)
									repaymentRecordList.add(record);
								}
								if (loanNumber.getLoanAmount().toString().equals(ArithUtil.round(repaymentAmount, 2) + "")) {
									resp.setLoanStatus(5);//5全部还款
								} else {
									resp.setLoanStatus(6);//6部分还款
								}
								resp.setB2bRepaymentRecordList(repaymentRecordList);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("searchRepayment",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto) {
		BankBaseResp resp =  null;
		String data = xml.applyLoanCondition(cgDto.getAgreementNumber(),cgDto.getBankAccountNumber(),"1",order.getOrderNumber()
				,order.getActualAmount(),DateUtil.formatYearMonthDay(DateUtil.dateStringToTimestamp(cgDto.getEndTime())),
				order.getPurchaser().getPurchaserName(),order.getSupplier().getSupplierName());
		try {
			//存前置通知
			String id = this.saveBankInfo("PusReqExInfo1_1Op", null, data, null, "active", "pay");
			//存后置通知
			String rest = postUtils.post("PusReqExInfo1_1Op", data);
			if (null != rest && rest.contains("loginFail")){
				this.saveBankInfo("PusReqExInfo1_1Op",null,data,rest,"active","login");
			}else{
				this.saveBankInfo("PusReqExInfo1_1Op", id, data, rest, "active", "pay");
				if (null != rest) {
					Document doc = null;
					try {
						doc = DocumentHelper.parseText(rest);
					} catch (DocumentException e) {
						logger.error("rest:------",rest);
						logger.error("errorShanghaiBankPay",e);
					}
					if (null != doc) {
						Element root = doc.getRootElement();
						resp = new BankBaseResp();
						Element e = root.element("opRep").element("retCode");
						String retCode = e.getTextTrim();
						String errMsg = root.element("opRep").element("errMsg").getTextTrim();
						//0表示成功 TXBK100073表示放款条件维护过
						if (null != retCode && ("0".equals(retCode) || "TXBK100073".equals(retCode))) {
							String loanAccount = KeyUtils.getFlow(6);
							String data2 = xml.applyLoan(loanAccount,cgDto.getAgreementNumber(), cgDto.getContractNumber()
									, cgDto.getBankAccountNumber(), card.getBankAccount(),
									DateUtil.formatYearMonthDayTwo(DateUtil.getDaysForDate(new Date(), cgDto.getTerm())), order.getOrderNumber(),
									order.getActualAmount());
							String id2 = this.saveBankInfo("applyFinancingLoan3_1Op", null, data2, null, "active", "payTwo");
							String rest2 = postUtils.post("applyFinancingLoan3_1Op", data2);
							if (null != rest2 && rest2.contains("loginFail")){
								this.saveBankInfo("applyFinancingLoan3_1Op",null,data2,rest2,"active","login");
								resp.setCode(RestCode.CODE_S999.getCode());
								resp.setMsg(RestCode.CODE_S999.getMsg());
							}else{
								//存后置通知
								this.saveBankInfo("applyFinancingLoan3_1Op", id2, data2, rest2, "active", "payTwo");
								try {
									doc = DocumentHelper.parseText(rest2);
									root = doc.getRootElement();
									retCode = root.element("opRep").element("retCode").getTextTrim();
									errMsg = root.element("opRep").element("errMsg").getTextTrim();
									if (null !=retCode && "0".equals(retCode)) {
										resp.setCode(RestCode.CODE_0000.getCode());
										resp.setLoanAccount(loanAccount);
									} else {
										resp.setCode(RestCode.CODE_Z000.getCode());
										resp.setMsg(errMsg);
									}
								} catch (DocumentException e1) {
									resp.setCode(RestCode.CODE_S999.getCode());
									resp.setMsg(RestCode.CODE_S999.getMsg());
									logger.error("errorShanghaiBankPay",e1);
								}
							}
						}else{
							resp.setCode(RestCode.CODE_Z000.getCode());
							resp.setMsg(errMsg);
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("payOrder",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp =  null;
		String data = xml.applyLoanCondition(loanNumber.getContractNumber(),loanNumber.getCustomerNumber(),"3",loanNumber.getOrderNumber()
				,loanNumber.getLoanAmount(),DateUtil.formatYearMonthDay(loanNumber.getLoanEndTime()),
				loanNumber.getPurchaserName(),loanNumber.getSupplierName());
		//存前置通知
		try {
			String id = this.saveBankInfo("PusReqExInfo1_1Op", null, data, null, "active", "cancel");
			//存后置通知
			String rest = postUtils.post("PusReqExInfo1_1Op", data);
			if (null != rest && rest.contains("loginFail")){
				this.saveBankInfo("PusReqExInfo1_1Op",null,data,rest,"active","login");
			}else{
				this.saveBankInfo("PusReqExInfo1_1Op", id, data, rest, "active", "cancel");
				if (null != rest) {
					Document doc = null;
					try {
						doc = DocumentHelper.parseText(rest);
					} catch (DocumentException e) {
						logger.error("rest:------",rest);
						logger.error("errorCancelOrder",e);
					}
					if (null != doc) {
						Element root = doc.getRootElement();
						resp = new BankBaseResp();
						String retCode = root.element("opRep").element("retCode").getTextTrim();
						String errMsg = root.element("opRep").element("errMsg").getTextTrim();
						//TXBK300270已关闭
						if (null != retCode && ("0".equals(retCode)||"TXBK300270".equals(retCode))) {
							resp.setCode(RestCode.CODE_0000.getCode());
						} else {
							resp.setCode(RestCode.CODE_Z000.getCode());
							resp.setMsg(errMsg);
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("cancelOrder",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp conditionLoan(B2bOrderDtoMa order,
			B2bCompanyDto company, SysCompanyBankcardDto card,
			B2bCreditGoodsDtoMa cgDto) {
		// TODO Auto-generated method stub
		return null;
	}

	public String saveBankInfo(String code, String id, String requestValue,
							   String responseValue,String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("shanghaiBank");
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

	@Override
	public BankBaseResp agentPay(B2bLoanNumberDto loanNumber,
			B2bRepaymentRecord record) {

		BankBaseResp resp =  null;
		String data = xml.agentPay(record.getId(), loanNumber.getPurchaserName(), record.getServiceCharge().toString(), loanNumber.getLoanAccount());
		//存前置通知
		try {

			String id = this.saveBankInfo("FeeCharge1_1Op", null, data, null, "active", "agentPay");
			String rest = postUtils.post("FeeCharge1_1Op", data);
			//存后置通知
			this.saveBankInfo("FeeCharge1_1Op", id, data, rest, "active", "agentPay");

			if (null != rest) {
				Document doc = null;
				try {
					doc = DocumentHelper.parseText(rest);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				if (null != doc) {
					Element root = doc.getRootElement();
					resp = new BankBaseResp();
					String code = root.element("opRep").element("retCode").getTextTrim();//返回码
					if (null != code && "0".equals(code)) {
						resp = new BankBaseResp();
						resp.setAgentPayStatus(2);
					} else {
						resp.setAgentPayStatus(3);
					}

				}

			}
		}catch (Exception e) {
			logger.error("errorRepaymentResultNotice",e);
		}
		return resp;
	
	}


}
