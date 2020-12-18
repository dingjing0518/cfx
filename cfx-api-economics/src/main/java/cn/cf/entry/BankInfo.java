package cn.cf.entry;

import org.springframework.data.annotation.Id;

public class BankInfo {
	@Id
	private String id;
	
	private String bankName;
	
	private String inAccount;
	
	private String outAccount;
	
	private String requestDesValue;//请求加密字符串
	
	private String requestValue;//请求解密字符串
	
	private String responseDesValue;//返回加密字符串
	
	private String responseValue;//返回解密字符串
	
	private String insertTime;
	
	private String orderNumber;
	
	private String code;
	
	//请求方式(主动主动:active;银行通知:passive)
	private String requestMode;
	
	private String hxhCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	public String getInAccount() {
		return inAccount;
	}

	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}

	public String getOutAccount() {
		return outAccount;
	}

	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRequestDesValue() {
		return requestDesValue;
	}

	public void setRequestDesValue(String requestDesValue) {
		this.requestDesValue = requestDesValue;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getResponseDesValue() {
		return responseDesValue;
	}

	public void setResponseDesValue(String responseDesValue) {
		this.responseDesValue = responseDesValue;
	}

	public String getHxhCode() {
		return hxhCode;
	}

	public void setHxhCode(String hxhCode) {
		this.hxhCode = hxhCode;
	}
	
	
	
	
}
