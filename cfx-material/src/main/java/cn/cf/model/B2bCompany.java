package cn.cf.model;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCompany extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.Integer isDelete;
	private java.lang.Integer auditStatus;
	private java.lang.Integer auditSpStatus;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.String parentPk;
	private java.lang.String provinceName;
	private java.lang.String province;
	private java.lang.String cityName;
	private java.lang.String city;
	private java.lang.String areaName;
	private java.lang.String area;
	private java.lang.String regAddress;
	private java.lang.String contactsTel;
	private java.lang.String contacts;
	private java.lang.Integer companyType;
	private java.lang.String blUrl;
	private java.lang.String organizationCode;
	private java.lang.String bankName;
	private java.lang.String bankAccount;
	private java.lang.String ecUrl;
	private java.lang.Integer isVisable;
	private java.util.Date auditTime;
	private java.util.Date auditSpTime;
	private java.lang.String auditPk;
	private java.lang.String auditSpPk;
	private java.lang.String refuseReason;
	private java.lang.String headPortrait;
	private java.lang.Integer isPerfect;
	private java.lang.String remarks;
	private java.lang.String addMemberPk;//添加该公司的memberPk
	private java.util.Date infoUpdateTime;

	private java.util.Date enterTime;
	private java.lang.String block;
	
	
	//columns END

	
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	

	public java.util.Date getInfoUpdateTime() {
		return infoUpdateTime;
	}

	public void setInfoUpdateTime(java.util.Date infoUpdateTime) {
		this.infoUpdateTime = infoUpdateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setAuditSpStatus(java.lang.Integer auditSpStatus) {
		this.auditSpStatus = auditSpStatus;
	}
	
	public java.lang.Integer getAuditSpStatus() {
		return this.auditSpStatus;
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
	public void setParentPk(java.lang.String parentPk) {
		this.parentPk = parentPk;
	}
	
	public java.lang.String getParentPk() {
		return this.parentPk;
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
	public void setCompanyType(java.lang.Integer companyType) {
		this.companyType = companyType;
	}
	
	public java.lang.Integer getCompanyType() {
		return this.companyType;
	}
	public void setBlUrl(java.lang.String blUrl) {
		this.blUrl = blUrl;
	}
	
	public java.lang.String getBlUrl() {
		return this.blUrl;
	}
	public void setOrganizationCode(java.lang.String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	public java.lang.String getOrganizationCode() {
		return this.organizationCode;
	}
	public void setEcUrl(java.lang.String ecUrl) {
		this.ecUrl = ecUrl;
	}
	
	public java.lang.String getEcUrl() {
		return this.ecUrl;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	public void setAuditSpTime(java.util.Date auditSpTime) {
		this.auditSpTime = auditSpTime;
	}
	
	public java.util.Date getAuditSpTime() {
		return this.auditSpTime;
	}
	public void setAuditPk(java.lang.String auditPk) {
		this.auditPk = auditPk;
	}
	
	public java.lang.String getAuditPk() {
		return this.auditPk;
	}
	public void setAuditSpPk(java.lang.String auditSpPk) {
		this.auditSpPk = auditSpPk;
	}
	
	public java.lang.String getAuditSpPk() {
		return this.auditSpPk;
	}
	public void setRefuseReason(java.lang.String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
	public java.lang.String getRefuseReason() {
		return this.refuseReason;
	}
	public void setHeadPortrait(java.lang.String headPortrait) {
		this.headPortrait = headPortrait;
	}
	
	public java.lang.String getHeadPortrait() {
		return this.headPortrait;
	}
	public void setIsPerfect(java.lang.Integer isPerfect) {
		this.isPerfect = isPerfect;
	}
	
	public java.lang.Integer getIsPerfect() {
		return this.isPerfect;
	}
	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}
	
	public java.lang.String getRemarks() {
		return this.remarks;
	}

	public java.lang.String getAddMemberPk() {
		return addMemberPk;
	}

	public void setAddMemberPk(java.lang.String addMemberPk) {
		this.addMemberPk = addMemberPk;
	}

	public java.util.Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(java.util.Date enterTime) {
		this.enterTime = enterTime;
	}


	public java.lang.String getRegAddress() {
		return regAddress;
	}


	public void setRegAddress(java.lang.String regAddress) {
		this.regAddress = regAddress;
	}


	public java.lang.String getBankName() {
		return bankName;
	}


	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}


	public java.lang.String getBankAccount() {
		return bankAccount;
	}


	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
}