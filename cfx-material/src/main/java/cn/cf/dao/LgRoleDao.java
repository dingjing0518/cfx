package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LgRoleDto;
import cn.cf.model.LgRole;

public interface LgRoleDao {
	int insert(LgRole model);
	int update(LgRole model);
	List<LgRoleDto> searchList(Map<String, Object> map);
	LgRoleDto getByPk(java.lang.String pk);
	LgRoleDto getByName(java.lang.String name);
	
	int searchGridCount(Map<String, Object> map);
	/**
	 * 匹配业务员
	 * @return
	 */
	List<String> matchMembersM(Map<String, Object> par);
}
