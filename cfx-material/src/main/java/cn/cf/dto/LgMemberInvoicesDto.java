package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgMemberInvoicesDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String month;
	private java.lang.Double billingAmount;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String contactTel;
	private java.lang.String contactName;
	private java.lang.String contactAddress;
	private java.util.Date applyTime;
	private java.lang.Integer status;
	private java.lang.String bankAccount;
	private java.lang.String bankName;
	private java.lang.String regTel;
	private java.lang.String regAddress;
	private java.lang.String taxId;
	private java.lang.String name;
	private java.util.Date invoiceTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMonth(java.lang.String month) {
		this.month = month;
	}
	
	public java.lang.String getMonth() {
		return this.month;
	}
	public void setBillingAmount(java.lang.Double billingAmount) {
		this.billingAmount = billingAmount;
	}
	
	public java.lang.Double getBillingAmount() {
		return this.billingAmount;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setContactTel(java.lang.String contactTel) {
		this.contactTel = contactTel;
	}
	
	public java.lang.String getContactTel() {
		return this.contactTel;
	}
	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}
	
	public java.lang.String getContactName() {
		return this.contactName;
	}
	public void setContactAddress(java.lang.String contactAddress) {
		this.contactAddress = contactAddress;
	}
	
	public java.lang.String getContactAddress() {
		return this.contactAddress;
	}
	public void setApplyTime(java.util.Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public java.util.Date getApplyTime() {
		return this.applyTime;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public java.lang.String getBankAccount() {
		return this.bankAccount;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setRegTel(java.lang.String regTel) {
		this.regTel = regTel;
	}
	
	public java.lang.String getRegTel() {
		return this.regTel;
	}
	public void setRegAddress(java.lang.String regAddress) {
		this.regAddress = regAddress;
	}
	
	public java.lang.String getRegAddress() {
		return this.regAddress;
	}
	public void setTaxId(java.lang.String taxId) {
		this.taxId = taxId;
	}
	
	public java.lang.String getTaxId() {
		return this.taxId;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setInvoiceTime(java.util.Date invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	
	public java.util.Date getInvoiceTime() {
		return this.invoiceTime;
	}
	

}