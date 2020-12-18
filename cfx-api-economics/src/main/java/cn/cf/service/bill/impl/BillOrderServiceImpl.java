package cn.cf.service.bill.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBillOrderDaoEx;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.model.B2bBillOrder;
import cn.cf.service.bill.BillOrderService;

@Service
public class BillOrderServiceImpl implements BillOrderService {

	@Autowired
	private B2bBillOrderDaoEx b2bBillOrderDaoEx;
	
	@Override
	public B2bBillOrderDto getBillOrder(String orderNumber) {
		return b2bBillOrderDaoEx.getByOrderNumber(orderNumber);
	}

	@Override
	public void insertBillOrder(B2bBillOrder order) {
		order.setInsertTime(new Date());
		b2bBillOrderDaoEx.insert(order);

	}

	@Override
	public void updateBillOrder(B2bBillOrder order) {
		b2bBillOrderDaoEx.update(order);
	}


	@Override
	public List<B2bBillOrderDto> searchListByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return b2bBillOrderDaoEx.searchList(map);
	}

	@Override
	public List<B2bBillOrderDto> searchCancelBillOrder() {
		// TODO Auto-generated method stub
		return b2bBillOrderDaoEx.searchCancelOrderList();
	}

}
