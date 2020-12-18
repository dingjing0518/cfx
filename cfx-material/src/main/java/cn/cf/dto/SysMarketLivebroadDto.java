package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysMarketLivebroadDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String livebroadcategoryName;
	private java.lang.String livebroadcategoryPk;
	private java.util.Date insertTime;
	private java.lang.String content;
	private java.lang.String keyword;
	private java.lang.Integer isDelete;
	private java.util.Date updateTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setLivebroadcategoryName(java.lang.String livebroadcategoryName) {
		this.livebroadcategoryName = livebroadcategoryName;
	}
	
	public java.lang.String getLivebroadcategoryName() {
		return this.livebroadcategoryName;
	}
	public void setLivebroadcategoryPk(java.lang.String livebroadcategoryPk) {
		this.livebroadcategoryPk = livebroadcategoryPk;
	}
	
	public java.lang.String getLivebroadcategoryPk() {
		return this.livebroadcategoryPk;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}
	
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	

}