/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.ManageRoleAuthority;
import cn.cf.dto.ManageRoleAuthorityDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface ManageRoleAuthorityDao {
	int insert(ManageRoleAuthority model);
	int update(ManageRoleAuthority model);
	int delete(String id);
	List<ManageRoleAuthorityDto> searchGrid(Map<String, Object> map);
	List<ManageRoleAuthorityDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 ManageRoleAuthorityDto getByPk(java.lang.String pk); 
	 ManageRoleAuthorityDto getByRolePk(java.lang.String rolePk); 
	 ManageRoleAuthorityDto getByAuthorityPk(java.lang.String authorityPk); 

}
