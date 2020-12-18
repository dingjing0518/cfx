package cn.cf.dto;

public class B2bLotteryActivityExDto extends B2bLotteryActivityDto{
	

	private java.lang.String startTimeStr;
	private java.lang.String endTimeStr;
	private java.lang.String onlineTimeStr;
	private  java.lang.Integer isEnd;//是否过期1.否 2是
	
	public java.lang.String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(java.lang.String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public java.lang.String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(java.lang.String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public java.lang.String getOnlineTimeStr() {
		return onlineTimeStr;
	}
	public void setOnlineTimeStr(java.lang.String onlineTimeStr) {
		this.onlineTimeStr = onlineTimeStr;
	}
	public java.lang.Integer getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(java.lang.Integer isEnd) {
		this.isEnd = isEnd;
	}
	
	
}
