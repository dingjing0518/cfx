package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBind  extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String bindName;
	private java.lang.String bindProductID;
	private java.lang.Integer bindProductCount;
	private java.lang.String bindReason;
	private java.lang.String bindProductDetails;
	private java.lang.Double bindProductPrice;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.util.Date updateTime;
	private java.util.Date insertTime;
	private java.lang.Integer status;
	private java.lang.Integer isUpDown;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setBindName(java.lang.String bindName) {
		this.bindName = bindName;
	}
	
	public java.lang.String getBindName() {
		return this.bindName;
	}
	
	
	public java.lang.String getBindProductID() {
		return bindProductID;
	}

	public void setBindProductID(java.lang.String bindProductID) {
		this.bindProductID = bindProductID;
	}

	public void setBindProductCount(java.lang.Integer bindProductCount) {
		this.bindProductCount = bindProductCount;
	}
	
	public java.lang.Integer getBindProductCount() {
		return this.bindProductCount;
	}
	public void setBindReason(java.lang.String bindReason) {
		this.bindReason = bindReason;
	}
	
	public java.lang.String getBindReason() {
		return this.bindReason;
	}
	public void setBindProductDetails(java.lang.String bindProductDetails) {
		this.bindProductDetails = bindProductDetails;
	}
	
	public java.lang.String getBindProductDetails() {
		return this.bindProductDetails;
	}
	public void setBindProductPrice(java.lang.Double bindProductPrice) {
		this.bindProductPrice = bindProductPrice;
	}
	
	public java.lang.Double getBindProductPrice() {
		return this.bindProductPrice;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	
	public java.lang.String getStoreName() {
		return storeName;
	}

	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setIsUpDown(java.lang.Integer isUpDown) {
		this.isUpDown = isUpDown;
	}
	
	public java.lang.Integer getIsUpDown() {
		return this.isUpDown;
	}
	

}