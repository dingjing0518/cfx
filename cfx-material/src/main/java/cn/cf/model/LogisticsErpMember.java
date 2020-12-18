package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsErpMember  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String memberPk;
	private java.lang.String logisticsPk;
	private java.lang.String employeeName;
	private java.lang.String employeeNumber;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setLogisticsPk(java.lang.String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	
	public java.lang.String getLogisticsPk() {
		return this.logisticsPk;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	
	public java.lang.String getEmployeeName() {
		return this.employeeName;
	}
	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public java.lang.String getEmployeeNumber() {
		return this.employeeNumber;
	}
	

}