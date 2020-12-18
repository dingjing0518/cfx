/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LgMenuDto;
import cn.cf.model.LgMenu;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgMenuDao {
	int insert(LgMenu model);
	int update(LgMenu model);
	int delete(String id);
	List<LgMenuDto> searchGrid(Map<String, Object> map);
	List<LgMenuDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgMenuDto getByPk(java.lang.String pk); 
	 LgMenuDto getByParentPk(java.lang.String parentPk); 
	 LgMenuDto getByName(java.lang.String name); 
	 LgMenuDto getByDisplayName(java.lang.String displayName); 
	 LgMenuDto getByUrl(java.lang.String url); 
	 LgMenuDto getByImage(java.lang.String image);
	List<LgMenuDto> searchMenuList(Map<String, Object> map); 

}
