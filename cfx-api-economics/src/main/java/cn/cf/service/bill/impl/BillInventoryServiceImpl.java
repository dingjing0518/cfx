package cn.cf.service.bill.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBillInventoryDaoEx;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.model.B2bBillInventory;
import cn.cf.service.bill.BillInventoryService;
import cn.cf.util.KeyUtils;

@Service
public class BillInventoryServiceImpl implements BillInventoryService {

	@Autowired
	private B2bBillInventoryDaoEx b2bBillInventoryDao;
	
	@Override
	public void insertInventory(B2bBillInventory inventory) {
		inventory.setPk(KeyUtils.getUUID());
		b2bBillInventoryDao.insert(inventory);

	}

	@Override
	public void deleteInventory(String orderNumber) {
		b2bBillInventoryDao.deleteByOrderNumber(orderNumber);
		
	}

	@Override
	public List<B2bBillInventoryDto> searchByMap(String orderNumber,
			Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderNumber", orderNumber);
		map.put("status", status);
		return b2bBillInventoryDao.searchList(map);
	}



}
