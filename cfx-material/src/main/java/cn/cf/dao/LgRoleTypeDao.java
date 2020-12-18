/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgRoleType;
import cn.cf.dto.LgRoleTypeDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgRoleTypeDao {
	int insert(LgRoleType model);
	int update(LgRoleType model);
	int delete(String id);
	List<LgRoleTypeDto> searchGrid(Map<String, Object> map);
	List<LgRoleTypeDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgRoleTypeDto getByPk(java.lang.String pk); 
	 LgRoleTypeDto getByName(java.lang.String name); 
	 LgRoleTypeDto getByCompanyType(java.lang.Integer companyType); 

}
