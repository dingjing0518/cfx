/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LogisticsErpBubbleStepInfoDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpBubbleStepInfoDaoEx extends LogisticsErpBubbleStepInfoDao {

	void deleteByBubblePk(String bubblePk);

	List<LogisticsErpBubbleStepInfoDto> find(Map<String,Object> map);

	List<LogisticsErpBubbleStepInfoDto> searchbubbleStepInfoList(String bubblePk);
	 
}
