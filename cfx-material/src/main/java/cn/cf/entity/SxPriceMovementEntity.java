package cn.cf.entity;

import javax.persistence.Id;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SxPriceMovementEntity {

	@Id
	private String id;
	
	private String technologyName;

	private String firstMaterialName;//一级原料

	private String secondMaterialName;//二级原料

	private String specifications;//商品规格
	
	private String batchNumber;
	
	private String brandName;

	private Double price;
	
	private Double yarnPrice;
	
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

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
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

	public Double getYarnPrice() {
		return yarnPrice;
	}

	public void setYarnPrice(Double yarnPrice) {
		this.yarnPrice = yarnPrice;
	}

	public String getMovementPk() {
		return movementPk;
	}

	public void setMovementPk(String movementPk) {
		this.movementPk = movementPk;
	}

	public String getGoodsPk() {
		return goodsPk;
	}

	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
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

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Double getYesterdayPrice() {
		return yesterdayPrice;
	}

	public void setYesterdayPrice(Double yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}

	public String getFirstMaterialName() {
		return firstMaterialName;
	}

	public void setFirstMaterialName(String firstMaterialName) {
		this.firstMaterialName = firstMaterialName;
	}

	public String getSecondMaterialName() {
		return secondMaterialName;
	}

	public void setSecondMaterialName(String secondMaterialName) {
		this.secondMaterialName = secondMaterialName;
	}
}