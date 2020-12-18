package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bEconomicsBankCompanyDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String bankPk;
	private java.lang.String bankName;
	private java.lang.Double creditAmount;
	private java.util.Date creditStartTime;
	private java.util.Date creditEndTime;
	private java.lang.String customerNumber;
	private java.lang.String contractNumber;
	private java.lang.String agreementNumber;
	private java.lang.Double contractAmount;
	private java.lang.Double availableAmount;
	private java.lang.String amountType;
	private java.lang.Integer type;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setBankPk(java.lang.String bankPk) {
		this.bankPk = bankPk;
	}
	
	public java.lang.String getBankPk() {
		return this.bankPk;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setCreditAmount(java.lang.Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public java.lang.Double getCreditAmount() {
		return this.creditAmount;
	}
	public void setCreditStartTime(java.util.Date creditStartTime) {
		this.creditStartTime = creditStartTime;
	}
	
	public java.util.Date getCreditStartTime() {
		return this.creditStartTime;
	}
	public void setCreditEndTime(java.util.Date creditEndTime) {
		this.creditEndTime = creditEndTime;
	}
	
	public java.util.Date getCreditEndTime() {
		return this.creditEndTime;
	}
	public void setCustomerNumber(java.lang.String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public java.lang.String getCustomerNumber() {
		return this.customerNumber;
	}
	public void setContractNumber(java.lang.String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	public java.lang.String getContractNumber() {
		return this.contractNumber;
	}
	public void setAgreementNumber(java.lang.String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}
	
	public java.lang.String getAgreementNumber() {
		return this.agreementNumber;
	}
	public void setContractAmount(java.lang.Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	public java.lang.Double getContractAmount() {
		return this.contractAmount;
	}
	public void setAvailableAmount(java.lang.Double availableAmount) {
		this.availableAmount = availableAmount;
	}
	
	public java.lang.Double getAvailableAmount() {
		return this.availableAmount;
	}
	public void setAmountType(java.lang.String amountType) {
		this.amountType = amountType;
	}
	
	public java.lang.String getAmountType() {
		return this.amountType;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	

}