package cn.cf.service;


import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bCartDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bBindGoods;
import cn.cf.entity.FirmOrder;

public interface B2bBindGoodsService {
    /**
     * 分页查询拼团商品的列表
     *
     * @param map
     * @return
     */
    PageModel<B2bBindGoodsDtoEx> searchBindGoodList(Map<String, Object> map);
    
    
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
    
    
    /**
     * 删除团购商品
     * @param bindPk 团购活动pk
     * @return
     */
    boolean delBindGoodsByBindPk(String bindPk);
    List<FirmOrder> searchFirmOrder(String bindPk, double price);


	B2bBindGoodsDtoEx getByGoodPkAndBindPk(String goodsPk, String bindPk);
	
	
	boolean delBindGoodsByGoodsPk(String goodsPk);
	
	
	/**
	 * 拼团中商品
	 * @param map
	 * @return
	 */
	List<B2bBindGoodsDtoEx> searchGoodsListOnBind(Map<String, Object> map);
	
	int searchGoodsListOnBindCount(Map<String, Object> map);

/**
 * 团购产品 库存
 * @param carts
 * @return
 */
	List<B2bCartDtoEx> searchBindGoodsBoxes(List<B2bCartDtoEx> carts);
	
	boolean checkGoodsOnUpBinding(String goodsPk);
	
	
	void updateEx(B2bBindGoods model);
	
}
