package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bDimensionalityRelevancyDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.Integer dimenCategory;
	private java.lang.String dimenName;
	private java.lang.Integer dimenType;
	private java.lang.String dimenTypeName;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String linkUrl;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setDimenCategory(java.lang.Integer dimenCategory) {
		this.dimenCategory = dimenCategory;
	}
	
	public java.lang.Integer getDimenCategory() {
		return this.dimenCategory;
	}
	public void setDimenName(java.lang.String dimenName) {
		this.dimenName = dimenName;
	}
	
	public java.lang.String getDimenName() {
		return this.dimenName;
	}
	public void setDimenType(java.lang.Integer dimenType) {
		this.dimenType = dimenType;
	}
	
	public java.lang.Integer getDimenType() {
		return this.dimenType;
	}
	public void setDimenTypeName(java.lang.String dimenTypeName) {
		this.dimenTypeName = dimenTypeName;
	}
	
	public java.lang.String getDimenTypeName() {
		return this.dimenTypeName;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
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
	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public java.lang.String getLinkUrl() {
		return this.linkUrl;
	}
	

}