package cn.cf.entity;

import javax.persistence.Id;

public class MatchEconomDimenCredit {
	
	@Id
	private String id;
	private String guaranty;// 抵押
	private Integer guarantyScore; //抵押得分
	private Integer notionBank;
	private Integer notionBankScore;
	private Integer businessBank;
	private Integer businessBankScore;
	private Integer creditNumber;
	private Integer creditNumberScore;
	private String creditAmount;
	private Integer creditAmountScore;
	private String category;
	private String economicsCustPk;
	private Integer allScore;
	private String insertTime;
	
	private String processInstanceId;
	
	
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuaranty() {
		return guaranty;
	}
	public void setGuaranty(String guaranty) {
		this.guaranty = guaranty;
	}
	public Integer getGuarantyScore() {
		return guarantyScore;
	}
	public void setGuarantyScore(Integer guarantyScore) {
		this.guarantyScore = guarantyScore;
	}
	public Integer getNotionBank() {
		return notionBank;
	}
	public void setNotionBank(Integer notionBank) {
		this.notionBank = notionBank;
	}
	public Integer getNotionBankScore() {
		return notionBankScore;
	}
	public void setNotionBankScore(Integer notionBankScore) {
		this.notionBankScore = notionBankScore;
	}
	public Integer getBusinessBank() {
		return businessBank;
	}
	public void setBusinessBank(Integer businessBank) {
		this.businessBank = businessBank;
	}
	public Integer getBusinessBankScore() {
		return businessBankScore;
	}
	public void setBusinessBankScore(Integer businessBankScore) {
		this.businessBankScore = businessBankScore;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Integer getCreditAmountScore() {
		return creditAmountScore;
	}
	public void setCreditAmountScore(Integer creditAmountScore) {
		this.creditAmountScore = creditAmountScore;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getAllScore() {
		return allScore;
	}
	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getCreditNumber() {
		return creditNumber;
	}
	public void setCreditNumber(Integer creditNumber) {
		this.creditNumber = creditNumber;
	}
	public Integer getCreditNumberScore() {
		return creditNumberScore;
	}
	public void setCreditNumberScore(Integer creditNumberScore) {
		this.creditNumberScore = creditNumberScore;
	}
	public String getEconomicsCustPk() {
		return economicsCustPk;
	}
	public void setEconomicsCustPk(String economicsCustPk) {
		this.economicsCustPk = economicsCustPk;
	}
	
	
}
