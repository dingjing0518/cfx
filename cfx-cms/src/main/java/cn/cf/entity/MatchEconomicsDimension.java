package cn.cf.entity;

import javax.persistence.Id;

public class MatchEconomicsDimension {
	
	@Id
	private String id;
	private String category;
	private String item;
	private String content;
	private Integer score;
	private String insertTime;
	private String economicsCustPk;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
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
