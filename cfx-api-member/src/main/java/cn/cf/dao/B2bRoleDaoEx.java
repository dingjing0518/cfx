package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bRoleDto;

public interface B2bRoleDaoEx extends B2bRoleDao {

	List<B2bRoleDto> searchAllRoleList(Map<String, Object> map);
	

}
