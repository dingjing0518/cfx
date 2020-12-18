package cn.cf.dto;

import java.util.Date;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

public class B2bCompanyExtDto extends B2bCompanyDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9193460147089815042L;

	private String storePk;
	private String storeName;

	private String mobile;

	private String isAudit; // 是否是审核操作 1是 2否

	private String isUpdateRemarks; // 是否修改备注标记 1是2否

	private String isChild;

	private java.lang.String insertStartTime;
	private java.lang.String insertEndTime;
	private java.lang.String updateStartTime;
	private java.lang.String updateEndTime;
	private java.lang.String auditStartTime;
	private java.lang.String auditEndTime;
	private java.lang.String auditSpStartTime;
	private java.lang.String auditSpEndTime;

	private java.lang.String accountPk;
	private java.lang.String personnelPk;
	private String accountName;
	private String personnelName;
	private String auditSpStatusName;
	private String auditStatusName;
	private String isVisableName;
	private String memberPk;
	private Integer flag ;

	private Date distributeMemberTime;
	private String address;
	private String distributeMemberStartTime;
    private String distributeMemberEndTime;

	//显示列
	private String contactsTelCol;
	private String contactsCol;

	private String mobileCol;
	private String companyNameCol;
	private String orgCodeCol;
	//营业执照五证
	private String bussLicenseCol;

	private String regionCol;
	private String contactsTelSubCol;
	private String contactsSubCol;

	private String mobileSubCol;
	private String companyNameSubCol;
	private String orgCodeSubCol;
	//营业执照五证
	private String bussLicenseSubCol;

	private String regionSubCol;

	private String modelType;
	
	private String regionName;
	
	private Integer isDistribute;

	private Integer type;

	private String block;
	
	

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDistribute() {
        return isDistribute;
    }

    public void setIsDistribute(Integer isDistribute) {
        this.isDistribute = isDistribute;
    }

    public String getDistributeMemberStartTime() {
        return distributeMemberStartTime;
    }

    public void setDistributeMemberStartTime(String distributeMemberStartTime) {
        this.distributeMemberStartTime = distributeMemberStartTime;
    }

    public String getDistributeMemberEndTime() {
        return distributeMemberEndTime;
    }

    public void setDistributeMemberEndTime(String distributeMemberEndTime) {
        this.distributeMemberEndTime = distributeMemberEndTime;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDistributeMemberTime() {
		return distributeMemberTime;
	}

	public void setDistributeMemberTime(Date distributeMemberTime) {
		this.distributeMemberTime = distributeMemberTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public String getAuditSpStatusName() {
		return auditSpStatusName;
	}

	public void setAuditSpStatusName(String auditSpStatusName) {
		this.auditSpStatusName = auditSpStatusName;
	}

	public String getIsVisableName() {
		return isVisableName;
	}

	public void setIsVisableName(String isVisableName) {
		this.isVisableName = isVisableName;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getIsUpdateRemarks() {
		return isUpdateRemarks;
	}

	public void setIsUpdateRemarks(String isUpdateRemarks) {
		this.isUpdateRemarks = isUpdateRemarks;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(java.lang.String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public java.lang.String getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(java.lang.String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public java.lang.String getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(java.lang.String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public java.lang.String getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(java.lang.String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public java.lang.String getAuditStartTime() {
		return auditStartTime;
	}

	public void setAuditStartTime(java.lang.String auditStartTime) {
		this.auditStartTime = auditStartTime;
	}

	public java.lang.String getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(java.lang.String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public String getIsChild() {
		return isChild;
	}

	public void setIsChild(String isChild) {
		this.isChild = isChild;
	}

	public java.lang.String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(java.lang.String accountPk) {
		this.accountPk = accountPk;
	}

	public java.lang.String getPersonnelPk() {
		return personnelPk;
	}

	public void setPersonnelPk(java.lang.String personnelPk) {
		this.personnelPk = personnelPk;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}

	public String getContactsTelCol() {
		return contactsTelCol;
	}

	public void setContactsTelCol(String contactsTelCol) {
		this.contactsTelCol = contactsTelCol;
	}

	public String getContactsCol() {
		return contactsCol;
	}

	public void setContactsCol(String contactsCol) {
		this.contactsCol = contactsCol;
	}

	public String getMobileCol() {
		return mobileCol;
	}

	public void setMobileCol(String mobileCol) {
		this.mobileCol = mobileCol;
	}

	public String getCompanyNameCol() {
		return companyNameCol;
	}

	public void setCompanyNameCol(String companyNameCol) {
		this.companyNameCol = companyNameCol;
	}

	public String getOrgCodeCol() {
		return orgCodeCol;
	}

	public void setOrgCodeCol(String orgCodeCol) {
		this.orgCodeCol = orgCodeCol;
	}

	public String getBussLicenseCol() {
		return bussLicenseCol;
	}

	public void setBussLicenseCol(String bussLicenseCol) {
		this.bussLicenseCol = bussLicenseCol;
	}

	public String getRegionCol() {
		return regionCol;
	}

	public void setRegionCol(String regionCol) {
		this.regionCol = regionCol;
	}

	public String getContactsTelSubCol() {
		return contactsTelSubCol;
	}

	public void setContactsTelSubCol(String contactsTelSubCol) {
		this.contactsTelSubCol = contactsTelSubCol;
	}

	public String getContactsSubCol() {
		return contactsSubCol;
	}

	public void setContactsSubCol(String contactsSubCol) {
		this.contactsSubCol = contactsSubCol;
	}

	public String getMobileSubCol() {
		return mobileSubCol;
	}

	public void setMobileSubCol(String mobileSubCol) {
		this.mobileSubCol = mobileSubCol;
	}

	public String getCompanyNameSubCol() {
		return companyNameSubCol;
	}

	public void setCompanyNameSubCol(String companyNameSubCol) {
		this.companyNameSubCol = companyNameSubCol;
	}

	public String getOrgCodeSubCol() {
		return orgCodeSubCol;
	}

	public void setOrgCodeSubCol(String orgCodeSubCol) {
		this.orgCodeSubCol = orgCodeSubCol;
	}

	public String getBussLicenseSubCol() {
		return bussLicenseSubCol;
	}

	public void setBussLicenseSubCol(String bussLicenseSubCol) {
		this.bussLicenseSubCol = bussLicenseSubCol;
	}

	public String getRegionSubCol() {
		return regionSubCol;
	}

	public void setRegionSubCol(String regionSubCol) {
		this.regionSubCol = regionSubCol;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getAuditSpStartTime() {
		return auditSpStartTime;
	}

	public void setAuditSpStartTime(String auditSpStartTime) {
		this.auditSpStartTime = auditSpStartTime;
	}

	public String getAuditSpEndTime() {
		return auditSpEndTime;
	}

	public void setAuditSpEndTime(String auditSpEndTime) {
		this.auditSpEndTime = auditSpEndTime;
	}
}
