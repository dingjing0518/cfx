package cn.cf.dto;

import java.math.BigDecimal;

public class B2bGoodsDtoEx extends B2bGoodsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer  isUpDown;
	private String details;
	private String purpose;
	private String plantCode;
	private String plantAddress;
	private String wareCode;
	private String wareAddress;
	private BigDecimal availableWeight;
	private Integer availableBoxes;
	private String activityStartTime;
	private String activityEndTime;
	private String goodsType;
	private Integer productType;
	private String packageNumber;
	private String splitAssistantName;
	private String splitAssistantNumber;
	private String batchNumber;//批号
	private String packNumber;//包装批号
	private String gradeName;//等级
	private String distinctNumber;//区分号
	private String productPk;
	private String productName;
	private String specifications;
	private String specPk;
	private String specName;
	private String seriesPk;
	private String seriesName;
	private String gradePk;
	private String gradeChineseName;
	private String varietiesPk;
	private String varietiesName;
	private String seedvarietyPk;
	private String seedvarietyName;
	private String plantPk;
	private String plantName;
	private String warePk;
	private String wareName;
	private String suitRangeDescribe;
	private String purposeDescribe;
	private Double packageFee=0d;
	private Double loadFee =0d;
	private Double adminFee=0d;
	private String warehouseNumber;
	private String warehouseType;
	private Integer unit;
	private String name;
	private Integer IsEstimate;
	private String bindName;
	private String bindProcuctID;
	private String bindProcuctCount;
	private String bindReason;
	private String bindProcuctDetails;
	private Double bindProcuctPrice;
	
	
	public Integer getIsUpDown() {
		return isUpDown;
	}
	public void setIsUpDown(Integer isUpDown) {
		this.isUpDown = isUpDown;
	}
	public String getPackageNumber() {
		return packageNumber;
	}
	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getActivityStartTime() {
		return activityStartTime;
	}
	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	public String getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}
	public BigDecimal getAvailableWeight() {
		return availableWeight;
	}
	public void setAvailableWeight(BigDecimal availableWeight) {
		this.availableWeight = availableWeight;
	}
	public Integer getAvailableBoxes() {
		return availableBoxes;
	}
	public void setAvailableBoxes(Integer availableBoxes) {
		this.availableBoxes = availableBoxes;
	}
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}
	public String getWareAddress() {
		return wareAddress;
	}
	public void setWareAddress(String wareAddress) {
		this.wareAddress = wareAddress;
	}
	public String getPlantCode() {
		return plantCode;
	}
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	public String getPlantAddress() {
		return plantAddress;
	}
	public void setPlantAddress(String plantAddress) {
		this.plantAddress = plantAddress;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getSplitAssistantName() {
		return splitAssistantName;
	}
	public void setSplitAssistantName(String splitAssistantName) {
		this.splitAssistantName = splitAssistantName;
	}
	public String getSplitAssistantNumber() {
		return splitAssistantNumber;
	}
	public void setSplitAssistantNumber(String splitAssistantNumber) {
		this.splitAssistantNumber = splitAssistantNumber;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
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
	public String getProductPk() {
		return productPk;
	}
	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getSpecPk() {
		return specPk;
	}
	public void setSpecPk(String specPk) {
		this.specPk = specPk;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSeriesPk() {
		return seriesPk;
	}
	public void setSeriesPk(String seriesPk) {
		this.seriesPk = seriesPk;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getGradePk() {
		return gradePk;
	}
	public void setGradePk(String gradePk) {
		this.gradePk = gradePk;
	}
	public String getGradeChineseName() {
		return gradeChineseName;
	}
	public void setGradeChineseName(String gradeChineseName) {
		this.gradeChineseName = gradeChineseName;
	}
	public String getVarietiesPk() {
		return varietiesPk;
	}
	public void setVarietiesPk(String varietiesPk) {
		this.varietiesPk = varietiesPk;
	}
	public String getVarietiesName() {
		return varietiesName;
	}
	public void setVarietiesName(String varietiesName) {
		this.varietiesName = varietiesName;
	}
	public String getSeedvarietyPk() {
		return seedvarietyPk;
	}
	public void setSeedvarietyPk(String seedvarietyPk) {
		this.seedvarietyPk = seedvarietyPk;
	}
	public String getSeedvarietyName() {
		return seedvarietyName;
	}
	public void setSeedvarietyName(String seedvarietyName) {
		this.seedvarietyName = seedvarietyName;
	}
	public String getPlantPk() {
		return plantPk;
	}
	public void setPlantPk(String plantPk) {
		this.plantPk = plantPk;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getWarePk() {
		return warePk;
	}
	public void setWarePk(String warePk) {
		this.warePk = warePk;
	}
	public String getWareName() {
		return wareName;
	}
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	public String getSuitRangeDescribe() {
		return suitRangeDescribe;
	}
	public void setSuitRangeDescribe(String suitRangeDescribe) {
		this.suitRangeDescribe = suitRangeDescribe;
	}
	public String getPurposeDescribe() {
		return purposeDescribe;
	}
	public void setPurposeDescribe(String purposeDescribe) {
		this.purposeDescribe = purposeDescribe;
	}
	public Double getPackageFee() {
		return packageFee;
	}
	public void setPackageFee(Double packageFee) {
		this.packageFee = packageFee;
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
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsEstimate() {
		return IsEstimate;
	}
	public void setIsEstimate(Integer isEstimate) {
		IsEstimate = isEstimate;
	}
	public String getBindName() {
		return bindName;
	}
	public void setBindName(String bindName) {
		this.bindName = bindName;
	}
	public String getBindProcuctID() {
		return bindProcuctID;
	}
	public void setBindProcuctID(String bindProcuctID) {
		this.bindProcuctID = bindProcuctID;
	}
	public String getBindProcuctCount() {
		return bindProcuctCount;
	}
	public void setBindProcuctCount(String bindProcuctCount) {
		this.bindProcuctCount = bindProcuctCount;
	}
	public String getBindReason() {
		return bindReason;
	}
	public void setBindReason(String bindReason) {
		this.bindReason = bindReason;
	}
	public String getBindProcuctDetails() {
		return bindProcuctDetails;
	}
	public void setBindProcuctDetails(String bindProcuctDetails) {
		this.bindProcuctDetails = bindProcuctDetails;
	}
	public Double getBindProcuctPrice() {
		return bindProcuctPrice;
	}
	public void setBindProcuctPrice(Double bindProcuctPrice) {
		this.bindProcuctPrice = bindProcuctPrice;
	}
	
	
	
}
