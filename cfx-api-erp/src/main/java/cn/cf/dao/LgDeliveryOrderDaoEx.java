package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LgCarDto;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgDeliveryOrderGoodsDto;
import cn.cf.dto.LgDriverDto;
import cn.cf.model.LgDeliveryOrder;

public interface LgDeliveryOrderDaoEx extends LgDeliveryOrderDao {

	LgDeliveryOrderDto getByMap(Map<String, Object> map);

	LgCarDto getCarByMap(Map<String, Object> map);

	LgDriverDto getDriverByMap(Map<String, Object> map);

	void BatchDeliveryGoods(List<LgDeliveryOrderGoodsDto> list);

	void updatelgDeliveryOrder(LgDeliveryOrder lgDeliveryOrder);
	
	void insertDeliveryGoods(LgDeliveryOrderGoodsDto dto);
	
	void updateDeliveryGoods(LgDeliveryOrderGoodsDto dto);

}
