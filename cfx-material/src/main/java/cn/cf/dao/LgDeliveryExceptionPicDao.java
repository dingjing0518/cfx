/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgDeliveryExceptionPic;
import cn.cf.dto.LgDeliveryExceptionPicDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgDeliveryExceptionPicDao {
	int insert(LgDeliveryExceptionPic model);
	int update(LgDeliveryExceptionPic model);
	int delete(String id);
	List<LgDeliveryExceptionPicDto> searchGrid(Map<String, Object> map);
	List<LgDeliveryExceptionPicDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgDeliveryExceptionPicDto getByPk(java.lang.String pk); 
	 LgDeliveryExceptionPicDto getByDeliveryPk(java.lang.String deliveryPk); 
	 LgDeliveryExceptionPicDto getByExceptionPicUrl(java.lang.String exceptionPicUrl); 

}
