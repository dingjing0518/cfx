package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysCompanyBankcard  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String bankAccount;
	private java.lang.String bankName;
	private java.lang.String bankNo;
	private java.lang.String ecbankPk;
	private java.lang.String ecbankName;
	private java.lang.String bankclscode;
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
	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public java.lang.String getBankAccount() {
		return this.bankAccount;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}
	
	public java.lang.String getBankNo() {
		return this.bankNo;
	}
	public void setEcbankPk(java.lang.String ecbankPk) {
		this.ecbankPk = ecbankPk;
	}
	
	public java.lang.String getEcbankPk() {
		return this.ecbankPk;
	}
	public void setEcbankName(java.lang.String ecbankName) {
		this.ecbankName = ecbankName;
	}
	
	public java.lang.String getEcbankName() {
		return this.ecbankName;
	}

	public String getBankclscode() {
		return bankclscode;
	}

	public void setBankclscode(String bankclscode) {
		this.bankclscode = bankclscode;
	}
}