package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bEconomicsBank  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String bankName;
	private java.lang.String gateway;
	private java.lang.Double creditTotalAmount;
	private java.lang.Integer isVisable;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	public void setGateway(java.lang.String gateway) {
		this.gateway = gateway;
	}
	
	public java.lang.String getGateway() {
		return this.gateway;
	}
	public void setCreditTotalAmount(java.lang.Double creditTotalAmount) {
		this.creditTotalAmount = creditTotalAmount;
	}
	
	public java.lang.Double getCreditTotalAmount() {
		return this.creditTotalAmount;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	

}