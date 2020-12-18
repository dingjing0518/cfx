package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysVersionManagement  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.Integer terminal;
	private java.lang.String version;
	private java.lang.String downloadUrl;
	private java.util.Date insertTime;
	private java.lang.String publisher;
	private java.lang.String remark;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.util.Date updateTime;
	private java.lang.String versionName;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setTerminal(java.lang.Integer terminal) {
		this.terminal = terminal;
	}
	
	public java.lang.Integer getTerminal() {
		return this.terminal;
	}
	public void setVersion(java.lang.String version) {
		this.version = version;
	}
	
	public java.lang.String getVersion() {
		return this.version;
	}
	public void setDownloadUrl(java.lang.String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	public java.lang.String getDownloadUrl() {
		return this.downloadUrl;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setPublisher(java.lang.String publisher) {
		this.publisher = publisher;
	}
	
	public java.lang.String getPublisher() {
		return this.publisher;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public java.lang.Integer getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setVersionName(java.lang.String versionName) {
		this.versionName = versionName;
	}
	
	public java.lang.String getVersionName() {
		return this.versionName;
	}
	

}