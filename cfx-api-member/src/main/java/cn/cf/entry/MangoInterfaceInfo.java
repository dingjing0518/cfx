package cn.cf.entry;

import org.springframework.data.annotation.Id;
/**
 * @author:FJM
 * @describe:接口请求日志内容
 * @time:2017-5-23 上午9:38:55
 */
public class MangoInterfaceInfo {
	
	@Id
	private String id;
	
	private String url;
	
	private String requestValue;
	
	private String responseValue;
	
	private String requestTime;
	
	private String responseTime;
	
	//请求方式(主动:active;被动:passive)
	private String requestMode;
	
	private String ip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestValue() {
		return requestValue;
	}

	public void setRequestValue(String requestValue) {
		this.requestValue = requestValue;
	}

	public String getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
