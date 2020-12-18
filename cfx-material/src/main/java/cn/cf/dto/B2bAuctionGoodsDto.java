package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bAuctionGoodsDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String goodsPk;
	private java.util.Date activityTime;
	private java.lang.String auctionPk;
	private java.lang.Double startingPrice;
	private java.lang.Double maximumPrice;
	private java.lang.Integer minimumBoxes;
	private java.lang.String startTime;
	private java.lang.String endTime;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.lang.Integer isDelete;
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
	public void setActivityTime(java.util.Date activityTime) {
		this.activityTime = activityTime;
	}
	
	public java.util.Date getActivityTime() {
		return this.activityTime;
	}
	public void setAuctionPk(java.lang.String auctionPk) {
		this.auctionPk = auctionPk;
	}
	
	public java.lang.String getAuctionPk() {
		return this.auctionPk;
	}
	public void setStartingPrice(java.lang.Double startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	public java.lang.Double getStartingPrice() {
		return this.startingPrice;
	}
	public void setMaximumPrice(java.lang.Double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}
	
	public java.lang.Double getMaximumPrice() {
		return this.maximumPrice;
	}
	public void setMinimumBoxes(java.lang.Integer minimumBoxes) {
		this.minimumBoxes = minimumBoxes;
	}
	
	public java.lang.Integer getMinimumBoxes() {
		return this.minimumBoxes;
	}
	public void setStartTime(java.lang.String startTime) {
		this.startTime = startTime;
	}
	
	public java.lang.String getStartTime() {
		return this.startTime;
	}
	public void setEndTime(java.lang.String endTime) {
		this.endTime = endTime;
	}
	
	public java.lang.String getEndTime() {
		return this.endTime;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	
	public java.lang.String getCompanyName() {
		return this.companyName;
	}
	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}
	
	public java.lang.String getStorePk() {
		return this.storePk;
	}
	public void setStoreName(java.lang.String storeName) {
		this.storeName = storeName;
	}
	
	public java.lang.String getStoreName() {
		return this.storeName;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public java.util.Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	

}