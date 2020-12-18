package cn.cf.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bGoodsEx;

import com.alibaba.fastjson.JSONObject;

public interface B2bGoodsService {

	/**
	 * 查询商品列表
	 * 
	 * @param map
	 * @param type
	 * @return
	 */
	PageModel<B2bGoodsDtoEx> searchGoodsList(Map<String, Object> map);

//	PageModel<B2bGoodsDtoEx> findIndex(Map<String, Object> map);

	/**
	 * 查询所有商品
	 * 
	 * @param map
	 * @param company
	 * @return
	 */
	List<B2bGoodsDtoEx> searchGoodsList(Map<String, Object> map,Sessions sessions);

	/**
	 * 当前账号是否已收藏
	 * 
	 * @param goodPk
	 * @param memberPk
	 * @return
	 */
	List<CollectionGoods> iscollected(String goodPk, String memberPk);

	/**
	 * 计算商品被收藏次数
	 * 
	 * @param goodPk
	 * @return
	 */
	Long countCollectGoods(String goodPk);

 
 

	void updateGoods(B2bGoodsEx goods);

	void insert(B2bGoodsEx goods);



	/**
	 * 上下架商品的数量
	 * 
	 * @param map
	 * @return
	 */
	List<B2bGoodsDtoEx> searchUpdownCounts(Map<String, Object> map);

	/**
	 * 更新 多个商品
	 * 
	 * @param good
	 */
	@Transactional
	RestCode updateMore(B2bGoodsEx good);

	/**
	 * 导入：检验商品参数是否为空
	 * 
	 * @param json
	 * @return
	 */
	RestCode IsGoodsParamNull(JSONObject json);

  
	/**
	 * 根据商品查询商品对应店铺是否开启中
	 * 
	 * @param goodsPk
	 * @return 商品信息
	 */
	B2bGoodsDto getOpenDetails(String goodsPk);

	/**
	 * 业务员商品：可见类型查询
	 * 
	 * @param map
	 * @return
	 */
	Map<String, Object> getGoodsByMember(Map<String, Object> map);

	PageModel<B2bGoodsDtoEx> searchGoodsListBySalesMan(Map<String, Object> param);

 
	int searchGridCount(Map<String, Object> map);

	// 查询供应商销售中的商品数量
	int searchSaleGoodsCounts(Map<String, Object> map);

  
	
	 
	
	/**
	 * 推荐商品列表
	 * @param map
	 * @return
	 */
	List<B2bGoodsDtoEx> searchRecomendGoodsList(Map<String,Object> map);
	
	/**
	 * 根据多个商品pk，返回商品详情
	 * @param pk
	 * @return
	 */
		public List<B2bGoodsDto> getGoodDetailsByPks(String pks);


	B2bGoodsDto getDetailsById(String pk);
}
