package cn.cf.dao;

import cn.cf.dto.LgDeliveryOrderGoodsDto;

public interface LgDeliveryOrderGoodsDaoEx extends LgDeliveryOrderGoodsDao{
	
	 void updateByPkSelective(LgDeliveryOrderGoodsDto dto);
}
