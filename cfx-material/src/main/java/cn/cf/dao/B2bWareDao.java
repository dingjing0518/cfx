/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bWare;
import cn.cf.dto.B2bWareDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bWareDao {
	int insert(B2bWare model);
	int update(B2bWare model);
	int delete(String id);
	List<B2bWareDto> searchGrid(Map<String, Object> map);
	List<B2bWareDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bWareDto getByPk(java.lang.String pk); 
	 B2bWareDto getByName(java.lang.String name); 
	 B2bWareDto getByStorePk(java.lang.String storePk); 
	 B2bWareDto getByAddress(java.lang.String address); 
	 B2bWareDto getByWareCode(java.lang.String wareCode); 
	 B2bWareDto getByContactsTel(java.lang.String contactsTel); 
	 B2bWareDto getByContacts(java.lang.String contacts); 
	 B2bWareDto getByLandline(java.lang.String landline); 

}
