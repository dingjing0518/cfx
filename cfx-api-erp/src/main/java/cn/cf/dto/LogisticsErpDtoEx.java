package cn.cf.dto;

import java.util.ArrayList;
import java.util.List;

public class LogisticsErpDtoEx extends LogisticsErpDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<LogisticsErpStepInfoDto>  stepInfos =new ArrayList<LogisticsErpStepInfoDto>() ;
	private List<LogisticsErpMemberDto> logisticsErpMembers =new ArrayList<LogisticsErpMemberDto>();
	public LogisticsErpDtoEx(){}

	public List<LogisticsErpStepInfoDto> getStepInfos() {
		return stepInfos;
	}

	public void setStepInfos(List<LogisticsErpStepInfoDto> stepInfos) {
		this.stepInfos = stepInfos;
	}

	public List<LogisticsErpMemberDto> getLogisticsErpMembers() {
		return logisticsErpMembers;
	}

	public void setLogisticsErpMembers(List<LogisticsErpMemberDto> logisticsErpMembers) {
		this.logisticsErpMembers = logisticsErpMembers;
	}
}
