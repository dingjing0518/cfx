package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bSpecHotDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String firstLevelPk;
	private java.lang.String firstLevelName;
	private java.lang.String secondLevelPk;
	private java.lang.String secondLevelName;
	private java.lang.String thirdLevelPk;
	private java.lang.String thirdLevelName;
	private java.lang.String linkUrl;
	private java.lang.Integer status;
	private java.lang.Integer sort;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer isDelete;
	private java.lang.String block;
	private java.lang.String fourthLevelPk;
	private java.lang.String fourthLevelName;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setFirstLevelPk(java.lang.String firstLevelPk) {
		this.firstLevelPk = firstLevelPk;
	}
	
	public java.lang.String getFirstLevelPk() {
		return this.firstLevelPk;
	}
	public void setFirstLevelName(java.lang.String firstLevelName) {
		this.firstLevelName = firstLevelName;
	}
	
	public java.lang.String getFirstLevelName() {
		return this.firstLevelName;
	}
	public void setSecondLevelPk(java.lang.String secondLevelPk) {
		this.secondLevelPk = secondLevelPk;
	}
	
	public java.lang.String getSecondLevelPk() {
		return this.secondLevelPk;
	}
	public void setSecondLevelName(java.lang.String secondLevelName) {
		this.secondLevelName = secondLevelName;
	}
	
	public java.lang.String getSecondLevelName() {
		return this.secondLevelName;
	}
	public void setThirdLevelPk(java.lang.String thirdLevelPk) {
		this.thirdLevelPk = thirdLevelPk;
	}
	
	public java.lang.String getThirdLevelPk() {
		return this.thirdLevelPk;
	}
	public void setThirdLevelName(java.lang.String thirdLevelName) {
		this.thirdLevelName = thirdLevelName;
	}
	
	public java.lang.String getThirdLevelName() {
		return this.thirdLevelName;
	}
	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public java.lang.String getLinkUrl() {
		return this.linkUrl;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setBlock(java.lang.String block) {
		this.block = block;
	}
	
	public java.lang.String getBlock() {
		return this.block;
	}
	public void setFourthLevelPk(java.lang.String fourthLevelPk) {
		this.fourthLevelPk = fourthLevelPk;
	}
	
	public java.lang.String getFourthLevelPk() {
		return this.fourthLevelPk;
	}
	public void setFourthLevelName(java.lang.String fourthLevelName) {
		this.fourthLevelName = fourthLevelName;
	}
	
	public java.lang.String getFourthLevelName() {
		return this.fourthLevelName;
	}
	

}