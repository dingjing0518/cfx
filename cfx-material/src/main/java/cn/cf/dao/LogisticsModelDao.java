/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LogisticsModel;
import cn.cf.dto.LogisticsModelDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsModelDao {
	int insert(LogisticsModel model);
	int update(LogisticsModel model);
	int delete(String id);
	List<LogisticsModelDto> searchGrid(Map<String, Object> map);
	List<LogisticsModelDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LogisticsModelDto getByPk(java.lang.String pk); 
	 LogisticsModelDto getByName(java.lang.String name); 
	 LogisticsModelDto getByType(java.lang.String type); 

}
