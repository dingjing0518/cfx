package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LgCarDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.lang.String plateNumber;
	private java.lang.String carLength;
	private java.lang.String carType;
	private java.lang.String licenseUrl;
	private java.lang.Integer isDeleted;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
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
	public void setPlateNumber(java.lang.String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	public java.lang.String getPlateNumber() {
		return this.plateNumber;
	}
	public void setCarLength(java.lang.String carLength) {
		this.carLength = carLength;
	}
	
	public java.lang.String getCarLength() {
		return this.carLength;
	}
	public void setCarType(java.lang.String carType) {
		this.carType = carType;
	}
	
	public java.lang.String getCarType() {
		return this.carType;
	}
	public void setLicenseUrl(java.lang.String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}
	
	public java.lang.String getLicenseUrl() {
		return this.licenseUrl;
	}
	public void setIsDeleted(java.lang.Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public java.lang.Integer getIsDeleted() {
		return this.isDeleted;
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
	

}