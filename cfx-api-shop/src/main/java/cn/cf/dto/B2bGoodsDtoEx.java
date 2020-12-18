package cn.cf.dto;

public class B2bGoodsDtoEx extends B2bGoodsDto {

	private static final long serialVersionUID = 1L;

	private Integer isOpen;// 店铺是否开业
	private Integer showPricebfOpen;// 开店之前是否显示价格(1是 2否)
	private Double upperWeight;// 超过X吨显示有货
	private String startTime;// 店铺开业时间
	private String endTime; // 店铺歇业时间
	private String unitName;
	private String upDown;
	private String qq;// 店铺qq
	private String contactsTel;// 客服电话
	private String wareAddress;// 仓库地址
	private Integer collectCompanysNum;// 公司被收藏数量
	private Long collectGoodsNum;// 商品被收藏次数
	private Integer isCollected;// 是否已被收藏：1未收藏 2已收藏
	private Integer isOffered;// 1未出价 2已出价
	private String headPortrait;// 公司头像
	private Integer counts;// 上下架商品数量
	private String activityTime;
	private Double startingPrice;
	private String auctionPk;// 竞拍pk
	private String bindPk;// 团购pk
	private Double tightStock;// 店铺表库存紧张标识
	private String unit;
	private double packageFee;
	private double loadFee;
	private double adminFee;
	private String suitRangeDescribe;
	private String purposeDescribe;
	private String goodsInfo;
	private String totalWeightUnit;
	private String productName;// 品名
	private String varietiesName;// 品种
	private String seedvarietyName;// 子品种
	private String specName;// 规格大类
	private String specifications;// 规格
	private String seriesName;// 规格系列
	private String batchNumber;// 批号
	private String gradeName;// 等级
	private String distinctNumber;// 区分号
	private String packNumber;// 包装形式
	private String rawMaterialParentName;
	private String rawMaterialName;
	private String technologyName;
	private String materialName;
	private String plantName;
	private String wareName;
	private String certificateName;// 证书名称

	@Override
	public String getGoodsInfo() {
		return goodsInfo;
	}

	@Override
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}


	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(double packageFee) {
		this.packageFee = packageFee;
	}

	public double getLoadFee() {
		return loadFee;
	}

	public void setLoadFee(double loadFee) {
		this.loadFee = loadFee;
	}

	public double getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(double adminFee) {
		this.adminFee = adminFee;
	}

	public Double getTightStock() {
		return tightStock;
	}

	public void setTightStock(Double tightStock) {
		this.tightStock = tightStock;
	}

	public String getAuctionPk() {
		return auctionPk;
	}

	public void setAuctionPk(String auctionPk) {
		this.auctionPk = auctionPk;
	}

	public String getBindPk() {
		return bindPk;
	}

	public void setBindPk(String bindPk) {
		this.bindPk = bindPk;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public Double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getShowPricebfOpen() {
		return showPricebfOpen;
	}

	public void setShowPricebfOpen(Integer showPricebfOpen) {
		this.showPricebfOpen = showPricebfOpen;
	}

	public Double getUpperWeight() {
		return upperWeight;
	}

	public void setUpperWeight(Double upperWeight) {
		this.upperWeight = upperWeight;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getWareAddress() {
		return wareAddress;
	}

	public void setWareAddress(String wareAddress) {
		this.wareAddress = wareAddress;
	}

	public Integer getCollectCompanysNum() {
		return collectCompanysNum;
	}

	public void setCollectCompanysNum(Integer collectCompanysNum) {
		this.collectCompanysNum = collectCompanysNum;
	}

	public Integer getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(Integer isCollected) {
		this.isCollected = isCollected;
	}

	public Integer getIsOffered() {
		return isOffered;
	}

	public void setIsOffered(Integer isOffered) {
		this.isOffered = isOffered;
	}

	public Long getCollectGoodsNum() {
		return collectGoodsNum;
	}

	public void setCollectGoodsNum(Long collectGoodsNum) {
		this.collectGoodsNum = collectGoodsNum;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public String getTotalWeightUnit() {
		return totalWeightUnit;
	}

	public void setTotalWeightUnit(String totalWeightUnit) {
		this.totalWeightUnit = totalWeightUnit;
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

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

}
