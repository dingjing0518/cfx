package cn.cf.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.FirmOrder;
import cn.cf.model.B2bGoodsEx;

import com.alibaba.fastjson.JSONObject;

public interface B2bGoodsService {

 

 
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

	/**
	 * 查询商品详情
	 * 
	 * @param pk
	 * @return
	 */
	B2bGoodsDtoEx getDetailsById(String pk);

	 

	void updateGoods(B2bGoodsEx goods);

	void insert(B2bGoodsEx goods);


 

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



 

	 

	List<FirmOrder> searchFirmOrder(String goodsPk, int boxs,Double weight,String offerPk);

	
	/**
	 * 供拼图选择的商品列表
	 * @param map
	 * @return
	 */
	List<B2bGoodsDtoEx> searchBindGoodsList(Map<String, Object> map);
	
	
	int searchBindGoodsCount(Map<String, Object> map);
	
	B2bGoodsDto getB2bGoods(String pk);
}
