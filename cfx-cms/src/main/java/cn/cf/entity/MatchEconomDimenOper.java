package cn.cf.entity;

import javax.persistence.Id;

public class MatchEconomDimenOper {
	
	@Id
	private String id;
	private Double capacity;//产能
	private Integer capacityScore;// 产能得分
	private String deviceStatus;//设备状况
	private Integer deviceStatusScore; //设备状况得分
	private String companyPlace;
	private Integer companyPlaceScore;
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
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	public Integer getCapacityScore() {
		return capacityScore;
	}
	public void setCapacityScore(Integer capacityScore) {
		this.capacityScore = capacityScore;
	}
	public Integer getDeviceStatusScore() {
		return deviceStatusScore;
	}
	public void setDeviceStatusScore(Integer deviceStatusScore) {
		this.deviceStatusScore = deviceStatusScore;
	}
	public String getCompanyPlace() {
		return companyPlace;
	}
	public void setCompanyPlace(String companyPlace) {
		this.companyPlace = companyPlace;
	}
	public Integer getCompanyPlaceScore() {
		return companyPlaceScore;
	}
	public void setCompanyPlaceScore(Integer companyPlaceScore) {
		this.companyPlaceScore = companyPlaceScore;
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
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public Integer getAllScore() {
		return allScore;
	}
	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}
	
}
