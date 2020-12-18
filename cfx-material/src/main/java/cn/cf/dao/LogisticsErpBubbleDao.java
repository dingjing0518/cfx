/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LogisticsErpBubble;
import cn.cf.dto.LogisticsErpBubbleDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpBubbleDao {
	int insert(LogisticsErpBubble model);
	int update(LogisticsErpBubble model);
	int delete(String id);
	List<LogisticsErpBubbleDto> searchGrid(Map<String, Object> map);
	List<LogisticsErpBubbleDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LogisticsErpBubbleDto getByPk(java.lang.String pk); 
	 LogisticsErpBubbleDto getByName(java.lang.String name); 
	 LogisticsErpBubbleDto getByProductPk(java.lang.String productPk); 
	 LogisticsErpBubbleDto getByProductName(java.lang.String productName); 

}
