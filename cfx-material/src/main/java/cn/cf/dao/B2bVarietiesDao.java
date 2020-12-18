/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bVarieties;
import cn.cf.dto.B2bVarietiesDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bVarietiesDao {
	int insert(B2bVarieties model);
	int update(B2bVarieties model);
	int delete(String id);
	List<B2bVarietiesDto> searchGrid(Map<String, Object> map);
	List<B2bVarietiesDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bVarietiesDto getByPk(java.lang.String pk); 
	 B2bVarietiesDto getByName(java.lang.String name); 
	 B2bVarietiesDto getByParentPk(java.lang.String parentPk); 

}
