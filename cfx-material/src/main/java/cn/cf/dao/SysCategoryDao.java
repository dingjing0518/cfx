/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysCategory;
import cn.cf.dto.SysCategoryDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysCategoryDao {
	int insert(SysCategory model);
	int update(SysCategory model);
	int delete(String id);
	List<SysCategoryDto> searchGrid(Map<String, Object> map);
	List<SysCategoryDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysCategoryDto getByPk(java.lang.String pk); 
	 SysCategoryDto getByName(java.lang.String name); 

}
