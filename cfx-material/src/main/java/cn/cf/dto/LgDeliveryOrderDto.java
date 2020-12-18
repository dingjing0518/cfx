package cn.cf.dto;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgDeliveryOrderDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String orderPk;
	private java.lang.String parentPk;
	private java.lang.Integer orderStatus;
	private java.lang.Integer isMatched;
	private java.lang.Double originalTotalFreight;
	private java.lang.Double presentTotalFreight;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.util.Date orderTime;
	private java.lang.String logisticsCompanyPk;
	private java.lang.String logisticsCompanyName;
	private java.util.Date arrivedTimeStart;
	private java.util.Date arrivedTimeEnd;
	private java.lang.String fromAddressPk;
	private java.lang.String fromCompanyName;
	private java.lang.String fromAddress;
	private java.lang.String fromContacts;
	private java.lang.String fromContactsTel;
	private java.lang.String fromProvincePk;
	private java.lang.String fromProvinceName;
	private java.lang.String fromCityPk;
	private java.lang.String fromCityName;
	private java.lang.String fromAreaPk;
	private java.lang.String fromAreaName;
	private java.lang.String fromTownPk;
	private java.lang.String fromTownName;
	private java.lang.String toAddressPk;
	private java.lang.String toCompanyPk;
	private java.lang.String toCompanyName;
	private java.lang.String toAddress;
	private java.lang.String toContacts;
	private java.lang.String toContactsTel;
	private java.lang.String toProvincePk;
	private java.lang.String toProvinceName;
	private java.lang.String toCityPk;
	private java.lang.String toCityName;
	private java.lang.String toAreaPk;
	private java.lang.String toAreaName;
	private java.lang.String toTownPk;
	private java.lang.String toTownName;
	private java.lang.String supplierInvoicePk;
	private java.lang.Integer supplierInvoiceStatus;
	private java.lang.String memberInvoicePk;
	private java.lang.Integer memberInvoiceStatus;
	private java.lang.String driver;
	private java.lang.String driverPk;
	private java.lang.String driverContact;
	private java.lang.String carPk;
	private java.lang.String carPlate;
	private java.lang.Integer source;
	private java.lang.String member;
	private java.lang.String memberPk;
	private java.lang.String abnormalRemark;
	private java.lang.String remark;
	private java.lang.String deliveryNumber;
	private java.util.Date deliveryTime;
	private java.util.Date signTime;
	private java.lang.Integer isAbnormal;
	private java.lang.Integer isConfirmed;
	private java.lang.String paymentPk;
	private java.lang.String paymentName;
	private java.util.Date paymentTime;
	private java.util.Date finishedTime;
	private java.util.Date financialTime;
	private java.lang.String purchaserName;
	private java.lang.String purchaserPk;
	private java.lang.String supplierName;
	private java.lang.String supplierPk;
	private java.lang.String invoicePk;
	private java.lang.String invoiceName;
	private java.lang.String invoiceTaxidNumber;
	private java.lang.String invoiceRegPhone;
	private java.lang.String invoiceBankAccount;
	private java.lang.String invoiceBankName;
	private java.lang.String invoiceProvinceName;
	private java.lang.String invoiceCityName;
	private java.lang.String invoiceAreaName;
	private java.lang.String invoiceRegAddress;
	private java.lang.String linePricePk;
	private java.lang.Double basisLinePrice;
	private java.lang.Double settleWeight;
	private java.lang.Integer isSettleFreight;
	private java.lang.String mandatePk;
	private java.lang.String mandateUrl;
	private java.lang.String orderNumber2;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setOrderPk(java.lang.String orderPk) {
		this.orderPk = orderPk;
	}
	
	public java.lang.String getOrderPk() {
		return this.orderPk;
	}
	public void setParentPk(java.lang.String parentPk) {
		this.parentPk = parentPk;
	}
	
	public java.lang.String getParentPk() {
		return this.parentPk;
	}
	public void setOrderStatus(java.lang.Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public java.lang.Integer getOrderStatus() {
		return this.orderStatus;
	}
	public void setIsMatched(java.lang.Integer isMatched) {
		this.isMatched = isMatched;
	}
	
	public java.lang.Integer getIsMatched() {
		return this.isMatched;
	}
	public void setOriginalTotalFreight(java.lang.Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}
	
	public java.lang.Double getOriginalTotalFreight() {
		return this.originalTotalFreight;
	}
	public void setPresentTotalFreight(java.lang.Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}
	
	public java.lang.Double getPresentTotalFreight() {
		return this.presentTotalFreight;
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
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public java.util.Date getOrderTime() {
		return this.orderTime;
	}
	public void setLogisticsCompanyPk(java.lang.String logisticsCompanyPk) {
		this.logisticsCompanyPk = logisticsCompanyPk;
	}
	
	public java.lang.String getLogisticsCompanyPk() {
		return this.logisticsCompanyPk;
	}
	public void setLogisticsCompanyName(java.lang.String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}
	
	public java.lang.String getLogisticsCompanyName() {
		return this.logisticsCompanyName;
	}
	public void setArrivedTimeStart(java.util.Date arrivedTimeStart) {
		this.arrivedTimeStart = arrivedTimeStart;
	}
	
	public java.util.Date getArrivedTimeStart() {
		return this.arrivedTimeStart;
	}
	public void setArrivedTimeEnd(java.util.Date arrivedTimeEnd) {
		this.arrivedTimeEnd = arrivedTimeEnd;
	}
	
	public java.util.Date getArrivedTimeEnd() {
		return this.arrivedTimeEnd;
	}
	public void setFromAddressPk(java.lang.String fromAddressPk) {
		this.fromAddressPk = fromAddressPk;
	}
	
	public java.lang.String getFromAddressPk() {
		return this.fromAddressPk;
	}
	public void setFromCompanyName(java.lang.String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}
	
	public java.lang.String getFromCompanyName() {
		return this.fromCompanyName;
	}
	public void setFromAddress(java.lang.String fromAddress) {
		this.fromAddress = fromAddress;
	}
	
	public java.lang.String getFromAddress() {
		return this.fromAddress;
	}
	public void setFromContacts(java.lang.String fromContacts) {
		this.fromContacts = fromContacts;
	}
	
	public java.lang.String getFromContacts() {
		return this.fromContacts;
	}
	public void setFromContactsTel(java.lang.String fromContactsTel) {
		this.fromContactsTel = fromContactsTel;
	}
	
	public java.lang.String getFromContactsTel() {
		return this.fromContactsTel;
	}
	public void setFromProvincePk(java.lang.String fromProvincePk) {
		this.fromProvincePk = fromProvincePk;
	}
	
	public java.lang.String getFromProvincePk() {
		return this.fromProvincePk;
	}
	public void setFromProvinceName(java.lang.String fromProvinceName) {
		this.fromProvinceName = fromProvinceName;
	}
	
	public java.lang.String getFromProvinceName() {
		return this.fromProvinceName;
	}
	public void setFromCityPk(java.lang.String fromCityPk) {
		this.fromCityPk = fromCityPk;
	}
	
	public java.lang.String getFromCityPk() {
		return this.fromCityPk;
	}
	public void setFromCityName(java.lang.String fromCityName) {
		this.fromCityName = fromCityName;
	}
	
	public java.lang.String getFromCityName() {
		return this.fromCityName;
	}
	public void setFromAreaPk(java.lang.String fromAreaPk) {
		this.fromAreaPk = fromAreaPk;
	}
	
	public java.lang.String getFromAreaPk() {
		return this.fromAreaPk;
	}
	public void setFromAreaName(java.lang.String fromAreaName) {
		this.fromAreaName = fromAreaName;
	}
	
	public java.lang.String getFromAreaName() {
		return this.fromAreaName;
	}
	public void setFromTownPk(java.lang.String fromTownPk) {
		this.fromTownPk = fromTownPk;
	}
	
	public java.lang.String getFromTownPk() {
		return this.fromTownPk;
	}
	public void setFromTownName(java.lang.String fromTownName) {
		this.fromTownName = fromTownName;
	}
	
	public java.lang.String getFromTownName() {
		return this.fromTownName;
	}
	public void setToAddressPk(java.lang.String toAddressPk) {
		this.toAddressPk = toAddressPk;
	}
	
	public java.lang.String getToAddressPk() {
		return this.toAddressPk;
	}
	public void setToCompanyPk(java.lang.String toCompanyPk) {
		this.toCompanyPk = toCompanyPk;
	}
	
	public java.lang.String getToCompanyPk() {
		return this.toCompanyPk;
	}
	public void setToCompanyName(java.lang.String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}
	
	public java.lang.String getToCompanyName() {
		return this.toCompanyName;
	}
	public void setToAddress(java.lang.String toAddress) {
		this.toAddress = toAddress;
	}
	
	public java.lang.String getToAddress() {
		return this.toAddress;
	}
	public void setToContacts(java.lang.String toContacts) {
		this.toContacts = toContacts;
	}
	
	public java.lang.String getToContacts() {
		return this.toContacts;
	}
	public void setToContactsTel(java.lang.String toContactsTel) {
		this.toContactsTel = toContactsTel;
	}
	
	public java.lang.String getToContactsTel() {
		return this.toContactsTel;
	}
	public void setToProvincePk(java.lang.String toProvincePk) {
		this.toProvincePk = toProvincePk;
	}
	
	public java.lang.String getToProvincePk() {
		return this.toProvincePk;
	}
	public void setToProvinceName(java.lang.String toProvinceName) {
		this.toProvinceName = toProvinceName;
	}
	
	public java.lang.String getToProvinceName() {
		return this.toProvinceName;
	}
	public void setToCityPk(java.lang.String toCityPk) {
		this.toCityPk = toCityPk;
	}
	
	public java.lang.String getToCityPk() {
		return this.toCityPk;
	}
	public void setToCityName(java.lang.String toCityName) {
		this.toCityName = toCityName;
	}
	
	public java.lang.String getToCityName() {
		return this.toCityName;
	}
	public void setToAreaPk(java.lang.String toAreaPk) {
		this.toAreaPk = toAreaPk;
	}
	
	public java.lang.String getToAreaPk() {
		return this.toAreaPk;
	}
	public void setToAreaName(java.lang.String toAreaName) {
		this.toAreaName = toAreaName;
	}
	
	public java.lang.String getToAreaName() {
		return this.toAreaName;
	}
	public void setToTownPk(java.lang.String toTownPk) {
		this.toTownPk = toTownPk;
	}
	
	public java.lang.String getToTownPk() {
		return this.toTownPk;
	}
	public void setToTownName(java.lang.String toTownName) {
		this.toTownName = toTownName;
	}
	
	public java.lang.String getToTownName() {
		return this.toTownName;
	}
	public void setSupplierInvoicePk(java.lang.String supplierInvoicePk) {
		this.supplierInvoicePk = supplierInvoicePk;
	}
	
	public java.lang.String getSupplierInvoicePk() {
		return this.supplierInvoicePk;
	}
	public void setSupplierInvoiceStatus(java.lang.Integer supplierInvoiceStatus) {
		this.supplierInvoiceStatus = supplierInvoiceStatus;
	}
	
	public java.lang.Integer getSupplierInvoiceStatus() {
		return this.supplierInvoiceStatus;
	}
	public void setMemberInvoicePk(java.lang.String memberInvoicePk) {
		this.memberInvoicePk = memberInvoicePk;
	}
	
	public java.lang.String getMemberInvoicePk() {
		return this.memberInvoicePk;
	}
	public void setMemberInvoiceStatus(java.lang.Integer memberInvoiceStatus) {
		this.memberInvoiceStatus = memberInvoiceStatus;
	}
	
	public java.lang.Integer getMemberInvoiceStatus() {
		return this.memberInvoiceStatus;
	}
	public void setDriver(java.lang.String driver) {
		this.driver = driver;
	}
	
	public java.lang.String getDriver() {
		return this.driver;
	}
	public void setDriverPk(java.lang.String driverPk) {
		this.driverPk = driverPk;
	}
	
	public java.lang.String getDriverPk() {
		return this.driverPk;
	}
	public void setDriverContact(java.lang.String driverContact) {
		this.driverContact = driverContact;
	}
	
	public java.lang.String getDriverContact() {
		return this.driverContact;
	}
	public void setCarPk(java.lang.String carPk) {
		this.carPk = carPk;
	}
	
	public java.lang.String getCarPk() {
		return this.carPk;
	}
	public void setCarPlate(java.lang.String carPlate) {
		this.carPlate = carPlate;
	}
	
	public java.lang.String getCarPlate() {
		return this.carPlate;
	}
	public void setSource(java.lang.Integer source) {
		this.source = source;
	}
	
	public java.lang.Integer getSource() {
		return this.source;
	}
	public void setMember(java.lang.String member) {
		this.member = member;
	}
	
	public java.lang.String getMember() {
		return this.member;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setAbnormalRemark(java.lang.String abnormalRemark) {
		this.abnormalRemark = abnormalRemark;
	}
	
	public java.lang.String getAbnormalRemark() {
		return this.abnormalRemark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setDeliveryNumber(java.lang.String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	
	public java.lang.String getDeliveryNumber() {
		return this.deliveryNumber;
	}
	public void setDeliveryTime(java.util.Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public java.util.Date getDeliveryTime() {
		return this.deliveryTime;
	}
	public void setSignTime(java.util.Date signTime) {
		this.signTime = signTime;
	}
	
	public java.util.Date getSignTime() {
		return this.signTime;
	}
	public void setIsAbnormal(java.lang.Integer isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
	public java.lang.Integer getIsAbnormal() {
		return this.isAbnormal;
	}
	public void setIsConfirmed(java.lang.Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public java.lang.Integer getIsConfirmed() {
		return this.isConfirmed;
	}
	public void setPaymentPk(java.lang.String paymentPk) {
		this.paymentPk = paymentPk;
	}
	
	public java.lang.String getPaymentPk() {
		return this.paymentPk;
	}
	public void setPaymentName(java.lang.String paymentName) {
		this.paymentName = paymentName;
	}
	
	public java.lang.String getPaymentName() {
		return this.paymentName;
	}
	public void setPaymentTime(java.util.Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public java.util.Date getPaymentTime() {
		return this.paymentTime;
	}
	public void setFinishedTime(java.util.Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	
	public java.util.Date getFinishedTime() {
		return this.finishedTime;
	}
	public void setFinancialTime(java.util.Date financialTime) {
		this.financialTime = financialTime;
	}
	
	public java.util.Date getFinancialTime() {
		return this.financialTime;
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
	public void setInvoicePk(java.lang.String invoicePk) {
		this.invoicePk = invoicePk;
	}
	
	public java.lang.String getInvoicePk() {
		return this.invoicePk;
	}
	public void setInvoiceName(java.lang.String invoiceName) {
		this.invoiceName = invoiceName;
	}
	
	public java.lang.String getInvoiceName() {
		return this.invoiceName;
	}
	public void setInvoiceTaxidNumber(java.lang.String invoiceTaxidNumber) {
		this.invoiceTaxidNumber = invoiceTaxidNumber;
	}
	
	public java.lang.String getInvoiceTaxidNumber() {
		return this.invoiceTaxidNumber;
	}
	public void setInvoiceRegPhone(java.lang.String invoiceRegPhone) {
		this.invoiceRegPhone = invoiceRegPhone;
	}
	
	public java.lang.String getInvoiceRegPhone() {
		return this.invoiceRegPhone;
	}
	public void setInvoiceBankAccount(java.lang.String invoiceBankAccount) {
		this.invoiceBankAccount = invoiceBankAccount;
	}
	
	public java.lang.String getInvoiceBankAccount() {
		return this.invoiceBankAccount;
	}
	public void setInvoiceBankName(java.lang.String invoiceBankName) {
		this.invoiceBankName = invoiceBankName;
	}
	
	public java.lang.String getInvoiceBankName() {
		return this.invoiceBankName;
	}
	public void setInvoiceProvinceName(java.lang.String invoiceProvinceName) {
		this.invoiceProvinceName = invoiceProvinceName;
	}
	
	public java.lang.String getInvoiceProvinceName() {
		return this.invoiceProvinceName;
	}
	public void setInvoiceCityName(java.lang.String invoiceCityName) {
		this.invoiceCityName = invoiceCityName;
	}
	
	public java.lang.String getInvoiceCityName() {
		return this.invoiceCityName;
	}
	public void setInvoiceAreaName(java.lang.String invoiceAreaName) {
		this.invoiceAreaName = invoiceAreaName;
	}
	
	public java.lang.String getInvoiceAreaName() {
		return this.invoiceAreaName;
	}
	public void setInvoiceRegAddress(java.lang.String invoiceRegAddress) {
		this.invoiceRegAddress = invoiceRegAddress;
	}
	
	public java.lang.String getInvoiceRegAddress() {
		return this.invoiceRegAddress;
	}
	public void setLinePricePk(java.lang.String linePricePk) {
		this.linePricePk = linePricePk;
	}
	
	public java.lang.String getLinePricePk() {
		return this.linePricePk;
	}
	public void setBasisLinePrice(java.lang.Double basisLinePrice) {
		this.basisLinePrice = basisLinePrice;
	}
	
	public java.lang.Double getBasisLinePrice() {
		return this.basisLinePrice;
	}
	public void setSettleWeight(java.lang.Double settleWeight) {
		this.settleWeight = settleWeight;
	}
	
	public java.lang.Double getSettleWeight() {
		return this.settleWeight;
	}
	public void setIsSettleFreight(java.lang.Integer isSettleFreight) {
		this.isSettleFreight = isSettleFreight;
	}
	
	public java.lang.Integer getIsSettleFreight() {
		return this.isSettleFreight;
	}
	public void setMandatePk(java.lang.String mandatePk) {
		this.mandatePk = mandatePk;
	}
	
	public java.lang.String getMandatePk() {
		return this.mandatePk;
	}
	public void setMandateUrl(java.lang.String mandateUrl) {
		this.mandateUrl = mandateUrl;
	}
	
	public java.lang.String getMandateUrl() {
		return this.mandateUrl;
	}
	public void setOrderNumber2(java.lang.String orderNumber2) {
		this.orderNumber2 = orderNumber2;
	}
	
	public java.lang.String getOrderNumber2() {
		return this.orderNumber2;
	}
	

}