package cn.cf.entity;

public class OrderGoodsSync {
	
	private String childOrderNumber;
	private String batchNumber;
	private String gradeName;
	private String distinctNumber;
	private String packNumber;
	private Double loadFee;
	private Double packageFee;
	private Double adminFee;
	private Double purchQuantity;
	private Integer purchWeight;
	private Double originalPrice;
	private Double presentPrice;
	private Double originalFreight;
	private Double originalTotalFreight;
	private Double presentTotalFreight;
	private Double presentFreight;
	private String logisticsPk;
	private String logisticsStepInfoPk;
	private Integer afterBoxes;
	private Double afterWeight;
	private String warehouseNumber;
	private String warehouseType;
	//columns END

	public String getWarehouseNumber() {
		return warehouseNumber;
	}

	public void setWarehouseNumber(String warehouseNumber) {
		this.warehouseNumber = warehouseNumber;
	}

	public String getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	public String getChildOrderNumber() {
		return childOrderNumber;
	}
	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
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
	public Double getLoadFee() {
		return loadFee;
	}
	public void setLoadFee(Double loadFee) {
		this.loadFee = loadFee;
	}
	public Double getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(Double packageFee) {
		this.packageFee = packageFee;
	}
	public Double getAdminFee() {
		return adminFee;
	}
	public void setAdminFee(Double adminFee) {
		this.adminFee = adminFee;
	}
	public Double getPurchQuantity() {
		return purchQuantity;
	}
	public void setPurchQuantity(Double purchQuantity) {
		this.purchQuantity = purchQuantity;
	}
	public Integer getPurchWeight() {
		return purchWeight;
	}
	public void setPurchWeight(Integer purchWeight) {
		this.purchWeight = purchWeight;
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
	public String getLogisticsPk() {
		return logisticsPk;
	}
	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	public String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}
	public void setLogisticsStepInfoPk(String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
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
	public Integer getAfterBoxes() {
		return afterBoxes;
	}
	public void setAfterBoxes(Integer afterBoxes) {
		this.afterBoxes = afterBoxes;
	}
	public Double getAfterWeight() {
		return afterWeight;
	}
	public void setAfterWeight(Double afterWeight) {
		this.afterWeight = afterWeight;
	}
	

}
