/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bPayment;
import cn.cf.dto.B2bPaymentDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bPaymentDao {
	int insert(B2bPayment model);
	int update(B2bPayment model);
	int delete(String id);
	List<B2bPaymentDto> searchGrid(Map<String, Object> map);
	List<B2bPaymentDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bPaymentDto getByPk(java.lang.String pk); 
	 B2bPaymentDto getByName(java.lang.String name); 

}
