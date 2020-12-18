package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bLotteryActivitySource  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	private java.lang.String pk;
	private java.lang.String activityPk;
	private java.lang.String sourcePk;
	private java.lang.String sourceName;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	//columns END
	public java.lang.String getPk() {
		return pk;
	}
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	public java.lang.String getActivityPk() {
		return activityPk;
	}
	public void setActivityPk(java.lang.String activityPk) {
		this.activityPk = activityPk;
	}
	public java.lang.String getSourcePk() {
		return sourcePk;
	}
	public void setSourcePk(java.lang.String sourcePk) {
		this.sourcePk = sourcePk;
	}
	public java.lang.String getSourceName() {
		return sourceName;
	}
	public void setSourceName(java.lang.String sourceName) {
		this.sourceName = sourceName;
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
	public java.lang.Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	public java.lang.Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	

}