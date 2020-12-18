package cn.cf.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SupplierInfo {
	
	private String supplierPk;
	private String supplierName;
	private String organizationCode;
	private String contacts;
	private String contactsTel;
	private String bankName;
	private String bankAccount;
	private String bankNo;
	
	public SupplierInfo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public SupplierInfo(String supplierPk, String supplierName,String organizationCode,
			String contacts, String contactsTel, String bankName,
			String bankAccount,String bankNo) {
		super();
		this.supplierPk = supplierPk;
		this.supplierName = supplierName;
		this.organizationCode = organizationCode;
		this.contacts = contacts;
		this.contactsTel = contactsTel;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
		this.bankNo = bankNo;
	}


	public SupplierInfo(String supplierInfo) {
		super();
		JSONObject js=JSON.parseObject(supplierInfo);
		this.supplierPk =  js.containsKey("supplierPk")?js.getString("supplierPk"):"";
		this.supplierName = js.containsKey("supplierName")?js.getString("supplierName"):"";
		this.organizationCode = js.containsKey("organizationCode")?js.getString("organizationCode"):"";
		this.contacts =  js.containsKey("contacts")?js.getString("contacts"):"";
		this.contactsTel =  js.containsKey("contactsTel")?js.getString("contactsTel"):"";
		this.bankName =  js.containsKey("bankName")?js.getString("bankName"):"";
		this.bankAccount =  js.containsKey("bankAccount")?js.getString("bankAccount"):"";
		this.bankNo =  js.containsKey("bankNo")?js.getString("bankNo"):"";
	}


	public String getSupplierPk() {
		return supplierPk;
	}
	public void setSupplierPk(String supplierPk) {
		this.supplierPk = supplierPk;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	public String getBankNo() {
		return bankNo;
	}


	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}


	public String getOrganizationCode() {
		return organizationCode;
	}


	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	
}
