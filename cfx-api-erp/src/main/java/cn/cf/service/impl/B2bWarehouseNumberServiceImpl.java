package cn.cf.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bWarehouseNumberDao;
import cn.cf.dto.B2bWarehouseNumberDto;
import cn.cf.service.B2bWareHouseNumberService;

@Service
public class B2bWarehouseNumberServiceImpl implements B2bWareHouseNumberService {

	@Autowired
	private B2bWarehouseNumberDao warehouseNumberDao;

	@Override
	public List<B2bWarehouseNumberDto> searchB2bWareHouseNumber(Map<String, Object> map) {
		return warehouseNumberDao.searchList(map);
	} 
	

	
}
