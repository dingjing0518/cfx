package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bTrancsation  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String storePk;
	private java.lang.String companyPk;
	private java.lang.Double orderAmounts;
	private java.lang.Double payAmounts;
	private java.lang.Integer orderCounts;
	private java.lang.Integer childOrderCounts;
	private java.lang.Integer payCounts;
	private java.lang.Integer childPayCounts;
	private java.lang.String trancsationDate;
	private java.lang.Integer isDelete;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setOrderAmounts(java.lang.Double orderAmounts) {
		this.orderAmounts = orderAmounts;
	}
	
	public java.lang.Double getOrderAmounts() {
		return this.orderAmounts;
	}
	public void setPayAmounts(java.lang.Double payAmounts) {
		this.payAmounts = payAmounts;
	}
	
	public java.lang.Double getPayAmounts() {
		return this.payAmounts;
	}
	public void setOrderCounts(java.lang.Integer orderCounts) {
		this.orderCounts = orderCounts;
	}
	
	public java.lang.Integer getOrderCounts() {
		return this.orderCounts;
	}
	public void setChildOrderCounts(java.lang.Integer childOrderCounts) {
		this.childOrderCounts = childOrderCounts;
	}
	
	public java.lang.Integer getChildOrderCounts() {
		return this.childOrderCounts;
	}
	public void setPayCounts(java.lang.Integer payCounts) {
		this.payCounts = payCounts;
	}
	
	public java.lang.Integer getPayCounts() {
		return this.payCounts;
	}
	public void setChildPayCounts(java.lang.Integer childPayCounts) {
		this.childPayCounts = childPayCounts;
	}
	
	public java.lang.Integer getChildPayCounts() {
		return this.childPayCounts;
	}
	
	public java.lang.String getTrancsationDate() {
		return trancsationDate;
	}

	public void setTrancsationDate(java.lang.String trancsationDate) {
		this.trancsationDate = trancsationDate;
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	

}