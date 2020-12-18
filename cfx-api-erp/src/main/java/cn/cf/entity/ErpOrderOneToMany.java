package cn.cf.entity;

import java.util.List;


import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.util.DateUtil;

public class ErpOrderOneToMany {
	
	private String orderNumber;
	private String orderAmount;
	private String actualAmount;
	private List<ErpChildOrder> subOrderDetails;
	private String orderTime;
	private String address;
	private String contacts;
	private String contactsTel;
	private String meno;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String purchaserName;
	private String supplierName;
	private String paymentType;
	private String paymentTime;
	private String purchaserId;
	private String logisticsModelId;
	private String logisticsModelName;
	private String status;
	private String employeeNumber;
	private String employeeName;
	private String purchaseType;
	private String signingCompany;

	public String getSigningCompany() {
		return signingCompany;
	}

	public void setSigningCompany(String signingCompany) {
		this.signingCompany = signingCompany;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public List<ErpChildOrder> getSubOrderDetails() {
		return subOrderDetails;
	}

	public void setSubOrderDetails(List<ErpChildOrder> subOrderDetails) {
		this.subOrderDetails = subOrderDetails;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getLogisticsModelId() {
		return logisticsModelId;
	}

	public void setLogisticsModelId(String logisticsModelId) {
		this.logisticsModelId = logisticsModelId;
	}

	public String getLogisticsModelName() {
		return logisticsModelName;
	}

	public void setLogisticsModelName(String logisticsModelName) {
		this.logisticsModelName = logisticsModelName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	public ErpOrderOneToMany(B2bOrderDtoEx orderDto,List<ErpChildOrder> subOrderDetails){
		B2bOrderDtoMa b2bOrderDtoMa = new B2bOrderDtoMa();
		b2bOrderDtoMa.UpdateMine(orderDto);
		AddressInfo addressInfo = b2bOrderDtoMa.getAddress();
		PurchaserInfo purchaserInfo = b2bOrderDtoMa.getPurchaser();
		SupplierInfo supplierInfo = b2bOrderDtoMa.getSupplier();
		
		this.orderNumber = orderDto.getOrderNumber();
		this.orderAmount = orderDto.getOrderAmount()== null?"0.0":String.valueOf(orderDto.getOrderAmount());
		this.actualAmount = orderDto.getActualAmount() == null?"0.0":String.valueOf(orderDto.getActualAmount());
		this.subOrderDetails = subOrderDetails;
		this.orderTime = orderDto.getInsertTime()==null?"":DateUtil.formatDateAndTime(orderDto.getInsertTime());
		this.address = addressInfo.getAddress();
		this.contacts = addressInfo.getContacts();
		this.contactsTel = addressInfo.getContactsTel();
		this.meno = orderDto.getMeno();
		this.provinceName = addressInfo.getProvinceName();
		this.cityName = addressInfo.getCityName();
		this.areaName = addressInfo.getAreaName();
		this.townName = addressInfo.getTownName();
		this.purchaserName = purchaserInfo.getInvoiceName();
		this.supplierName = supplierInfo.getSupplierName();
		this.paymentType = orderDto.getPaymentType() == null?"":String.valueOf(orderDto.getPaymentType());
		this.paymentTime = orderDto.getPaymentTime()==null?"":DateUtil.formatDateAndTime(orderDto.getPaymentTime());
		this.purchaserId = orderDto.getPurchaserPk();
		this.logisticsModelId = orderDto.getLogisticsModelPk() == null?"":String.valueOf(orderDto.getLogisticsModelPk());
		this.logisticsModelName = orderDto.getLogisticsModelName();
		this.status = orderDto.getOrderStatus() == null?"":String.valueOf(orderDto.getOrderStatus());
		this.employeeNumber = orderDto.getEmployeeNumber();
		this.employeeName = orderDto.getEmployeeName();
		this.purchaseType = orderDto.getPurchaseType() == null?"":String.valueOf(orderDto.getPurchaseType());
		this.signingCompany=addressInfo.getSigningCompany()==null?purchaserInfo.getInvoiceName():addressInfo.getSigningCompany();
	}
	
}
