package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class MarketingCompany  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String personnelPk;
	private java.lang.String accountPk;
	private java.lang.String companyPk;
	private java.lang.Integer companyType;
	
	private java.util.Date distributeMemberTime;
	
	
	
	//columns END

	public java.util.Date getDistributeMemberTime() {
		return distributeMemberTime;
	}

	public void setDistributeMemberTime(java.util.Date distributeMemberTime) {
		this.distributeMemberTime = distributeMemberTime;
	}

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setPersonnelPk(java.lang.String personnelPk) {
		this.personnelPk = personnelPk;
	}
	
	public java.lang.String getPersonnelPk() {
		return this.personnelPk;
	}
	public void setAccountPk(java.lang.String accountPk) {
		this.accountPk = accountPk;
	}
	
	public java.lang.String getAccountPk() {
		return this.accountPk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setCompanyType(java.lang.Integer companyType) {
		this.companyType = companyType;
	}
	
	public java.lang.Integer getCompanyType() {
		return this.companyType;
	}
	

}