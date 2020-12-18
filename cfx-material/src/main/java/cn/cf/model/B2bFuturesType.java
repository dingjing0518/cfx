package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bFuturesType  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.Integer isVisable;
	private java.lang.Integer isHome;
	private java.lang.Integer sort;
	private java.util.Date updateTime;
	private java.lang.Integer isDelete;
	private java.lang.String block;
	private java.lang.String currencyUnit;
	private java.lang.String unit;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setIsHome(java.lang.Integer isHome) {
		this.isHome = isHome;
	}
	
	public java.lang.Integer getIsHome() {
		return this.isHome;
	}
	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public java.lang.String getBlock() {
		return block;
	}

	public void setBlock(java.lang.String block) {
		this.block = block;
	}

	public java.lang.String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(java.lang.String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public java.lang.String getUnit() {
		return unit;
	}

	public void setUnit(java.lang.String unit) {
		this.unit = unit;
	}
	

}