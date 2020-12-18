/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bRole;
import cn.cf.dto.B2bRoleDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bRoleDao {
	int insert(B2bRole model);
	int update(B2bRole model);
	int delete(String id);
	List<B2bRoleDto> searchGrid(Map<String, Object> map);
	List<B2bRoleDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bRoleDto getByPk(java.lang.String pk); 
	 B2bRoleDto getByName(java.lang.String name); 

}
