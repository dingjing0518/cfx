package cn.cf.service.bill;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillOrderDto;
import cn.cf.model.B2bBillOrder;

public interface BillOrderService {

	B2bBillOrderDto getBillOrder(String orderNumber);
	
	List<B2bBillOrderDto> searchListByMap(Map<String,Object> map);
	
	List<B2bBillOrderDto> searchCancelBillOrder();
	
	void insertBillOrder(B2bBillOrder order);
	
	void updateBillOrder(B2bBillOrder order);
	
}
