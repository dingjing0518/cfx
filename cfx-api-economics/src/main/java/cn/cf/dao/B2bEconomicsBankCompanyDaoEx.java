package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.model.B2bEconomicsBankCompany;

public interface B2bEconomicsBankCompanyDaoEx extends B2bEconomicsBankCompanyDao{
	
	void delByCompanyAndBank(Map<String,Object> map);
	
	void insertBatch(List<B2bEconomicsBankCompanyDto> dto);
	
	void updateByCompanyAndBank(B2bEconomicsBankCompany dto);
	
}
