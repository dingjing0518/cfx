package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bOnlinepayRecordDto extends BaseDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.Integer status;
	private java.lang.String serialNumber;
	private java.lang.String purchaserPk;
	private java.lang.String purchaserName;
	private java.lang.String supplierName;
	private java.lang.String supplierPk;
	private java.lang.Double orderAmount;
	private java.util.Date insertTime;
	private java.lang.String receivablesAccount;
	private java.lang.String receivablesAccountName;
	private java.lang.String onlinePayGoodsPk;
	private java.lang.String onlinePayGoodsName;
	private java.lang.String shotName;
	private java.lang.String returnUrl;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setSerialNumber(java.lang.String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public java.lang.String getSerialNumber() {
		return this.serialNumber;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
	}
	public void setOrderAmount(java.lang.Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public java.lang.Double getOrderAmount() {
		return this.orderAmount;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setReceivablesAccount(java.lang.String receivablesAccount) {
		this.receivablesAccount = receivablesAccount;
	}
	
	public java.lang.String getReceivablesAccount() {
		return this.receivablesAccount;
	}
	public void setReceivablesAccountName(java.lang.String receivablesAccountName) {
		this.receivablesAccountName = receivablesAccountName;
	}
	
	public java.lang.String getReceivablesAccountName() {
		return this.receivablesAccountName;
	}
	public void setOnlinePayGoodsPk(java.lang.String onlinePayGoodsPk) {
		this.onlinePayGoodsPk = onlinePayGoodsPk;
	}
	
	public java.lang.String getOnlinePayGoodsPk() {
		return this.onlinePayGoodsPk;
	}
	public void setOnlinePayGoodsName(java.lang.String onlinePayGoodsName) {
		this.onlinePayGoodsName = onlinePayGoodsName;
	}
	
	public java.lang.String getOnlinePayGoodsName() {
		return this.onlinePayGoodsName;
	}

	public String getShotName() {
		return shotName;
	}

	public void setShotName(String shotName) {
		this.shotName = shotName;
	}

	public java.lang.String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(java.lang.String returnUrl) {
		this.returnUrl = returnUrl;
	}
	

}