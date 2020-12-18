package cn.cf.entity;

import org.springframework.data.annotation.Id;

public class MangoBackstageInfo {
	@Id
	private String id;
	private String url;
	private String insertTime;
	private String name;
	private String accountPk;
	private String accountName;
	private String insertStartTime;
	private String insertEndTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
