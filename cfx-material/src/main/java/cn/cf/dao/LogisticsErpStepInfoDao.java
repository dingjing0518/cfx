/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LogisticsErpStepInfo;
import cn.cf.dto.LogisticsErpStepInfoDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpStepInfoDao {
	int insert(LogisticsErpStepInfo model);
	int update(LogisticsErpStepInfo model);
	int delete(String id);
	List<LogisticsErpStepInfoDto> searchGrid(Map<String, Object> map);
	List<LogisticsErpStepInfoDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LogisticsErpStepInfoDto getByPk(java.lang.String pk); 
	 LogisticsErpStepInfoDto getByLogisticsPk(java.lang.String logisticsPk); 
	 LogisticsErpStepInfoDto getByProductPk(java.lang.String productPk); 
	 LogisticsErpStepInfoDto getByProductName(java.lang.String productName); 
	 LogisticsErpStepInfoDto getByPackNumber(java.lang.String packNumber); 

}
