/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bWarehouseNumber;
import cn.cf.dto.B2bWarehouseNumberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bWarehouseNumberDao {
	int insert(B2bWarehouseNumber model);
	int update(B2bWarehouseNumber model);
	int delete(String id);
	List<B2bWarehouseNumberDto> searchGrid(Map<String, Object> map);
	List<B2bWarehouseNumberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bWarehouseNumberDto getByPk(java.lang.String pk); 
	 B2bWarehouseNumberDto getByType(java.lang.String type); 
	 B2bWarehouseNumberDto getByNumber(java.lang.String number); 
	 B2bWarehouseNumberDto getByStorePk(java.lang.String storePk);

}
