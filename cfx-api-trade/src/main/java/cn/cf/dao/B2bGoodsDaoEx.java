package cn.cf.dao;

import java.util.List;
import java.util.Map;


import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.FirmOrder;
import cn.cf.entity.UpdateGoods;
import cn.cf.model.B2bGoods;

public interface B2bGoodsDaoEx  extends B2bGoodsDao{
	

	void updateGoods(B2bGoods goods);
	

	
	B2bGoodsDtoEx getDetailsById(String pk);

	List<B2bGoodsDtoEx> distributionGoods(Map<String, Object> map);


	
	int updateBatch(List<UpdateGoods> glist);


	boolean updateDataZero(Map<String, Object> params);
	
	B2bGoodsDto getOpenDetails(String goodsPk);

 
	
	

	List<FirmOrder> searchFirmOrder(Map<String, Object> map);
	
	List<B2bGoodsDtoEx> searchBindGoodsList(Map<String, Object> map);
	
	int searchBindGoodsCount(Map<String, Object> map);

	
	
	
}
