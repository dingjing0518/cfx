package cn.cf.dto;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bAddressDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String companyPk;
	private java.util.Date insertTime;
	private java.lang.String address;
	private java.lang.String provinceName;
	private java.lang.String province;
	private java.lang.String cityName;
	private java.lang.String city;
	private java.lang.String areaName;
	private java.lang.String area;
	private java.lang.String town;
	private java.lang.String townName;
	private java.lang.Integer isDelete;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.Integer isDefault;
	private java.lang.String signingCompany;
	private java.lang.Integer syncStatus;
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
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setTown(java.lang.String town) {
		this.town = town;
	}
	
	public java.lang.String getTown() {
		return this.town;
	}
	public void setTownName(java.lang.String townName) {
		this.townName = townName;
	}
	
	public java.lang.String getTownName() {
		return this.townName;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
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
	public void setIsDefault(java.lang.Integer isDefault) {
		this.isDefault = isDefault;
	}
	
	public java.lang.Integer getIsDefault() {
		return this.isDefault;
	}

	public java.lang.String getSigningCompany() {
		return signingCompany;
	}

	public void setSigningCompany(java.lang.String signingCompany) {
		this.signingCompany = signingCompany;
	}

	public java.lang.Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(java.lang.Integer syncStatus) {
		this.syncStatus = syncStatus;
	}
	
	
}