package cn.cf.entity;

public class FirmOrder {
	private String storePk;
	private String storeName;
	private String goodsPk;
	private String cartPk;
	private String bindPk;
	private String boxes;
	private Double weight;
	private String tareWeight;
	private double price;//商品价格
	private double increasePrice=0;//加价金额
	private String brandName;
	private String supplierName;
	private String supplierPk;
	private String goodsInfo;
	private String block;

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
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

	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}	
	

}
