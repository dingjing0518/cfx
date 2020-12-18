/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bMemberRole;
import cn.cf.dto.B2bMemberRoleDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberRoleDao {
	int insert(B2bMemberRole model);
	int update(B2bMemberRole model);
	int delete(String id);
	List<B2bMemberRoleDto> searchGrid(Map<String, Object> map);
	List<B2bMemberRoleDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bMemberRoleDto getByPk(java.lang.String pk); 
	 List<B2bMemberRoleDto> getByMemberPk(java.lang.String memberPk); 
	 B2bMemberRoleDto getByRolePk(java.lang.String rolePk); 

}
