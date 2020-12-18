package cn.cf.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.entity.FirmOrder;
import cn.cf.entity.UpdateGoods;
import cn.cf.model.B2bBindGoods;

public interface B2bBindGoodsDaoEx  extends B2bBindGoodsDao{


    List<B2bBindGoodsDtoEx> getBindGoodsGridByBpk(Map<String, Object> map);

    List<B2bBindGoodsDtoEx> searchListEx(Map<String, Object> map);

    int searchGridCount(Map<String, Object> map);

    int searchGridCountByBpk(Map<String, Object> map);


    B2bBindGoodsDtoEx getByGoodPkAndBindPk(@Param("goodsPk") String goodsPk, @Param("bindPk") String bindPk);

	int updateBatch(List<UpdateGoods> glist);

	int checkBind( @Param("bindPk") String bindPk);

	List<FirmOrder> searchFirmOrder(Map<String, Object> map);
	
	int delBindGoodsByBindPk(String bindPk);
	
	int delBindGoodsByGoodsPk(@Param("goodsPk") String goodsPk);
	
	
	List<B2bBindGoodsDtoEx> searchGoodsListOnBind(Map<String, Object> map);
	
	int searchGoodsListOnBindCount(Map<String, Object> map);
	
	int checkGoodsOnUpBinding(@Param("goodsPk") String goodsPk);
	
	
	
	List<B2bBindGoodsDto> searchBindingGoods(Map<String,Object> map);
	
	int updateEx(B2bBindGoods mnodel);

	void cleanBindGoods(String bindPk);
	
}
