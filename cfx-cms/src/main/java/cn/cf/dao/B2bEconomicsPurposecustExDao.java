package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsPurposecustExDto;
import cn.cf.model.B2bEconomicsPurposecust;

public interface B2bEconomicsPurposecustExDao extends B2bEconomicsPurposecustDao {

	int countPurposecustGrid(Map<String, Object> map);

	List<B2bEconomicsPurposecustExDto> searchPurposecustGrid(Map<String, Object> map);

	Integer updateExt(B2bEconomicsPurposecust ecPurposecust);

}
