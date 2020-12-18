/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LogisticsErpBubbleStepInfo;
import cn.cf.dto.LogisticsErpBubbleStepInfoDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpBubbleStepInfoDao {
	int insert(LogisticsErpBubbleStepInfo model);
	int update(LogisticsErpBubbleStepInfo model);
	int delete(String id);
	List<LogisticsErpBubbleStepInfoDto> searchGrid(Map<String, Object> map);
	List<LogisticsErpBubbleStepInfoDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LogisticsErpBubbleStepInfoDto getByPk(java.lang.String pk); 
	 LogisticsErpBubbleStepInfoDto getByBubblePk(java.lang.String bubblePk); 

}
