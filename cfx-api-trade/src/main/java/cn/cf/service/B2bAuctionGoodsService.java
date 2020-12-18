package cn.cf.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bGoodsEx;

public interface B2bAuctionGoodsService {
	
	/**
	 * 查询竞拍中商品
	 * @param map
	 * @return
	 */
	List<B2bAuctionGoodsDto> searchAuctionGoods(Map<String, Object> map);
	
	/**
	 * 查询竞拍商品
	 * @param map
	 * @return
	 */
	B2bAuctionGoodsDto getAuctionGoods(String auctionPk, String pk);
	
	/**
	 * 是否是竞拍商品
	 * @param type 
	 * @return
	 */
	boolean  isAuctionGoods(String type);
	
	/**
	 * 竞拍商品是否可编辑
	 * @param goodsPk
	 * @return
	 */
	boolean isEditAddAuctionGoods(String goodsPk);
	
	/**
	 * 添加竞拍商品
	 * @param goodsEx
	 * @return
	 */
	void addAuctionGoods(B2bGoodsEx goodsEx);


	int countAuctionGoodsGrid(Map<String, Object> map);
	
	/**
	 * 查看该竞拍活动是否已结束
	 * @return
	 */
	boolean IsAuctionEffective(String pk);

	void update(B2bAuctionGoods auctionGoods);

	B2bAuctionGoodsDto getById(String auctionPk);
	
	
	
	/**
	 * 设置商品的竞拍场次
	 * @param goodsDto  商品信息
	 * @param auctionPks  场次pk 多个时英文逗号隔开
	 * @param activityTime  活动日期  2017-12-11
	 */
	void addToAuctionGoods(B2bGoodsDtoEx goodsDto,String auctionPks,Date activityTime);
	
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
	 * 竞拍中商品页签数量
	 * @param map
	 * @return
	 */
	Map<String, Object> searchAuctionGoodsGridNewCount(Map<String, Object> map);
	
	/**
	 * 竞拍中商品更新为正常商品
	 * @param dto
	 */
	int downToNormal(B2bAuctionGoodsDto dto);
	

	/**
	 * 设置竞拍中的商品的最低竞拍量
	 * @param dto
	 * @return
	 */
	int setMinimumBoxes(B2bAuctionGoodsDtoEx dto);
	
	/**
	 * 更新部分字段
	 * @param auctionGoods
	 */
	int updateEx(B2bAuctionGoods auctionGoods);
	
	
	/**
	 * 客户管理-查询竞拍中的商品
	 * @param map
	 * @return
	 */
	List<B2bAuctionGoodsDtoEx> searchAuctionGoodsGridNewByMember(Map<String, Object> map);
	
	
	/**
	 * 代拍-查询竞拍中的商品的数量
	 * @param map
	 * @return
	 */
	int countAuctionGoodsGridNewByMember(Map<String, Object> map);
	
	
	B2bAuctionGoodsDtoEx getInfoByAuctionPk(String pk);
	
	
	/**
	 * 检查商品是否处于某个竞拍进行中
	 * @param goodsPk
	 * @return
	 */
	B2bAuctionGoodsDtoEx checkGoodsAuctionStatus(String goodsPk);
	
	
	void deleteByPk(String pk);

	
	B2bAuctionGoodsDtoEx getAuctionByGoodsPk(String goodsPk);
	
	
	List<B2bAuctionGoodsDto> searchAuctionByIsStart();
	
	
	void updateAuctionGoodsToNormal();
	
	
	List<B2bAuctionGoodsDto> getAuctionGoodsListByGoodsPk(String goodsPk);
	
	
	void insert(B2bAuctionGoods auctionGoods);
}
