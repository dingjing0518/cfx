package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillOrder extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.String serialNumber;
	private java.lang.Integer status;
	private java.lang.Double amount;
	private java.lang.Double billAmount;
	private java.util.Date insertTime;
	private java.lang.String purchaserPk;
	private java.lang.String purchaserName;
	private java.lang.String supplierPk;
	private java.lang.String supplierName;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.lang.String goodsPk;
	private java.lang.String goodsName;
	private java.lang.String goodsShotName;
	private java.lang.String payerAccount;
	private java.lang.String payerBankNo;
	private java.lang.String payerOrzaCode;
	private java.lang.String payeeAccount;
	private java.lang.String payeeBankNo;
	private java.lang.String payeeOrzaCode;
	private java.lang.String returnUrl;
	private java.lang.Integer type;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setSerialNumber(java.lang.String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public java.lang.String getSerialNumber() {
		return this.serialNumber;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setAmount(java.lang.Double amount) {
		this.amount = amount;
	}
	
	public java.lang.Double getAmount() {
		return this.amount;
	}
	public void setBillAmount(java.lang.Double billAmount) {
		this.billAmount = billAmount;
	}
	
	public java.lang.Double getBillAmount() {
		return this.billAmount;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
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
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
	}
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
	public java.lang.String getStoreName() {
		return this.storeName;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	
	public java.lang.String getGoodsName() {
		return this.goodsName;
	}
	public void setGoodsShotName(java.lang.String goodsShotName) {
		this.goodsShotName = goodsShotName;
	}
	
	public java.lang.String getGoodsShotName() {
		return this.goodsShotName;
	}
	public void setPayerAccount(java.lang.String payerAccount) {
		this.payerAccount = payerAccount;
	}
	
	public java.lang.String getPayerAccount() {
		return this.payerAccount;
	}
	public void setPayerBankNo(java.lang.String payerBankNo) {
		this.payerBankNo = payerBankNo;
	}
	
	public java.lang.String getPayerBankNo() {
		return this.payerBankNo;
	}
	public void setPayerOrzaCode(java.lang.String payerOrzaCode) {
		this.payerOrzaCode = payerOrzaCode;
	}
	
	public java.lang.String getPayerOrzaCode() {
		return this.payerOrzaCode;
	}
	public void setPayeeAccount(java.lang.String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	
	public java.lang.String getPayeeAccount() {
		return this.payeeAccount;
	}
	public void setPayeeBankNo(java.lang.String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}
	
	public java.lang.String getPayeeBankNo() {
		return this.payeeBankNo;
	}
	public void setPayeeOrzaCode(java.lang.String payeeOrzaCode) {
		this.payeeOrzaCode = payeeOrzaCode;
	}
	
	public java.lang.String getPayeeOrzaCode() {
		return this.payeeOrzaCode;
	}
	public void setReturnUrl(java.lang.String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public java.lang.String getReturnUrl() {
		return this.returnUrl;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	

}