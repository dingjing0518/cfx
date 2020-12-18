package cn.cf.entity;

import javax.persistence.Id;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class SysExcelStoreExceptionEntity {
	@Id
	private String id;

	private String methodName;

	private String errorMsg;

	private String insertTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}