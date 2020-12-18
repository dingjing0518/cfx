/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bSpec;
import cn.cf.dto.B2bSpecDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bSpecDao {
	int insert(B2bSpec model);
	int update(B2bSpec model);
	int delete(String id);
	List<B2bSpecDto> searchGrid(Map<String, Object> map);
	List<B2bSpecDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bSpecDto getByPk(java.lang.String pk); 
	 B2bSpecDto getByName(java.lang.String name); 
	 B2bSpecDto getByParentPk(java.lang.String parentPk); 

}
