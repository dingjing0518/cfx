package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgRoleMenu  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String rolePk;
	private java.lang.String menuPk;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setRolePk(java.lang.String rolePk) {
		this.rolePk = rolePk;
	}
	
	public java.lang.String getRolePk() {
		return this.rolePk;
	}
	public void setMenuPk(java.lang.String menuPk) {
		this.menuPk = menuPk;
	}
	
	public java.lang.String getMenuPk() {
		return this.menuPk;
	}
	

}