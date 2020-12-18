package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLoanNumber  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.String loanNumber;
	private java.lang.String contractNumber;
	private java.lang.String bankPk;
	private java.lang.String bankName;
	private java.lang.Integer loanStatus;
	private java.lang.Double loanAmount;
	private java.util.Date loanStartTime;
	private java.util.Date loanEndTime;
	private java.util.Date repaymentTime;
	private java.lang.Double principal;
	private java.lang.Double interest;
	private java.lang.Double penalty;
	private java.lang.Double compound;
	private java.lang.String economicsGoodsName;
	private java.lang.Integer economicsGoodsType;
	private java.lang.String purchaserPk;
	private java.lang.String purchaserName;
	private java.lang.String supplierPk;
	private java.lang.String supplierName;
	private java.lang.String organizationCode;
	private java.lang.String customerNumber;
	private java.lang.String loanAccount;
	private java.util.Date insertTime;
	private java.lang.Double totalRate;
	private java.lang.Double bankRate;
	private java.lang.Double sevenRate;
	private java.lang.Double repaidInterest;
	private java.lang.Double repaidSerCharge;
	private java.lang.Integer isOverdue;
	private java.lang.String qrCode;
	private java.lang.String returnUrl;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setLoanNumber(java.lang.String loanNumber) {
		this.loanNumber = loanNumber;
	}
	
	public java.lang.String getLoanNumber() {
		return this.loanNumber;
	}
	public void setContractNumber(java.lang.String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	public java.lang.String getContractNumber() {
		return this.contractNumber;
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
	public void setLoanStatus(java.lang.Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	public java.lang.Integer getLoanStatus() {
		return this.loanStatus;
	}
	public void setLoanAmount(java.lang.Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public java.lang.Double getLoanAmount() {
		return this.loanAmount;
	}
	public void setLoanStartTime(java.util.Date loanStartTime) {
		this.loanStartTime = loanStartTime;
	}
	
	public java.util.Date getLoanStartTime() {
		return this.loanStartTime;
	}
	public void setLoanEndTime(java.util.Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}
	
	public java.util.Date getLoanEndTime() {
		return this.loanEndTime;
	}
	public void setRepaymentTime(java.util.Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	
	public java.util.Date getRepaymentTime() {
		return this.repaymentTime;
	}
	public void setPrincipal(java.lang.Double principal) {
		this.principal = principal;
	}
	
	public java.lang.Double getPrincipal() {
		return this.principal;
	}
	public void setInterest(java.lang.Double interest) {
		this.interest = interest;
	}
	
	public java.lang.Double getInterest() {
		return this.interest;
	}
	public void setPenalty(java.lang.Double penalty) {
		this.penalty = penalty;
	}
	
	public java.lang.Double getPenalty() {
		return this.penalty;
	}
	public void setCompound(java.lang.Double compound) {
		this.compound = compound;
	}
	
	public java.lang.Double getCompound() {
		return this.compound;
	}
	public void setEconomicsGoodsName(java.lang.String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}
	
	public java.lang.String getEconomicsGoodsName() {
		return this.economicsGoodsName;
	}
	public void setEconomicsGoodsType(java.lang.Integer economicsGoodsType) {
		this.economicsGoodsType = economicsGoodsType;
	}
	
	public java.lang.Integer getEconomicsGoodsType() {
		return this.economicsGoodsType;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
	}
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}
	public void setOrganizationCode(java.lang.String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	public java.lang.String getOrganizationCode() {
		return this.organizationCode;
	}
	public void setCustomerNumber(java.lang.String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public java.lang.String getCustomerNumber() {
		return this.customerNumber;
	}
	public void setLoanAccount(java.lang.String loanAccount) {
		this.loanAccount = loanAccount;
	}
	
	public java.lang.String getLoanAccount() {
		return this.loanAccount;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setTotalRate(java.lang.Double totalRate) {
		this.totalRate = totalRate;
	}
	
	public java.lang.Double getTotalRate() {
		return this.totalRate;
	}
	public void setBankRate(java.lang.Double bankRate) {
		this.bankRate = bankRate;
	}
	
	public java.lang.Double getBankRate() {
		return this.bankRate;
	}
	public void setSevenRate(java.lang.Double sevenRate) {
		this.sevenRate = sevenRate;
	}
	
	public java.lang.Double getSevenRate() {
		return this.sevenRate;
	}
	public void setRepaidInterest(java.lang.Double repaidInterest) {
		this.repaidInterest = repaidInterest;
	}
	
	public java.lang.Double getRepaidInterest() {
		return this.repaidInterest;
	}
	public void setRepaidSerCharge(java.lang.Double repaidSerCharge) {
		this.repaidSerCharge = repaidSerCharge;
	}
	
	public java.lang.Double getRepaidSerCharge() {
		return this.repaidSerCharge;
	}
	public void setIsOverdue(java.lang.Integer isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	public java.lang.Integer getIsOverdue() {
		return this.isOverdue;
	}
	public void setQrCode(java.lang.String qrCode) {
		this.qrCode = qrCode;
	}
	
	public java.lang.String getQrCode() {
		return this.qrCode;
	}
	public void setReturnUrl(java.lang.String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public java.lang.String getReturnUrl() {
		return this.returnUrl;
	}
	

}