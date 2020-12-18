package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class BussEconPurchaserDataReport {
	@Id
	private String id;
	private String date;
	private String purchaserPk;
	private String purchaserName;
	private Integer btNumber;
	private BigDecimal btWeight;
	private BigDecimal btAmount;
	private Integer dNumber;
	private BigDecimal dWeight;
	private BigDecimal dAmount;
	
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
	public String getPurchaserPk() {
		return purchaserPk;
	}
	public void setPurchaserPk(String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	public String getPurchaserName() {
		return purchaserName;
	}
	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	public Integer getBtNumber() {
		return btNumber;
	}
	public void setBtNumber(Integer btNumber) {
		this.btNumber = btNumber;
	}
	public BigDecimal getBtWeight() {
		return btWeight;
	}
	public void setBtWeight(BigDecimal btWeight) {
		this.btWeight = btWeight;
	}
	public BigDecimal getBtAmount() {
		return btAmount;
	}
	public void setBtAmount(BigDecimal btAmount) {
		this.btAmount = btAmount;
	}
	public Integer getdNumber() {
		return dNumber;
	}
	public void setdNumber(Integer dNumber) {
		this.dNumber = dNumber;
	}
	public BigDecimal getdWeight() {
		return dWeight;
	}
	public void setdWeight(BigDecimal dWeight) {
		this.dWeight = dWeight;
	}
	public BigDecimal getdAmount() {
		return dAmount;
	}
	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}
	
	
	
	
	
	

}
