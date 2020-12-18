package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCompanyDto;

public interface B2bCompanyDaoEx extends B2bCompanyDao{


	B2bCompanyDto getCompanyInfoByMap(Map<String, Object> param);
	
	List<B2bCompanyDto> getByCompanyName(Map<String,Object> map);

}
