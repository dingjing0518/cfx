package cn.cf.dto;

import java.io.Serializable;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgMemberDto  extends BaseDTO implements Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String mobile;
	private java.lang.String password;
	private java.lang.String companyName;
	private java.util.Date insertTime;
	private java.lang.Integer isDelete;
	private java.lang.Integer auditStatus;
	private java.util.Date updateTime;
	private java.lang.String rememberToken;
	private java.util.Date loginTime;
	private java.lang.String headPortrait;
	private java.lang.String parantPk;
	private java.lang.Integer isVisable;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	
	public java.lang.String getCompanyName() {
		return this.companyName;
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
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setRememberToken(java.lang.String rememberToken) {
		this.rememberToken = rememberToken;
	}
	
	public java.lang.String getRememberToken() {
		return this.rememberToken;
	}
	public void setLoginTime(java.util.Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public java.util.Date getLoginTime() {
		return this.loginTime;
	}
	public void setHeadPortrait(java.lang.String headPortrait) {
		this.headPortrait = headPortrait;
	}
	
	public java.lang.String getHeadPortrait() {
		return this.headPortrait;
	}

	public java.lang.String getParantPk() {
		return parantPk;
	}

	public void setParantPk(java.lang.String parantPk) {
		this.parantPk = parantPk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.lang.Integer getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	

}