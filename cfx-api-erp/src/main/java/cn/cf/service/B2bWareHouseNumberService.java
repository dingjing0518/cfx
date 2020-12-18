package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bWarehouseNumberDto;

public interface B2bWareHouseNumberService {
	
	/**
	 * 查询B2bWareHouseNumber List
	 * @param map
	 * @return
	 */
	List<B2bWarehouseNumberDto> searchB2bWareHouseNumber(Map<String, Object> map);

}
