package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bOrder  extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.String purchaserPk;
	private java.lang.String supplierPk;
	private java.lang.Integer orderStatus;
	private java.lang.Double orderAmount;
	private java.lang.Double actualAmount;
	private java.util.Date insertTime;
	private java.lang.String meno;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.String storeName;
	private java.lang.String storePk;
	private java.lang.Integer paymentType;
	private java.lang.String paymentName;
	private java.lang.Integer economicsGoodsType;
	private java.lang.String economicsGoodsName;
	private java.lang.Integer source;
	private java.util.Date paymentTime;
	private java.lang.String logisticsModelPk;
	private java.lang.String logisticsModelName;
	private java.lang.Integer purchaseType;
	private java.lang.Integer isDelete;
	private java.lang.Integer isDeleteSp;
	private java.lang.String employeeNumber;
	private java.lang.String employeeName;
	private java.lang.String employeePk;
	private java.util.Date completeTime;
	private java.lang.Integer orderClassify;
	private java.util.Date receivablesTime;
	private java.lang.Integer isBatches;
	private java.lang.String deliverDetails;
	private java.lang.String otherNumber;

	private java.lang.String purchaserInfo;
	private java.lang.String supplierInfo;
	private java.lang.String addressInfo;
	private java.lang.Double ownAmount;
	private java.lang.String block;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
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
	public void setPaymentType(java.lang.Integer paymentType) {
		this.paymentType = paymentType;
	}
	
	public java.lang.Integer getPaymentType() {
		return this.paymentType;
	}
	public void setPaymentName(java.lang.String paymentName) {
		this.paymentName = paymentName;
	}
	
	public java.lang.String getPaymentName() {
		return this.paymentName;
	}
	public void setEconomicsGoodsType(java.lang.Integer economicsGoodsType) {
		this.economicsGoodsType = economicsGoodsType;
	}
	
	public java.lang.Integer getEconomicsGoodsType() {
		return this.economicsGoodsType;
	}
	public void setEconomicsGoodsName(java.lang.String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}
	
	public java.lang.String getEconomicsGoodsName() {
		return this.economicsGoodsName;
	}
	public void setSource(java.lang.Integer source) {
		this.source = source;
	}
	
	public java.lang.Integer getSource() {
		return this.source;
	}
	public void setPaymentTime(java.util.Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public java.util.Date getPaymentTime() {
		return this.paymentTime;
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
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsDeleteSp(java.lang.Integer isDeleteSp) {
		this.isDeleteSp = isDeleteSp;
	}
	
	public java.lang.Integer getIsDeleteSp() {
		return this.isDeleteSp;
	}
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public java.lang.String getEmployeeNumber() {
		return this.employeeNumber;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	
	public java.lang.String getEmployeeName() {
		return this.employeeName;
	}
	public void setEmployeePk(java.lang.String employeePk) {
		this.employeePk = employeePk;
	}
	
	public java.lang.String getEmployeePk() {
		return this.employeePk;
	}
	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
	}
	
	public java.util.Date getCompleteTime() {
		return this.completeTime;
	}
	public void setOrderClassify(java.lang.Integer orderClassify) {
		this.orderClassify = orderClassify;
	}
	
	public java.lang.Integer getOrderClassify() {
		return this.orderClassify;
	}
	public void setReceivablesTime(java.util.Date receivablesTime) {
		this.receivablesTime = receivablesTime;
	}
	
	public java.util.Date getReceivablesTime() {
		return this.receivablesTime;
	}
	public void setIsBatches(java.lang.Integer isBatches) {
		this.isBatches = isBatches;
	}
	
	public java.lang.Integer getIsBatches() {
		return this.isBatches;
	}
	public void setDeliverDetails(java.lang.String deliverDetails) {
		this.deliverDetails = deliverDetails;
	}
	
	public java.lang.String getDeliverDetails() {
		return this.deliverDetails;
	}
	public void setOtherNumber(java.lang.String otherNumber) {
		this.otherNumber = otherNumber;
	}
	public java.lang.String getOtherNumber() {
		return this.otherNumber;
	}
	public void setPurchaserInfo(java.lang.String purchaserInfo) {
		this.purchaserInfo = purchaserInfo;
	}

	public java.lang.String getPurchaserInfo() {
		return this.purchaserInfo;
	}
	public void setSupplierInfo(java.lang.String supplierInfo) {
		this.supplierInfo = supplierInfo;
	}
	
	public java.lang.String getSupplierInfo() {
		return this.supplierInfo;
	}
	public void setAddressInfo(java.lang.String addressInfo) {
		this.addressInfo = addressInfo;
	}
	
	public java.lang.String getAddressInfo() {
		return this.addressInfo;
	}
	public void setOwnAmount(java.lang.Double ownAmount) {
		this.ownAmount = ownAmount;
	}
	
	public java.lang.Double getOwnAmount() {
		return this.ownAmount;
	}
	public void setBlock(java.lang.String block) {
		this.block = block;
	}
	
	public java.lang.String getBlock() {
		return this.block;
	}
	

}