package cn.cf.entity;

import cn.cf.dto.B2bAddressDto;

public class Address {
	private java.lang.String provinceName;
	private java.lang.String cityName;
	private java.lang.String areaName;
	private java.lang.String townName;
	private java.lang.String address;
	private java.lang.String signingCompany;
	private java.lang.String contacts;
	private java.lang.String contactsTel;

	public java.lang.String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	public java.lang.String getCityName() {
		return cityName;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	public java.lang.String getAreaName() {
		return areaName;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	public java.lang.String getTownName() {
		return townName;
	}
	public void setTownName(java.lang.String townName) {
		this.townName = townName;
	}
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getSigningCompany() {
		return signingCompany;
	}
	public void setSigningCompany(java.lang.String signingCompany) {
		this.signingCompany = signingCompany;
	}
	public java.lang.String getContacts() {
		return contacts;
	}
	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}
	public java.lang.String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}
	
	public Address(B2bAddressDto adto) {
		this.setAreaName(null == adto?"":adto.getAreaName());
		this.setProvinceName(null == adto?"":adto.getProvinceName());
		this.setCityName(null == adto?"":adto.getCityName());
		this.setTownName(null == adto?"":adto.getTownName());
		this.setAddress(null == adto?"":adto.getAddress());
		this.setContacts(null == adto?"":adto.getContacts());
		this.setContactsTel(null == adto?"":adto.getContactsTel());
		this.setSigningCompany(null == adto?"":adto.getSigningCompany());
	}
}
