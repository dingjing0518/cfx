package cn.cf.dto;

import java.util.Date;

/**
 * 
 * @author gejianming
 * @date 2017年11月1日
 */
public class OrderDeliveryDto  implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	private String pk;
	private String orderPK;//订单编号
	private String deliveryNumber;//提货单号
	private String parentPk;
	private Double originalTotalFreight;
	private Double presentTotalFreight;
	private Date orderTime;
	private Date updateTime;
	private String logisticsCompanyName;
	private String fromCompanyName;
	private String supplierName;
	private String fromAddressPk;//提货地址PK
	private String fromAddress;
	private String fromContacts;
	private String fromContactsTel;
	private String toCompanyName;
	private String toAddressPk;//收货地址PK
	private String toAddress;
	private String toContacts;
	private String toContactsTel;
	private String orderStatusName;
	private Integer orderStatus;
	private Integer isMatched;//是否匹配
	private String goodsPk;
	private String deliveryPk;
	private String productName;
	private String productPk;
	private String varietiesName;
	private String seedvarietyName;
	private String specName;
	private String seriesName;
	private String gradeName;
	private String gradePk;
	private String batchNumber;
	private Integer boxes;
	private Double  weight;
	private Integer unit;
	private String unitName;
	private Double  originalFreight;
	private Double presentFreight;
	private String goodsName;
	private Date arrivedTimeStart;
	private Date arrivedTimeEnd;
	private String arrivedHourStart;
	private String arrivedHourEnd;
	private Integer isAbnormal;
	private Integer isConfirmed;
	private String isConfirmedName; 
	private String invoiceTitle; 
	private String paymentName;
	private String abnormalRemark;
	private String remark;
	private Double tareWeight;
	private Date deliveryTime;
	private String driver;
	private String driverContact;
	private String carPlate;
	
	private String invoicePk;
    private String invoiceName;
    private String invoiceTaxidNumber;
    private String invoiceRegPhone;
    private String invoiceBankAccount;
    private String invoiceBankName;
    private String invoiceProvinceName;
    private String invoiceCityName;
    private String invoiceAreaName;
    private String invoiceRegAddress;
	private Date paymentTime;
	private Date signTime;
	private Date finishedTime;
	
	private Integer supplierInvoiceStatus;//物流供应商发票状态
	private String supplierInvoiceStatusName;//物流供应商发票状态对应名称
	private Integer memberInvoiceStatus;//平台用户发票状态
	private String memberInvoiceP;//平台用户发票状态
	private String memberInvoiceStatusName;//平台用户发票状态对应名称
	private String fromProvincePk;//提货地省
	private String fromProvinceName;//提货地省
	private String fromCityPk;//提货地市
	private String fromCityName;//提货地市
	private String fromAreaPk;//提货地区县
	private String fromAreaName;//提货地区县
	private String toProvincePk;//收货地省
	private String toProvinceName;//收货地省
	private String toCityPk;//收货地市
	private String toCityName;//收货地市
	private String toAreaPk;//收货地区县
	private String toAreaName;//收货地区县
	private String fromTownPk;//提货地镇
	private String fromTownName;//提货地镇
	private String toTownPk;//收获地镇
	private String toTownName;//收货地镇
	private String mandatePk;//自提委托书Pk
	private String mandateUrl;//自提委托书URL
	
	public String getOrderPK() {
		return orderPK;
	}

	public void setOrderPK(String orderPK) {
		this.orderPK = orderPK;
	}

	public String getMandateUrl() {
		return mandateUrl;
	}

	public void setMandateUrl(String mandateUrl) {
		this.mandateUrl = mandateUrl;
	}

	public String getMandatePk() {
		return mandatePk;
	}

	public void setMandatePk(String mandatePk) {
		this.mandatePk = mandatePk;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getArrivedHourStart() {
		return arrivedHourStart;
	}

	public void setArrivedHourStart(String arrivedHourStart) {
		this.arrivedHourStart = arrivedHourStart;
	}

	public String getArrivedHourEnd() {
		return arrivedHourEnd;
	}

	public void setArrivedHourEnd(String arrivedHourEnd) {
		this.arrivedHourEnd = arrivedHourEnd;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFromAddressPk() {
		return fromAddressPk;
	}

	public Integer getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(Integer isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public void setFromAddressPk(String fromAddressPk) {
		this.fromAddressPk = fromAddressPk;
	}

	public String getToAddressPk() {
		return toAddressPk;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setToAddressPk(String toAddressPk) {
		this.toAddressPk = toAddressPk;
	}

	public String getProductPk() {
		return productPk;
	}

	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}

	public String getGradePk() {
		return gradePk;
	}

	public void setGradePk(String gradePk) {
		this.gradePk = gradePk;
	}

	public Integer getUnit() {
		return unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getFromProvincePk() {
		return fromProvincePk;
	}

	public void setFromProvincePk(String fromProvincePk) {
		this.fromProvincePk = fromProvincePk;
	}

	public String getFromProvinceName() {
		return fromProvinceName;
	}

	public String getInvoiceProvinceName() {
		return invoiceProvinceName;
	}

	public void setInvoiceProvinceName(String invoiceProvinceName) {
		this.invoiceProvinceName = invoiceProvinceName;
	}

	public String getInvoiceCityName() {
		return invoiceCityName;
	}

	public void setInvoiceCityName(String invoiceCityName) {
		this.invoiceCityName = invoiceCityName;
	}

	public String getInvoiceAreaName() {
		return invoiceAreaName;
	}

	public void setInvoiceAreaName(String invoiceAreaName) {
		this.invoiceAreaName = invoiceAreaName;
	}

	public void setFromProvinceName(String fromProvinceName) {
		this.fromProvinceName = fromProvinceName;
	}

	public String getFromCityPk() {
		return fromCityPk;
	}

	public void setFromCityPk(String fromCityPk) {
		this.fromCityPk = fromCityPk;
	}

	public String getFromCityName() {
		return fromCityName;
	}

	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}

	public String getFromAreaPk() {
		return fromAreaPk;
	}

	public void setFromAreaPk(String fromAreaPk) {
		this.fromAreaPk = fromAreaPk;
	}

	public String getFromAreaName() {
		return fromAreaName;
	}

	public void setFromAreaName(String fromAreaName) {
		this.fromAreaName = fromAreaName;
	}

	public String getToProvincePk() {
		return toProvincePk;
	}

	public void setToProvincePk(String toProvincePk) {
		this.toProvincePk = toProvincePk;
	}

	public String getToProvinceName() {
		return toProvinceName;
	}

	public void setToProvinceName(String toProvinceName) {
		this.toProvinceName = toProvinceName;
	}

	public String getToCityPk() {
		return toCityPk;
	}

	public void setToCityPk(String toCityPk) {
		this.toCityPk = toCityPk;
	}

	public String getToCityName() {
		return toCityName;
	}

	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}

	public String getToAreaPk() {
		return toAreaPk;
	}

	public void setToAreaPk(String toAreaPk) {
		this.toAreaPk = toAreaPk;
	}

	public String getToAreaName() {
		return toAreaName;
	}

	public void setToAreaName(String toAreaName) {
		this.toAreaName = toAreaName;
	}

	public String getFromTownPk() {
		return fromTownPk;
	}

	public void setFromTownPk(String fromTownPk) {
		this.fromTownPk = fromTownPk;
	}

	public String getFromTownName() {
		return fromTownName;
	}

	public void setFromTownName(String fromTownName) {
		this.fromTownName = fromTownName;
	}

	public String getToTownPk() {
		return toTownPk;
	}

	public void setToTownPk(String toTownPk) {
		this.toTownPk = toTownPk;
	}

	public String getToTownName() {
		return toTownName;
	}

	public void setToTownName(String toTownName) {
		this.toTownName = toTownName;
	}

	public Integer getMemberInvoiceStatus() {
		return memberInvoiceStatus;
	}

	public Integer getIsMatched() {
		return isMatched;
	}

	public void setIsMatched(Integer isMatched) {
		this.isMatched = isMatched;
	}

	public void setMemberInvoiceStatus(Integer memberInvoiceStatus) {
		this.memberInvoiceStatus = memberInvoiceStatus;
	}

	public String getMemberInvoiceP() {
		return memberInvoiceP;
	}

	public void setMemberInvoiceP(String memberInvoiceP) {
		this.memberInvoiceP = memberInvoiceP;
	}

	public String getMemberInvoiceStatusName() {
		return memberInvoiceStatusName;
	}

	public void setMemberInvoiceStatusName(String memberInvoiceStatusName) {
		this.memberInvoiceStatusName = memberInvoiceStatusName;
	}

	public String getSupplierInvoiceStatusName() {
		return supplierInvoiceStatusName;
	}
	public void setSupplierInvoiceStatusName(String supplierInvoiceStatusName) {
		this.supplierInvoiceStatusName = supplierInvoiceStatusName;
	}
	public Integer getSupplierInvoiceStatus() {
		return supplierInvoiceStatus;
	}
	public void setSupplierInvoiceStatus(Integer supplierInvoiceStatus) {
		this.supplierInvoiceStatus = supplierInvoiceStatus;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public Date getSignTime() {
		return signTime;
	}
	public Date getFinishedTime() {
		return finishedTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
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
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDriverContact() {
		return driverContact;
	}
	public void setDriverContact(String driverContact) {
		this.driverContact = driverContact;
	}
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Double getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getAbnormalRemark() {
		return abnormalRemark;
	}
	public void setAbnormalRemark(String abnormalRemark) {
		this.abnormalRemark = abnormalRemark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsConfirmedName() {
		return isConfirmedName;
	}
	public void setIsConfirmedName(String isConfirmedName) {
		this.isConfirmedName = isConfirmedName;
	}
	public Integer getIsConfirmed() {
		return isConfirmed;
	}
	public void setIsConfirmed(Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public Date getArrivedTimeStart() {
		return arrivedTimeStart;
	}
	public void setArrivedTimeStart(Date arrivedTimeStart) {
		this.arrivedTimeStart = arrivedTimeStart;
	}
	public Date getArrivedTimeEnd() {
		return arrivedTimeEnd;
	}
	public void setArrivedTimeEnd(Date arrivedTimeEnd) {
		this.arrivedTimeEnd = arrivedTimeEnd;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getParentPk() {
		return parentPk;
	}
	public void setParentPk(String parentPk) {
		this.parentPk = parentPk;
	}
	public Double getOriginalTotalFreight() {
		return originalTotalFreight;
	}
	public void setOriginalTotalFreight(Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}
	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}
	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}
	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}
	public String getFromCompanyName() {
		return fromCompanyName;
	}
	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getFromContacts() {
		return fromContacts;
	}
	public void setFromContacts(String fromContacts) {
		this.fromContacts = fromContacts;
	}
	public String getFromContactsTel() {
		return fromContactsTel;
	}
	public void setFromContactsTel(String fromContactsTel) {
		this.fromContactsTel = fromContactsTel;
	}
	public String getToCompanyName() {
		return toCompanyName;
	}
	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getToContacts() {
		return toContacts;
	}
	public void setToContacts(String toContacts) {
		this.toContacts = toContacts;
	}
	public String getToContactsTel() {
		return toContactsTel;
	}
	public void setToContactsTel(String toContactsTel) {
		this.toContactsTel = toContactsTel;
	}
	public String getOrderStatusName() {
		return orderStatusName;
	}
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getGoodsPk() {
		return goodsPk;
	}
	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}
	public String getDeliveryPk() {
		return deliveryPk;
	}
	public void setDeliveryPk(String deliveryPk) {
		this.deliveryPk = deliveryPk;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getVarietiesName() {
		return varietiesName;
	}
	public void setVarietiesName(String varietiesName) {
		this.varietiesName = varietiesName;
	}
	public String getSeedvarietyName() {
		return seedvarietyName;
	}
	public void setSeedvarietyName(String seedvarietyName) {
		this.seedvarietyName = seedvarietyName;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getOriginalFreight() {
		return originalFreight;
	}
	public void setOriginalFreight(Double originalFreight) {
		this.originalFreight = originalFreight;
	}
	public Double getPresentFreight() {
		return presentFreight;
	}
	public void setPresentFreight(Double presentFreight) {
		this.presentFreight = presentFreight;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


}
