/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysHelpsCategory;
import cn.cf.dto.SysHelpsCategoryDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHelpsCategoryDao {
	int insert(SysHelpsCategory model);
	int update(SysHelpsCategory model);
	int delete(String id);
	List<SysHelpsCategoryDto> searchGrid(Map<String, Object> map);
	List<SysHelpsCategoryDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHelpsCategoryDto getByPk(java.lang.String pk); 
	 List<SysHelpsCategoryDto> getByName(java.lang.String name); 

}
