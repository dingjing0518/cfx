package cn.cf.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class PurchaserInfo {

	private String purchaserPk;
	private String purchaserName;
	private String contacts;
	private String contactsTel;
	private String organizationCode;
	private String invoicePk;
	private String invoiceName;
	private String invoiceTaxidNumber;
	private String invoiceRegPhone;
	private String invoiceBankAccount;
	private String invoiceBankName;
	private String invoiceRegAddress;
	
	public PurchaserInfo() {
		// TODO Auto-generated constructor stub
	}
	public PurchaserInfo(String purchaserInfo) {
		JSONObject js=JSON.parseObject(purchaserInfo);
		this.purchaserPk = js.containsKey("purchaserPk")?js.getString("purchaserPk"):"";
		this.purchaserName = js.containsKey("purchaserName")?js.getString("purchaserName"):"";
		this.contacts = js.containsKey("contacts")?js.getString("contacts"):"";
		this.contactsTel = js.containsKey("contactsTel")?js.getString("contactsTel"):"";
		this.organizationCode = js.containsKey("organizationCode")?js.getString("organizationCode"):"";
		this.invoicePk = js.containsKey("invoicePk")?js.getString("invoicePk"):"" ;
		this.invoiceName = js.containsKey("invoiceName")?js.getString("invoiceName"):"" ;
		this.invoiceTaxidNumber =js.containsKey("invoiceTaxidNumber")?js.getString("invoiceTaxidNumber"):"" ;
		this.invoiceRegPhone = js.containsKey("invoiceRegPhone")?js.getString("invoiceRegPhone"):"";
		this.invoiceBankAccount = js.containsKey("invoiceBankAccount")?js.getString("invoiceBankAccount"):"";
		this.invoiceBankName = js.containsKey("invoiceBankName")?js.getString("invoiceBankName"):"";
		this.invoiceRegAddress = js.containsKey("invoiceRegAddress")?js.getString("invoiceRegAddress"):"" ;
	}
	
	public PurchaserInfo(String purchaserPk, String purchaserName, String contacts,
			String contactsTel, String organizationCode, String invoicePk,
			String invoiceName, String invoiceTaxidNumber, String invoiceRegPhone,
			String invoiceBankAccount, String invoiceBankName,
			String invoiceRegAddress) {
		super();
		this.purchaserPk = purchaserPk;
		this.purchaserName = purchaserName;
		this.contacts = contacts;
		this.contactsTel = contactsTel;
		this.organizationCode = organizationCode;
		this.invoicePk = invoicePk;
		this.invoiceName = invoiceName;
		this.invoiceTaxidNumber = invoiceTaxidNumber;
		this.invoiceRegPhone = invoiceRegPhone;
		this.invoiceBankAccount = invoiceBankAccount;
		this.invoiceBankName = invoiceBankName;
		this.invoiceRegAddress = invoiceRegAddress;
	}
	/**
	 * @return
	 */
	public String getPurchaserPk() {
		return purchaserPk;
	}

	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
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
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getInvoicePk() {
		return invoicePk;
	}
	public void setInvoicePk(String invoicePk) {
		this.invoicePk = invoicePk;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	public String getInvoiceTaxidNumber() {
		return invoiceTaxidNumber;
	}
	public void setInvoiceTaxidNumber(String invoiceTaxidNumber) {
		this.invoiceTaxidNumber = invoiceTaxidNumber;
	}
	public String getInvoiceRegPhone() {
		return invoiceRegPhone;
	}
	public void setInvoiceRegPhone(String invoiceRegPhone) {
		this.invoiceRegPhone = invoiceRegPhone;
	}
	public String getInvoiceBankAccount() {
		return invoiceBankAccount;
	}
	public void setInvoiceBankAccount(String invoiceBankAccount) {
		this.invoiceBankAccount = invoiceBankAccount;
	}
	public String getInvoiceBankName() {
		return invoiceBankName;
	}
	public void setInvoiceBankName(String invoiceBankName) {
		this.invoiceBankName = invoiceBankName;
	}
	public String getInvoiceRegAddress() {
		return invoiceRegAddress;
	}
	public void setInvoiceRegAddress(String invoiceRegAddress) {
		this.invoiceRegAddress = invoiceRegAddress;
	}
	
	
	
}
