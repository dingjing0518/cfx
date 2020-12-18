package cn.cf.dto;

import java.util.Date;

import cn.cf.entity.Sessions;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bGoodsEx;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

public class B2bAuctionGoodsEx extends B2bAuctionGoods{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public B2bAuctionGoodsEx(B2bGoodsEx goods, B2bAuctionDto auctionDto,
			Sessions session) {
		this.setPk(KeyUtils.getUUID());
		this.setGoodsPk(goods.getPk());
		this.setActivityTime(DateUtil.parseDateFormat(goods.getActivityTime(), "yyyy-MM-dd"));
		this.setAuctionPk(goods.getAuctionPk());
		this.setStartingPrice(null==goods.getStartingPrice()?goods.getSalePrice():goods.getStartingPrice());
		this.setMinimumBoxes(1);//最低起拍量
		this.setStartTime(auctionDto.getStartTime());
		this.setEndTime(auctionDto.getEndTime());
		this.setCompanyPk(session.getCompanyDto().getPk());
		this.setCompanyName(session.getCompanyDto().getName());
		this.setStorePk(session.getStoreDto().getPk());
		this.setStoreName(session.getStoreDto().getName());
		this.setIsDelete(1);
		this.setInsertTime(new Date());
	}

}
