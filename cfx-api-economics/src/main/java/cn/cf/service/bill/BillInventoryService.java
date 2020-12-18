package cn.cf.service.bill;

import java.util.List;

import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.model.B2bBillInventory;

public interface BillInventoryService {
	
	void deleteInventory(String orderNumber);
	
	void insertInventory(B2bBillInventory inventory);

	List<B2bBillInventoryDto> searchByMap(String orderNumber,Integer status);
}
