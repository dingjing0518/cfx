package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LgRoleDtoEx;

public interface LgRoleDaoEx extends LgRoleDao {

	int isReapet(Map<String, Object> map);

	List<LgRoleDtoEx> searchGrid(Map<String, Object> map);

	List<LgRoleDtoEx> searchRole (Map<String, Object> par);
	
	/**
	 * 匹配业务员
	 * @return
	 */
	List<String> matchMembers(Map<String, Object> par);


}
