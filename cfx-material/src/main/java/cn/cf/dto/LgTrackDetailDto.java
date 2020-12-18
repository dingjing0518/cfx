package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgTrackDetailDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String deliveryPk;
	private java.lang.Integer orderStatus;
	private java.lang.String stepDetail;
	private java.util.Date finishedTime;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setDeliveryPk(java.lang.String deliveryPk) {
		this.deliveryPk = deliveryPk;
	}
	
	public java.lang.String getDeliveryPk() {
		return this.deliveryPk;
	}
	public void setOrderStatus(java.lang.Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public java.lang.Integer getOrderStatus() {
		return this.orderStatus;
	}
	public void setStepDetail(java.lang.String stepDetail) {
		this.stepDetail = stepDetail;
	}
	
	public java.lang.String getStepDetail() {
		return this.stepDetail;
	}
	public void setFinishedTime(java.util.Date finishedTime) {
		this.finishedTime = finishedTime;
	}
	
	public java.util.Date getFinishedTime() {
		return this.finishedTime;
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
	

}