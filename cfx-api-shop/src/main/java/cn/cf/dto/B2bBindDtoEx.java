package cn.cf.dto;


public class B2bBindDtoEx extends B2bBindDto {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.lang.Double progress;
	private String isUpDownName;//上下架状态
	private String statusName;//成团状态
	private String flagName;//来源类型
	

	public java.lang.Double getProgress() {
		return progress;
	}

	public void setProgress(java.lang.Double progress) {
		this.progress = progress;
	}

	public String getIsUpDownName() {
		return isUpDownName;
	}

	public void setIsUpDownName(String isUpDownName) {
		this.isUpDownName = isUpDownName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}
	
	
}
