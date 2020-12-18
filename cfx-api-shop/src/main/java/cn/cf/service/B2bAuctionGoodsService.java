package cn.cf.service;

import java.util.Date;



import cn.cf.dto.B2bAuctionGoodsDtoEx;

import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bGoodsEx;

public interface B2bAuctionGoodsService {
	
	/**
	 * 是否是竞拍商品
	 * @param type 
	 * @return
	 */
	boolean  isAuctionGoods(String type);
	/**
	 * 添加竞拍商品
	 * @param goodsEx
	 * @return
	 */
	void addAuctionGoods(B2bGoodsEx goodsEx);
	/**
	 * 设置商品的竞拍场次
	 * @param goodsDto  商品信息
	 * @param auctionPks  场次pk 多个时英文逗号隔开
	 * @param activityTime  活动日期  2017-12-11
	 */
	void addToAuctionGoods(B2bGoodsDtoEx goodsDto,String auctionPks,Date activityTime);
	
	/**
	 * 检查商品是否处于某个竞拍进行中
	 * @param goodsPk
	 * @return
	 */
	B2bAuctionGoodsDtoEx checkGoodsAuctionStatus(String goodsPk);
	
	/**
	 * 根据pk删除
	 * @param pk
	 */
	void deleteByPk(String pk);

	/**
	 * 插入
	 * @param auctionGoods
	 */
	void insert(B2bAuctionGoods auctionGoods);
}
