package cn.cf.dto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCreditDtoEx extends B2bCreditDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double creditBankAmount;
	private String yzmlsh;//验证码流水号
	private String yzm;//验证码
	private String newPhone;
	private String newYzm;
	private String newYzmlsh;//新验证码流水号
	private String StartTime;
	private String creditAvaiableAmount;
	private String pledgeAvaiableAmount;
	private Integer isAmount;//1已授额 2未授额
	private String EndTime;
	private String orderUse;
	private String code;
	private String serialNumber;
	private String receiptIds;//银行回单编号列表
	private Double pledgeUsAmount;//货押已使用额度
	private String bankAccount;
	private String bankName;
	private String beInvitedCode;//受邀码
	public Double getCreditBankAmount() {
		return creditBankAmount;
	}
	public void setCreditBankAmount(Double creditBankAmount) {
		this.creditBankAmount = creditBankAmount;
	}
	public String getYzmlsh() {
		return yzmlsh;
	}
	public void setYzmlsh(String yzmlsh) {
		this.yzmlsh = yzmlsh;
	}
	public String getYzm() {
		return yzm;
	}
	public void setYzm(String yzm) {
		this.yzm = yzm;
	}
	public String getNewPhone() {
		return newPhone;
	}
	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}
	public String getNewYzm() {
		return newYzm;
	}
	public void setNewYzm(String newYzm) {
		this.newYzm = newYzm;
	}
	public String getNewYzmlsh() {
		return newYzmlsh;
	}
	public void setNewYzmlsh(String newYzmlsh) {
		this.newYzmlsh = newYzmlsh;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getCreditAvaiableAmount() {
		return creditAvaiableAmount;
	}
	public void setCreditAvaiableAmount(String creditAvaiableAmount) {
		this.creditAvaiableAmount = creditAvaiableAmount;
	}
	public String getPledgeAvaiableAmount() {
		return pledgeAvaiableAmount;
	}
	public void setPledgeAvaiableAmount(String pledgeAvaiableAmount) {
		this.pledgeAvaiableAmount = pledgeAvaiableAmount;
	}
	public Integer getIsAmount() {
		return isAmount;
	}
	public void setIsAmount(Integer isAmount) {
		this.isAmount = isAmount;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getOrderUse() {
		return orderUse;
	}
	public void setOrderUse(String orderUse) {
		this.orderUse = orderUse;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getReceiptIds() {
		return receiptIds;
	}
	public void setReceiptIds(String receiptIds) {
		this.receiptIds = receiptIds;
	}
	public Double getPledgeUsAmount() {
		return pledgeUsAmount;
	}
	public void setPledgeUsAmount(Double pledgeUsAmount) {
		this.pledgeUsAmount = pledgeUsAmount;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBeInvitedCode() {
		return beInvitedCode;
	}
	public void setBeInvitedCode(String beInvitedCode) {
		this.beInvitedCode = beInvitedCode;
	}
	
}