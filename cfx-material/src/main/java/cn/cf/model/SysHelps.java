package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysHelps  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String title;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer isVisable;
	private java.lang.String content;
	private java.lang.Integer status;
	private java.lang.String helpCategoryPk;
	private java.lang.Integer sort;
	private java.lang.Integer showType;
	private java.lang.String showPlace;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	public java.lang.String getTitle() {
		return this.title;
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
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setHelpCategoryPk(java.lang.String helpCategoryPk) {
		this.helpCategoryPk = helpCategoryPk;
	}
	
	public java.lang.String getHelpCategoryPk() {
		return this.helpCategoryPk;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setShowType(java.lang.Integer showType) {
		this.showType = showType;
	}
	
	public java.lang.Integer getShowType() {
		return this.showType;
	}

	public java.lang.String getShowPlace() {
		return showPlace;
	}

	public void setShowPlace(java.lang.String showPlace) {
		this.showPlace = showPlace;
	}
	

}