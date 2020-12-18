package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.PageModel;
import cn.cf.dto.LgRoleDto;
import cn.cf.dto.LgRoleDtoEx;

public interface LgRoleService {

	/**
	 * 验证角色是否重复
	 * @param map
	 * @return
	 */
	boolean isReapet(Map<String, Object> map);

	/**
	 * 添加角色
	 * @param dto
	 */
	void addRole(LgRoleDto dto);

	/**
	 * 更新角色
	 * @param dto
	 */
	void updateRole(LgRoleDto dto);

	
	/**
	 * 查询角色列表
	 * @param par
	 * @return
	 */
	PageModel<LgRoleDtoEx> searchLgRoleList(Map<String, Object> par);

	/**
	 * 根据pk查询角色
	 * @param pk
	 * @return
	 */
	LgRoleDto getByPk(String pk);

	/**
	 * 查询角色
	 * @param par
	 * @return
	 */
	List<LgRoleDtoEx> searchLgRole(Map<String, Object> par);


}
