package cn.cf.entry;

import java.util.Date;
import java.util.List;

import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.OrderPaymentLimitDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.model.B2bEconomicsBankCompany;

public class BankBaseResp {

	private String code;
	
	private String msg;
	//银行额度
	private List<B2bEconomicsBankCompanyDto> ebcList;
	//放款信息
	private BankCreditDto bankCreditDto;
	//还款信息
	private List<B2bRepaymentRecord> B2bRepaymentRecordList;
	//还款状态
	private Integer loanStatus;
	//支付状态
	private Integer payStatus;
	//最后还款日期
	private Date repaymentDate;
	//授信用户信息
	private B2bEconomicsBankCompany economicsBankCompany;
	//多笔支付记录
	private List<OrderPaymentLimitDto> orderPaymentList;
	//还款记录类型(null:不需要做代扣  1需要做代扣(根据还款金额推算还款记录) 2需要做代扣(根据还款流水推算还款记录) )
	private Integer repaymentType;
	//贷款账户、银行流水号
	private String loanAccount;
	//代扣状态
	private Integer agentPayStatus;
	//微众文件
	private String wxFileName;
	//回调地址
	private String returnUrl;
	//流水号
	private String serialNumber;
	//html页面
	private String html;
	//票据信息
	private List<B2bBillInventoryDto> inventoryList;
	//票据支付流水状态
	private Integer billPayStatus;//1处理中 2成功 3失败
	//票据支付流水状态
	private Integer billOrderStatus;//1处理中 2成功 3失败
	//票据客户账户
	private List<B2bBillSigncardDto> billcardList;
	//贴现产品额度信息
	private B2bBillGoodsDto b2bBillGoodsDto;
	
	private BankJiansheResp bankJiansheResp;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<B2bEconomicsBankCompanyDto> getEbcList() {
		return ebcList;
	}

	public void setEbcList(List<B2bEconomicsBankCompanyDto> ebcList) {
		this.ebcList = ebcList;
	}

	public BankCreditDto getBankCreditDto() {
		return bankCreditDto;
	}

	public void setBankCreditDto(BankCreditDto bankCreditDto) {
		this.bankCreditDto = bankCreditDto;
	}

	public List<B2bRepaymentRecord> getB2bRepaymentRecordList() {
		return B2bRepaymentRecordList;
	}

	public void setB2bRepaymentRecordList(
			List<B2bRepaymentRecord> b2bRepaymentRecordList) {
		B2bRepaymentRecordList = b2bRepaymentRecordList;
	}

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public B2bEconomicsBankCompany getEconomicsBankCompany() {
		return economicsBankCompany;
	}

	public void setEconomicsBankCompany(B2bEconomicsBankCompany economicsBankCompany) {
		this.economicsBankCompany = economicsBankCompany;
	}

	public List<OrderPaymentLimitDto> getOrderPaymentList() {
		return orderPaymentList;
	}

	public void setOrderPaymentList(List<OrderPaymentLimitDto> orderPaymentList) {
		this.orderPaymentList = orderPaymentList;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Integer getAgentPayStatus() {
		return agentPayStatus;
	}

	public void setAgentPayStatus(Integer agentPayStatus) {
		this.agentPayStatus = agentPayStatus;
	}

	public String getWxFileName() {
		return wxFileName;
	}

	public void setWxFileName(String wxFileName) {
		this.wxFileName = wxFileName;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public List<B2bBillInventoryDto> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<B2bBillInventoryDto> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public Integer getBillPayStatus() {
		return billPayStatus;
	}

	public void setBillPayStatus(Integer billPayStatus) {
		this.billPayStatus = billPayStatus;
	}

	

	public List<B2bBillSigncardDto> getBillcardList() {
		return billcardList;
	}

	public void setBillcardList(List<B2bBillSigncardDto> billcardList) {
		this.billcardList = billcardList;
	}

	public B2bBillGoodsDto getB2bBillGoodsDto() {
		return b2bBillGoodsDto;
	}

	public void setB2bBillGoodsDto(B2bBillGoodsDto b2bBillGoodsDto) {
		this.b2bBillGoodsDto = b2bBillGoodsDto;
	}

	public Integer getBillOrderStatus() {
		return billOrderStatus;
	}

	public void setBillOrderStatus(Integer billOrderStatus) {
		this.billOrderStatus = billOrderStatus;
	}

	public String getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}

	public BankJiansheResp getBankJiansheResp() {
		return bankJiansheResp;
	}

	public void setBankJiansheResp(BankJiansheResp bankJiansheResp) {
		this.bankJiansheResp = bankJiansheResp;
	}
	
	
	
	
}
