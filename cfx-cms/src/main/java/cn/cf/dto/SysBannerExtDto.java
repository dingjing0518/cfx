package cn.cf.dto;

public class SysBannerExtDto extends SysBannerDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3875556805420256321L;
	
	private String startStime;
	private String startEtime;
	private String endStime;
	private String endEtime;
	private String onlineStime;
	private String onlineEtime;
	public String getStartStime() {
		return startStime;
	}
	public void setStartStime(String startStime) {
		this.startStime = startStime;
	}
	public String getStartEtime() {
		return startEtime;
	}
	public void setStartEtime(String startEtime) {
		this.startEtime = startEtime;
	}
	public String getEndStime() {
		return endStime;
	}
	public void setEndStime(String endStime) {
		this.endStime = endStime;
	}
	public String getEndEtime() {
		return endEtime;
	}
	public void setEndEtime(String endEtime) {
		this.endEtime = endEtime;
	}
	public String getOnlineStime() {
		return onlineStime;
	}
	public void setOnlineStime(String onlineStime) {
		this.onlineStime = onlineStime;
	}
	public String getOnlineEtime() {
		return onlineEtime;
	}
	public void setOnlineEtime(String onlineEtime) {
		this.onlineEtime = onlineEtime;
	}
	
	
}
