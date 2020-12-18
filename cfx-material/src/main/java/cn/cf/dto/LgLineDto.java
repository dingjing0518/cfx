package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgLineDto  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.String companyPk;
	private java.lang.Integer status;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.String gradePk;
	private java.lang.String gradeName;
	private java.lang.String fromProvicePk;
	private java.lang.String fromProviceName;
	private java.lang.String fromCityPk;
	private java.lang.String fromCityName;
	private java.lang.String fromAreaPk;
	private java.lang.String fromAreaName;
	private java.lang.String toProvicePk;
	private java.lang.String toProviceName;
	private java.lang.String toCityPk;
	private java.lang.String toCityName;
	private java.lang.String toAreaPk;
	private java.lang.String toAreaName;
	private java.lang.Integer isDelete;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Double leastWeight;
	private java.lang.Double freight;
	private java.lang.Double basicPrice;
	private java.lang.String fromTownPk;
	private java.lang.String fromTownName;
	private java.lang.String toTownPk;
	private java.lang.String toTownName;
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
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setProductPk(java.lang.String productPk) {
		this.productPk = productPk;
	}
	
	public java.lang.String getProductPk() {
		return this.productPk;
	}
	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}
	
	public java.lang.String getProductName() {
		return this.productName;
	}
	public void setGradePk(java.lang.String gradePk) {
		this.gradePk = gradePk;
	}
	
	public java.lang.String getGradePk() {
		return this.gradePk;
	}
	public void setGradeName(java.lang.String gradeName) {
		this.gradeName = gradeName;
	}
	
	public java.lang.String getGradeName() {
		return this.gradeName;
	}
	public void setFromProvicePk(java.lang.String fromProvicePk) {
		this.fromProvicePk = fromProvicePk;
	}
	
	public java.lang.String getFromProvicePk() {
		return this.fromProvicePk;
	}
	public void setFromProviceName(java.lang.String fromProviceName) {
		this.fromProviceName = fromProviceName;
	}
	
	public java.lang.String getFromProviceName() {
		return this.fromProviceName;
	}
	public void setFromCityPk(java.lang.String fromCityPk) {
		this.fromCityPk = fromCityPk;
	}
	
	public java.lang.String getFromCityPk() {
		return this.fromCityPk;
	}
	public void setFromCityName(java.lang.String fromCityName) {
		this.fromCityName = fromCityName;
	}
	
	public java.lang.String getFromCityName() {
		return this.fromCityName;
	}
	public void setFromAreaPk(java.lang.String fromAreaPk) {
		this.fromAreaPk = fromAreaPk;
	}
	
	public java.lang.String getFromAreaPk() {
		return this.fromAreaPk;
	}
	public void setFromAreaName(java.lang.String fromAreaName) {
		this.fromAreaName = fromAreaName;
	}
	
	public java.lang.String getFromAreaName() {
		return this.fromAreaName;
	}
	public void setToProvicePk(java.lang.String toProvicePk) {
		this.toProvicePk = toProvicePk;
	}
	
	public java.lang.String getToProvicePk() {
		return this.toProvicePk;
	}
	public void setToProviceName(java.lang.String toProviceName) {
		this.toProviceName = toProviceName;
	}
	
	public java.lang.String getToProviceName() {
		return this.toProviceName;
	}
	public void setToCityPk(java.lang.String toCityPk) {
		this.toCityPk = toCityPk;
	}
	
	public java.lang.String getToCityPk() {
		return this.toCityPk;
	}
	public void setToCityName(java.lang.String toCityName) {
		this.toCityName = toCityName;
	}
	
	public java.lang.String getToCityName() {
		return this.toCityName;
	}
	public void setToAreaPk(java.lang.String toAreaPk) {
		this.toAreaPk = toAreaPk;
	}
	
	public java.lang.String getToAreaPk() {
		return this.toAreaPk;
	}
	public void setToAreaName(java.lang.String toAreaName) {
		this.toAreaName = toAreaName;
	}
	
	public java.lang.String getToAreaName() {
		return this.toAreaName;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
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
	public void setLeastWeight(java.lang.Double leastWeight) {
		this.leastWeight = leastWeight;
	}
	
	public java.lang.Double getLeastWeight() {
		return this.leastWeight;
	}
	public void setFreight(java.lang.Double freight) {
		this.freight = freight;
	}
	
	public java.lang.Double getFreight() {
		return this.freight;
	}
	public void setBasicPrice(java.lang.Double basicPrice) {
		this.basicPrice = basicPrice;
	}
	
	public java.lang.Double getBasicPrice() {
		return this.basicPrice;
	}
	public void setFromTownPk(java.lang.String fromTownPk) {
		this.fromTownPk = fromTownPk;
	}
	
	public java.lang.String getFromTownPk() {
		return this.fromTownPk;
	}
	public void setFromTownName(java.lang.String fromTownName) {
		this.fromTownName = fromTownName;
	}
	
	public java.lang.String getFromTownName() {
		return this.fromTownName;
	}
	public void setToTownPk(java.lang.String toTownPk) {
		this.toTownPk = toTownPk;
	}
	
	public java.lang.String getToTownPk() {
		return this.toTownPk;
	}
	public void setToTownName(java.lang.String toTownName) {
		this.toTownName = toTownName;
	}
	
	public java.lang.String getToTownName() {
		return this.toTownName;
	}
	

}