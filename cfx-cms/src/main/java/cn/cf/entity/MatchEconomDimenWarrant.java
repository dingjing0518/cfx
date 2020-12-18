package cn.cf.entity;

import javax.persistence.Id;

public class MatchEconomDimenWarrant {
	
	@Id
	private String id;
	private String warrantObj;
	private Integer warrantObjScore;
	private String warrantType;
	private Integer warrantTypeScore;
	private Integer warrantNumber;
	private Integer warrantNumberScore;
	private String economicsCustPk;
	private String category;
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
	public String getWarrantObj() {
		return warrantObj;
	}
	public void setWarrantObj(String warrantObj) {
		this.warrantObj = warrantObj;
	}
	public Integer getWarrantObjScore() {
		return warrantObjScore;
	}
	public void setWarrantObjScore(Integer warrantObjScore) {
		this.warrantObjScore = warrantObjScore;
	}
	public String getWarrantType() {
		return warrantType;
	}
	public void setWarrantType(String warrantType) {
		this.warrantType = warrantType;
	}
	public Integer getWarrantTypeScore() {
		return warrantTypeScore;
	}
	public void setWarrantTypeScore(Integer warrantTypeScore) {
		this.warrantTypeScore = warrantTypeScore;
	}
	public Integer getWarrantNumber() {
		return warrantNumber;
	}
	public void setWarrantNumber(Integer warrantNumber) {
		this.warrantNumber = warrantNumber;
	}
	public Integer getWarrantNumberScore() {
		return warrantNumberScore;
	}
	public void setWarrantNumberScore(Integer warrantNumberScore) {
		this.warrantNumberScore = warrantNumberScore;
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
	public String getEconomicsCustPk() {
		return economicsCustPk;
	}
	public void setEconomicsCustPk(String economicsCustPk) {
		this.economicsCustPk = economicsCustPk;
	}
}
