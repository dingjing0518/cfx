package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class C2bMarrieddealTrack  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String marrieddealId;
	private java.lang.String supplierPk;
	private java.lang.String supplierName;
	private java.lang.String purchaserPk;
	private java.lang.String purchaserName;
	private java.lang.String supplierContacts;
	private java.lang.String purchaserContacts;
	private java.lang.String supplierTel;
	private java.lang.String purchaserTel;
	private java.lang.String bidPk;
	private java.lang.Double bidPrice;
	private java.util.Date bidTime;
	private java.lang.String remark;
	private java.lang.Integer isConfirmed;
	private java.util.Date insertTime;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.Integer isDelete;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMarrieddealId(java.lang.String marrieddealId) {
		this.marrieddealId = marrieddealId;
	}
	
	public java.lang.String getMarrieddealId() {
		return this.marrieddealId;
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
	public void setPurchaserPk(java.lang.String purchaserPk) {
		this.purchaserPk = purchaserPk;
	}
	
	public java.lang.String getPurchaserPk() {
		return this.purchaserPk;
	}
	public void setPurchaserName(java.lang.String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	public java.lang.String getPurchaserName() {
		return this.purchaserName;
	}
	public void setSupplierContacts(java.lang.String supplierContacts) {
		this.supplierContacts = supplierContacts;
	}
	
	public java.lang.String getSupplierContacts() {
		return this.supplierContacts;
	}
	public void setPurchaserContacts(java.lang.String purchaserContacts) {
		this.purchaserContacts = purchaserContacts;
	}
	
	public java.lang.String getPurchaserContacts() {
		return this.purchaserContacts;
	}
	public void setSupplierTel(java.lang.String supplierTel) {
		this.supplierTel = supplierTel;
	}
	
	public java.lang.String getSupplierTel() {
		return this.supplierTel;
	}
	public void setPurchaserTel(java.lang.String purchaserTel) {
		this.purchaserTel = purchaserTel;
	}
	
	public java.lang.String getPurchaserTel() {
		return this.purchaserTel;
	}
	public void setBidPk(java.lang.String bidPk) {
		this.bidPk = bidPk;
	}
	
	public java.lang.String getBidPk() {
		return this.bidPk;
	}
	public void setBidPrice(java.lang.Double bidPrice) {
		this.bidPrice = bidPrice;
	}
	
	public java.lang.Double getBidPrice() {
		return this.bidPrice;
	}
	public void setBidTime(java.util.Date bidTime) {
		this.bidTime = bidTime;
	}
	
	public java.util.Date getBidTime() {
		return this.bidTime;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setIsConfirmed(java.lang.Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public java.lang.Integer getIsConfirmed() {
		return this.isConfirmed;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
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
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	

}