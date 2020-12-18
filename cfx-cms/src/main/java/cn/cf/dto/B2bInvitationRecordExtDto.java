package cn.cf.dto;

public class B2bInvitationRecordExtDto extends B2bInvitationRecordDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 301700736377786447L;

	private Integer isInvitationStatus;
	
	private boolean companyNameIsExist;
	
	private String applyStatusName;
	
	private String invitationStatusName;
	
	private String awardStatusName;
	
	private Integer isPut;

	public Integer getIsInvitationStatus() {
		return isInvitationStatus;
	}

	public void setIsInvitationStatus(Integer isInvitationStatus) {
		this.isInvitationStatus = isInvitationStatus;
	}

	public boolean isCompanyNameIsExist() {
		return companyNameIsExist;
	}

	public void setCompanyNameIsExist(boolean companyNameIsExist) {
		this.companyNameIsExist = companyNameIsExist;
	}

	public Integer getIsPut() {
		return isPut;
	}

	public void setIsPut(Integer isPut) {
		this.isPut = isPut;
	}

	public String getApplyStatusName() {
		return applyStatusName;
	}

	public void setApplyStatusName(String applyStatusName) {
		this.applyStatusName = applyStatusName;
	}

	public String getInvitationStatusName() {
		return invitationStatusName;
	}

	public void setInvitationStatusName(String invitationStatusName) {
		this.invitationStatusName = invitationStatusName;
	}

	public String getAwardStatusName() {
		return awardStatusName;
	}

	public void setAwardStatusName(String awardStatusName) {
		this.awardStatusName = awardStatusName;
	}
	
	
	
}
