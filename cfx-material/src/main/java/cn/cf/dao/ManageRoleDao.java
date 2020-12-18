/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.ManageRole;
import cn.cf.dto.ManageRoleDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface ManageRoleDao {
	int insert(ManageRole model);
	int update(ManageRole model);
	int delete(String id);
	List<ManageRoleDto> searchGrid(Map<String, Object> map);
	List<ManageRoleDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 ManageRoleDto getByPk(java.lang.String pk); 
	 ManageRoleDto getByName(java.lang.String name); 

}
