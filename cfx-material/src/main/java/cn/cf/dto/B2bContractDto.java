package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bContractDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String contractNo;
	private java.lang.Integer contractStatus;
	private java.lang.Integer contractSource;
	private java.lang.String saleDepartment;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.Integer days;
	private java.lang.String covenant;
	private java.lang.String supplementary;
	private java.lang.String priceType;
	private java.lang.String member;
	private java.lang.String employeePk;
	private java.lang.String salesman;
	private java.lang.String salesmanNumber;
	private java.lang.String supplierPk;
	private java.lang.String purchaserPk;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.lang.Integer contractType;
	private java.lang.Integer purchaserType;
	private java.lang.String contractRate;
	private java.lang.Double contractCount;
	private java.lang.Double orderAmount;
	private java.lang.Double totalAmount;
	private java.lang.String logisticsModel;
	private java.lang.String logisticsModelPk;
	private java.lang.Integer logisticsModelType;
	private java.lang.String carrier;
	private java.lang.String carrierPk;
	private java.lang.Integer isDelete;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String paymentName;
	private java.lang.Integer paymentType;
	private java.util.Date paymentTime;
	private java.lang.String economicsGoodsName;
	private java.lang.Integer economicsGoodsType;
	private java.lang.Integer source;
	private java.lang.String plantName;
	private java.lang.String plantPk;
	private java.lang.String supplierInfo;
	private java.lang.String purchaserInfo;
	private java.lang.String addressInfo;
	private java.lang.Double ownAmount;
	private java.lang.String block;
	private java.util.Date receivablesTime;
	//columns END

	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}
	
	public java.lang.String getContractNo() {
		return this.contractNo;
	}
	public void setContractStatus(java.lang.Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public java.lang.Integer getContractStatus() {
		return this.contractStatus;
	}
	public void setContractSource(java.lang.Integer contractSource) {
		this.contractSource = contractSource;
	}
	
	public java.lang.Integer getContractSource() {
		return this.contractSource;
	}
	public void setSaleDepartment(java.lang.String saleDepartment) {
		this.saleDepartment = saleDepartment;
	}
	
	public java.lang.String getSaleDepartment() {
		return this.saleDepartment;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setDays(java.lang.Integer days) {
		this.days = days;
	}
	
	public java.lang.Integer getDays() {
		return this.days;
	}
	public void setCovenant(java.lang.String covenant) {
		this.covenant = covenant;
	}
	
	public java.lang.String getCovenant() {
		return this.covenant;
	}
	public void setSupplementary(java.lang.String supplementary) {
		this.supplementary = supplementary;
	}
	
	public java.lang.String getSupplementary() {
		return this.supplementary;
	}
	public void setPriceType(java.lang.String priceType) {
		this.priceType = priceType;
	}
	
	public java.lang.String getPriceType() {
		return this.priceType;
	}
	public void setMember(java.lang.String member) {
		this.member = member;
	}
	
	public java.lang.String getMember() {
		return this.member;
	}
	public void setEmployeePk(java.lang.String employeePk) {
		this.employeePk = employeePk;
	}
	
	public java.lang.String getEmployeePk() {
		return this.employeePk;
	}
	public void setSalesman(java.lang.String salesman) {
		this.salesman = salesman;
	}
	
	public java.lang.String getSalesman() {
		return this.salesman;
	}
	public void setSalesmanNumber(java.lang.String salesmanNumber) {
		this.salesmanNumber = salesmanNumber;
	}
	
	public java.lang.String getSalesmanNumber() {
		return this.salesmanNumber;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
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
	public void setContractType(java.lang.Integer contractType) {
		this.contractType = contractType;
	}
	
	public java.lang.Integer getContractType() {
		return this.contractType;
	}
	public void setPurchaserType(java.lang.Integer purchaserType) {
		this.purchaserType = purchaserType;
	}
	
	public java.lang.Integer getPurchaserType() {
		return this.purchaserType;
	}
	public void setContractRate(java.lang.String contractRate) {
		this.contractRate = contractRate;
	}
	
	public java.lang.String getContractRate() {
		return this.contractRate;
	}
	public void setContractCount(java.lang.Double contractCount) {
		this.contractCount = contractCount;
	}
	
	public java.lang.Double getContractCount() {
		return this.contractCount;
	}
	public void setOrderAmount(java.lang.Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public java.lang.Double getOrderAmount() {
		return this.orderAmount;
	}
	public void setTotalAmount(java.lang.Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public java.lang.Double getTotalAmount() {
		return this.totalAmount;
	}
	public void setLogisticsModel(java.lang.String logisticsModel) {
		this.logisticsModel = logisticsModel;
	}
	
	public java.lang.String getLogisticsModel() {
		return this.logisticsModel;
	}
	public void setLogisticsModelPk(java.lang.String logisticsModelPk) {
		this.logisticsModelPk = logisticsModelPk;
	}
	
	public java.lang.String getLogisticsModelPk() {
		return this.logisticsModelPk;
	}
	public void setLogisticsModelType(java.lang.Integer logisticsModelType) {
		this.logisticsModelType = logisticsModelType;
	}
	
	public java.lang.Integer getLogisticsModelType() {
		return this.logisticsModelType;
	}
	public void setCarrier(java.lang.String carrier) {
		this.carrier = carrier;
	}
	
	public java.lang.String getCarrier() {
		return this.carrier;
	}
	public void setCarrierPk(java.lang.String carrierPk) {
		this.carrierPk = carrierPk;
	}
	
	public java.lang.String getCarrierPk() {
		return this.carrierPk;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setPaymentName(java.lang.String paymentName) {
		this.paymentName = paymentName;
	}
	
	public java.lang.String getPaymentName() {
		return this.paymentName;
	}
	public void setPaymentType(java.lang.Integer paymentType) {
		this.paymentType = paymentType;
	}
	
	public java.lang.Integer getPaymentType() {
		return this.paymentType;
	}
	public void setPaymentTime(java.util.Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public java.util.Date getPaymentTime() {
		return this.paymentTime;
	}
	public void setEconomicsGoodsName(java.lang.String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}
	
	public java.lang.String getEconomicsGoodsName() {
		return this.economicsGoodsName;
	}
	public void setEconomicsGoodsType(java.lang.Integer economicsGoodsType) {
		this.economicsGoodsType = economicsGoodsType;
	}
	
	public java.lang.Integer getEconomicsGoodsType() {
		return this.economicsGoodsType;
	}
	public void setSource(java.lang.Integer source) {
		this.source = source;
	}
	
	public java.lang.Integer getSource() {
		return this.source;
	}
	public void setPlantName(java.lang.String plantName) {
		this.plantName = plantName;
	}
	
	public java.lang.String getPlantName() {
		return this.plantName;
	}
	public void setPlantPk(java.lang.String plantPk) {
		this.plantPk = plantPk;
	}
	
	public java.lang.String getPlantPk() {
		return this.plantPk;
	}
	public void setSupplierInfo(java.lang.String supplierInfo) {
		this.supplierInfo = supplierInfo;
	}
	
	public java.lang.String getSupplierInfo() {
		return this.supplierInfo;
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
	public void setReceivablesTime(java.util.Date receivablesTime) {
		this.receivablesTime = receivablesTime;
	}
	
	public java.util.Date getReceivablesTime() {
		return this.receivablesTime;
	}
	

}