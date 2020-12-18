package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCreditGoodsDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String creditPk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String economicsGoodsPk;
	private java.lang.String economicsGoodsName;
	private java.lang.Integer goodsType;
	private java.util.Date creditStartTime;
	private java.util.Date creditEndTime;
	private java.util.Date creditInsertTime;
	private java.util.Date creditAuditTime;
	private java.lang.Double platformAmount;
	private java.lang.Double suggestAmount;
	private java.lang.Double pledgeAmount;
	private java.lang.Double pledgeUsedAmount;
	private java.lang.Integer status;
	private java.lang.Double totalRate;
	private java.lang.Double bankRate;
	private java.lang.Integer term;
	private java.lang.Double sevenRate;
	private java.lang.String bankPk;
	private java.lang.String bank;
	private java.lang.String bankAccountNumber;
	private java.lang.Integer isVisable;
	private  String agreementNumber;
	//columns END

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setCreditPk(java.lang.String creditPk) {
		this.creditPk = creditPk;
	}
	
	public java.lang.String getCreditPk() {
		return this.creditPk;
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
	public void setEconomicsGoodsPk(java.lang.String economicsGoodsPk) {
		this.economicsGoodsPk = economicsGoodsPk;
	}
	
	public java.lang.String getEconomicsGoodsPk() {
		return this.economicsGoodsPk;
	}
	public void setEconomicsGoodsName(java.lang.String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}
	
	public java.lang.String getEconomicsGoodsName() {
		return this.economicsGoodsName;
	}
	public void setGoodsType(java.lang.Integer goodsType) {
		this.goodsType = goodsType;
	}
	
	public java.lang.Integer getGoodsType() {
		return this.goodsType;
	}
	public void setCreditStartTime(java.util.Date creditStartTime) {
		this.creditStartTime = creditStartTime;
	}
	
	public java.util.Date getCreditStartTime() {
		return this.creditStartTime;
	}
	public void setCreditEndTime(java.util.Date creditEndTime) {
		this.creditEndTime = creditEndTime;
	}
	
	public java.util.Date getCreditEndTime() {
		return this.creditEndTime;
	}
	public void setCreditInsertTime(java.util.Date creditInsertTime) {
		this.creditInsertTime = creditInsertTime;
	}
	
	public java.util.Date getCreditInsertTime() {
		return this.creditInsertTime;
	}
	public void setCreditAuditTime(java.util.Date creditAuditTime) {
		this.creditAuditTime = creditAuditTime;
	}
	
	public java.util.Date getCreditAuditTime() {
		return this.creditAuditTime;
	}
	public void setPlatformAmount(java.lang.Double platformAmount) {
		this.platformAmount = platformAmount;
	}
	
	public java.lang.Double getPlatformAmount() {
		return this.platformAmount;
	}
	public void setSuggestAmount(java.lang.Double suggestAmount) {
		this.suggestAmount = suggestAmount;
	}
	
	public java.lang.Double getSuggestAmount() {
		return this.suggestAmount;
	}
	public void setPledgeAmount(java.lang.Double pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	
	public java.lang.Double getPledgeAmount() {
		return this.pledgeAmount;
	}
	public void setPledgeUsedAmount(java.lang.Double pledgeUsedAmount) {
		this.pledgeUsedAmount = pledgeUsedAmount;
	}
	
	public java.lang.Double getPledgeUsedAmount() {
		return this.pledgeUsedAmount;
	}
	
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setTotalRate(java.lang.Double totalRate) {
		this.totalRate = totalRate;
	}
	
	public java.lang.Double getTotalRate() {
		return this.totalRate;
	}
	public void setBankRate(java.lang.Double bankRate) {
		this.bankRate = bankRate;
	}
	
	public java.lang.Double getBankRate() {
		return this.bankRate;
	}
	public void setTerm(java.lang.Integer term) {
		this.term = term;
	}
	
	public java.lang.Integer getTerm() {
		return this.term;
	}
	public void setSevenRate(java.lang.Double sevenRate) {
		this.sevenRate = sevenRate;
	}
	
	public java.lang.Double getSevenRate() {
		return this.sevenRate;
	}
	public void setBankPk(java.lang.String bankPk) {
		this.bankPk = bankPk;
	}
	
	public java.lang.String getBankPk() {
		return this.bankPk;
	}
	public void setBank(java.lang.String bank) {
		this.bank = bank;
	}
	
	public java.lang.String getBank() {
		return this.bank;
	}
	public void setBankAccountNumber(java.lang.String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	public java.lang.String getBankAccountNumber() {
		return this.bankAccountNumber;
	}

	public java.lang.Integer getIsVisable() {
		return isVisable;
	}

	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}


}