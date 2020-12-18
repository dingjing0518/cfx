package cn.cf.model;

import cn.cf.dto.B2bLotteryActivityDto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLotteryActivity  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.util.Date onlineTime;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.Integer isDelete;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer isVisable;
	private java.lang.Integer activityType;
	private java.lang.Integer maximumNumber;
	private java.lang.Integer bindRole;
	//columns END


	public B2bLotteryActivity(){}

	public B2bLotteryActivity(B2bLotteryActivityDto dto){

		this.pk = dto.getPk();
		this.name = dto.getName();
		this.onlineTime = dto.getOnlineTime();
		this.startTime = dto.getStartTime();
		this.endTime = dto.getEndTime();
		this.isDelete = dto.getIsDelete();
		this.insertTime = dto.getInsertTime();
		this.updateTime = dto.getUpdateTime();
		this.isVisable = dto.getIsVisable();
		this.activityType = dto.getActivityType();
		this.maximumNumber = dto.getMaximumNumber();
		this.bindRole = dto.getBindRole();
	}

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
	public void setOnlineTime(java.util.Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public java.util.Date getOnlineTime() {
		return this.onlineTime;
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
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
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
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setActivityType(java.lang.Integer activityType) {
		this.activityType = activityType;
	}
	
	public java.lang.Integer getActivityType() {
		return this.activityType;
	}
	public void setMaximumNumber(java.lang.Integer maximumNumber) {
		this.maximumNumber = maximumNumber;
	}
	
	public java.lang.Integer getMaximumNumber() {
		return this.maximumNumber;
	}
	public void setBindRole(java.lang.Integer bindRole) {
		this.bindRole = bindRole;
	}
	
	public java.lang.Integer getBindRole() {
		return this.bindRole;
	}
	

}