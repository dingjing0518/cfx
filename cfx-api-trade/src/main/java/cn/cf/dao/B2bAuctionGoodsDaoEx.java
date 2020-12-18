package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.model.B2bAuctionGoods;


public interface B2bAuctionGoodsDaoEx extends B2bAuctionGoodsDao{

	List<B2bAuctionGoodsDto> searchAuctionGoods(Map<String, Object> map);
	
	/**
	 * 查询商品正在进行中的场次
	 * @param auctionMap
	 * @return
	 */
	List<B2bAuctionGoodsDto> searchAuctingByGoodsPk(Map<String, Object> auctionMap);


	int countAuctionGoodsGrid(Map<String, Object> map);

	/**
	 * 检查改场次竞拍活动有没有结束
	 * @param pk 活动场次pk
	 * @return
	 */
	B2bAuctionGoodsDto IsAuctionEffective(String pk);
	

	/**
	 * 查询竞拍中的商品
	 * @param map
	 * @return
	 */
	List<B2bAuctionGoodsDtoEx> searchAuctionGoodsGridNew(Map<String, Object> map);
	
	
	/**
	 * 查询竞拍中的商品的数量
	 * @param map
	 * @return
	 */
	int countAuctionGoodsGridNew(Map<String, Object> map);
	
	
	/**
	 * 竞拍中商品更新为正常商品
	 * @param goodsPk
	 * @return
	 */
	int downToNormal(String goodsPk);
	
	/**
	 * 更新部分字段
	 * @param map
	 * @return
	 */
	int updateEx(B2bAuctionGoods model);
	
	
	/**
	 * 代拍 - 查询竞拍中的商品
	 * @param map
	 * @return
	 */
	List<B2bAuctionGoodsDtoEx> searchAuctionGoodsGridNewByMember(Map<String, Object> map);
	
	/**
	 * 查询竞拍中的商品的数量
	 * @param map
	 * @return
	 */
	int countAuctionGoodsGridNewByMember(Map<String, Object> map);
	
	
	List<B2bAuctionGoodsDtoEx> getAuctionGoodsByLately();
	
	List<B2bAuctionGoodsDtoEx> getAuctionGoodsByLately2();
	
	B2bAuctionGoodsDtoEx getInfoByAuctionPk(Map<String, Object> map);
	
	
	B2bAuctionGoodsDtoEx checkGoodsAuctionStatus(String goodsPk);
	
	int deleteByPk(String pk);
	
	
	B2bAuctionGoodsDtoEx getAuctionByGoodsPk(String goodsPk);
	
	List<B2bAuctionGoodsDto> searchAuctionByIsStart();
	
	
	List<B2bAuctionGoodsDto> selectOverDateAuction();
	
	
 	List<B2bAuctionGoodsDto> getAuctionGoodsListByGoodsPk(Map<String, Object> map);
 	
 	List<B2bAuctionGoodsDto> searchAuctingGoods(Map<String, Object> map);
	

 	int deleteByGoodsPk(String goodsPk);
 	
 	/**
 	 * 竞拍中商品的页签数量
 	 * @param map
 	 * @return
 	 */
 	Map<String, Object> searchAuctionGoodsGridNewCount(Map<String, Object> map);
}
