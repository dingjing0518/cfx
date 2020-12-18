package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysBanner  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.Integer type;
	private java.lang.Integer auditStatus;
	private java.lang.Integer recommend;
	private java.lang.Integer top;
	private java.lang.String url;
	private java.lang.Integer sort;
	private java.lang.String details;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.util.Date onlineTime;
	private java.lang.String linkUrl;
	private java.lang.Integer platform;
	private java.lang.Integer position;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setRecommend(java.lang.Integer recommend) {
		this.recommend = recommend;
	}
	
	public java.lang.Integer getRecommend() {
		return this.recommend;
	}
	public void setTop(java.lang.Integer top) {
		this.top = top;
	}
	
	public java.lang.Integer getTop() {
		return this.top;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setDetails(java.lang.String details) {
		this.details = details;
	}
	
	public java.lang.String getDetails() {
		return this.details;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	public void setOnlineTime(java.util.Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public java.util.Date getOnlineTime() {
		return this.onlineTime;
	}
	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public java.lang.String getLinkUrl() {
		return this.linkUrl;
	}

	public java.lang.Integer getPlatform() {
		return platform;
	}

	public void setPlatform(java.lang.Integer platform) {
		this.platform = platform;
	}

	public java.lang.Integer getPosition() {
		return position;
	}

	public void setPosition(java.lang.Integer position) {
		this.position = position;
	}

}