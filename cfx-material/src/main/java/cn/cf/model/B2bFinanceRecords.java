package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bFinanceRecords  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String supplierPk;
	private java.lang.String supplierName;
	private java.lang.Integer status;
	private java.lang.String transactionAccount;
	private java.lang.String receivablesAccount;
	private java.lang.Double transactionAmount;
	private java.lang.Integer transactionType;
	private java.util.Date insertTime;
	private java.lang.String transactionAccountName;
	private java.lang.String receivablesAccountName;
	private java.lang.String description;
	private java.lang.Double poundage;
	private java.lang.String currentIp;
	private java.lang.String orderNumber;
	private java.lang.String serialNumber;
	private java.lang.String iouNumber;
	private java.util.Date loanEndTime;
	private java.lang.String reason;
	private java.lang.String employeePk;
	private java.lang.String employeeName;
	private java.lang.String employeeNumber;
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
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	
	public java.lang.String getCompanyName() {
		return this.companyName;
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
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setTransactionAccount(java.lang.String transactionAccount) {
		this.transactionAccount = transactionAccount;
	}
	
	public java.lang.String getTransactionAccount() {
		return this.transactionAccount;
	}
	public void setReceivablesAccount(java.lang.String receivablesAccount) {
		this.receivablesAccount = receivablesAccount;
	}
	
	public java.lang.String getReceivablesAccount() {
		return this.receivablesAccount;
	}
	public void setTransactionAmount(java.lang.Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public java.lang.Double getTransactionAmount() {
		return this.transactionAmount;
	}
	public void setTransactionType(java.lang.Integer transactionType) {
		this.transactionType = transactionType;
	}
	
	public java.lang.Integer getTransactionType() {
		return this.transactionType;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setTransactionAccountName(java.lang.String transactionAccountName) {
		this.transactionAccountName = transactionAccountName;
	}
	
	public java.lang.String getTransactionAccountName() {
		return this.transactionAccountName;
	}
	public void setReceivablesAccountName(java.lang.String receivablesAccountName) {
		this.receivablesAccountName = receivablesAccountName;
	}
	
	public java.lang.String getReceivablesAccountName() {
		return this.receivablesAccountName;
	}
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setPoundage(java.lang.Double poundage) {
		this.poundage = poundage;
	}
	
	public java.lang.Double getPoundage() {
		return this.poundage;
	}
	public void setCurrentIp(java.lang.String currentIp) {
		this.currentIp = currentIp;
	}
	
	public java.lang.String getCurrentIp() {
		return this.currentIp;
	}
	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setSerialNumber(java.lang.String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public java.lang.String getSerialNumber() {
		return this.serialNumber;
	}
	public void setIouNumber(java.lang.String iouNumber) {
		this.iouNumber = iouNumber;
	}
	
	public java.lang.String getIouNumber() {
		return this.iouNumber;
	}
	public void setLoanEndTime(java.util.Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}
	
	public java.util.Date getLoanEndTime() {
		return this.loanEndTime;
	}
	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}
	
	public java.lang.String getReason() {
		return this.reason;
	}
	public void setEmployeePk(java.lang.String employeePk) {
		this.employeePk = employeePk;
	}
	
	public java.lang.String getEmployeePk() {
		return this.employeePk;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	
	public java.lang.String getEmployeeName() {
		return this.employeeName;
	}
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public java.lang.String getEmployeeNumber() {
		return this.employeeNumber;
	}
	

}