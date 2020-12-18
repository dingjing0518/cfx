package cn.cf.entity;

public class SubContractSync {
	private String detailNumber;
	private String batchNumber;
	private String level;
	private String packageNumber;
	private String distinctNumber;
	
	private Integer boxes;
	private Double weight;
	private Double basicPrice;
	private Double freight;
	private Double totalFreight;
	private Double contractPrice;
	private Double discount;
	private Double loadFee;
	private Double adminFee;
	private Double packageFee;
	private Double totalPrice;
	private String logisticsPk;
	private String logisticsStepInfoPk;
	private Integer boxesShipped;
	private Double weightShipped;
	private Integer contractStatus;
	
	public String getDetailNumber() {
		return detailNumber;
	}
	public void setDetailNumber(String detailNumber) {
		this.detailNumber = detailNumber;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPackageNumber() {
		return packageNumber;
	}
	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	public String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
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
	public Double getBasicPrice() {
		return basicPrice;
	}
	public void setBasicPrice(Double basicPrice) {
		this.basicPrice = basicPrice;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public Double getContractPrice() {
		return contractPrice;
	}
	public void setContractPrice(Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getLoadFee() {
		return loadFee;
	}
	public void setLoadFee(Double loadFee) {
		this.loadFee = loadFee;
	}
	public Double getAdminFee() {
		return adminFee;
	}
	public void setAdminFee(Double adminFee) {
		this.adminFee = adminFee;
	}
	public Double getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(Double packageFee) {
		this.packageFee = packageFee;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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
	public Double getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}
	public Integer getBoxesShipped() {
		return boxesShipped;
	}
	public void setBoxesShipped(Integer boxesShipped) {
		this.boxesShipped = boxesShipped;
	}
	public Double getWeightShipped() {
		return weightShipped;
	}
	public void setWeightShipped(Double weightShipped) {
		this.weightShipped = weightShipped;
	}
	public Integer getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
	

}
