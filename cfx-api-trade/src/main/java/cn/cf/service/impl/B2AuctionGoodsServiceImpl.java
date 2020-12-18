package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bAuctionDaoEx;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bGoods;
import cn.cf.model.B2bGoodsEx;
import cn.cf.service.B2bAuctionGoodsService;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class B2AuctionGoodsServiceImpl implements B2bAuctionGoodsService {

	@Autowired
	private B2bAuctionGoodsDaoEx auctionGoodsDaoEx;

	@Autowired
	private B2bGoodsDaoEx goodsDaoEx;

	@Autowired
	private B2bAuctionGoodsService auctionGoodsService;

	@Autowired
	private B2bAuctionDaoEx auctionDaoEx;

	/**
	 * 查询商品的 一次竞拍场次
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public List<B2bAuctionGoodsDto> searchAuctionGoods(Map<String, Object> map) {

		return auctionGoodsDaoEx.searchAuctionGoods(map);
	}

	@Override
	public B2bAuctionGoodsDto getAuctionGoods(String auctionPk, String goodsPk) {
		Map<String, Object> auctionMap = new HashMap<String, Object>();
		B2bAuctionGoodsDto auctionGood = null;
		// 1.有竞争pk
		if (null != auctionPk && !"".equals(auctionPk)) {
			auctionGood = auctionGoodsDaoEx.getByPk(auctionPk);
			// 2.如果没有查指定活动场次 则查询最近一个场次
		} else {
			auctionMap.put("goodsPk", goodsPk);
			List<B2bAuctionGoodsDto> auctionGoodslist = auctionGoodsDaoEx.searchAuctingByGoodsPk(auctionMap);
			if (null != auctionGoodslist && auctionGoodslist.size() > 0) {
				auctionGood = auctionGoodslist.get(0);
			}
		}
		// 3.如果没有, 查询过期最近的一个场次
		if (null == auctionGood) {
			List<B2bAuctionGoodsDto> auctionGoodslist = auctionGoodsDaoEx.getByGoodsPk(goodsPk);
			if (null != auctionGoodslist && auctionGoodslist.size() > 0) {
				auctionGood = auctionGoodslist.get(0);
			}
		}
		return auctionGood;
	}

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
	public boolean isEditAddAuctionGoods(String goodsPk) {
		Boolean flag = true;
		B2bGoodsDto gdto = goodsDaoEx.getByPk(goodsPk);
		if (gdto.getIsUpdown() == 2) {
			// 活动是当前正在进行中的
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodsPk", goodsPk);
			map.put("isDelete", 1);
			List<B2bAuctionGoodsDto> auctionList = auctionGoodsService.searchAuctionGoods(map);
			if (auctionList.size() > 0) {
				flag = false;
			} else {// 没有正在进行中的
				auctionGoodsDaoEx.delete(auctionList.get(0).getPk());
				flag = true;
			}
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

 

	@Override
	public int countAuctionGoodsGrid(Map<String, Object> map) {
		return auctionGoodsDaoEx.countAuctionGoodsGrid(map);
	}

	@Override
	public boolean IsAuctionEffective(String pk) {
		B2bAuctionGoodsDto gdto = auctionGoodsDaoEx.IsAuctionEffective(pk);
		if(null == gdto){
			return false;
		}else {
			return true;
		}
	}

	@Override
	public void update(B2bAuctionGoods auctionGoods) {
		auctionGoodsDaoEx.update(auctionGoods);
		
	}

	@Override
	public B2bAuctionGoodsDto getById(String pk) {
		return auctionGoodsDaoEx.getByPk(pk);
	}

	
	//=============================================竞拍新的逻辑=================================================
	
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
	 * 查询竞拍中的商品
	 */
	@Override
	public List<B2bAuctionGoodsDtoEx> searchAuctionGoodsGridNew(Map<String, Object> map) {
		return auctionGoodsDaoEx.searchAuctionGoodsGridNew(map);
	}

	/**
	 * 查询竞拍中的商品的数量
	 */
	@Override
	public int countAuctionGoodsGridNew(Map<String, Object> map) {
		return auctionGoodsDaoEx.countAuctionGoodsGridNew(map);
	}

	/**
	 * 竞拍中商品更新为正常商品
	 */
	@Override
	public int downToNormal(B2bAuctionGoodsDto dto) {
		B2bAuctionGoods model = new B2bAuctionGoods();
		model.setPk(dto.getPk());
		model.setIsDelete(2);
		return auctionGoodsDaoEx.updateEx(model);
	}

	/**
	 * 设置竞拍中的商品的最低竞拍量
	 * @param dto
	 * @return
	 */
	@Override
	public int setMinimumBoxes(B2bAuctionGoodsDtoEx dto) {
		B2bAuctionGoods model = new B2bAuctionGoods();
		model.setPk(dto.getPk());
		model.setMinimumBoxes(dto.getMinimumBoxes());
		return auctionGoodsDaoEx.updateEx(model);
	}

	/**
	 * 更新部分字段
	 * @return 
	 */
	@Override
	public int updateEx(B2bAuctionGoods auctionGoods) {
		return auctionGoodsDaoEx.updateEx(auctionGoods);
	}

	@Override
	public List<B2bAuctionGoodsDtoEx> searchAuctionGoodsGridNewByMember(Map<String, Object> map) {
		return auctionGoodsDaoEx.searchAuctionGoodsGridNewByMember(map);
	}

	@Override
	public int countAuctionGoodsGridNewByMember(Map<String, Object> map) {
		return auctionGoodsDaoEx.countAuctionGoodsGridNewByMember(map);
	}

	@Override
	public B2bAuctionGoodsDtoEx getInfoByAuctionPk(String pk) {
		Map<String, Object> map=new HashMap<>();
		map.put("pk", pk);
		return auctionGoodsDaoEx.getInfoByAuctionPk(map);
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
	public B2bAuctionGoodsDtoEx getAuctionByGoodsPk(String goodsPk) {
		return auctionGoodsDaoEx.getAuctionByGoodsPk(goodsPk);
	}

	@Override
	public List<B2bAuctionGoodsDto> searchAuctionByIsStart() {
		return auctionGoodsDaoEx.searchAuctionByIsStart();
	}

	@Override
	public void updateAuctionGoodsToNormal() {
		List<B2bAuctionGoodsDto> list= auctionGoodsDaoEx.selectOverDateAuction();
		B2bAuctionGoodsDto dto = new B2bAuctionGoodsDto();
		for (int i = 0; i < list.size(); i++) {
			dto=list.get(i);
			if (null!=dto && null!=dto.getPk() ) {
				auctionGoodsDaoEx.deleteByPk(dto.getPk());
				B2bGoods goods=new B2bGoods();
				goods.setPk(dto.getGoodsPk());
				goods.setIsUpdown(3);
				goods.setType("normal");
				goods.setUpdateTime(new Date());
				goodsDaoEx.updateGoods(goods);
			}
		}
	}

	@Override
	public List<B2bAuctionGoodsDto> getAuctionGoodsListByGoodsPk(String goodsPk) {
		Map<String, Object> map=new HashMap<>();
		map.put("goodsPk", goodsPk);
		return auctionGoodsDaoEx.getAuctionGoodsListByGoodsPk(map);
	}

	@Override
	public void insert(B2bAuctionGoods model) {
		auctionGoodsDaoEx.insert(model);
	}

	
	/**
	 * 竞拍中商品页签数量
	 */
	@Override
	public Map<String, Object> searchAuctionGoodsGridNewCount(Map<String, Object> map) {
		return auctionGoodsDaoEx.searchAuctionGoodsGridNewCount(map);
	}


	
}