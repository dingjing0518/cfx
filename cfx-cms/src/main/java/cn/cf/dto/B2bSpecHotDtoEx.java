package cn.cf.dto;

public class B2bSpecHotDtoEx extends B2bSpecHotDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String insertTimeBegin;
	private String insertTimeEnd;
	private String updateTimeBegin;
	private String updateTimeEnd;
	public String getInsertTimeBegin() {
		return insertTimeBegin;
	}
	public void setInsertTimeBegin(String insertTimeBegin) {
		this.insertTimeBegin = insertTimeBegin;
	}
	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}
	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}
	public String getUpdateTimeBegin() {
		return updateTimeBegin;
	}
	public void setUpdateTimeBegin(String updateTimeBegin) {
		this.updateTimeBegin = updateTimeBegin;
	}
	public String getUpdateTimeEnd() {
		return updateTimeEnd;
	}
	public void setUpdateTimeEnd(String updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	
	
}
