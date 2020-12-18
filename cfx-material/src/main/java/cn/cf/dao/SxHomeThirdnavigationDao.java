/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SxHomeThirdnavigation;
import cn.cf.dto.SxHomeThirdnavigationDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SxHomeThirdnavigationDao {
	int insert(SxHomeThirdnavigation model);
	int update(SxHomeThirdnavigation model);
	int delete(String id);
	List<SxHomeThirdnavigationDto> searchGrid(Map<String, Object> map);
	List<SxHomeThirdnavigationDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SxHomeThirdnavigationDto getByPk(java.lang.String pk); 
	 SxHomeThirdnavigationDto getBySecondNavigationPk(java.lang.String secondNavigationPk); 
	 SxHomeThirdnavigationDto getByNavigation(java.lang.String navigation); 
	 SxHomeThirdnavigationDto getByNavigationPk(java.lang.String navigationPk); 

}
