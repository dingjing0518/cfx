package cn.cf.dto;

public class B2bMemberGradeExtDto extends B2bMemberGradeDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2745080307374196035L;

	private String updateStartTime;
	
	private String updateEndTime;
	
	private String rangeNumber; //分值范围，开始和结束拼接到一起

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

	public String getRangeNumber() {
		return rangeNumber;
	}

	public void setRangeNumber(String rangeNumber) {
		this.rangeNumber = rangeNumber;
	}
}
