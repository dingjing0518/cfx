package cn.cf.service.platform;

import java.util.List;

import cn.cf.dto.B2bEconomicsPurposecustDto;

public interface B2bEconomicsPurposecustService {
	
	List<B2bEconomicsPurposecustDto> getB2bEconomicsPurposecust(String companyPk,Integer status);

	/**
	 * 意向客户申请
	 * @param b2bEconomicsPurposecustDto
	 * @param code 短信验证码
	 * @return
	 */
	String addEconomicsPurposecust(B2bEconomicsPurposecustDto b2bEconomicsPurposecustDto,String code);
}
