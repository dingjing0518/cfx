/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SxHomeSecondnavigation;
import cn.cf.dto.SxHomeSecondnavigationDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SxHomeSecondnavigationDao {
	int insert(SxHomeSecondnavigation model);
	int update(SxHomeSecondnavigation model);
	int delete(String id);
	List<SxHomeSecondnavigationDto> searchGrid(Map<String, Object> map);
	List<SxHomeSecondnavigationDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SxHomeSecondnavigationDto getByPk(java.lang.String pk); 
	 SxHomeSecondnavigationDto getByNavigation(java.lang.String navigation); 
	 SxHomeSecondnavigationDto getByNavigationPk(java.lang.String navigationPk); 
	 SxHomeSecondnavigationDto getBySort(java.lang.Integer sort); 
	 SxHomeSecondnavigationDto getByIsVisable(java.lang.Integer isVisable); 
	 SxHomeSecondnavigationDto getByIsDelete(java.lang.Integer isDelete); 
	 SxHomeSecondnavigationDto getByParentNavigation(java.lang.String parentNavigation); 
	 SxHomeSecondnavigationDto getByUrl(java.lang.String url); 

}
