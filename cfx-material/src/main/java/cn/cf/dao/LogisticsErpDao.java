/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LogisticsErp;
import cn.cf.dto.LogisticsErpDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LogisticsErpDao {
	int insert(LogisticsErp model);
	int update(LogisticsErp model);
	int delete(String id);
	List<LogisticsErpDto> searchGrid(Map<String, Object> map);
	List<LogisticsErpDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LogisticsErpDto getByPk(java.lang.String pk); 
	 LogisticsErpDto getByProvinceName(java.lang.String provinceName); 
	 LogisticsErpDto getByProvince(java.lang.String province); 
	 LogisticsErpDto getByCityName(java.lang.String cityName); 
	 LogisticsErpDto getByCity(java.lang.String city); 
	 LogisticsErpDto getByAreaName(java.lang.String areaName); 
	 LogisticsErpDto getByArea(java.lang.String area); 
	 LogisticsErpDto getByTown(java.lang.String town); 
	 LogisticsErpDto getByTownName(java.lang.String townName); 
	 LogisticsErpDto getByPlantPk(java.lang.String plantPk); 
	 LogisticsErpDto getByPlantName(java.lang.String plantName);
	 LogisticsErpDto getByStorePk(java.lang.String storePk);
	 

}
