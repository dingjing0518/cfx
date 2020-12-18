package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bEconomicsCreditcustomerDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Integer source;
	private java.lang.String assidirPk;
	private java.lang.String assidirName;
	private java.lang.String productPks;
	private java.lang.Integer status;
	private java.util.Date staticTime;
	private java.lang.String goodsName;
	private java.lang.Integer assiFlag;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
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
	public void setSource(java.lang.Integer source) {
		this.source = source;
	}
	
	public java.lang.Integer getSource() {
		return this.source;
	}
	public void setAssidirPk(java.lang.String assidirPk) {
		this.assidirPk = assidirPk;
	}
	
	public java.lang.String getAssidirPk() {
		return this.assidirPk;
	}
	public void setAssidirName(java.lang.String assidirName) {
		this.assidirName = assidirName;
	}
	
	public java.lang.String getAssidirName() {
		return this.assidirName;
	}
	public void setProductPks(java.lang.String productPks) {
		this.productPks = productPks;
	}
	
	public java.lang.String getProductPks() {
		return this.productPks;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setStaticTime(java.util.Date staticTime) {
		this.staticTime = staticTime;
	}
	
	public java.util.Date getStaticTime() {
		return this.staticTime;
	}
	public void setGoodsName(java.lang.String goodsName) {
		this.goodsName = goodsName;
	}
	
	public java.lang.String getGoodsName() {
		return this.goodsName;
	}
	public void setAssiFlag(java.lang.Integer assiFlag) {
		this.assiFlag = assiFlag;
	}
	
	public java.lang.Integer getAssiFlag() {
		return this.assiFlag;
	}
	

}