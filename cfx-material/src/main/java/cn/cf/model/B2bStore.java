package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bStore extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String startTime;
	private java.lang.String endTime;
	private java.lang.Integer isOpen;
	private java.lang.String customerTel;
	private java.lang.String name;
	private java.lang.String qq;
	private java.lang.String introduce;
	private java.lang.Double upperWeight;
	private java.lang.Integer showPricebfOpen;
	private java.lang.String shopNotices;
	private java.lang.String logoSettings;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private Double tightStock;
	private java.lang.String block;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public Double getTightStock() {
		return tightStock;
	}

	public void setTightStock(Double tightStock) {
		this.tightStock = tightStock;
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
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime;
	}
	
	public java.lang.String getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}
	
	public java.lang.String getEndTime() {
		return this.endTime;
	}
	public void setIsOpen(java.lang.Integer isOpen) {
		this.isOpen = isOpen;
	}
	
	public java.lang.Integer getIsOpen() {
		return this.isOpen;
	}
	public void setCustomerTel(java.lang.String customerTel) {
		this.customerTel = customerTel;
	}
	
	public java.lang.String getCustomerTel() {
		return this.customerTel;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}
	
	public java.lang.String getQq() {
		return this.qq;
	}
	public void setIntroduce(java.lang.String introduce) {
		this.introduce = introduce;
	}
	
	public java.lang.String getIntroduce() {
		return this.introduce;
	}
	public void setUpperWeight(java.lang.Double upperWeight) {
		this.upperWeight = upperWeight;
	}
	
	public java.lang.Double getUpperWeight() {
		return this.upperWeight;
	}
	public void setShowPricebfOpen(java.lang.Integer showPricebfOpen) {
		this.showPricebfOpen = showPricebfOpen;
	}
	
	public java.lang.Integer getShowPricebfOpen() {
		return this.showPricebfOpen;
	}
	public void setShopNotices(java.lang.String shopNotices) {
		this.shopNotices = shopNotices;
	}
	
	public java.lang.String getShopNotices() {
		return this.shopNotices;
	}
	public void setLogoSettings(java.lang.String logoSettings) {
		this.logoSettings = logoSettings;
	}
	
	public java.lang.String getLogoSettings() {
		return this.logoSettings;
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

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
}