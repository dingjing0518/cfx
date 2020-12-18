package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class MarketingOrderMember  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderPk;
	private java.lang.Integer type;
	private java.lang.String storePk;
	private java.lang.String purchaserPk;
	private java.lang.String purProvince;
	private java.lang.String purCity;
	private java.lang.String purArea;
	private java.lang.String purAddress;
	private java.lang.String stProvince;
	private java.lang.String stCity;
	private java.lang.String stArea;
	private java.lang.String stAddress;
	private java.lang.String purAccount;
	private java.lang.String stAccount;



	//columns END

	public void setOrderPk(java.lang.String orderPk) {
		this.orderPk = orderPk;
	}
	
	public java.lang.String getOrderPk() {
		return this.orderPk;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setPurProvince(java.lang.String purProvince) {
		this.purProvince = purProvince;
	}
	
	public java.lang.String getPurProvince() {
		return this.purProvince;
	}
	public void setPurCity(java.lang.String purCity) {
		this.purCity = purCity;
	}
	
	public java.lang.String getPurCity() {
		return this.purCity;
	}
	public void setPurArea(java.lang.String purArea) {
		this.purArea = purArea;
	}
	
	public java.lang.String getPurArea() {
		return this.purArea;
	}
	public void setPurAddress(java.lang.String purAddress) {
		this.purAddress = purAddress;
	}
	
	public java.lang.String getPurAddress() {
		return this.purAddress;
	}
	public void setStProvince(java.lang.String stProvince) {
		this.stProvince = stProvince;
	}
	
	public java.lang.String getStProvince() {
		return this.stProvince;
	}
	public void setStCity(java.lang.String stCity) {
		this.stCity = stCity;
	}
	
	public java.lang.String getStCity() {
		return this.stCity;
	}
	public void setStArea(java.lang.String stArea) {
		this.stArea = stArea;
	}
	
	public java.lang.String getStArea() {
		return this.stArea;
	}
	public void setStAddress(java.lang.String stAddress) {
		this.stAddress = stAddress;
	}
	
	public java.lang.String getStAddress() {
		return this.stAddress;
	}

    public java.lang.String getPurAccount() {
        return purAccount;
    }

    public void setPurAccount(java.lang.String purAccount) {
        this.purAccount = purAccount;
    }

    public java.lang.String getStAccount() {
        return stAccount;
    }

    public void setStAccount(java.lang.String stAccount) {
        this.stAccount = stAccount;
    }
	

}