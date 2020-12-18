package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.ManageRoleDto;

public interface ManageRoleExtDao extends ManageRoleDao{

	/**
	 * 根据名称查询角色
	 * @param map
	 * @return
	 */
	List<ManageRoleDto> getManageRoleByName(Map<String, Object> map);
	
	/**
	 * 根据角色Pk查询
	 * @param rolePk
	 * @return
	 */
	ManageRoleDto getManageRoleByPk(String rolePk);
}
