package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bReserveOrderDto;
import cn.cf.model.B2bReserveOrder;
import cn.cf.model.B2bReserveOrderEx;

public interface B2bReserveOrderDaoEx extends B2bReserveOrderDao{

	int insertBatch(List<B2bReserveOrder> list);

	List<B2bReserveOrderEx> searchReserveOrderList(Map<String, Object> map);

	int searchReserveOrderCount(Map<String, Object> map);

	Map<String, Object> searchReserveStatusCounts(Map<String, Object> map);

	int updateReserve(Map<String, Object> map);

	List<B2bReserveOrderDto> searchReserveOrderListForToday();
}
