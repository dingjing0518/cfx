package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgLogisticsInvoices  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String month;
	private java.lang.Double billAccount;
	private java.lang.Integer orderCount;
	private java.util.Date applyTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
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
	public void setMonth(java.lang.String month) {
		this.month = month;
	}
	
	public java.lang.String getMonth() {
		return this.month;
	}
	public void setBillAccount(java.lang.Double billAccount) {
		this.billAccount = billAccount;
	}
	
	public java.lang.Double getBillAccount() {
		return this.billAccount;
	}
	public void setOrderCount(java.lang.Integer orderCount) {
		this.orderCount = orderCount;
	}
	
	public java.lang.Integer getOrderCount() {
		return this.orderCount;
	}
	public void setApplyTime(java.util.Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public java.util.Date getApplyTime() {
		return this.applyTime;
	}
	

}