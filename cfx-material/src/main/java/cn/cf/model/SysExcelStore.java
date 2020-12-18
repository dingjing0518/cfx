package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysExcelStore  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String name;
	private java.lang.String params;
	private java.lang.Integer isDeal;
	private java.util.Date insertTime;
	private java.lang.String methodName;
	private java.lang.String url;
	private java.lang.String accountPk;
	private java.lang.Integer type;
	private java.lang.String paramsName;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setParams(java.lang.String params) {
		this.params = params;
	}
	
	public java.lang.String getParams() {
		return this.params;
	}
	public void setIsDeal(java.lang.Integer isDeal) {
		this.isDeal = isDeal;
	}
	
	public java.lang.Integer getIsDeal() {
		return this.isDeal;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setMethodName(java.lang.String methodName) {
		this.methodName = methodName;
	}
	
	public java.lang.String getMethodName() {
		return this.methodName;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}

	public String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParamsName() {
		return paramsName;
	}

	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}
}