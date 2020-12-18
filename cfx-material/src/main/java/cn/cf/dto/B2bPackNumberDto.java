package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bPackNumberDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.Integer bucketsNum;
	private java.lang.Double grainWeight;
	private java.lang.String packNumber;
	private java.lang.String batchNumber;
	private java.util.Date updateTime;
	private java.lang.Double netWeight;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.String storePk;
	private java.lang.String storeName;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setProductPk(java.lang.String productPk) {
		this.productPk = productPk;
	}
	
	public java.lang.String getProductPk() {
		return this.productPk;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setBucketsNum(java.lang.Integer bucketsNum) {
		this.bucketsNum = bucketsNum;
	}
	
	public java.lang.Integer getBucketsNum() {
		return this.bucketsNum;
	}
	public void setGrainWeight(java.lang.Double grainWeight) {
		this.grainWeight = grainWeight;
	}
	
	public java.lang.Double getGrainWeight() {
		return this.grainWeight;
	}
	public void setPackNumber(java.lang.String packNumber) {
		this.packNumber = packNumber;
	}
	
	public java.lang.String getPackNumber() {
		return this.packNumber;
	}
	public void setBatchNumber(java.lang.String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
	public java.lang.String getBatchNumber() {
		return this.batchNumber;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setNetWeight(java.lang.Double netWeight) {
		this.netWeight = netWeight;
	}
	
	public java.lang.Double getNetWeight() {
		return this.netWeight;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
	public java.lang.String getStoreName() {
		return this.storeName;
	}
	

}