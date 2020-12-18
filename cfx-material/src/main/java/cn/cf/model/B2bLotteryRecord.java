package cn.cf.model;

import java.util.Date;

import cn.cf.dto.BaseDTO;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLotteryRecord  extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String activityPk;
	private java.lang.String awardPk;
	private java.lang.Integer status;
	private java.lang.String name;
	private java.lang.String awardName;
	private java.lang.Integer awardStatus;
	private java.lang.Integer awardType;
	private java.util.Date insertTime;
	private java.lang.String provinceName;
	private java.lang.String province;
	private java.lang.String cityName;
	private java.lang.String city;
	private java.lang.String areaName;
	private java.lang.String area;
	private java.lang.String town;
	private java.lang.String townName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.Integer grantType;
	private java.lang.String note;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.String address;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String mobile;
	private java.lang.String addressPk;
	private java.lang.Integer activityType;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setActivityPk(java.lang.String activityPk) {
		this.activityPk = activityPk;
	}
	
	public java.lang.String getActivityPk() {
		return this.activityPk;
	}
	public void setAwardPk(java.lang.String awardPk) {
		this.awardPk = awardPk;
	}
	
	public java.lang.String getAwardPk() {
		return this.awardPk;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setAwardName(java.lang.String awardName) {
		this.awardName = awardName;
	}
	
	public java.lang.String getAwardName() {
		return this.awardName;
	}
	public void setAwardStatus(java.lang.Integer awardStatus) {
		this.awardStatus = awardStatus;
	}
	
	public java.lang.Integer getAwardStatus() {
		return this.awardStatus;
	}
	public void setAwardType(java.lang.Integer awardType) {
		this.awardType = awardType;
	}
	
	public java.lang.Integer getAwardType() {
		return this.awardType;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setTown(java.lang.String town) {
		this.town = town;
	}
	
	public java.lang.String getTown() {
		return this.town;
	}
	public void setTownName(java.lang.String townName) {
		this.townName = townName;
	}
	
	public java.lang.String getTownName() {
		return this.townName;
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
	public void setGrantType(java.lang.Integer grantType) {
		this.grantType = grantType;
	}
	
	public java.lang.Integer getGrantType() {
		return this.grantType;
	}
	public void setNote(java.lang.String note) {
		this.note = note;
	}
	
	public java.lang.String getNote() {
		return this.note;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setMemberName(java.lang.String memberName) {
		this.memberName = memberName;
	}
	
	public java.lang.String getMemberName() {
		return this.memberName;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	public java.lang.String getAddress() {
		return this.address;
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
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setAddressPk(java.lang.String addressPk) {
		this.addressPk = addressPk;
	}
	
	public java.lang.String getAddressPk() {
		return this.addressPk;
	}
	public void setActivityType(java.lang.Integer activityType) {
		this.activityType = activityType;
	}
	
	public java.lang.Integer getActivityType() {
		return this.activityType;
	}
	

}