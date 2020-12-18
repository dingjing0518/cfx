package cn.cf.entity;

import javax.persistence.Id;

public class DistributeAccountOperationLogs {
	
	@Id
	private String id;
	
	private String longinRoleName;//操作人角色
	
	private String longinName;//操作人名称

	private String platformRoleName;//平台交易员角色

	private String platformName;//平台交易员

	private String companyName;
	
	private String offlineAccount;//线下业务员账号
	
	private String offlineRoleName;//线下业务员角色

	private String offlineName;//线下业务员

	private String insertTime;
	
	private String insertStartTime;
	private String insertEndTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLonginRoleName() {
		return longinRoleName;
	}
	public void setLonginRoleName(String longinRoleName) {
		this.longinRoleName = longinRoleName;
	}
	public String getLonginName() {
		return longinName;
	}
	public void setLonginName(String longinName) {
		this.longinName = longinName;
	}
	public String getPlatformRoleName() {
		return platformRoleName;
	}
	public void setPlatformRoleName(String platformRoleName) {
		this.platformRoleName = platformRoleName;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOfflineAccount() {
		return offlineAccount;
	}
	public void setOfflineAccount(String offlineAccount) {
		this.offlineAccount = offlineAccount;
	}
	public String getOfflineRoleName() {
		return offlineRoleName;
	}
	public void setOfflineRoleName(String offlineRoleName) {
		this.offlineRoleName = offlineRoleName;
	}
	public String getOfflineName() {
		return offlineName;
	}
	public void setOfflineName(String offlineName) {
		this.offlineName = offlineName;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getInsertStartTime() {
		return insertStartTime;
	}
	public void setInsertStartTime(String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}
	public String getInsertEndTime() {
		return insertEndTime;
	}
	public void setInsertEndTime(String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}
	
}