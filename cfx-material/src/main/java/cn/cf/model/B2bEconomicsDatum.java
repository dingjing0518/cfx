package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bEconomicsDatum  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String econCustmerPk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.Integer companyType;
	private java.lang.Double capacity;
	private java.lang.Integer datumType;
	private java.lang.String remarks;
	private java.lang.String rule;
	private java.lang.String url;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setEconCustmerPk(java.lang.String econCustmerPk) {
		this.econCustmerPk = econCustmerPk;
	}
	
	public java.lang.String getEconCustmerPk() {
		return this.econCustmerPk;
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
	public void setCompanyType(java.lang.Integer companyType) {
		this.companyType = companyType;
	}
	
	public java.lang.Integer getCompanyType() {
		return this.companyType;
	}
	public void setCapacity(java.lang.Double capacity) {
		this.capacity = capacity;
	}
	
	public java.lang.Double getCapacity() {
		return this.capacity;
	}
	public void setDatumType(java.lang.Integer datumType) {
		this.datumType = datumType;
	}
	
	public java.lang.Integer getDatumType() {
		return this.datumType;
	}
	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}
	
	public java.lang.String getRemarks() {
		return this.remarks;
	}
	public void setRule(java.lang.String rule) {
		this.rule = rule;
	}
	
	public java.lang.String getRule() {
		return this.rule;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	

}