package cn.cf.dto;

public class LgDeliveryOrderForB2BPTDto extends BaseDTO implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String orderPk;//商城订单编号
    private Double originalTotalFreight;//商城发货部分的运费总价
    private Double presentTotalFreight;//商城发货部分的运费总价
    private String orderTime;//商城订单的发货时间
    private String fromCompanyName;//提货公司名称
    private String fromAddress;//提货地址
    private String fromContacts;
    private String fromContactsTel;
    private String fromProvincePk;
    private String fromProvinceName;
    private String fromCityPk;
    private String fromCityName;
    private String fromAreaPk;
    private String fromAreaName;
    private String fromTownPk;
    private String fromTownName;
    private String toCompanyName;
    private String toAddress;
    private String toContacts;
    private String toContactsTel;
    private String toProvinceName;
    private String toCityName;
    private String toAreaName;
    private String toTownName;
    private String member;
    private String memberPk;
    private String paymentName;
    private String paymentTime;
    private String purchaserName;
    private String purchaserPk;
    private String supplierName;
    private String supplierPk;
    private String invoiceName;
    private String invoiceTaxidNumber;
    private String invoiceRegPhone;
    private String invoiceBankAccount;
    private String invoiceBankName;
    private String invoiceProvinceName;
    private String invoiceCityName;
    private String invoiceAreaName;
    private String invoiceRegAddress;
    private Double basisLinePrice;
    
    private String productName;
    private String productPk;
    private String varietiesName;
    private String seedvarietyName;
    private String specName;		
    private String seriesName;		
    private String gradeName;
    private String gradePk;
    private String batchNumber;		
    private Double weight;//实际发货重量
    private Integer boxes;//实际发货箱数
    private Double tareWeight;//箱重
    private Double originalFreight;//运费对外单价
    private Double presentFreight;//运费对外单价
    private String goodsName;
    private Integer unit;
    private String remark;
	private String lgLinePk;//物流线路pk
	private String lgLinePricePk;//物流线路阶梯价pk
	
	
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
	public Double getBasisLinePrice() {
		return basisLinePrice;
	}
	public void setBasisLinePrice(Double basisLinePrice) {
		this.basisLinePrice = basisLinePrice;
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
	public String getOrderPk() {
		return orderPk;
	}
	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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
	public String getFromProvincePk() {
		return fromProvincePk;
	}
	public void setFromProvincePk(String fromProvincePk) {
		this.fromProvincePk = fromProvincePk;
	}
	public String getFromProvinceName() {
		return fromProvinceName;
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
	public String getToProvinceName() {
		return toProvinceName;
	}
	public void setToProvinceName(String toProvinceName) {
		this.toProvinceName = toProvinceName;
	}
	public String getToCityName() {
		return toCityName;
	}
	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}
	public String getToAreaName() {
		return toAreaName;
	}
	public void setToAreaName(String toAreaName) {
		this.toAreaName = toAreaName;
	}
	public String getToTownName() {
		return toTownName;
	}
	public void setToTownName(String toTownName) {
		this.toTownName = toTownName;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public String getPurchaserPk() {
		return purchaserPk;
	}
	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierPk() {
		return supplierPk;
	}
	public void setSupplierPk(String supplierPk) {
		this.supplierPk = supplierPk;
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
	public String getInvoiceRegAddress() {
		return invoiceRegAddress;
	}
	public void setInvoiceRegAddress(String invoiceRegAddress) {
		this.invoiceRegAddress = invoiceRegAddress;
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
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}
	public Double getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLgLinePk() {
		return lgLinePk;
	}
	public void setLgLinePk(String lgLinePk) {
		this.lgLinePk = lgLinePk;
	}
	public String getLgLinePricePk() {
		return lgLinePricePk;
	}
	public void setLgLinePricePk(String lgLinePricePk) {
		this.lgLinePricePk = lgLinePricePk;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
