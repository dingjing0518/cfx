package cn.cf.entry;

import org.springframework.data.annotation.Id;

public class BillInfo {
	@Id
	private String id;
	
	private String code;
	
	private String requestValue;//请求解密字符串
	
	private String responseValue;//返回解密字符串
	
	private String insertTime;
	
	private String hxhCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getHxhCode() {
		return hxhCode;
	}

	public void setHxhCode(String hxhCode) {
		this.hxhCode = hxhCode;
	}


	
	
	
	
}
