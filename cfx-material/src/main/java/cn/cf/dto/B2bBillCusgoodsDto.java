package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillCusgoodsDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String customerPk;
	private java.lang.String companyPk;
	private java.lang.String goodsPk;
	private java.lang.String goodsName;
	private java.lang.String goodsShotName;
	private java.lang.Integer bindStatus;
	private java.lang.String account;
	private java.lang.String bankName;
	private java.lang.String bankNo;
	private java.lang.Integer isVisable;
	private java.lang.Double platformAmount;
	private java.lang.Double useAmount;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setCustomerPk(java.lang.String customerPk) {
		this.customerPk = customerPk;
	}
	
	public java.lang.String getCustomerPk() {
		return this.customerPk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	
	public java.lang.String getGoodsName() {
		return this.goodsName;
	}
	public void setGoodsShotName(java.lang.String goodsShotName) {
		this.goodsShotName = goodsShotName;
	}
	
	public java.lang.String getGoodsShotName() {
		return this.goodsShotName;
	}
	public void setBindStatus(java.lang.Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	
	public java.lang.Integer getBindStatus() {
		return this.bindStatus;
	}
	public void setAccount(java.lang.String account) {
		this.account = account;
	}
	
	public java.lang.String getAccount() {
		return this.account;
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
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setPlatformAmount(java.lang.Double platformAmount) {
		this.platformAmount = platformAmount;
	}
	
	public java.lang.Double getPlatformAmount() {
		return this.platformAmount;
	}
	public void setUseAmount(java.lang.Double useAmount) {
		this.useAmount = useAmount;
	}
	
	public java.lang.Double getUseAmount() {
		return this.useAmount;
	}
	

}