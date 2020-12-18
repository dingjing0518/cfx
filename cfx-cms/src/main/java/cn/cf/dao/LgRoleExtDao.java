package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bRoleExtDto;

public interface LgRoleExtDao extends B2bRoleDao{

	List<B2bRoleExtDto> getRoleList(Map<String, Object> map);

	int getRoleCount(Map<String, Object> map);

	void delMemberRole(String memberPk);

	void insertMemberRole(Map<String, Object> map);
	
	int delMemberRoleByMemberPkAndRolePk(Map<String, Object> map);
	
	List<B2bRoleExtDto> searchRoleBymemberPk(String memberPk);
	
	List<B2bRoleExtDto> searchRoleAdmin();

	List<B2bRoleExtDto> getRoleListBySms(Map<String, Object> map);

	int getRoleCountBySms(Map<String, Object> map);

	List<B2bRoleExtDto> searchGridExt(Map<String, Object> map);


}
