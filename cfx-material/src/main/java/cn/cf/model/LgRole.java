package cn.cf.model;

import java.io.Serializable;

public class LgRole extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.String  pk;
	private java.lang.String name;
	private java.lang.String province;
	private java.lang.String provinceName;
	private java.lang.String city;
	private java.lang.String cityName;
	private java.lang.String area;
	private java.lang.String areaName;
	private java.lang.String town;
	private java.lang.String townName;
	private java.lang.Integer isDelete;
	private java.util.Date insertime; 
	private java.lang.Integer isVisable;
	private java.lang.String companyPk;
	public java.lang.String getPk() {
		return pk;
	}
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getProvince() {
		return province;
	}
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	public java.lang.String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	public java.lang.String getCity() {
		return city;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	public java.lang.String getCityName() {
		return cityName;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	public java.lang.String getArea() {
		return area;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	public java.lang.String getAreaName() {
		return areaName;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	public java.lang.String getTown() {
		return town;
	}
	public void setTown(java.lang.String town) {
		this.town = town;
	}
	public java.lang.String getTownName() {
		return townName;
	}
	public void setTownName(java.lang.String townName) {
		this.townName = townName;
	}
	public java.lang.Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	public java.util.Date getInsertime() {
		return insertime;
	}
	public void setInsertime(java.util.Date insertime) {
		this.insertime = insertime;
	}
	public java.lang.Integer getIsVisable() {
		return isVisable;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public java.lang.String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	//columns END
	
	
}
