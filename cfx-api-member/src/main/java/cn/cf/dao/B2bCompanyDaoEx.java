package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;

public interface B2bCompanyDaoEx extends B2bCompanyDao{
	
	B2bCompanyDtoEx getByCompanyPk(String pk);
	
	List<B2bCompanyDtoEx> searchCompanyList(Map<String, Object> map);

	List<B2bCompanyDtoEx> searchCompanyByMember(B2bCompanyDto coms);

	B2bCompanyDto getByCustomerPk(String customerPk);

	List<B2bCompanyDtoEx> getCustomerList(Map<String, Object> map);

	int getCustomerCount(Map<String, Object> map);
	
	int updateParentPk(Map<String, Object> map);

}
