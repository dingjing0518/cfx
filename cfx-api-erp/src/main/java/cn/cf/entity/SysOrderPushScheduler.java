package cn.cf.entity;

import javax.persistence.Id;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysOrderPushScheduler  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	@Id
	private java.lang.String id;
	private java.lang.String childOrderNumber;
	private java.lang.String orderNumber;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer logisticsStatus;
	private java.lang.Integer erpStatus;
	private java.lang.String detail;
	private java.lang.String tokenPk;
	private java.lang.String method;
	private java.lang.String url;
	private java.lang.Integer times;
	
	
	
	//columns END

	public java.lang.Integer getTimes() {
		return times;
	}

	public void setTimes(java.lang.Integer times) {
		this.times = times;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	public java.lang.String getId() {
		return this.id;
	}
	public void setChildOrderNumber(java.lang.String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}
	
	public java.lang.String getChildOrderNumber() {
		return this.childOrderNumber;
	}
	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
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
	public void setLogisticsStatus(java.lang.Integer logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}
	
	public java.lang.Integer getLogisticsStatus() {
		return this.logisticsStatus;
	}
	public void setErpStatus(java.lang.Integer erpStatus) {
		this.erpStatus = erpStatus;
	}
	
	public java.lang.Integer getErpStatus() {
		return this.erpStatus;
	}
	public void setDetail(java.lang.String detail) {
		this.detail = detail;
	}
	
	public java.lang.String getDetail() {
		return this.detail;
	}

	public java.lang.String getTokenPk() {
		return tokenPk;
	}

	public void setTokenPk(java.lang.String tokenPk) {
		this.tokenPk = tokenPk;
	}

	public java.lang.String getMethod() {
		return method;
	}

	public void setMethod(java.lang.String method) {
		this.method = method;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	

}