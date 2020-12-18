package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysSmsRole  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String smsName;
	private java.lang.String rolePk;
	private java.lang.Integer type;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setSmsName(java.lang.String smsName) {
		this.smsName = smsName;
	}
	
	public java.lang.String getSmsName() {
		return this.smsName;
	}
	public void setRolePk(java.lang.String rolePk) {
		this.rolePk = rolePk;
	}
	
	public java.lang.String getRolePk() {
		return this.rolePk;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	

}