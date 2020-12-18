package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.ErpGoodsShow;
import cn.cf.model.B2bGoods;

public interface B2bGoodsDaoEx extends B2bGoodsDao{

	/**
	 * 查询商品
	 * @param b2bgoodsEx
	 * @return
	 */
	B2bGoodsDto searchGoodsIs(B2bGoodsDtoEx b2bgoodsEx);
	
	/**
	 * 清除商品价格，库存
	 * @param params
	 */
	void updateDataZero(Map<String, Object> params);

	/**
	 * 批量更新商品库存
	 * @param list
	 * @return
	 */
	int updateWeightBatch(List<B2bGoodsDtoEx> list);

	/**
	 * 批量更新商品价格
	 * @param list
	 * @return
	 */
	int updatePriceBatch(List<B2bGoodsDtoEx> list);
	
	/**
	 * 更新商品影藏状态
	 * @param list
	 * @return
	 */
	int updateIsNewProduct(List<ErpGoodsShow> list);
	
	/**
	 * 批量更新竞拍商品
	 * @param list
	 * @return
	 */
	int updateGoodsAuctionBatch(List<B2bGoodsDto> list);

	/**
	 * 更新商品
	 * @param b2bGoodsDto
	 * @return
	 */
	int updateEx(B2bGoodsDto b2bGoodsDto);

	
	/**
	 * 更新商品类型
	 * @param list
	 * @return
	 */
	int updateGoodsType(List<B2bBindGoodsDto> list);

	/**
	 * 插入商品对象
	 * @param model 商品对象
	 * @return
	 */
	int insertEx(B2bGoods model);
	
	/**
	 * 更新商品类型
	 * @param map
	 * @return
	 */
	int updateNewGoodsType(Map<String,Object> map);
	
	/**
	 * 查询商品List
	 * @param goodsDtoEx
	 * @return
	 */
	List<B2bGoodsDtoEx> searchGoodsList(B2bGoodsDtoEx goodsDtoEx);
	/**
	 * 隐藏店铺下所有商品
	 * @param storePk
	 */
	void updateShowByStorePk(String storePk);

}
