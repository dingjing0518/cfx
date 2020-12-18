package cn.cf.entity;

public class CreditInfo {

	private Integer hpcnt;//上一年流动资金周转次数
	private String prchAmount;//累计采购金额
	private Integer prchCount;//累计采购次数
	private String rcName;//实控人姓名
	private String rcDocType;//实控人证件类型(1身份证)
	private String rcDocNumber;//实控人证件号码
	private String rcDocMobile;//实控人手机号
	private String taxAmount;//纳税金额
	private String coopStartDate;//合作开始时间
	private String creditAmount;//授信申请额度
	private Integer loanTrem;//贷款期限最大值
	
	private String customerNumber;//客户号
	private String creditNumber;//授信编号
	private String userId;//用户id
	private Double totalAmount;//总额度
	public Integer getHpcnt() {
		return hpcnt;
	}
	public void setHpcnt(Integer hpcnt) {
		this.hpcnt = hpcnt;
	}
	public Integer getPrchCount() {
		return prchCount;
	}
	public void setPrchCount(Integer prchCount) {
		this.prchCount = prchCount;
	}
	public String getRcName() {
		return rcName;
	}
	public void setRcName(String rcName) {
		this.rcName = rcName;
	}
	public String getRcDocType() {
		return rcDocType;
	}
	public void setRcDocType(String rcDocType) {
		this.rcDocType = rcDocType;
	}
	public String getRcDocNumber() {
		return rcDocNumber;
	}
	public void setRcDocNumber(String rcDocNumber) {
		this.rcDocNumber = rcDocNumber;
	}
	public String getRcDocMobile() {
		return rcDocMobile;
	}
	public void setRcDocMobile(String rcDocMobile) {
		this.rcDocMobile = rcDocMobile;
	}
	public String getCoopStartDate() {
		return coopStartDate;
	}
	public void setCoopStartDate(String coopStartDate) {
		this.coopStartDate = coopStartDate;
	}
	public Integer getLoanTrem() {
		return loanTrem;
	}
	public void setLoanTrem(Integer loanTrem) {
		this.loanTrem = loanTrem;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getCreditNumber() {
		return creditNumber;
	}
	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPrchAmount() {
		return prchAmount;
	}
	public void setPrchAmount(String prchAmount) {
		this.prchAmount = prchAmount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	
	
	
	
	
}
