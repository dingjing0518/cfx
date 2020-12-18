package cn.cf.entity;

import javax.persistence.Id;

public class MatchEconomDimenOnline {
	
	@Id
	private String id;

	private Double salesVolume;
	private String tradeYear;
	private Integer tradeScore;
	
	private Double invoiceAmount;
	private Integer invoiceScore;
	private Double salesAmount;
	private Integer salesScore;
	private String economicsCustPk;
	private String category;
	private String insertTime;
	private Integer allScore;
	
	
	private String processInstanceId;
	
	
	
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Integer getAllScore() {
		return allScore;
	}
	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Double salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getTradeYear() {
		return tradeYear;
	}
	public void setTradeYear(String tradeYear) {
		this.tradeYear = tradeYear;
	}
	public Integer getTradeScore() {
		return tradeScore;
	}
	public void setTradeScore(Integer tradeScore) {
		this.tradeScore = tradeScore;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Integer getInvoiceScore() {
		return invoiceScore;
	}
	public void setInvoiceScore(Integer invoiceScore) {
		this.invoiceScore = invoiceScore;
	}
	public Double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}
	public Integer getSalesScore() {
		return salesScore;
	}
	public void setSalesScore(Integer salesScore) {
		this.salesScore = salesScore;
	}
	public String getEconomicsCustPk() {
		return economicsCustPk;
	}
	public void setEconomicsCustPk(String economicsCustPk) {
		this.economicsCustPk = economicsCustPk;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

}
