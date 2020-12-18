/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.dto.LogisticsErpBubbleDto;
import cn.cf.dto.LogisticsErpBubbleDtoEx;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpBubbleDaoEx extends LogisticsErpBubbleDao  {

	int searchExistence(LogisticsErpBubbleDtoEx dto);

	List<LogisticsErpBubbleDto> searchBubbleList(Map<String, Object> map);

	int searchBubbleCount(Map<String, Object> map);

	Map<String, Object> searchBubbleCounts(Map<String, Object> map);
	 

}
