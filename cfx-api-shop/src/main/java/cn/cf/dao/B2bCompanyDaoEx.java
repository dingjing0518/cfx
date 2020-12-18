package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;

public interface B2bCompanyDaoEx extends B2bCompanyDao{
	
	List<B2bCompanyDtoEx> searchCompanyList(Map<String,Object> map);
	
	B2bCompanyDtoEx getCompany(String pk);

	List<B2bCompanyDtoEx> searchCompanyByMember(B2bCompanyDto coms);

	B2bCompanyDto getByCustomerPk(String customerPk);
	
	int searchGridCountEx(Map<String, Object> map);
	
	int searchOrganizationCode(Map<String, Object> map);

	
}
