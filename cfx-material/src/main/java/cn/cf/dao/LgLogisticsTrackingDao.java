/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgLogisticsTracking;
import cn.cf.dto.LgLogisticsTrackingDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgLogisticsTrackingDao {
	int insert(LgLogisticsTracking model);
	int update(LgLogisticsTracking model);
	int delete(String id);
	List<LgLogisticsTrackingDto> searchGrid(Map<String, Object> map);
	List<LgLogisticsTrackingDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgLogisticsTrackingDto getByPk(java.lang.String pk); 
	 LgLogisticsTrackingDto getByProvinceName(java.lang.String provinceName); 
	 LgLogisticsTrackingDto getByProvincePk(java.lang.String provincePk); 
	 LgLogisticsTrackingDto getByCityName(java.lang.String cityName); 
	 LgLogisticsTrackingDto getByCityPk(java.lang.String cityPk); 
	 LgLogisticsTrackingDto getByAreaName(java.lang.String areaName); 
	 LgLogisticsTrackingDto getByAreaPk(java.lang.String areaPk); 
	 LgLogisticsTrackingDto getByArrivalTime(java.lang.String arrivalTime); 
	 LgLogisticsTrackingDto getByMeno(java.lang.String meno); 
	 LgLogisticsTrackingDto getByOrderPk(java.lang.String orderPk); 

}
