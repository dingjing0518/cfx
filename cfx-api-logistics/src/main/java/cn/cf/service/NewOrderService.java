package cn.cf.service;

import cn.cf.dto.B2bOrderDto;


public interface NewOrderService {
	
	/**
	 * 查询订单详情
	 * @param orderNumber  订单编号
	 * @return
	 */
	B2bOrderDto searchOrderDetails(String orderNumber);
}
