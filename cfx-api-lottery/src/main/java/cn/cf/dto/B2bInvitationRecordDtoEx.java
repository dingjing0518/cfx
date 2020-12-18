package cn.cf.dto;

public class B2bInvitationRecordDtoEx  extends B2bInvitationRecordDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statusName;
	private Integer counts;
	private Integer successCount;
	private Integer failCount;
	private Integer confirmedCount;
	private Integer isOverDue;//标识：邀请是否已过期
	//columns END
	

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public Integer getConfirmedCount() {
		return confirmedCount;
	}

	public void setConfirmedCount(Integer confirmedCount) {
		this.confirmedCount = confirmedCount;
	}

	public Integer getIsOverDue() {
		return isOverDue;
	}

	public void setIsOverDue(Integer isOverDue) {
		this.isOverDue = isOverDue;
	}
	

	

}