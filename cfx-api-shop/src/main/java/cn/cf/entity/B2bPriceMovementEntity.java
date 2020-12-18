package cn.cf.entity;

import org.springframework.data.annotation.Id;

public class B2bPriceMovementEntity {
	
	@Id
	private String id;
	private String goodsPk;
	private String productName;
	private String varietiesName;
	private String specName;
	private String seriesName;
	private String specifications;
	private String batchNumber;
	private String brandName;
	private Double price;
	private String date;
	private String insertTime;
	private String movementPk;
	private Integer isShow;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsPk() {
		return goodsPk;
	}
	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
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
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getMovementPk() {
		return movementPk;
	}
	public void setMovementPk(String movementPk) {
		this.movementPk = movementPk;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
}	
