/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bRoleMenu;
import cn.cf.dto.B2bRoleMenuDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bRoleMenuDao {
	int insert(B2bRoleMenu model);
	int update(B2bRoleMenu model);
	int delete(String id);
	List<B2bRoleMenuDto> searchGrid(Map<String, Object> map);
	List<B2bRoleMenuDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bRoleMenuDto getByPk(java.lang.String pk); 
	 B2bRoleMenuDto getByRolePk(java.lang.String rolePk); 
	 B2bRoleMenuDto getByMenuPk(java.lang.String menuPk); 

}
