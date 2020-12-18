package cn.cf.dto;

import java.util.Date;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bReserveOrderDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.String purchaserName;
	private java.lang.String purchaserPk;
	private java.lang.Integer orderStatus;
	private java.lang.Double orderAmount;
	private java.lang.Double actualAmount;
	private java.util.Date insertTime;
	private java.lang.String meno;
	private java.lang.String deliverDetails;
	private java.lang.String addressJson;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.String storeName;
	private java.lang.String storePk;
	private java.lang.Integer source;
	private java.lang.String logisticsModelPk;
	private java.lang.String logisticsModelName;
	private java.lang.String invoiceJson;
	private java.lang.Integer purchaseType;
	private java.util.Date completeTime;
	private java.lang.String purchaserMobile;
	private java.lang.String goodsJson;
	private java.lang.Integer isBatches;
	
	private Date appointmentTime;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public java.lang.Integer getIsBatches() {
		return isBatches;
	}

	public void setIsBatches(java.lang.Integer isBatches) {
		this.isBatches = isBatches;
	}

	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setOrderStatus(java.lang.Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public java.lang.Integer getOrderStatus() {
		return this.orderStatus;
	}
	public void setOrderAmount(java.lang.Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public java.lang.Double getOrderAmount() {
		return this.orderAmount;
	}
	public void setActualAmount(java.lang.Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public java.lang.Double getActualAmount() {
		return this.actualAmount;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setMeno(java.lang.String meno) {
		this.meno = meno;
	}
	
	public java.lang.String getMeno() {
		return this.meno;
	}
	public void setDeliverDetails(java.lang.String deliverDetails) {
		this.deliverDetails = deliverDetails;
	}
	
	public java.lang.String getDeliverDetails() {
		return this.deliverDetails;
	}
	public void setAddressJson(java.lang.String addressJson) {
		this.addressJson = addressJson;
	}
	
	public java.lang.String getAddressJson() {
		return this.addressJson;
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
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
	public java.lang.String getStoreName() {
		return this.storeName;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setSource(java.lang.Integer source) {
		this.source = source;
	}
	
	public java.lang.Integer getSource() {
		return this.source;
	}
	public void setLogisticsModelPk(java.lang.String logisticsModelPk) {
		this.logisticsModelPk = logisticsModelPk;
	}
	
	public java.lang.String getLogisticsModelPk() {
		return this.logisticsModelPk;
	}
	public void setLogisticsModelName(java.lang.String logisticsModelName) {
		this.logisticsModelName = logisticsModelName;
	}
	
	public java.lang.String getLogisticsModelName() {
		return this.logisticsModelName;
	}
	public void setInvoiceJson(java.lang.String invoiceJson) {
		this.invoiceJson = invoiceJson;
	}
	
	public java.lang.String getInvoiceJson() {
		return this.invoiceJson;
	}
	public void setPurchaseType(java.lang.Integer purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	public java.lang.Integer getPurchaseType() {
		return this.purchaseType;
	}
	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
	}
	
	public java.util.Date getCompleteTime() {
		return this.completeTime;
	}
	public void setPurchaserMobile(java.lang.String purchaserMobile) {
		this.purchaserMobile = purchaserMobile;
	}
	
	public java.lang.String getPurchaserMobile() {
		return this.purchaserMobile;
	}
	public void setGoodsJson(java.lang.String goodsJson) {
		this.goodsJson = goodsJson;
	}
	
	public java.lang.String getGoodsJson() {
		return this.goodsJson;
	}
	

}