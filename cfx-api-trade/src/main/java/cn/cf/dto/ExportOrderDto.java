package cn.cf.dto;


public class ExportOrderDto extends B2bOrderDto{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer loanNumberStatus;
	private String purchaserName;
	private String supplierName;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String address;
	private String goodsInfo;
	private Double originalPrice;
	private Double presentPrice;
	private String childOrderNumber;
	private int afterBoxes;
	private Double afterWeight;
	private String afterBoxesS;
	private String afterWeightS;
	private Double presentTotalFreight;
	private String paymentName;
	private String economicsGoodsName;
	private String sourceName;
	private int boxes;
	private String boxesS;
	private Double weight;
	private String weightS;
	private  Double cWeightS;
	private int boxesShipped;
	private String boxesShippedS;
	private  int cBoxesShippedS;
	private Double weightShipped;
	private String weightShippedS;
	private  Double cWeightShippedS;
	private int boxesNoShipped;
	private String boxesNoShippedS;
	private int cAfterBoxesS;
	private Double cAfterWeightS;
	private  int cBoxesNoShippedS;
	private Double weightNoShipped;
	private String weightNoShippedS;
	private Double cWeightNoShippedS;
	private String meno;
	private String orderStatusName;
	private String purchaseTypeName;
	private Double presentTotalPrice;
	private String purchaserInfo;
	private String supplierInfo;
    private  String productName;
    private  String varietiesName;
    private  String seedvarietyName;
    private  String specName;
    private  String specifications;
    private  String seriesName;
    private  String batchNumber;
    private  String gradeName;

    private  String technologyName;
    private  String rawMaterialParentName;
    private  String rawMaterialName;
    private  String materialName;

	private Double otherDiscount;


	public Double getOtherDiscount() {
		return otherDiscount == null ? 0d : otherDiscount;
	}

	public void setOtherDiscount(Double otherDiscount) {
		this.otherDiscount = otherDiscount;
	}

	public int getcAfterBoxesS() {
		return cAfterBoxesS;
	}

	public void setcAfterBoxesS(int cAfterBoxesS) {
		this.cAfterBoxesS = cAfterBoxesS;
	}

	public Double getcAfterWeightS() {
		return cAfterWeightS;
	}

	public void setcAfterWeightS(Double cAfterWeightS) {
		this.cAfterWeightS = cAfterWeightS;
	}




	private  String brandName;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public String getRawMaterialParentName() {
		return rawMaterialParentName;
	}

	public void setRawMaterialParentName(String rawMaterialParentName) {
		this.rawMaterialParentName = rawMaterialParentName;
	}

	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getDistinctNumber() {
		return distinctNumber;
	}

	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}

	public String getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}

	private  String distinctNumber;
    private  String packNumber;
	public String getAfterBoxesS() {
		return afterBoxesS;
	}

	public void setAfterBoxesS(String afterBoxesS) {
		this.afterBoxesS = afterBoxesS;
	}

	public String getAfterWeightS() {
		return afterWeightS;
	}

	public void setAfterWeightS(String afterWeightS) {
		this.afterWeightS = afterWeightS;
	}

	public String getBoxesS() {
		return boxesS;
	}

	public void setBoxesS(String boxesS) {
		this.boxesS = boxesS;
	}

	public String getWeightS() {
		return weightS;
	}

	public void setWeightS(String weightS) {
		this.weightS = weightS;
	}

	public String getBoxesShippedS() {
		return boxesShippedS;
	}

	public void setBoxesShippedS(String boxesShippedS) {
		this.boxesShippedS = boxesShippedS;
	}

	public String getWeightShippedS() {
		return weightShippedS;
	}

	public void setWeightShippedS(String weightShippedS) {
		this.weightShippedS = weightShippedS;
	}

	public String getBoxesNoShippedS() {
		return boxesNoShippedS;
	}

	public void setBoxesNoShippedS(String boxesNoShippedS) {
		this.boxesNoShippedS = boxesNoShippedS;
	}

	public String getWeightNoShippedS() {
		return weightNoShippedS;
	}

	public void setWeightNoShippedS(String weightNoShippedS) {
		this.weightNoShippedS = weightNoShippedS;
	}

	@Override
	public String getPurchaserInfo() {
		return purchaserInfo;
	}

	@Override
	public void setPurchaserInfo(String purchaserInfo) {
		this.purchaserInfo = purchaserInfo;
	}

	@Override
	public String getSupplierInfo() {
		return supplierInfo;
	}

	@Override
	public void setSupplierInfo(String supplierInfo) {
		this.supplierInfo = supplierInfo;
	}

	public String getPurchaseTypeName() {
		return purchaseTypeName;
	}

	public void setPurchaseTypeName(String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}




	public String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}

	public int getAfterBoxes() {
		return afterBoxes;
	}

	public void setAfterBoxes(int afterBoxes) {
		this.afterBoxes = afterBoxes;
	}

	@Override
	public String getPaymentName() {
		return paymentName;
	}

	@Override
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	@Override
	public String getEconomicsGoodsName() {
		return economicsGoodsName;
	}

	@Override
	public void setEconomicsGoodsName(String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getBoxes() {
		return boxes;
	}

	public void setBoxes(int boxes) {
		this.boxes = boxes;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public int getBoxesShipped() {
		return boxesShipped;
	}

	public void setBoxesShipped(int boxesShipped) {
		this.boxesShipped = boxesShipped;
	}


	public int getBoxesNoShipped() {
		return boxesNoShipped;
	}

	public void setBoxesNoShipped(int boxesNoShipped) {
		this.boxesNoShipped = boxesNoShipped;
	}


	@Override
	public String getMeno() {
		return meno;
	}

	@Override
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

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public Double getPresentTotalPrice() {
		return presentTotalPrice;
	}

	public void setPresentTotalPrice(Double presentTotalPrice) {
		this.presentTotalPrice = presentTotalPrice;
	}

	@Override
	public Double getActualAmount() {
		return actualAmount;
	}

	@Override
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	private String contactsTel;
	private String contacts;
	private String plantName;
	private Double actualAmount;

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private Integer logisticsModelType;

	public Integer getLogisticsModelType() {
		return logisticsModelType;
	}

	public void setLogisticsModelType(Integer logisticsModelType) {
		this.logisticsModelType = logisticsModelType;
	}

	public Integer getLoanNumberStatus() {
		return loanNumberStatus;
	}

	public void setLoanNumberStatus(Integer loanNumberStatus) {
		this.loanNumberStatus = loanNumberStatus;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(Double presentPrice) {
		this.presentPrice = presentPrice;
	}

	public Double getAfterWeight() {
		return afterWeight;
	}

	public void setAfterWeight(Double afterWeight) {
		this.afterWeight = afterWeight;
	}

	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public Double getWeightShipped() {
		return weightShipped;
	}

	public void setWeightShipped(Double weightShipped) {
		this.weightShipped = weightShipped;
	}

	public Double getWeightNoShipped() {
		return weightNoShipped;
	}

	public void setWeightNoShipped(Double weightNoShipped) {
		this.weightNoShipped = weightNoShipped;
	}

	public Double getcWeightS() {
		return cWeightS;
	}

	public void setcWeightS(Double cWeightS) {
		this.cWeightS = cWeightS;
	}

	public int getcBoxesShippedS() {
		return cBoxesShippedS;
	}

	public void setcBoxesShippedS(int cBoxesShippedS) {
		this.cBoxesShippedS = cBoxesShippedS;
	}

	public Double getcWeightShippedS() {
		return cWeightShippedS;
	}

	public void setcWeightShippedS(Double cWeightShippedS) {
		this.cWeightShippedS = cWeightShippedS;
	}
	

	public int getcBoxesNoShippedS() {
		return cBoxesNoShippedS;
	}

	public void setcBoxesNoShippedS(int cBoxesNoShippedS) {
		this.cBoxesNoShippedS = cBoxesNoShippedS;
	}

	public Double getcWeightNoShippedS() {
		return cWeightNoShippedS;
	}

	public void setcWeightNoShippedS(Double cWeightNoShippedS) {
		this.cWeightNoShippedS = cWeightNoShippedS;
	}
	


}
