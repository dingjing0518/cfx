package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.dto.B2bMemberRoleDto;

public interface B2bMemberRoleDaoEx extends B2bMemberRoleDao{
	
	void deleteByMemberPk(String pk);
	
	List<B2bMemberDtoEx> getAdmin(String companyPk);

	void deleteByMemberPkAndType(Map<String, Object> map);

	List<B2bMemberRoleDto> searchMemberRoles(Map<String, Object> map);
	
	List<B2bMemberRoleDto> searchMemberRolesByMemberPk(String memberPk);
	
}
