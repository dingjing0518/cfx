package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCredit  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.Integer creditStatus;
	private java.lang.String creditReason;
	private java.lang.String creditContacts;
	private java.lang.String creditContactsTel;
	private java.lang.String creditAddress;
	private java.util.Date creditInsertTime;
	private java.util.Date creditAuditTime;
	private java.lang.String memberPk;
	private java.lang.String virtualPayPassword;
	private java.lang.Integer isDelete;
	private java.lang.String financePk;
	private java.lang.String financeContacts;
	private java.lang.String source;
	private java.lang.String processInstanceId;
	private java.lang.Integer certificateStatus;
	private java.lang.String delegateCertUrl;
	private java.lang.Integer taxNature;
	private java.lang.String taxAuthorityCode;
	private java.lang.String taxAuthorityName;
	private java.lang.String creditInfo;
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
	public void setCreditStatus(java.lang.Integer creditStatus) {
		this.creditStatus = creditStatus;
	}
	
	public java.lang.Integer getCreditStatus() {
		return this.creditStatus;
	}
	public void setCreditReason(java.lang.String creditReason) {
		this.creditReason = creditReason;
	}
	
	public java.lang.String getCreditReason() {
		return this.creditReason;
	}
	public void setCreditContacts(java.lang.String creditContacts) {
		this.creditContacts = creditContacts;
	}
	
	public java.lang.String getCreditContacts() {
		return this.creditContacts;
	}
	public void setCreditContactsTel(java.lang.String creditContactsTel) {
		this.creditContactsTel = creditContactsTel;
	}
	
	public java.lang.String getCreditContactsTel() {
		return this.creditContactsTel;
	}
	public void setCreditAddress(java.lang.String creditAddress) {
		this.creditAddress = creditAddress;
	}
	
	public java.lang.String getCreditAddress() {
		return this.creditAddress;
	}
	public void setCreditInsertTime(java.util.Date creditInsertTime) {
		this.creditInsertTime = creditInsertTime;
	}
	
	public java.util.Date getCreditInsertTime() {
		return this.creditInsertTime;
	}
	public void setCreditAuditTime(java.util.Date creditAuditTime) {
		this.creditAuditTime = creditAuditTime;
	}
	
	public java.util.Date getCreditAuditTime() {
		return this.creditAuditTime;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setVirtualPayPassword(java.lang.String virtualPayPassword) {
		this.virtualPayPassword = virtualPayPassword;
	}
	
	public java.lang.String getVirtualPayPassword() {
		return this.virtualPayPassword;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setFinancePk(java.lang.String financePk) {
		this.financePk = financePk;
	}
	
	public java.lang.String getFinancePk() {
		return this.financePk;
	}
	public void setFinanceContacts(java.lang.String financeContacts) {
		this.financeContacts = financeContacts;
	}
	
	public java.lang.String getFinanceContacts() {
		return this.financeContacts;
	}
	public void setSource(java.lang.String source) {
		this.source = source;
	}
	
	public java.lang.String getSource() {
		return this.source;
	}
	public void setProcessInstanceId(java.lang.String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public java.lang.String getProcessInstanceId() {
		return this.processInstanceId;
	}
	public void setCertificateStatus(java.lang.Integer certificateStatus) {
		this.certificateStatus = certificateStatus;
	}
	
	public java.lang.Integer getCertificateStatus() {
		return this.certificateStatus;
	}
	public void setDelegateCertUrl(java.lang.String delegateCertUrl) {
		this.delegateCertUrl = delegateCertUrl;
	}
	
	public java.lang.String getDelegateCertUrl() {
		return this.delegateCertUrl;
	}
	public void setTaxNature(java.lang.Integer taxNature) {
		this.taxNature = taxNature;
	}
	
	public java.lang.Integer getTaxNature() {
		return this.taxNature;
	}
	public void setTaxAuthorityCode(java.lang.String taxAuthorityCode) {
		this.taxAuthorityCode = taxAuthorityCode;
	}
	
	public java.lang.String getTaxAuthorityCode() {
		return this.taxAuthorityCode;
	}
	public void setTaxAuthorityName(java.lang.String taxAuthorityName) {
		this.taxAuthorityName = taxAuthorityName;
	}
	
	public java.lang.String getTaxAuthorityName() {
		return this.taxAuthorityName;
	}
	public void setCreditInfo(java.lang.String creditInfo) {
		this.creditInfo = creditInfo;
	}
	
	public java.lang.String getCreditInfo() {
		return this.creditInfo;
	}
	

}