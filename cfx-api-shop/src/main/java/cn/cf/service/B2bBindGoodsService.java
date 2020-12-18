package cn.cf.service;


import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;

public interface B2bBindGoodsService {
  
    
    
    /**
     * 商品设置为拼团商品
     * @param bindPk 拼团活动pk
     * @param goodsDtoEx  商品信息
     * @return
     */
    boolean upToBindGoods(String bindPk,B2bGoodsDtoEx goodsDtoEx);
    
    
    /**
     * 根据bindPk查询拼团商品列表
     * @param map
     * @return
     */
    List<B2bBindGoodsDto> getBindGoodList(Map<String, Object> map);
    
    


	
	
	boolean delBindGoodsByGoodsPk(String goodsPk);
	
	 	

 
	
	 
	
	B2bBindGoodsDtoEx getBindGoodDtoExByGoodsPk(String goodsPk);
	
 
	
}
