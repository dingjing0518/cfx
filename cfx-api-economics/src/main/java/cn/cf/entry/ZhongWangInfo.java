package cn.cf.entry;

import org.springframework.data.annotation.Id;

public class ZhongWangInfo {
	@Id
	private String id;
	
	private String APIName;//接口名称
	
	private String taxNo;//客户税号
	
	private String param;//请求参数
	
	private String responseValue;//返回解密结果
	
	private String insertTime;//添加时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAPIName() {
		return APIName;
	}

	public void setAPIName(String aPIName) {
		APIName = aPIName;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
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
	
	
}
