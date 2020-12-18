package cn.cf.dto;

import java.io.Serializable;

public class PurchaserInvoiceDto implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String pk;

	private String invoiceName;// 公司抬头

	private String invoiceTaxidNumber; // 税号

	private String invoiceRegAddress;

	private String invoiceRegPhone;

	private String invoiceBankName;

	private String invoiceBankAccount;

	private String invoiceTime;// 开票时间

	private String applyTime; // 申请时间

	private String presentTotalFreight;

	private Integer memberInvoiceStatus;

	private String memberInvoiceName;
	
	private Integer orderStatus;
	
	private String contactTel;
	
	private String contactName;
	
	private String contactAddress;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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

	public String getInvoiceRegAddress() {
		return invoiceRegAddress;
	}

	public void setInvoiceRegAddress(String invoiceRegAddress) {
		this.invoiceRegAddress = invoiceRegAddress;
	}

	public String getInvoiceRegPhone() {
		return invoiceRegPhone;
	}

	public void setInvoiceRegPhone(String invoiceRegPhone) {
		this.invoiceRegPhone = invoiceRegPhone;
	}

	public String getInvoiceBankName() {
		return invoiceBankName;
	}

	public void setInvoiceBankName(String invoiceBankName) {
		this.invoiceBankName = invoiceBankName;
	}

	public String getInvoiceBankAccount() {
		return invoiceBankAccount;
	}

	public void setInvoiceBankAccount(String invoiceBankAccount) {
		this.invoiceBankAccount = invoiceBankAccount;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}


	public String getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(String presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public Integer getMemberInvoiceStatus() {
		return memberInvoiceStatus;
	}

	public void setMemberInvoiceStatus(Integer memberInvoiceStatus) {
		this.memberInvoiceStatus = memberInvoiceStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMemberInvoiceName() {
		return memberInvoiceName;
	}

	public void setMemberInvoiceName(String memberInvoiceName) {
		this.memberInvoiceName = memberInvoiceName;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	
	
}
