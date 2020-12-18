package cn.cf.dto;

import java.util.Date;

import cn.cf.model.BaseModel;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class C2bMarrieddealDto  extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String memberPk;
	private java.lang.String memberName;
	private java.lang.Integer status;
	private java.lang.Integer auditStatus;
	private java.util.Date auditTime;
	private java.lang.String auditPk;
	private java.lang.String contactsTel;
	private java.lang.String contacts;
	private java.lang.String brandPk;
	private java.lang.String brandName;
	private java.lang.String productPk;
	private java.lang.String productName;
	private java.lang.String specPk;
	private java.lang.String specName;
	private java.lang.String specifications;
	private java.lang.String gradePk;
	private java.lang.String gradeName;
	private java.lang.String seriesPk;
	private java.lang.String seriesName;
	private java.lang.String varietiesPk;
	private java.lang.String varietiesName;
	private java.lang.String seedvarietyPk;
	private java.lang.String seedvarietyName;
    private String startTime;
    private String endTime;
	private java.lang.Double buyCounts;
	private java.lang.Integer flag;
	private java.lang.String refuseReason;
	private java.lang.String province;
	private java.lang.String provinceName;
	private java.lang.String city;
	private java.lang.String cityName;
	private java.lang.String area;
	private java.lang.String areaName;
	private java.lang.String address;
	private java.lang.String usePk;
	private java.lang.String useName;
	private java.lang.String remarks;
	private java.lang.Double price;
	private java.lang.Integer isDelete;
	private java.lang.Integer productType;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String purchaserPk;
	private java.lang.String purchaserName;
	private java.lang.String supplierPk;
	private java.lang.String supplierName;
	private java.lang.Integer isFinished;
	private java.lang.Integer type;
	private java.lang.Double actualCount;
	private java.lang.Integer isHidden;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
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
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setAuditPk(java.lang.String auditPk) {
		this.auditPk = auditPk;
	}
	
	public java.lang.String getAuditPk() {
		return this.auditPk;
	}
	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}
	
	public java.lang.String getContactsTel() {
		return this.contactsTel;
	}
	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}
	
	public java.lang.String getContacts() {
		return this.contacts;
	}
	public void setBrandPk(java.lang.String brandPk) {
		this.brandPk = brandPk;
	}
	
	public java.lang.String getBrandPk() {
		return this.brandPk;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	
	public java.lang.String getBrandName() {
		return this.brandName;
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
	public void setSpecPk(java.lang.String specPk) {
		this.specPk = specPk;
	}
	
	public java.lang.String getSpecPk() {
		return this.specPk;
	}
	public void setSpecName(java.lang.String specName) {
		this.specName = specName;
	}
	
	public java.lang.String getSpecName() {
		return this.specName;
	}
	public void setSpecifications(java.lang.String specifications) {
		this.specifications = specifications;
	}
	
	public java.lang.String getSpecifications() {
		return this.specifications;
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
	public void setSeriesPk(java.lang.String seriesPk) {
		this.seriesPk = seriesPk;
	}
	
	public java.lang.String getSeriesPk() {
		return this.seriesPk;
	}
	public void setSeriesName(java.lang.String seriesName) {
		this.seriesName = seriesName;
	}
	
	public java.lang.String getSeriesName() {
		return this.seriesName;
	}
	public void setVarietiesPk(java.lang.String varietiesPk) {
		this.varietiesPk = varietiesPk;
	}
	
	public java.lang.String getVarietiesPk() {
		return this.varietiesPk;
	}
	public void setVarietiesName(java.lang.String varietiesName) {
		this.varietiesName = varietiesName;
	}
	
	public java.lang.String getVarietiesName() {
		return this.varietiesName;
	}
	public void setSeedvarietyPk(java.lang.String seedvarietyPk) {
		this.seedvarietyPk = seedvarietyPk;
	}
	
	public java.lang.String getSeedvarietyPk() {
		return this.seedvarietyPk;
	}
	public void setSeedvarietyName(java.lang.String seedvarietyName) {
		this.seedvarietyName = seedvarietyName;
	}
	
	public java.lang.String getSeedvarietyName() {
		return this.seedvarietyName;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	public void setBuyCounts(java.lang.Double buyCounts) {
		this.buyCounts = buyCounts;
	}
	
	public java.lang.Double getBuyCounts() {
		return this.buyCounts;
	}
	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}
	
	public java.lang.Integer getFlag() {
		return this.flag;
	}
	public void setRefuseReason(java.lang.String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
	public java.lang.String getRefuseReason() {
		return this.refuseReason;
	}
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setUsePk(java.lang.String usePk) {
		this.usePk = usePk;
	}
	
	public java.lang.String getUsePk() {
		return this.usePk;
	}
	public void setUseName(java.lang.String useName) {
		this.useName = useName;
	}
	
	public java.lang.String getUseName() {
		return this.useName;
	}
	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}
	
	public java.lang.String getRemarks() {
		return this.remarks;
	}
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	
	public java.lang.Double getPrice() {
		return this.price;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setProductType(java.lang.Integer productType) {
		this.productType = productType;
	}
	
	public java.lang.Integer getProductType() {
		return this.productType;
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
	public void setIsFinished(java.lang.Integer isFinished) {
		this.isFinished = isFinished;
	}
	
	public java.lang.Integer getIsFinished() {
		return this.isFinished;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setActualCount(java.lang.Double actualCount) {
		this.actualCount = actualCount;
	}
	
	public java.lang.Double getActualCount() {
		return this.actualCount;
	}
	public void setIsHidden(java.lang.Integer isHidden) {
		this.isHidden = isHidden;
	}
	
	public java.lang.Integer getIsHidden() {
		return this.isHidden;
	}
	

}