package cn.cf.dto;

public class B2bDimensionalityRelevancyExtDto extends B2bDimensionalityRelevancyDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7080097046319673004L;
	
	
	private String updateStartTime;
	
	private String updateEndTime;

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
}
