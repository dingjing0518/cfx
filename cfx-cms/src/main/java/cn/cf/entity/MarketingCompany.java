package cn.cf.entity;

public class MarketingCompany {
	private String pk;
	private String personnelPk;
	private String accountPk;
	private String companyPk;
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getPersonnelPk() {
		return personnelPk;
	}
	public void setPersonnelPk(String personnelPk) {
		this.personnelPk = personnelPk;
	}
	public String getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
}
