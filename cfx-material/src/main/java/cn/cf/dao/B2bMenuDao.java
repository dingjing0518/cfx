/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bMenu;
import cn.cf.dto.B2bMenuDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMenuDao {
	int insert(B2bMenu model);
	int update(B2bMenu model);
	int delete(String id);
	List<B2bMenuDto> searchGrid(Map<String, Object> map);
	List<B2bMenuDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bMenuDto getByPk(java.lang.String pk); 
	 B2bMenuDto getByParentPk(java.lang.String parentPk); 
	 B2bMenuDto getByName(java.lang.String name); 
	 B2bMenuDto getByDisplayName(java.lang.String displayName); 

}
