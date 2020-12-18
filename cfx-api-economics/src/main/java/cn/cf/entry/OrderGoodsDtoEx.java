package cn.cf.entry;

public class OrderGoodsDtoEx {

	private String productName;
	private String rawMaterialName;
	private String goodsType;
	private String specifications;
	private String batchNumber;
	private Integer afterBoxes;
	private Double afterWeight;
	private Double originalPrice;
	private Double presentPrice;
	private String  unit;
//	private Double presentTotalPrice;
//	private Double presentTotalFreight;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRawMaterialName() {
		return rawMaterialName;
	}
	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Double getAfterWeight() {
		return afterWeight;
	}
	public void setAfterWeight(Double afterWeight) {
		this.afterWeight = afterWeight;
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
	public Integer getAfterBoxes() {
		return afterBoxes;
	}
	public void setAfterBoxes(Integer afterBoxes) {
		this.afterBoxes = afterBoxes;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	
	
}
