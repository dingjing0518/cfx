package cn.cf.dto;

import com.alibaba.fastjson.JSONObject;


public class LogisticsErpMemberDtoEx extends BaseDTO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pk;
	private String memberPk;
	private String logisticsPk;
	private String employeeName;
	private String employeeNumber;

	public LogisticsErpMemberDtoEx(JSONObject js) {
		memberPk=js.containsKey("memberPk")?js.getString("memberPk"):"";
		employeeName=js.containsKey("employeeName")?js.getString("employeeName"):"";
		employeeNumber=js.containsKey("employeeNumber")?js.getString("employeeNumber"):"";
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}

	public String getLogisticsPk() {
		return logisticsPk;
	}

	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

}
