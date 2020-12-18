package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.ManageAuthorityDto;
import cn.cf.entity.BtnTree;
import cn.cf.model.ManageRoleAuthority;
import org.apache.ibatis.annotations.Param;

public interface ManageAuthorityExtDao extends ManageAuthorityDao{
	
	/**
	 * 根据条件查询权限列表
	 * @param map
	 * @return
	 */
	List<ManageAuthorityDto> getManageAuthorityByAccount(Map<String, Object> map);
	/**
	 * 通过角色PK查询权限列表
	 * @param map
	 * @return
	 */
	List<ManageAuthorityDto> getBtnManageAuthorityByRolePk(Map<String, Object> map);


	/**
	 * 通过角色PK查询列显示权限列表
	 * @param rolePk
	 * @return
	 */
	List<ManageAuthorityDto> getColManageAuthorityByRolePk(@Param("rolePk") String rolePk);


	/**
	 * 通过角色PK和PartentPk查询权限列表
	 * @param map
	 * @return
	 */
	List<ManageAuthorityDto> getBtnByPartentPkRolePk(Map<String, Object> map);

	/**
	 * 根据PartentPk查询子目录
	 * @param map
	 * @return
	 */
	List<BtnTree> getBtnByPartentPk(Map<String, Object> map);
	/**
	 * 通过角色PK查询权限列表
	 * @param rolePk
	 * @return
	 */
	List<ManageAuthorityDto> getManageAuthorityByRolePk(String rolePk);


	int deleteByManagRolePk(ManageRoleAuthority mra);
	int insertMaRoAu(ManageRoleAuthority mra);

	/**
	 *查询是否存在配置的角色权限
	 */

	int isAuthorityCount(@Param(value="rolePk") String rolePk);

	/**
	 * 获取所有权限列的权限名
	 * @return
	 */
	List<String> getListName();
}
