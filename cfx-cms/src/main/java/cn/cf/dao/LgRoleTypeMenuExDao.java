package cn.cf.dao;

import java.util.List;

import cn.cf.dto.LgRoleMenuDto;
import cn.cf.model.LgRoleMenu;

public interface LgRoleTypeMenuExDao  {
	
	 List<LgRoleMenuDto> getRoleMenuByRolePk(String rolePk);
	
	 void deleteByLgRoleMenuPk(LgRoleMenu menu);
	 
	 int insert(LgRoleMenu model);
}
