package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class BussStoreDataReport {
	@Id
	private String id;
	private String date;
	private String  storeName;
	private String storePk;
	private Integer number;
	private BigDecimal  weight;//成交吨数
	private BigDecimal amount;//交易额
	private BigDecimal accumNumber;//累计订单数
	private BigDecimal accumWeight;//累计成交量
	private BigDecimal accumAmount;//累计交易额
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStorePk() {
		return storePk;
	}
	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAccumNumber() {
		return accumNumber;
	}
	public void setAccumNumber(BigDecimal accumNumber) {
		this.accumNumber = accumNumber;
	}
	public BigDecimal getAccumWeight() {
		return accumWeight;
	}
	public void setAccumWeight(BigDecimal accumWeight) {
		this.accumWeight = accumWeight;
	}
	public BigDecimal getAccumAmount() {
		return accumAmount;
	}
	public void setAccumAmount(BigDecimal accumAmount) {
		this.accumAmount = accumAmount;
	}
	
	 
}
