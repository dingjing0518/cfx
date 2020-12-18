package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgLogisticsTrackingDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.util.Date updateTime;
	private java.lang.String provinceName;
	private java.lang.String provincePk;
	private java.lang.String cityName;
	private java.lang.String cityPk;
	private java.lang.String areaName;
	private java.lang.String areaPk;
	private java.lang.String arrivalTime;
	private java.lang.String meno;
	private java.lang.String orderPk;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setProvincePk(java.lang.String provincePk) {
		this.provincePk = provincePk;
	}
	
	public java.lang.String getProvincePk() {
		return this.provincePk;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setCityPk(java.lang.String cityPk) {
		this.cityPk = cityPk;
	}
	
	public java.lang.String getCityPk() {
		return this.cityPk;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	public void setAreaPk(java.lang.String areaPk) {
		this.areaPk = areaPk;
	}
	
	public java.lang.String getAreaPk() {
		return this.areaPk;
	}
	public void setArrivalTime(java.lang.String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public java.lang.String getArrivalTime() {
		return this.arrivalTime;
	}
	public void setMeno(java.lang.String meno) {
		this.meno = meno;
	}
	
	public java.lang.String getMeno() {
		return this.meno;
	}
	public void setOrderPk(java.lang.String orderPk) {
		this.orderPk = orderPk;
	}
	
	public java.lang.String getOrderPk() {
		return this.orderPk;
	}
	

}