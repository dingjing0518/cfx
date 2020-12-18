/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.dto.LogisticsErpStepInfoDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpStepInfoExtDao extends LogisticsErpStepInfoDao{
	int insertLgErpStepInfo(LogisticsErpStepInfoDto dto);
	int updateLgErpStepInfo(LogisticsErpStepInfoDto Dto);
	int deleteByLogisticsPk(String logisticsPk);
}
