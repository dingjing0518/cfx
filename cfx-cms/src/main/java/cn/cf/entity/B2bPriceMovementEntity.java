package cn.cf.entity;

import javax.persistence.Id;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bPriceMovementEntity{

	@Id
	private String id;
	
	private String varietiesName;
	
	private String specName;
	
	private String seriesName;
	
	private String specifications;//商品规格
	
	private String batchNumber;
	
	private String brandName;
	
	private String productName;
	
	private String gradeName;
	
	private Double price;
	
	private Double tonPrice;
	
	private String movementPk;//价格趋势表Pk

	private String goodsPk;
	
	private String insertTime;
	
	private String date;
	
	private Integer isShow = 1;
	
	private Double yesterdayPrice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getGoodsPk() {
		return goodsPk;
	}

	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}

	public String getMovementPk() {
		return movementPk;
	}

	public void setMovementPk(String movementPk) {
		this.movementPk = movementPk;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getYesterdayPrice() {
		return yesterdayPrice;
	}

	public void setYesterdayPrice(Double yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}

	public Double getTonPrice() {
		return tonPrice;
	}

	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

}