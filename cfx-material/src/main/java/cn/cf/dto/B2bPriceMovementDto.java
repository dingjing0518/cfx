package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bPriceMovementDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String goodsPk;
	private java.lang.Double price;
	private java.lang.Double yesterdayPrice;
	private java.util.Date date;
	private java.lang.Integer sort;
	private java.util.Date updateTime;
	private java.lang.Integer isHome;
	private java.lang.Integer isDelete;
	private java.lang.String block;
	private java.util.Date insertTime;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	
	public java.lang.Double getPrice() {
		return this.price;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	
	public java.util.Date getDate() {
		return this.date;
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
	public void setIsHome(java.lang.Integer isHome) {
		this.isHome = isHome;
	}
	
	public java.lang.Integer getIsHome() {
		return this.isHome;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public java.lang.Double getYesterdayPrice() {
		return yesterdayPrice;
	}

	public void setYesterdayPrice(java.lang.Double yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}

	public java.lang.String getBlock() {
		return block;
	}

	public void setBlock(java.lang.String block) {
		this.block = block;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}