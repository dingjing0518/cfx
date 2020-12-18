package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillCusgoodsApply  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String customerPk;
	private java.lang.String companyPk;
	private java.lang.String goodsPk;
	private java.lang.String goodsName;
	private java.lang.String goodsShotName;
	private java.lang.Integer status;
	private java.lang.Integer isVisable;
	private java.lang.Double suggestAmount;
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
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setSuggestAmount(java.lang.Double suggestAmount) {
		this.suggestAmount = suggestAmount;
	}
	
	public java.lang.Double getSuggestAmount() {
		return this.suggestAmount;
	}
	

}