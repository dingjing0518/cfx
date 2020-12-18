package cn.cf.dto;

import java.util.List;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLotteryActivityDtoEx  implements java.io.Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.String detail;
	private java.lang.Integer isDelete;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer isVisable;
	private java.lang.Integer activityType;
	private java.lang.Integer maximumNumber;
	private java.lang.String awardrule;
	private java.lang.String activityRule;
	private java.lang.Integer bindRole;
	
	
	
	private String ruleDescription;
	private List<B2bLotteryAwardDto> awards;
	private List<B2bLotteryRecordDto> records;
	private List<B2bLotteryAwardDto> awardAll;
	private Integer luckyNumber;
	private Integer creditStatus;
	
	
 
	public Integer getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(Integer creditStatus) {
		this.creditStatus = creditStatus;
	}
	public java.lang.String getPk() {
		return pk;
	}
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public java.lang.String getDetail() {
		return detail;
	}
	public void setDetail(java.lang.String detail) {
		this.detail = detail;
	}
	public java.lang.Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	public java.util.Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	public java.lang.Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	public java.lang.Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(java.lang.Integer activityType) {
		this.activityType = activityType;
	}
	public java.lang.Integer getMaximumNumber() {
		return maximumNumber;
	}
	public void setMaximumNumber(java.lang.Integer maximumNumber) {
		this.maximumNumber = maximumNumber;
	}
	public java.lang.String getAwardrule() {
		return awardrule;
	}
	public void setAwardrule(java.lang.String awardrule) {
		this.awardrule = awardrule;
	}
	public java.lang.String getActivityRule() {
		return activityRule;
	}
	public void setActivityRule(java.lang.String activityRule) {
		this.activityRule = activityRule;
	}
	public java.lang.Integer getBindRole() {
		return bindRole;
	}
	public void setBindRole(java.lang.Integer bindRole) {
		this.bindRole = bindRole;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	
	 
	public List<B2bLotteryAwardDto> getAwards() {
		return awards;
	}
	public void setAwards(List<B2bLotteryAwardDto> awards) {
		this.awards = awards;
	}
	public List<B2bLotteryRecordDto> getRecords() {
		return records;
	}
	public void setRecords(List<B2bLotteryRecordDto> records) {
		this.records = records;
	}
	public List<B2bLotteryAwardDto> getAwardAll() {
		return awardAll;
	}
	public void setAwardAll(List<B2bLotteryAwardDto> awardAll) {
		this.awardAll = awardAll;
	}
	public Integer getLuckyNumber() {
		return luckyNumber;
	}
	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}
	

}