package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bBillOrderDto;
import cn.cf.model.B2bBillOrder;

public interface B2bBillOrderDaoEx extends B2bBillOrderDao{

	int updateBillOrder(B2bBillOrder order);
	
	List<B2bBillOrderDto> searchCancelOrderList();
}
