package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillCustomerDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.String address;
	private java.lang.Integer status;
	private java.util.Date insertTime;
	private java.util.Date auditTime;
	private java.lang.Integer isDelete;
	private java.lang.String assidirPk;
	private java.lang.String assidirName;
	private java.lang.String processInstanceId;
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
	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}
	
	public java.lang.String getContacts() {
		return this.contacts;
	}
	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}
	
	public java.lang.String getContactsTel() {
		return this.contactsTel;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setAssidirPk(java.lang.String assidirPk) {
		this.assidirPk = assidirPk;
	}
	
	public java.lang.String getAssidirPk() {
		return this.assidirPk;
	}
	public void setAssidirName(java.lang.String assidirName) {
		this.assidirName = assidirName;
	}
	
	public java.lang.String getAssidirName() {
		return this.assidirName;
	}
	public void setProcessInstanceId(java.lang.String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public java.lang.String getProcessInstanceId() {
		return this.processInstanceId;
	}
	

}