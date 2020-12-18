package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgUserInvoiceDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String userPk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String taxidNumber;
	private java.lang.String regPhone;
	private java.lang.String bankAccount;
	private java.lang.String bankName;
	private java.lang.String regAddress;
	private java.lang.String province;
	private java.lang.String provinceName;
	private java.lang.String city;
	private java.lang.String cityName;
	private java.lang.String area;
	private java.lang.String areaName;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String recipt;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setUserPk(java.lang.String userPk) {
		this.userPk = userPk;
	}
	
	public java.lang.String getUserPk() {
		return this.userPk;
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
	public void setTaxidNumber(java.lang.String taxidNumber) {
		this.taxidNumber = taxidNumber;
	}
	
	public java.lang.String getTaxidNumber() {
		return this.taxidNumber;
	}
	public void setRegPhone(java.lang.String regPhone) {
		this.regPhone = regPhone;
	}
	
	public java.lang.String getRegPhone() {
		return this.regPhone;
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
	public void setRegAddress(java.lang.String regAddress) {
		this.regAddress = regAddress;
	}
	
	public java.lang.String getRegAddress() {
		return this.regAddress;
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
	public void setRecipt(java.lang.String recipt) {
		this.recipt = recipt;
	}
	
	public java.lang.String getRecipt() {
		return this.recipt;
	}
	

}