package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.LgRoleMenuDto;
import cn.cf.dto.LgRoleTypeDto;
import cn.cf.model.LgRoleType;

public interface LgRoleTypeExDao extends LgRoleTypeDao{
	
	List<LgRoleTypeDto> searchGridEx(Map<String, Object> map);
	
	int searchGridCountEx(Map<String, Object> map);
	
	/**
	 * 根据RolePk查询角色菜单
	 * @param rolePk
	 * @return
	 */
	List<LgRoleMenuDto> getRoleMenuByRolePk(String rolePk);
	
	int updateEx(LgRoleType model);
	
}
