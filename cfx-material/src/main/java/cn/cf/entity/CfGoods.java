package cn.cf.entity;

public class CfGoods {
	
	private String pk;
	private String productPk;
	private String productName;
	private String specifications;
	private String specPk;
	private String specName;
	private String seriesPk;
	private String seriesName;
	private String gradePk;
	private String gradeName;
	private String gradeChineseName;
	private String varietiesPk;
	private String varietiesName;
	private String seedvarietyPk;
	private String seedvarietyName;
	private String packNumber;
	private String batchNumber;
	private String plantPk;
	private String plantName;
	private String warePk;
	private String wareName;
	private String wareCode;
	private String wareAddress;
	private String suitRangeDescribe;
	private String purposeDescribe;
	private String distinctNumber;
	private Double packageFee;
	private Double loadFee;
	private Double adminFee;
	private String warehouseNumber;
	private String warehouseType;
	private Integer unit;
	private String block;
	private String brandPk;
	private String brandName;
    private Double tareWeight;
    private Double ingotWeight;
    private Integer ingotNum;
    private Double stampDuty;//印花税 1否2是
	
	 
 
	public Double getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(Double stampDuty) {
		this.stampDuty = stampDuty;
	}
	public Double getIngotWeight() {
		return ingotWeight;
	}
	public void setIngotWeight(Double ingotWeight) {
		this.ingotWeight = ingotWeight;
	}
	public Integer getIngotNum() {
		return ingotNum;
	}
	public void setIngotNum(Integer ingotNum) {
		this.ingotNum = ingotNum;
	}
	public Double getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
	public String getBrandPk() {
		return brandPk;
	}
	public void setBrandPk(String brandPk) {
		this.brandPk = brandPk;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getWareAddress() {
		return wareAddress;
	}
	public void setWareAddress(String wareAddress) {
		this.wareAddress = wareAddress;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
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
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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
	public String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
	public String getWareCode() {
		return wareCode;
	}
	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
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
	public String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
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
	
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public String getWarehouseType() {
		return warehouseType;
	}
	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}
	
	
}
