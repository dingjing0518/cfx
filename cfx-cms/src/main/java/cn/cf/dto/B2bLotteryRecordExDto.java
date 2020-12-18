package cn.cf.dto;

public class B2bLotteryRecordExDto extends B2bLotteryRecordDto {
	private static final long serialVersionUID = 5454155825314635342L;
	
	private java.lang.Integer awardVariety;
	private java.lang.Integer isAwardStatus;
	private java.lang.Integer isStatus;
	private java.lang.String insertTimeStart;
	private java.lang.String insertTimeEnd;
	private java.lang.Integer isHistory;
	private java.lang.String awardVarietyName;
	private java.lang.String awardStatusName;
	private java.lang.String grantTypeName;

	private java.lang.String activityTypeName;

	private String memberNameCol;
	private String mobileCol;
	private String companyNameCol;

	private Integer colAuthAwardType;

	private String awardName;

	public String getActivityTypeName() {
		return activityTypeName;
	}
	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.lang.Integer getAwardVariety() {
		return awardVariety;
	}

	public void setAwardVariety(java.lang.Integer awardVariety) {
		this.awardVariety = awardVariety;
	}

	public java.lang.Integer getIsAwardStatus() {
		return isAwardStatus;
	}

	public void setIsAwardStatus(java.lang.Integer isAwardStatus) {
		this.isAwardStatus = isAwardStatus;
	}

	public java.lang.Integer getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(java.lang.Integer isStatus) {
		this.isStatus = isStatus;
	}

	public java.lang.String getInsertTimeStart() {
		return insertTimeStart;
	}

	public void setInsertTimeStart(java.lang.String insertTimeStart) {
		this.insertTimeStart = insertTimeStart;
	}

	public java.lang.String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(java.lang.String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}

	public java.lang.Integer getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(java.lang.Integer isHistory) {
		this.isHistory = isHistory;
	}

	public java.lang.String getAwardVarietyName() {
		return awardVarietyName;
	}

	public void setAwardVarietyName(java.lang.String awardVarietyName) {
		this.awardVarietyName = awardVarietyName;
	}

	public java.lang.String getAwardStatusName() {
		return awardStatusName;
	}

	public void setAwardStatusName(java.lang.String awardStatusName) {
		this.awardStatusName = awardStatusName;
	}

	public java.lang.String getGrantTypeName() {
		return grantTypeName;
	}

	public void setGrantTypeName(java.lang.String grantTypeName) {
		this.grantTypeName = grantTypeName;
	}

	public String getMemberNameCol() {
		return memberNameCol;
	}

	public void setMemberNameCol(String memberNameCol) {
		this.memberNameCol = memberNameCol;
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

	public Integer getColAuthAwardType() {
		return colAuthAwardType;
	}

	public void setColAuthAwardType(Integer colAuthAwardType) {
		this.colAuthAwardType = colAuthAwardType;
	}

	@Override
	public String getAwardName() {
		return awardName;
	}

	@Override
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
}
