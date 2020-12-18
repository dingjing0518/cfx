package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bInvitationRecordDto  extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String member;
	private java.lang.String mname;
	private java.lang.String mphone;
	private java.lang.String mcompanyPk;
	private java.lang.String mcompanyName;
	private java.lang.String beInvitedCode;
	private java.lang.String tname;
	private java.lang.String tphone;
	private java.lang.String tcompanyPk;
	private java.lang.String tcompanyName;
	private java.util.Date insertTime;
	private java.lang.Integer applyStatus;
	private java.lang.Integer invitationStatus;
	private java.lang.Integer awardStatus;
	private java.lang.String address;
	private java.lang.String provinceName;
	private java.lang.String province;
	private java.lang.String cityName;
	private java.lang.String city;
	private java.lang.String areaName;
	private java.lang.String area;
	private java.lang.String town;
	private java.lang.String townName;
	private java.lang.String addressPk;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.String activityPk;
	private java.lang.String awardName;
	private java.lang.Integer grantType;
	private java.lang.String note;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMember(java.lang.String member) {
		this.member = member;
	}
	
	public java.lang.String getMember() {
		return this.member;
	}
	public void setMname(java.lang.String mname) {
		this.mname = mname;
	}
	
	public java.lang.String getMname() {
		return this.mname;
	}
	public void setMphone(java.lang.String mphone) {
		this.mphone = mphone;
	}
	
	public java.lang.String getMphone() {
		return this.mphone;
	}
	public void setMcompanyPk(java.lang.String mcompanyPk) {
		this.mcompanyPk = mcompanyPk;
	}
	
	public java.lang.String getMcompanyPk() {
		return this.mcompanyPk;
	}
	public void setMcompanyName(java.lang.String mcompanyName) {
		this.mcompanyName = mcompanyName;
	}
	
	public java.lang.String getMcompanyName() {
		return this.mcompanyName;
	}
	public void setBeInvitedCode(java.lang.String beInvitedCode) {
		this.beInvitedCode = beInvitedCode;
	}
	
	public java.lang.String getBeInvitedCode() {
		return this.beInvitedCode;
	}
	public void setTname(java.lang.String tname) {
		this.tname = tname;
	}
	
	public java.lang.String getTname() {
		return this.tname;
	}
	public void setTphone(java.lang.String tphone) {
		this.tphone = tphone;
	}
	
	public java.lang.String getTphone() {
		return this.tphone;
	}
	public void setTcompanyPk(java.lang.String tcompanyPk) {
		this.tcompanyPk = tcompanyPk;
	}
	
	public java.lang.String getTcompanyPk() {
		return this.tcompanyPk;
	}
	public void setTcompanyName(java.lang.String tcompanyName) {
		this.tcompanyName = tcompanyName;
	}
	
	public java.lang.String getTcompanyName() {
		return this.tcompanyName;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setApplyStatus(java.lang.Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	public java.lang.Integer getApplyStatus() {
		return this.applyStatus;
	}
	public void setInvitationStatus(java.lang.Integer invitationStatus) {
		this.invitationStatus = invitationStatus;
	}
	
	public java.lang.Integer getInvitationStatus() {
		return this.invitationStatus;
	}
	public void setAwardStatus(java.lang.Integer awardStatus) {
		this.awardStatus = awardStatus;
	}
	
	public java.lang.Integer getAwardStatus() {
		return this.awardStatus;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	public java.lang.String getAddress() {
		return this.address;
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
	public void setAddressPk(java.lang.String addressPk) {
		this.addressPk = addressPk;
	}
	
	public java.lang.String getAddressPk() {
		return this.addressPk;
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
	public void setActivityPk(java.lang.String activityPk) {
		this.activityPk = activityPk;
	}
	
	public java.lang.String getActivityPk() {
		return this.activityPk;
	}
	public void setAwardName(java.lang.String awardName) {
		this.awardName = awardName;
	}
	
	public java.lang.String getAwardName() {
		return this.awardName;
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
	

}