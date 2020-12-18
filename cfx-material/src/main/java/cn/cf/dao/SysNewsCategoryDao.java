/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysNewsCategory;
import cn.cf.dto.SysNewsCategoryDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysNewsCategoryDao {
	int insert(SysNewsCategory model);
	int update(SysNewsCategory model);
	int delete(String id);
	List<SysNewsCategoryDto> searchGrid(Map<String, Object> map);
	List<SysNewsCategoryDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysNewsCategoryDto getByPk(java.lang.String pk); 
	 List<SysNewsCategoryDto> getByNewsPk(java.lang.String newsPk); 
	 SysNewsCategoryDto getByCategoryPk(java.lang.String categoryPk); 

}
