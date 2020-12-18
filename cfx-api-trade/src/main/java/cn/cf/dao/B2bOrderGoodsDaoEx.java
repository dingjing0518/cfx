package cn.cf.dao;

import java.util.List;
import java.util.Map;


import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.model.B2bOrderGoods;

public interface B2bOrderGoodsDaoEx extends B2bOrderGoodsDao{

	List<B2bOrderGoodsDtoEx> searchOrderGoodsList(Map<String,Object> map);
	
	int insertBatch(List<B2bOrderGoods> list);
	
	int updateOrderStatus(Map<String,Object> map);
	

	void updateCarrier(Map<String,Object> map);


	B2bOrderGoodsDtoEx getbyChildContractNo( String childContractNo);
	
	void updateOrderGoods(B2bOrderGoods o);
	
}
