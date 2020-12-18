package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBindOrder  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.String bindPk;
	private java.lang.String purchaserPk;
	private java.lang.Double actualAmount;
	private java.util.Date insertTime;
	private java.lang.String meno;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.String storeName;
	private java.lang.String storePk;
	private java.lang.Integer source;
	private java.lang.String logisticsModelPk;
	private java.lang.String logisticsModelName;
	private java.lang.Integer purchaseType;
	private java.lang.Integer status;
	private java.lang.String bindProductId;
	private java.lang.String purchaserInfo;
	private java.lang.String addressInfo;
	private java.lang.String goodsJson;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setBindPk(java.lang.String bindPk) {
		this.bindPk = bindPk;
	}
	
	public java.lang.String getBindPk() {
		return this.bindPk;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
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
	public void setPurchaseType(java.lang.Integer purchaseType) {
		this.purchaseType = purchaseType;
	}
	
	public java.lang.Integer getPurchaseType() {
		return this.purchaseType;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setBindProductId(java.lang.String bindProductId) {
		this.bindProductId = bindProductId;
	}
	
	public java.lang.String getBindProductId() {
		return this.bindProductId;
	}
	public void setPurchaserInfo(java.lang.String purchaserInfo) {
		this.purchaserInfo = purchaserInfo;
	}
	
	public java.lang.String getPurchaserInfo() {
		return this.purchaserInfo;
	}
	public void setAddressInfo(java.lang.String addressInfo) {
		this.addressInfo = addressInfo;
	}
	
	public java.lang.String getAddressInfo() {
		return this.addressInfo;
	}
	public void setGoodsJson(java.lang.String goodsJson) {
		this.goodsJson = goodsJson;
	}
	
	public java.lang.String getGoodsJson() {
		return this.goodsJson;
	}
	

}