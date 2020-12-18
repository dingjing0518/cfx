package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgArayacakMandateDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String userPk;
	private java.lang.String userMobile;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String mandateName;
	private java.lang.String mandateUrl;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer delStatus;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setUserPk(java.lang.String userPk) {
		this.userPk = userPk;
	}
	
	public java.lang.String getUserPk() {
		return this.userPk;
	}
	public void setUserMobile(java.lang.String userMobile) {
		this.userMobile = userMobile;
	}
	
	public java.lang.String getUserMobile() {
		return this.userMobile;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	
	public java.lang.String getCompanyName() {
		return this.companyName;
	}
	public void setMandateName(java.lang.String mandateName) {
		this.mandateName = mandateName;
	}
	
	public java.lang.String getMandateName() {
		return this.mandateName;
	}
	public void setMandateUrl(java.lang.String mandateUrl) {
		this.mandateUrl = mandateUrl;
	}
	
	public java.lang.String getMandateUrl() {
		return this.mandateUrl;
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
	public void setDelStatus(java.lang.Integer delStatus) {
		this.delStatus = delStatus;
	}
	
	public java.lang.Integer getDelStatus() {
		return this.delStatus;
	}
	

}