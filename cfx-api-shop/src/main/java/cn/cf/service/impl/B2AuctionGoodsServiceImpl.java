package cn.cf.service.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bAuctionDaoEx;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bGoodsEx;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class B2AuctionGoodsServiceImpl implements B2bAuctionGoodsService {

	@Autowired
	private B2bAuctionGoodsDaoEx auctionGoodsDaoEx;

	@Autowired
	private B2bAuctionDaoEx auctionDaoEx;


	@Override
	public boolean isAuctionGoods(String type) {
		boolean flag = true;
		if ("auction".equals(type)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	@Override
	public void addAuctionGoods(B2bGoodsEx goods) {
		B2bAuctionGoods auctiongoods = new B2bAuctionGoods();
		auctiongoods.setPk(KeyUtils.getUUID());
		auctiongoods.setGoodsPk(goods.getPk());
		auctiongoods.setActivityTime(goods.getAuctionTime());
		if (null != goods.getStartingPrice() && !"".equals(goods.getStartingPrice())) {
			auctiongoods.setStartingPrice(goods.getStartingPrice());
		} else {
			auctiongoods.setStartingPrice(goods.getSalePrice());
		}
		auctiongoods.setCompanyPk(goods.getCompanyPk());
		auctiongoods.setCompanyName(goods.getCompanyName());
		auctiongoods.setStoreName(goods.getStoreName());
		auctiongoods.setStorePk(goods.getStorePk());
		String[] auctionScreeningPks = StringUtils.splitStrs(goods.getAuctionScreenpks());
		if (auctionScreeningPks.length > 0) {
			for (int i = 0; i < auctionScreeningPks.length; i++) {
			 	// 查询竞拍场次
				B2bAuctionDto s = auctionDaoEx.getByPk(auctionScreeningPks[i]);
				auctiongoods.setAuctionPk(auctionScreeningPks[i]);
				auctiongoods.setEndTime(s.getEndTime());
				auctiongoods.setStartTime(s.getStartTime());
				auctionGoodsDaoEx.insert(auctiongoods);
			}
		}

	}
	
	/**
	 * 设置商品的竞拍场次
	 */
	@Override
	public void addToAuctionGoods(B2bGoodsDtoEx goodsDto, String auctionPks, Date activityTime) {
		String[] temps = StringUtils.splitStrs(auctionPks);
		B2bAuctionDto tempAuction=null;
		for (String auctionPk : temps) {
			tempAuction=auctionDaoEx.getByPk(auctionPk);
			B2bAuctionGoods model = new B2bAuctionGoods();
			model.setPk(KeyUtils.getUUID());
			model.setGoodsPk(goodsDto.getPk());
			model.setActivityTime(activityTime);
			model.setAuctionPk(auctionPk);
			model.setStartingPrice(null==goodsDto.getStartingPrice()?goodsDto.getSalePrice():goodsDto.getStartingPrice());//起拍价
			//model.setMaximumPrice(maximumPrice);//当前最高价
			model.setMinimumBoxes(1);//最低起拍量
			model.setStartTime(tempAuction.getStartTime());
			model.setEndTime(tempAuction.getEndTime());
			model.setCompanyPk(goodsDto.getCompanyPk());
			model.setCompanyName(goodsDto.getCompanyName());
			model.setStorePk(goodsDto.getStorePk());
			model.setStoreName(goodsDto.getStoreName());
			model.setIsDelete(1);
			model.setInsertTime(new Date());
			auctionGoodsDaoEx.insert(model);
		}
 		
	}

	/**
	 * 检查某个商品是否处于竞拍进行中
	 */
	@Override
	public B2bAuctionGoodsDtoEx checkGoodsAuctionStatus(String goodsPk) {
		return auctionGoodsDaoEx.checkGoodsAuctionStatus(goodsPk);
	}

	@Override
	public void deleteByPk(String pk) {
		auctionGoodsDaoEx.deleteByPk(pk);
	}


	@Override
	public void insert(B2bAuctionGoods model) {
		auctionGoodsDaoEx.insert(model);
	}


	
}