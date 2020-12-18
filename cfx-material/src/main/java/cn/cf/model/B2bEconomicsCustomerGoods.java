package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bEconomicsCustomerGoods  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String economicsGoodsPk;
	private java.lang.String economicsGoodsName;
	private java.lang.Integer goodsType;
	private java.lang.Double suggestAmount;
	private java.lang.String bankPk;
	private java.lang.String bankName;
	private java.util.Date effectStartTime;
	private java.util.Date effectEndTime;
	private java.lang.String economicsCustomerPk;
	private java.lang.Double totalRate;
	private java.lang.Double bankRate;
	private java.lang.Integer term;
	private java.lang.Double sevenRate;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
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
	public void setSuggestAmount(java.lang.Double suggestAmount) {
		this.suggestAmount = suggestAmount;
	}
	
	public java.lang.Double getSuggestAmount() {
		return this.suggestAmount;
	}
	public void setBankPk(java.lang.String bankPk) {
		this.bankPk = bankPk;
	}
	
	public java.lang.String getBankPk() {
		return this.bankPk;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setEffectStartTime(java.util.Date effectStartTime) {
		this.effectStartTime = effectStartTime;
	}
	
	public java.util.Date getEffectStartTime() {
		return this.effectStartTime;
	}
	public void setEffectEndTime(java.util.Date effectEndTime) {
		this.effectEndTime = effectEndTime;
	}
	
	public java.util.Date getEffectEndTime() {
		return this.effectEndTime;
	}

	public java.lang.String getEconomicsCustomerPk() {
		return economicsCustomerPk;
	}

	public void setEconomicsCustomerPk(java.lang.String economicsCustomerPk) {
		this.economicsCustomerPk = economicsCustomerPk;
	}

	public java.lang.Double getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(java.lang.Double totalRate) {
		this.totalRate = totalRate;
	}

	public java.lang.Double getBankRate() {
		return bankRate;
	}

	public void setBankRate(java.lang.Double bankRate) {
		this.bankRate = bankRate;
	}

	public java.lang.Integer getTerm() {
		return term;
	}

	public void setTerm(java.lang.Integer term) {
		this.term = term;
	}

	public java.lang.Double getSevenRate() {
		return sevenRate;
	}

	public void setSevenRate(java.lang.Double sevenRate) {
		this.sevenRate = sevenRate;
	}
	
	
	

}