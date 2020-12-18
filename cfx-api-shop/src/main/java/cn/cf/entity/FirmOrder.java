package cn.cf.entity;

public class FirmOrder {
	private String storePk;
	private String storeName;
	private String productPk;
	private String productName;
	private String plantPk;
	private String plantName;
	private String goodsPk;
	private String cartPk;
	private String bindPk;
	private String packNumber;
	private String batchNumber;
	private String distinctNumber;
	private double packageFee = 0;//包装费用
	private double adminFee=0;//自提管理费
	private double loadFee=0;//装车费用
	private String boxes;
	private String tareWeight;
	private double price;//商品价格
	private double increasePrice=0;//加价金额
	private String varietiesName;
	private String seedvarietyName;
	private String seriesName;
	private String specName;
	private String gradeName;
	private String brandName;
	private String supplierName;
	private String supplierPk;
	
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

	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
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

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
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

	public String getGoodsPk() {
		return goodsPk;
	}

	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}

	public String getCartPk() {
		return cartPk;
	}

	public void setCartPk(String cartPk) {
		this.cartPk = cartPk;
	}

	public String getBindPk() {
		return bindPk;
	}

	public void setBindPk(String bindPk) {
		this.bindPk = bindPk;
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

	public String getDistinctNumber() {
		return distinctNumber;
	}

	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}

	public double getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(double packageFee) {
		this.packageFee = packageFee;
	}

	public double getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(double adminFee) {
		this.adminFee = adminFee;
	}

	public double getLoadFee() {
		return loadFee;
	}

	public void setLoadFee(double loadFee) {
		this.loadFee = loadFee;
	}

	public String getBoxes() {
		return boxes;
	}

	public void setBoxes(String boxes) {
		this.boxes = boxes;
	}

	public String getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getIncreasePrice() {
		return increasePrice;
	}

	public void setIncreasePrice(double increasePrice) {
		this.increasePrice = increasePrice;
	}


}
