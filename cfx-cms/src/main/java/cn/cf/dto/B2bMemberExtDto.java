package cn.cf.dto;

public class B2bMemberExtDto extends B2bMemberDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7076191949636743651L;
	
	private String insertStartTime;
	private String insertEndTime;
	private String updateStartTime;
	private String updateEndTime;
	private String auditStartTime;
	private String auditEndTime;
	private String feedEndTime;
	private String feedStartTime;
	private Integer type;
	private Integer orderCounts;
	private String roleName;
	private Integer companyType;
	
	private String refuseReason;
	
	private String levelName;
	
	
	private String employeeNumber;
	private String employeeName;
	
	private String auditStatusName;
	
	private String isVisableName;
	
	private String companyTypeArr;
	
	private String rolePkArr;

	private String sAdminShowMobile;

	private String companyNameCol;

	private String memberTelCol;

	private String memberNameCol;

	private String memberAccCol;

	private String modelType;



	public String getFeedEndTime() {
		return feedEndTime;
	}

	public void setFeedEndTime(String feedEndTime) {
		this.feedEndTime = feedEndTime;
	}

	public String getFeedStartTime() {
		return feedStartTime;
	}

	public void setFeedStartTime(String feedStartTime) {
		this.feedStartTime = feedStartTime;
	}

	public String getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public String getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public String getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public String getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public String getAuditStartTime() {
		return auditStartTime;
	}

	public void setAuditStartTime(String auditStartTime) {
		this.auditStartTime = auditStartTime;
	}

	public String getAuditEndTime() {
		return auditEndTime;
	}

	public void setAuditEndTime(String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrderCounts() {
		return orderCounts;
	}

	public void setOrderCounts(Integer orderCounts) {
		this.orderCounts = orderCounts;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public String getIsVisableName() {
		return isVisableName;
	}

	public void setIsVisableName(String isVisableName) {
		this.isVisableName = isVisableName;
	}

	public String getCompanyTypeArr() {
		return companyTypeArr;
	}

	public void setCompanyTypeArr(String companyTypeArr) {
		this.companyTypeArr = companyTypeArr;
	}

	public String getRolePkArr() {
		return rolePkArr;
	}

	public void setRolePkArr(String rolePkArr) {
		this.rolePkArr = rolePkArr;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getsAdminShowMobile() {
		return sAdminShowMobile;
	}

	public void setsAdminShowMobile(String sAdminShowMobile) {
		this.sAdminShowMobile = sAdminShowMobile;
	}

	public String getCompanyNameCol() {
		return companyNameCol;
	}

	public void setCompanyNameCol(String companyNameCol) {
		this.companyNameCol = companyNameCol;
	}

	public String getMemberTelCol() {
		return memberTelCol;
	}

	public void setMemberTelCol(String memberTelCol) {
		this.memberTelCol = memberTelCol;
	}

	public String getMemberNameCol() {
		return memberNameCol;
	}

	public void setMemberNameCol(String memberNameCol) {
		this.memberNameCol = memberNameCol;
	}

	public String getMemberAccCol() {
		return memberAccCol;
	}

	public void setMemberAccCol(String memberAccCol) {
		this.memberAccCol = memberAccCol;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
}
