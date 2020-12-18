package cn.cf.service.creditpay.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.sunong.PostUtils;
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
import cn.cf.service.creditpay.BankSunongService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class BankSunongServiceImpl implements BankSunongService {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private  static final String SN_LOAN_CODE = "111562";//化纤白条
	private  static final String SN_PLEDGE_CODE = "111561";//化纤贷
	public static final String SUCCESS = "000000";
	public static final String NOORDER = "loan.check.NoOrderInfo";
	
	@SuppressWarnings("unchecked")
	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,
			B2bCreditGoodsDto dto) {
		BankBaseResp resp =  null;
		String id =null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("TrsCode", "B2ECreditLineQry");
			map.put("CifNo", dto.getBankAccountNumber());
			map.put("ChannelFlag", PropertyConfig.getProperty("sn_no"));
			Map<String, Object> result = PostUtils.post(PropertyConfig.getProperty("sn_url")+"/B2ECreditLineQry.do", map);
			id = this.saveBankInfo(null, null,
					map.toString(), null,
					"active","customer");
			if(null != result && "000000".equals(result.get("IBSReturnCode"))){
				this.saveBankInfo(null, id, map.toString(),
						result.toString(), "active","customer");
				if(null !=result.get("List")){
					List<Map<String,Object>> list= (List<Map<String, Object>>) result.get("List");
					resp = new BankBaseResp();
					List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
					for(Map<String,Object> m : list){
						B2bEconomicsBankCompanyDto ebc = new B2bEconomicsBankCompanyDto();
						String customerNumber = null==m.get("CifNo")?"":m.get("CifNo").toString();//银行客户号
						String contractNo = null==m.get("ContNo")?"":m.get("ContNo").toString();//合同编号
						String proNumbber = null==m.get("ProdId")?"":m.get("ProdId").toString();//产品编号
//					String customrCreditNumber = json.isNull("CustCredId")?"":json.get("CustCredId").toString();//客户授信编号
						Double contractAmount = null==m.get("ContNo")?0d:Double.parseDouble(m.get("ContTotalAmt").toString());//授信额度
						Double avaiableAmount = null==m.get("LeftAmt")?0d:Double.parseDouble(m.get("LeftAmt").toString());//可用额度
						String startDate = null==m.get("BeginDate")?"":m.get("BeginDate").toString();//生效开始日期
						String endDate = null==m.get("EndDate")?"":m.get("EndDate").toString();//生效结束日期
						ebc.setPk(KeyUtils.getUUID());
						ebc.setCreditAmount(contractAmount);
						ebc.setCompanyPk(company.getPk());
						ebc.setBankPk(dto.getBankPk());
						ebc.setBankName(dto.getBank());
						ebc.setCreditStartTime(DateUtil.parseDateFormatYMD(startDate));
						ebc.setCreditEndTime(DateUtil.parseDateFormatYMD(endDate));
						ebc.setContractAmount(contractAmount);
						ebc.setAvailableAmount(avaiableAmount);
						ebc.setContractNumber(contractNo);
						ebc.setAmountType(proNumbber);
						ebc.setCompanyPk(company.getPk());
						ebc.setCustomerNumber(customerNumber);
						if(SN_LOAN_CODE.equals(proNumbber)){
							ebc.setType(1);//化纤白条类型
						}else if (SN_PLEDGE_CODE.equals(proNumbber)){
							ebc.setType(2);//化纤贷类型
						}
						ebcList.add(ebc);
					}
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
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("TrsCode", "B2ELoanStatusQry");
			map.put("ChannelFlag", PropertyConfig.getProperty("sn_no"));
			map.put("CifNo", loanNumber.getCustomerNumber());
			map.put("OrderNumber", loanNumber.getOrderNumber());
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","loan");
			Map<String, Object> result = PostUtils.post(PropertyConfig.getProperty("sn_url")+"/B2ELoanStatusQry.do", map);
			if(null != result){
				this.saveBankInfo(null, id, map.toString(),
						result.toString(), "active","loan");
					//loanStatus:1-待放款 2-已放款 3-取消放款
					if(null!=result.get("ListId") && null!=result.get("LoanStatus") && "2".equals(result.get("LoanStatus").toString())){
						resp = new BankBaseResp();
						//放款开始日期
						String loanStartTime = result.get("BeginDate").toString();
						//放款结束日期
						String loanEndTime = result.get("EndDate").toString();
						//借据编号
						String iouNumber = result.get("ListId").toString();
						//贷款账号
						String accountNo = result.get("LoanAcNo").toString();
						//贷款利率
						String InteRate = result.get("InteRate").toString();
						BankCreditDto cdto = new BankCreditDto();
						cdto.setLoanStartDate(DateUtil.parseDateFormatYMD(loanStartTime));
						cdto.setLoanEndDate(DateUtil.parseDateFormatYMD(loanEndTime));
						cdto.setIouNumber(iouNumber);
						cdto.setPayStatus(1);
						cdto.setLoanAccount(accountNo);
						cdto.setLoanRate(Double.parseDouble(InteRate));
						resp.setBankCreditDto(cdto);	
					}
					if(null!=result.get("ListId") && null!=result.get("LoanStatus") && "3".equals(result.get("LoanStatus").toString())){
						resp = new BankBaseResp();
						BankCreditDto cdto = new BankCreditDto();
						cdto.setLoanStatus(4);
						resp.setBankCreditDto(cdto);	
					}
			}
		} catch (Exception e) {
			logger.error("errorSearchLoan",e);
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		BankBaseResp resp =  null;
		String id = null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("TrsCode", "B2ELoanDetailQry");
			map.put("ChannelFlag", PropertyConfig.getProperty("sn_no"));
			map.put("CifNo", loanNumber.getCustomerNumber());
			map.put("ListId", loanNumber.getLoanNumber());
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","repayment");
			Map<String, Object> result = PostUtils.post(PropertyConfig.getProperty("sn_url")+"/B2ELoanDetailQry.do", map);
			if(null != result){
				this.saveBankInfo(null, id, map.toString(),
						result.toString(), "active","repayment");
				//总笔数
				if(null !=result.get("TotalNum") && Integer.parseInt(result.get("TotalNum").toString())>0){
					List<Map<String,Object>> list = (List<Map<String, Object>>) result.get("List");
						if(null != list && list.size()>0){
							List<B2bRepaymentRecord> brpdList = new ArrayList<B2bRepaymentRecord>();
							resp = new BankBaseResp();
							Double repaymentAmount =0d;
							for (int j = 0; j < list.size(); j++) {
								Map<String, Object> m  = list.get(j);
								B2bRepaymentRecord brpr = new B2bRepaymentRecord();
								brpr.setOrderNumber(loanNumber.getOrderNumber());
								brpr.setIouNumber(loanNumber.getLoanNumber());
								brpr.setStatus(1);
								brpr.setCompanyPk(loanNumber.getPurchaserPk());
								brpr.setCompanyName(loanNumber.getPurchaserName());
								brpr.setBankPk(loanNumber.getBankPk());
								brpr.setBankName(loanNumber.getBankName());
								brpr.setAmount(null!=m.get("Rcapi")?Double.parseDouble(m.get("Rcapi").toString()):0d);//实还本金
								brpr.setInterest(null!=m.get("Rinte")?Double.parseDouble(m.get("Rinte").toString()):0d);//实还利息
								brpr.setPenalty(null!=m.get("Rafine")?Double.parseDouble(m.get("Rafine").toString()):0d);//实还罚息
								brpr.setCreateTime(null!=m.get("Rdate")?m.get("Rdate").toString():"");//实还日期
								brpr.setOrganizationCode(loanNumber.getOrganizationCode());
								repaymentAmount+=brpr.getAmount();//实还本金(总)
								brpdList.add(brpr);
								//最后还款日
								if(j == list.size()-1){
									resp.setRepaymentDate(DateUtil.numberToString(brpr.getCreateTime()));
								}
							}
							//a-正常e-逾期h-核销o-结清
//							if(null!=result.get("AcFlag") && "o".equals(result.get("AcFlag").toString())){
//								resp.setLoanStatus(5);//5全部还款 
//							}else{
//								resp.setLoanStatus(6);//6部分还款
//							}
							if(loanNumber.getLoanAmount().toString().equals(ArithUtil.round(repaymentAmount, 2)+"")){
								resp.setLoanStatus(5);//5全部还款 
							}else{
								resp.setLoanStatus(6);//6部分还款
							}
							resp.setB2bRepaymentRecordList(brpdList);
						}
				}
				
			}
			
		} catch (Exception e) {
			logger.error("errorRepayment",e);
		}
		logger.error("searchRepayment of sunong", resp);
		return resp;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto) {

		BankBaseResp resp = null;
		String id = null;
		try {
			//金融产品支付比例
			Double proportion = cgDto.getProportion();
			if(null == proportion || proportion == 0d){
				proportion = 1d;
			}
			Double totalAmount = ArithUtil.round(ArithUtil.mul(order.getActualAmount(), proportion), 2);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("TrsCode", "B2EOrderPay");
			map.put("ChannelFlag", PropertyConfig.getProperty("sn_no"));
			map.put("OrderNumber", order.getOrderNumber());
			map.put("OrderAmt", totalAmount+"");
			map.put("ContNo", cgDto.getContractNumber());
			map.put("CifNo", cgDto.getBankAccountNumber());
			map.put("SaveaAmt", totalAmount+"");
			map.put("BeginDate", DateUtil.formatYearMonthDay(new Date()));
			map.put("EndDate",  DateUtil.formatYearMonthDay(DateUtil.getDaysForDate(new Date(),cgDto.getTerm())));
			
			
			List<Map<String,String>> orderList = new ArrayList<Map<String,String>>();
			Map<String,String> counterparty = new HashMap<String, String>();
			counterparty.put("CifName", order.getSupplier().getSupplierName());
			counterparty.put("PayName", order.getSupplier().getSupplierName());
			counterparty.put("PayAcNo", card.getBankAccount());
			counterparty.put("PayBankNo", card.getBankNo());
			counterparty.put("PayBankName", card.getBankName());
			counterparty.put("PayAmt", totalAmount+"");
			orderList.add(counterparty);
			map.put("List", orderList);
			id = this.saveBankInfo(null, null,
					map.toString(), null,
					"active","pay");
			//https接入,需要双向认证   结束
			Map<String, Object> result = PostUtils.post(PropertyConfig.getProperty("sn_url")+"/B2EOrderPay.do", map);
			if(null != result ){
				this.saveBankInfo(null, id, map.toString(),
						result.toString(), "active","pay");
				resp = new BankBaseResp();
				if(null != result && null !=result.get("IBSReturnCode")
						&& null !=result.get("ReturnCode")){
					if(SUCCESS.equals(result.get("IBSReturnCode").toString())
							&& SUCCESS.equals(result.get("ReturnCode").toString())){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null==result.get("ReturnMsg")?"银行信息数据异常":
							result.get("ReturnMsg").toString());
					}
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg(null==result.get("ReturnMsg")?"银行信息数据异常":
						result.get("ReturnMsg").toString());
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
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("TrsCode", "B2EOrderCancel");
			map.put("ChannelFlag", PropertyConfig.getProperty("sn_no"));
			map.put("CifNo", loanNumber.getCustomerNumber());
			map.put("OrderNumber", loanNumber.getOrderNumber());
			id = this.saveBankInfo(null, null,
					map.toString(), null,
						"active","cancel");
			//https接入,需要双向认证   结束
			Map<String, Object> result = PostUtils.post(PropertyConfig.getProperty("sn_url")+"/B2EOrderCancel.do", map);
			if(null != result){
				this.saveBankInfo(null, id, map.toString(),
						result.toString(), "active","cancel");
				resp = new BankBaseResp();
				if(null != result && null !=result.get("IBSReturnCode") && null !=result.get("ReturnCode")){
					if(SUCCESS.equals(result.get("IBSReturnCode").toString())
							&& (SUCCESS.equals(result.get("ReturnCode").toString()) ||
									NOORDER.equals(result.get("ReturnCode").toString()))){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null==result.get("ReturnMsg")?"银行信息数据异常":
							result.get("ReturnMsg").toString());
					}
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg(null==result.get("ReturnMsg")?"银行信息数据异常":
						result.get("ReturnMsg").toString());
				}
			}	
		}catch (Exception e){
			logger.error("errorCancelOrder",e);
		}
		return resp;
	}
	
	
	private String saveBankInfo(String code, String id, String requestValue,
			 String responseValue,
		 String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("sunongBank");
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
