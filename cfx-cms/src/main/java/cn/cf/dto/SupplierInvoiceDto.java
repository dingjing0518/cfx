package cn.cf.dto;

import java.io.Serializable;

public class SupplierInvoiceDto implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String pk;

	private String orderPk;

	private String logisticsCompanyName;

	private String contacts;

	private String contactsTel;
	
	private Double basisLinePrice; //单价

	private Double presentTotalFreight; //对外总价

	private Double weight;

	private Integer orderStatus;

	private String orderStatusName;
	
	private Integer supplierInvoiceStatus;

	private String supplierInvoiceName;
	
	private String insertTime;

	private Double settleWeight;
	
	private String applyTime;
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getOrderPk() {
		return orderPk;
	}

	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}

	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
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

	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public Integer getSupplierInvoiceStatus() {
		return supplierInvoiceStatus;
	}

	public void setSupplierInvoiceStatus(Integer supplierInvoiceStatus) {
		this.supplierInvoiceStatus = supplierInvoiceStatus;
	}

	public String getSupplierInvoiceName() {
		return supplierInvoiceName;
	}

	public void setSupplierInvoiceName(String supplierInvoiceName) {
		this.supplierInvoiceName = supplierInvoiceName;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getSettleWeight() {
		return settleWeight;
	}

	public void setSettleWeight(Double settleWeight) {
		this.settleWeight = settleWeight;
	}

	public Double getBasisLinePrice() {
		return basisLinePrice;
	}

	public void setBasisLinePrice(Double basisLinePrice) {
		this.basisLinePrice = basisLinePrice;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	
	
}
