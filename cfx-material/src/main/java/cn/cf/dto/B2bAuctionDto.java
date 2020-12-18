package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bAuctionDto  extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String auctionName;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String startTime;
	private java.lang.String endTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.Integer sort;
	private java.lang.String storePk;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setAuctionName(java.lang.String auctionName) {
		this.auctionName = auctionName;
	}
	
	public java.lang.String getAuctionName() {
		return this.auctionName;
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
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime;
	}
	
	public java.lang.String getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}
	
	public java.lang.String getEndTime() {
		return this.endTime;
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
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	

}