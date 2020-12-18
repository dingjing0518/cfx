package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgCompany  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.Integer isDelete;
	private java.lang.Integer auditStatus;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String province;
	private java.lang.String provinceName;
	private java.lang.String city;
	private java.lang.String cityName;
	private java.lang.String area;
	private java.lang.String areaName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.String businessLicense;
	private java.lang.String blUrl;
	private java.lang.Integer isVisable;
	private java.util.Date auditTime;
	private java.lang.String headPortrait;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
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
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
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
	public void setBusinessLicense(java.lang.String businessLicense) {
		this.businessLicense = businessLicense;
	}
	
	public java.lang.String getBusinessLicense() {
		return this.businessLicense;
	}
	public void setBlUrl(java.lang.String blUrl) {
		this.blUrl = blUrl;
	}
	
	public java.lang.String getBlUrl() {
		return this.blUrl;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setHeadPortrait(java.lang.String headPortrait) {
		this.headPortrait = headPortrait;
	}
	
	public java.lang.String getHeadPortrait() {
		return this.headPortrait;
	}
	

}