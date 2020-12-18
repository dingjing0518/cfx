package cn.cf.service;

import java.util.List;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.B2bBindDtoEx;
import cn.cf.entity.ErpGoodsShow;


public interface B2bGoodsService {

	/**
	 * 更新商品数据
	 * @param storePk  店铺pk
	 * @param clearKuCun  是否清除库存
	 * @param clearPrice  是否清除价格
	 * @param ckearUpdown 是否清除上下架
	 * @param clearIsNewPrice   是否清除隐藏状态
	 */
	void updateDataZero(String storePk, boolean clearKuCun, boolean clearPrice,boolean ckearUpdown, boolean clearIsNewPrice);
	
	
	/**
	 * 批量同步商品数据
	 * @param insertList 新增商品列表
	 * @param updateList 修改商品列表
	 * @param companyPk 营销公司
	 * @param storePk店铺
	 * @param flag true 开店 false 关店
	 */
	void insertBatch(List<B2bGoodsDtoEx> insertList, List<B2bGoodsDtoEx> updateList,String companyPk, String storePk,Boolean flag);
	
	
	/**
	 * 批量同步库存
	 * @param list
	 * @return
	 */
	int updateWeightBatch(List<B2bGoodsDtoEx> list);
	
	
	/**
	 * 批量同步价格
	 * @param list
	 * @return
	 */
	int updatePriceBatch(List<B2bGoodsDtoEx> list);
	
	
	/**
	 * 批量隐藏产品
	 * @param list
	 * @return
	 */
	int updateIsNewProductBatch(List<ErpGoodsShow> list);
	
	
	/**
	 * 批量同步竞拍商品
	 * @param list
	 * @return
	 */
	int updateGoodsAuctionBatch(List<B2bGoodsDto> list);
	
	
	/**
	 * 批量同步捆绑商品
	 * @param list
	 * @return
	 */
	int updateGoodsBindBatch(List<B2bBindDtoEx> list);
	
	
	/**
	 * 批号 包装批号 等级  区分号 店铺 查询某个商品
	 * @param b2bgoodsEx
	 * @return
	 */
	B2bGoodsDto searchGoodsIs(B2bGoodsDtoEx b2bgoodsEx);
	
	
	/**
	 * 查询商品
	 * @param b2bBindGoodsDto
	 * @param storePk
	 * @return
	 */
	B2bGoodsDto getB2bGoodsInfoByBindGoods(B2bBindGoodsDtoEx b2bBindGoodsDto, String storePk);
	
	
	/**
	  * 修改某一类商品类型
	  * @param storePk
	  * @param oldType
	  * @param newType
	  * @return
	  */
	int updateGoodsType(String storePk,String oldType,String newType);
	
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
