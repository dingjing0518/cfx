package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLotteryAwardDto  extends BaseDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String activityPk;
	private java.lang.String name;
	private java.lang.Integer awardType;
	private java.lang.Integer awardVariety;
	private java.lang.String awardName;
	private java.lang.String relevancyPk;
	private java.lang.Integer amount;
	private java.lang.Double awardPercent;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.String awardRule;
	private java.lang.Integer status;
	private java.lang.Integer awardSort;
	private java.lang.Integer showSort;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.Integer packageNumber;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setActivityPk(java.lang.String activityPk) {
		this.activityPk = activityPk;
	}
	
	public java.lang.String getActivityPk() {
		return this.activityPk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setAwardType(java.lang.Integer awardType) {
		this.awardType = awardType;
	}
	
	public java.lang.Integer getAwardType() {
		return this.awardType;
	}
	public void setAwardVariety(java.lang.Integer awardVariety) {
		this.awardVariety = awardVariety;
	}
	
	public java.lang.Integer getAwardVariety() {
		return this.awardVariety;
	}
	public void setAwardName(java.lang.String awardName) {
		this.awardName = awardName;
	}
	
	public java.lang.String getAwardName() {
		return this.awardName;
	}
	public void setRelevancyPk(java.lang.String relevancyPk) {
		this.relevancyPk = relevancyPk;
	}
	
	public java.lang.String getRelevancyPk() {
		return this.relevancyPk;
	}
	public void setAmount(java.lang.Integer amount) {
		this.amount = amount;
	}
	
	public java.lang.Integer getAmount() {
		return this.amount;
	}
	public void setAwardPercent(java.lang.Double awardPercent) {
		this.awardPercent = awardPercent;
	}
	
	public java.lang.Double getAwardPercent() {
		return this.awardPercent;
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
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setAwardRule(java.lang.String awardRule) {
		this.awardRule = awardRule;
	}
	
	public java.lang.String getAwardRule() {
		return this.awardRule;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setAwardSort(java.lang.Integer awardSort) {
		this.awardSort = awardSort;
	}
	
	public java.lang.Integer getAwardSort() {
		return this.awardSort;
	}
	public void setShowSort(java.lang.Integer showSort) {
		this.showSort = showSort;
	}
	
	public java.lang.Integer getShowSort() {
		return this.showSort;
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
	public void setPackageNumber(java.lang.Integer packageNumber) {
		this.packageNumber = packageNumber;
	}
	
	public java.lang.Integer getPackageNumber() {
		return this.packageNumber;
	}
	

}