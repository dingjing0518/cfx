package cn.cf.dto;

import java.util.ArrayList;
import java.util.List;



public class LogisticsErpDtoEx extends LogisticsErpDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<LogisticsErpStepInfoDto>  stepInfos =new ArrayList<LogisticsErpStepInfoDto>() ;
	private String stepPrices;
	private String members;
	private List<LogisticsErpMemberDto> logisticsErpMembers =new ArrayList<LogisticsErpMemberDto>();
	private String employeeNumber;//业务员
	private String memberPk;
	private String employeeName;
	private Integer memberType;

	 

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
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

	public List<LogisticsErpMemberDto> getLogisticsErpMembers() {
		return logisticsErpMembers;
	}

	public void setLogisticsErpMembers(
			List<LogisticsErpMemberDto> logisticsErpMembers) {
		this.logisticsErpMembers = logisticsErpMembers;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getStepPrices() {
		return stepPrices;
	}

	public void setStepPrices(String stepPrices) {
		this.stepPrices = stepPrices;
	}

	public List<LogisticsErpStepInfoDto> getStepInfos() {
		return stepInfos;
	}

	public void setStepInfos(List<LogisticsErpStepInfoDto> stepInfos) {
		this.stepInfos = stepInfos;
	}
 
 
 

}
