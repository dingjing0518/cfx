package cn.cf.dto;


@SuppressWarnings("serial")
public class LogisticsErpMemberDto extends BaseDTO  {
	private String pk;
	private String memberPk;
	private String logisticsPk;
	private String employeeName;
	private String employeeNumber;
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
