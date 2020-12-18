package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.ExportOrderDto;
import cn.cf.model.B2bOrder;

public interface B2bOrderDaoEx extends B2bOrderDao{
	/**
	 * 订单信息(带付款方式)
	 * @param orderNumber
	 * @return
	 */
	B2bOrderDtoEx getB2bOrder(String orderNumber);
	
	List<B2bOrderDtoEx> searchB2bOrderList(Map<String,Object> map);
	
	int searchB2bOrderCounts(Map<String,Object> map);
	
	int insertBatch(List<B2bOrder> list);
	
	void updateOrder(B2bOrder o);
	
	Map<String,Object> orderStatusCounts(Map<String,Object> map);
	
	List<ExportOrderDto> exportOrderList(Map<String,Object> map);
	
	void updateCarrier(String childOrderNumber, String carrierPk, String carrierName);
	
	List<B2bOrderDto> cancelOrder();

}
