package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgLinePriceDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String linePk;
	private java.lang.Double fromWeight;
	private java.lang.Double toWeight;
	private java.lang.Double basisPrice;
	private java.lang.Double salePrice;
	private java.lang.Integer isDelete;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setLinePk(java.lang.String linePk) {
		this.linePk = linePk;
	}
	
	public java.lang.String getLinePk() {
		return this.linePk;
	}
	public void setFromWeight(java.lang.Double fromWeight) {
		this.fromWeight = fromWeight;
	}
	
	public java.lang.Double getFromWeight() {
		return this.fromWeight;
	}
	public void setToWeight(java.lang.Double toWeight) {
		this.toWeight = toWeight;
	}
	
	public java.lang.Double getToWeight() {
		return this.toWeight;
	}
	public void setBasisPrice(java.lang.Double basisPrice) {
		this.basisPrice = basisPrice;
	}
	
	public java.lang.Double getBasisPrice() {
		return this.basisPrice;
	}
	public void setSalePrice(java.lang.Double salePrice) {
		this.salePrice = salePrice;
	}
	
	public java.lang.Double getSalePrice() {
		return this.salePrice;
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
	

}