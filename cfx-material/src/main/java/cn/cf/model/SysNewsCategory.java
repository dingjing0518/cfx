package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysNewsCategory  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String newsPk;
	private java.lang.String categoryPk;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setNewsPk(java.lang.String newsPk) {
		this.newsPk = newsPk;
	}
	
	public java.lang.String getNewsPk() {
		return this.newsPk;
	}
	public void setCategoryPk(java.lang.String categoryPk) {
		this.categoryPk = categoryPk;
	}
	
	public java.lang.String getCategoryPk() {
		return this.categoryPk;
	}
	

}