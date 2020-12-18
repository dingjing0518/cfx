package cn.cf.dto;

import java.io.Serializable;

public class LgMemberRoleDto extends BaseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String pk;
	private java.lang.String memberPk;
	private java.lang.String rolePk;

	public java.lang.String getPk() {
		return pk;
	}
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	public java.lang.String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	public java.lang.String getRolePk() {
		return rolePk;
	}
	public void setRolePk(java.lang.String rolePk) {
		this.rolePk = rolePk;
	}


}
