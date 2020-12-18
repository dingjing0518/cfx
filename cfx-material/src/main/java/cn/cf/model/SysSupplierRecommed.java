package cn.cf.model;

import java.util.Date;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 * */
public class SysSupplierRecommed  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;


	private java.lang.String pk;
	private java.lang.String storeName;
	private java.lang.String storePk;
	private java.lang.Integer isRecommed;
	private java.lang.String url;
	private java.lang.Integer sort;
	private java.lang.Integer isDelete;
	private java.lang.Integer isOnline;
	private java.util.Date insertTime;
	private java.lang.String position;
	private java.lang.String linkUrl;
	private java.lang.String block;
	private java.lang.String brandPk;
	private java.lang.String brandName;
	private java.lang.String platform;
	private java.util.Date updateTime;
	private java.lang.String region;
	private java.lang.String abbreviated;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}

	public java.lang.String getPk() {
		return this.pk;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}

	public java.lang.String getStoreName() {
		return this.storeName;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}

	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setIsRecommed(java.lang.Integer isRecommed) {
		this.isRecommed = isRecommed;
	}

	public java.lang.Integer getIsRecommed() {
		return this.isRecommed;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getUrl() {
		return this.url;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}

	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsOnline(java.lang.Integer isOnline) {
		this.isOnline = isOnline;
	}

	public java.lang.Integer getIsOnline() {
		return this.isOnline;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}

	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setPosition(java.lang.String position) {
		this.position = position;
	}

	public java.lang.String getPosition() {
		return this.position;
	}
	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public java.lang.String getLinkUrl() {
		return this.linkUrl;
	}
	public void setBlock(java.lang.String block) {
		this.block = block;
	}

	public java.lang.String getBlock() {
		return this.block;
	}
	public void setBrandPk(java.lang.String brandPk) {
		this.brandPk = brandPk;
	}

	public java.lang.String getBrandPk() {
		return this.brandPk;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}

	public java.lang.String getBrandName() {
		return this.brandName;
	}
	public void setPlatform(java.lang.String platform) {
		this.platform = platform;
	}

	public java.lang.String getPlatform() {
		return this.platform;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setRegion(java.lang.String region) {
		this.region = region;
	}

	public java.lang.String getRegion() {
		return this.region;
	}

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}
}