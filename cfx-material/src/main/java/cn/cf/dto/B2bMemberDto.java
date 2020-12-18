package cn.cf.dto;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bMemberDto extends BaseDTO  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String mobile;
	private java.lang.String password;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.util.Date insertTime;
	private java.lang.Integer auditStatus;
	private java.lang.Integer isVisable;
	private java.lang.String rolePk;
	private java.util.Date auditTime;
	private java.lang.String auditPk;
	private java.util.Date updateTime;
	private java.lang.Integer registerSource;
	private java.lang.String invitationCode;
	private java.lang.String beInvitedCode;
	private java.util.Date loginTime;
	private java.lang.String headPortrait;
	private java.lang.String refuseReason;
	private java.lang.String employeeNumber;
	private java.lang.String employeeName;
	private java.lang.String parentPk;
	private java.lang.Integer level;
	private java.lang.Double experience;
	private java.lang.Double shell;
	private java.lang.Integer addMembers;
	private java.lang.String  addPk;
	private java.util.Date feedTime;
	private java.lang.String levelName;
	//columns END
	
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.Double getExperience() {
		return experience;
	}

	public java.lang.Double getShell() {
		return shell;
	}

	public java.util.Date getFeedTime() {
		return feedTime;
	}

	public void setFeedTime(java.util.Date feedTime) {
		this.feedTime = feedTime;
	}

	public java.lang.String getLevelName() {
		return levelName;
	}

	public void setLevelName(java.lang.String levelName) {
		this.levelName = levelName;
	}

	public void setExperience(java.lang.Double experience) {
		this.experience = experience;
	}

	public void setShell(java.lang.Double shell) {
		this.shell = shell;
	}

	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	public java.lang.String getPassword() {
		return this.password;
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
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setAuditStatus(java.lang.Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public java.lang.Integer getAuditStatus() {
		return this.auditStatus;
	}
	public void setIsVisable(java.lang.Integer isVisable) {
		this.isVisable = isVisable;
	}
	
	public java.lang.Integer getIsVisable() {
		return this.isVisable;
	}
	public void setRolePk(java.lang.String rolePk) {
		this.rolePk = rolePk;
	}
	
	public java.lang.String getRolePk() {
		return this.rolePk;
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
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setRegisterSource(java.lang.Integer registerSource) {
		this.registerSource = registerSource;
	}
	
	public java.lang.Integer getRegisterSource() {
		return this.registerSource;
	}
	public void setInvitationCode(java.lang.String invitationCode) {
		this.invitationCode = invitationCode;
	}
	
	public java.lang.String getInvitationCode() {
		return this.invitationCode;
	}
	public void setBeInvitedCode(java.lang.String beInvitedCode) {
		this.beInvitedCode = beInvitedCode;
	}
	
	public java.lang.String getBeInvitedCode() {
		return this.beInvitedCode;
	}
	public void setLoginTime(java.util.Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public java.util.Date getLoginTime() {
		return this.loginTime;
	}
	public void setHeadPortrait(java.lang.String headPortrait) {
		this.headPortrait = headPortrait;
	}
	
	public java.lang.String getHeadPortrait() {
		return this.headPortrait;
	}
	public void setRefuseReason(java.lang.String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
	public java.lang.String getRefuseReason() {
		return this.refuseReason;
	}
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public java.lang.String getEmployeeNumber() {
		return this.employeeNumber;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	
	public java.lang.String getEmployeeName() {
		return this.employeeName;
	}
	public void setParentPk(java.lang.String parentPk) {
		this.parentPk = parentPk;
	}
	
	public java.lang.String getParentPk() {
		return this.parentPk;
	}

	public java.lang.Integer getLevel() {
		return level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}


	public java.lang.Integer getAddMembers() {
		return addMembers;
	}

	public void setAddMembers(java.lang.Integer addMembers) {
		this.addMembers = addMembers;
	}

	public java.lang.String getAddPk() {
		return addPk;
	}

	public void setAddPk(java.lang.String addPk) {
		this.addPk = addPk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}