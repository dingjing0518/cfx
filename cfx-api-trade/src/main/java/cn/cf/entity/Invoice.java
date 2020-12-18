package cn.cf.entity;

import cn.cf.dto.B2bCompanyDtoEx;

public class Invoice {
	private java.lang.String name;
	private java.lang.String organizationCode;
	private java.lang.String contactsTel;
	private java.lang.String bankName;
	private java.lang.String bankAccount;
	private java.lang.String regAddress;
	
	  
	public java.lang.String getName() {
		return name;
	}


	public void setName(java.lang.String name) {
		this.name = name;
	}


	public java.lang.String getOrganizationCode() {
		return organizationCode;
	}


	public void setOrganizationCode(java.lang.String organizationCode) {
		this.organizationCode = organizationCode;
	}


	public java.lang.String getContactsTel() {
		return contactsTel;
	}


	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}


	public java.lang.String getBankName() {
		return bankName;
	}


	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}


	public java.lang.String getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}


	public java.lang.String getRegAddress() {
		return regAddress;
	}


	public void setRegAddress(java.lang.String regAddress) {
		this.regAddress = regAddress;
	}


	public Invoice(B2bCompanyDtoEx company) {
		this.setName(null == company?"":company.getName());
		this.setOrganizationCode(null == company?"":company.getOrganizationCode());
		this.setContactsTel(null == company?"":company.getContactsTel());
		this.setBankAccount(null == company?"":company.getBankAccount());
		this.setBankName(null == company?"":company.getBankName());
		this.setRegAddress(null == company?"":company.getRegAddress());
	}
}
