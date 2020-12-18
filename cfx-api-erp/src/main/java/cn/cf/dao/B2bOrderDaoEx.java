package cn.cf.dao;

import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderDtoEx;

public interface B2bOrderDaoEx extends B2bOrderDao{

	B2bOrderDtoEx getByOrderNumberEx(String orderNumber);

	void updateOrderStatusAndAmount(B2bOrderDto orderDto);

}
