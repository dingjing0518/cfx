package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SxHomeThirdnavigation  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String secondNavigationPk;
	private java.lang.String navigation;
	private java.lang.String navigationPk;
	private java.lang.Integer sort;
	private java.lang.Integer isVisable;
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
	public void setSecondNavigationPk(java.lang.String secondNavigationPk) {
		this.secondNavigationPk = secondNavigationPk;
	}
	
	public java.lang.String getSecondNavigationPk() {
		return this.secondNavigationPk;
	}
	public void setNavigation(java.lang.String navigation) {
		this.navigation = navigation;
	}
	
	public java.lang.String getNavigation() {
		return this.navigation;
	}
	public void setNavigationPk(java.lang.String navigationPk) {
		this.navigationPk = navigationPk;
	}
	
	public java.lang.String getNavigationPk() {
		return this.navigationPk;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public java.util.Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	

}