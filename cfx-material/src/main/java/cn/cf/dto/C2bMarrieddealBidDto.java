package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class C2bMarrieddealBidDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String marrieddealPk;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.String meno;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Double bidPrice;
	private java.lang.String supplierPk;
	private java.lang.String supplierName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.String packNumber;
	private java.lang.String batchNumber;
	private java.lang.Integer type;
	private java.lang.Integer bidStatus;
	private java.lang.String goodsPk;
	private java.lang.String goodsName;
	private java.lang.String storePk;
	private java.lang.String storeName;
	private java.lang.Integer isFinished;
	private java.lang.Integer bidBoxes;
	private java.lang.Double bidCount;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMarrieddealPk(java.lang.String marrieddealPk) {
		this.marrieddealPk = marrieddealPk;
	}
	
	public java.lang.String getMarrieddealPk() {
		return this.marrieddealPk;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setMemberName(java.lang.String memberName) {
		this.memberName = memberName;
	}
	
	public java.lang.String getMemberName() {
		return this.memberName;
	}
	public void setMeno(java.lang.String meno) {
		this.meno = meno;
	}
	
	public java.lang.String getMeno() {
		return this.meno;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setBidPrice(java.lang.Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	
	public java.lang.Double getBidPrice() {
		return this.bidPrice;
	}
	public void setSupplierPk(java.lang.String supplierPk) {
		this.supplierPk = supplierPk;
	}
	
	public java.lang.String getSupplierPk() {
		return this.supplierPk;
	}
	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}
	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}
	
	public java.lang.String getContacts() {
		return this.contacts;
	}
	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}
	
	public java.lang.String getContactsTel() {
		return this.contactsTel;
	}
	public void setPackNumber(java.lang.String packNumber) {
		this.packNumber = packNumber;
	}
	
	public java.lang.String getPackNumber() {
		return this.packNumber;
	}
	public void setBatchNumber(java.lang.String batchNumber) {
		this.batchNumber = batchNumber;
	}
	
	public java.lang.String getBatchNumber() {
		return this.batchNumber;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setBidStatus(java.lang.Integer bidStatus) {
		this.bidStatus = bidStatus;
	}
	
	public java.lang.Integer getBidStatus() {
		return this.bidStatus;
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
	public void setIsFinished(java.lang.Integer isFinished) {
		this.isFinished = isFinished;
	}
	
	public java.lang.Integer getIsFinished() {
		return this.isFinished;
	}
	public void setBidBoxes(java.lang.Integer bidBoxes) {
		this.bidBoxes = bidBoxes;
	}
	
	public java.lang.Integer getBidBoxes() {
		return this.bidBoxes;
	}
	public void setBidCount(java.lang.Double bidCount) {
		this.bidCount = bidCount;
	}
	
	public java.lang.Double getBidCount() {
		return this.bidCount;
	}
	

}