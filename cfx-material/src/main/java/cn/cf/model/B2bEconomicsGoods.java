package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bEconomicsGoods  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.String url;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer status;
	private java.lang.Integer isDelete;
	private java.lang.Integer goodsType;
	private java.lang.Double proportion;
	private java.lang.String qualityValue;
	private java.lang.String storeInfo;
	//columns END

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
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getUrl() {
		return this.url;
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
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setGoodsType(java.lang.Integer goodsType) {
		this.goodsType = goodsType;
	}
	
	public java.lang.Integer getGoodsType() {
		return this.goodsType;
	}
	public void setProportion(java.lang.Double proportion) {
		this.proportion = proportion;
	}
	
	public java.lang.Double getProportion() {
		return this.proportion;
	}
	public void setQualityValue(java.lang.String qualityValue) {
		this.qualityValue = qualityValue;
	}
	
	public java.lang.String getQualityValue() {
		return this.qualityValue;
	}

	public java.lang.String getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(java.lang.String storeInfo) {
		this.storeInfo = storeInfo;
	}
	

}