package cn.cf.entity;

import javax.persistence.Id;

public class RayxDownLoad {
	@Id
	private String id;
	private String companyPk;
	private String insertTime;
	private Integer downLoadStatus;//1未下载 2已下载未上传3已上传
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getDownLoadStatus() {
		return downLoadStatus;
	}
	public void setDownLoadStatus(Integer downLoadStatus) {
		this.downLoadStatus = downLoadStatus;
	}
}
