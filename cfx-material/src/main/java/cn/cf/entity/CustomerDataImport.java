package cn.cf.entity;

import javax.persistence.Id;

public class CustomerDataImport {

	@Id
	private String id;
	private String storePk;
	private String url;
	private Integer status;
	private String remark;
	private String insertTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStorePk() {
		return storePk;
	}
	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	
}
