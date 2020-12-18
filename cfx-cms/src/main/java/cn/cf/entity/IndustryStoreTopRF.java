package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class IndustryStoreTopRF {
	
	@Id
	private String id;
	
	private BigDecimal weight = new BigDecimal(0.0);
	
	private BigDecimal allAmount = new BigDecimal(0.0);
	
	private String storeName;
	
	private Integer counts = 0;
	
	private int month;
	
	private String year;
	
	private int numbers;

	private Double afterWeight;

	private Double presentTotalFreight;

	private Double goodsWeight;

	private String goodsInfo;

	private Double presentPrice;

	private String orderNumber;

	private String block;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getAllAmount() {
		return allAmount;
	}

	public void setAllAmount(BigDecimal allAmount) {
		this.allAmount = allAmount;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getNumbers() {
		return numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	public Double getAfterWeight() {
		return afterWeight;
	}

	public void setAfterWeight(Double afterWeight) {
		this.afterWeight = afterWeight;
	}

	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public Double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public Double getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(Double presentPrice) {
		this.presentPrice = presentPrice;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
}
