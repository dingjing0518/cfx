/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.ManageAccount;
import cn.cf.dto.ManageAccountDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface ManageAccountDao {
	int insert(ManageAccount model);
	int update(ManageAccount model);
	int delete(String id);
	List<ManageAccountDto> searchGrid(Map<String, Object> map);
	List<ManageAccountDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 ManageAccountDto getByPk(java.lang.String pk); 
	 ManageAccountDto getByAccount(java.lang.String account); 
	 ManageAccountDto getByPassword(java.lang.String password); 
	 ManageAccountDto getByName(java.lang.String name); 
	 ManageAccountDto getByEmail(java.lang.String email); 
	 ManageAccountDto getByRolePk(java.lang.String rolePk); 
	 ManageAccountDto getByMobile(java.lang.String mobile); 

}
