package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysRegions  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.String parentPk;
	private java.lang.Integer isDelete;
	private java.lang.String isVisable;
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
	public void setParentPk(java.lang.String parentPk) {
		this.parentPk = parentPk;
	}
	
	public java.lang.String getParentPk() {
		return this.parentPk;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setIsVisable(java.lang.String isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.String getIsVisable() {
		return this.isVisable;
	}
	

}