/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysRegions;
import cn.cf.dto.SysRegionsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysRegionsDao {
	int insert(SysRegions model);
	int update(SysRegions model);
	int delete(String id);
	List<SysRegionsDto> searchGrid(Map<String, Object> map);
	List<SysRegionsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysRegionsDto getByPk(java.lang.String pk); 
	 SysRegionsDto getByName(java.lang.String name); 
	 List<SysRegionsDto> getByParentPk(java.lang.String parentPk); 
	 SysRegionsDto getByIsVisable(java.lang.String isVisable); 
	 SysRegionsDto getByNameExM(Map<String, Object> map); 
}
