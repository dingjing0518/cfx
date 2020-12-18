/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysLivebroadCategory;
import cn.cf.dto.SysLivebroadCategoryDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysLivebroadCategoryDao {
	int insert(SysLivebroadCategory model);
	int update(SysLivebroadCategory model);
	int delete(String id);
	List<SysLivebroadCategoryDto> searchGrid(Map<String, Object> map);
	List<SysLivebroadCategoryDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysLivebroadCategoryDto getByPk(java.lang.String pk); 
	 SysLivebroadCategoryDto getByName(java.lang.String name); 
	 SysLivebroadCategoryDto getByIsVisable(java.lang.String isVisable); 

}
