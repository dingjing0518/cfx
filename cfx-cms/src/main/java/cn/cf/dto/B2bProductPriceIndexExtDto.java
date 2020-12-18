package cn.cf.dto;

import java.util.Date;

public class B2bProductPriceIndexExtDto extends B2bProductPriceIndexDto{

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