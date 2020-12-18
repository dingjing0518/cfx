/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgTrackDetail;
import cn.cf.dto.LgTrackDetailDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgTrackDetailDao {
	int insert(LgTrackDetail model);
	int update(LgTrackDetail model);
	int delete(String id);
	List<LgTrackDetailDto> searchGrid(Map<String, Object> map);
	List<LgTrackDetailDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgTrackDetailDto getByPk(java.lang.String pk); 
	 LgTrackDetailDto getByDeliveryPk(java.lang.String deliveryPk); 
	 LgTrackDetailDto getByStepDetail(java.lang.String stepDetail); 

}
