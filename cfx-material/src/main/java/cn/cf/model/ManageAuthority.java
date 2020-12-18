package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class ManageAuthority  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String parentPk;
	private java.lang.String name;
	private java.lang.Integer type;
	private java.lang.String displayName;
	private java.lang.String url;
	private java.lang.Integer sort;
	private java.lang.Integer isDelete;
	private java.lang.String image;
	private java.lang.Integer isBtn;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setParentPk(java.lang.String parentPk) {
		this.parentPk = parentPk;
	}
	
	public java.lang.String getParentPk() {
		return this.parentPk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setDisplayName(java.lang.String displayName) {
		this.displayName = displayName;
	}
	
	public java.lang.String getDisplayName() {
		return this.displayName;
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
	public void setImage(java.lang.String image) {
		this.image = image;
	}
	
	public java.lang.String getImage() {
		return this.image;
	}
	public void setIsBtn(java.lang.Integer isBtn) {
		this.isBtn = isBtn;
	}
	
	public java.lang.Integer getIsBtn() {
		return this.isBtn;
	}
	

}