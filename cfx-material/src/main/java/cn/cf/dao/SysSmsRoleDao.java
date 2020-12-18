/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysSmsRole;
import cn.cf.dto.SysSmsRoleDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysSmsRoleDao {
	int insert(SysSmsRole model);
	int update(SysSmsRole model);
	int delete(String id);
	List<SysSmsRoleDto> searchGrid(Map<String, Object> map);
	List<SysSmsRoleDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysSmsRoleDto getByPk(java.lang.String pk); 
	 SysSmsRoleDto getBySmsName(java.lang.String smsName); 
	 SysSmsRoleDto getByRolePk(java.lang.String rolePk); 

}
