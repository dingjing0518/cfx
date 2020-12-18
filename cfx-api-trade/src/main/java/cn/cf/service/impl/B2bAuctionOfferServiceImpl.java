package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bAuctionOfferDaoEx;
import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bAuctionOfferDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.PageModelAuctionBid;
import cn.cf.dto.PageModelAuctionOrder;
import cn.cf.dto.PageModelBidRecord;
import cn.cf.dto.PageModelOfferRecord;
import cn.cf.dto.PageModelOrderRecord;
import cn.cf.model.B2bAuctionGoods;
import cn.cf.model.B2bAuctionOffer;
import cn.cf.service.B2bAuctionOfferService;
import cn.cf.util.ArithUtil;

@Service
public class B2bAuctionOfferServiceImpl implements B2bAuctionOfferService {

	@Autowired
	private B2bAuctionOfferDaoEx auctionOfferDaoEx;
	
	@Autowired
	private B2bAuctionGoodsDaoEx auctionGoodsDaoEx;

	@Override
	public List<B2bAuctionOfferDto> searchAuctionOffer(Map<String, Object> auctionMap) {
		return auctionOfferDaoEx.searchList(auctionMap);
	}

	
	@Override
	public B2bAuctionOfferDto getAuctionOffer(String pk) {
		// TODO Auto-generated method stub
		return auctionOfferDaoEx.getByPk(pk);
	}

	/**
	 * 中标记录
	 */
	@Override
	public PageModelBidRecord<B2bAuctionOfferDtoEx> bidRecords(Map<String, Object> map,B2bMemberDto memberDto,Integer isAdmin) {
		PageModelBidRecord<B2bAuctionOfferDtoEx> pm = new PageModelBidRecord<B2bAuctionOfferDtoEx>();
		// 超级管理员查看所有,业务员查看自己的出价记录,业务助理查看对应业务员的出价记录
		if (isAdmin != 1) {
			if (null == memberDto.getParentPk() || "".equals(memberDto.getParentPk())
					|| "-1".equals(memberDto.getParentPk())) {
				map.put("saleManPk", memberDto.getPk());
			} else {
				map.put("saleManPk", memberDto.getParentPk());
			}
		}
		List<B2bAuctionOfferDtoEx> list = auctionOfferDaoEx.bidRecords(map);
		int count = auctionOfferDaoEx.countBidRecords(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		map.put("bidStatus", 0);//全部数量
		pm.setAllNum(auctionOfferDaoEx.countBidRecords(map));
		map.put("bidStatus", 1);//未中标
		pm.setNoBidNum(auctionOfferDaoEx.countBidRecords(map));
		map.put("bidStatus", 2);//已中标
		pm.setHasBidNum(auctionOfferDaoEx.countBidRecords(map));
		map.put("bidStatus", 3);//已过期数量
		pm.setDatedNum(auctionOfferDaoEx.countBidRecords(map));
		return pm;
	}

	
	/**
	 * 订单记录
	 */
	@Override
	public PageModelOrderRecord<B2bAuctionOfferDtoEx> orderRecords(Map<String, Object> map,B2bMemberDto memberDto,Integer isAdmin) {
		PageModelOrderRecord<B2bAuctionOfferDtoEx> pm = new PageModelOrderRecord<B2bAuctionOfferDtoEx>();
		// 超级管理员查看所有,业务员查看自己的出价记录,业务助理查看对应业务员的出价记录
		if (isAdmin != 1) {
			if (null == memberDto.getParentPk() || "".equals(memberDto.getParentPk())
					|| "-1".equals(memberDto.getParentPk())) {
				map.put("saleManPk", memberDto.getPk());
			} else {
				map.put("saleManPk", memberDto.getParentPk());
			}
		}
		List<B2bAuctionOfferDtoEx> list = auctionOfferDaoEx.orderRecords(map);
		int count = auctionOfferDaoEx.countOrderRecords(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		map.put("orderStatus", 0);//全部数量
		pm.setAllNum(auctionOfferDaoEx.countOrderRecords(map));
		map.put("orderStatus", 1);//未提交
		pm.setNoSubmitNum(auctionOfferDaoEx.countOrderRecords(map));
		map.put("orderStatus", 2);//已提交
		pm.setHasSubmitNum(auctionOfferDaoEx.countOrderRecords(map));
		map.put("orderStatus", 3);//已过期数量
		pm.setDatedNum(auctionOfferDaoEx.countOrderRecords(map));
		return pm;
	}

	/**
	 * 订单管理
	 */
	@Override
	public PageModelAuctionOrder<B2bAuctionOfferDtoEx> orderManagement(Map<String, Object> map) {
		PageModelAuctionOrder<B2bAuctionOfferDtoEx> pm = new PageModelAuctionOrder<B2bAuctionOfferDtoEx>();
		List<B2bAuctionOfferDtoEx> list = auctionOfferDaoEx.orderManagement(map);
		int count = auctionOfferDaoEx.countOrderManagement(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		map.put("submitStatus", 0);//全部数量
		pm.setAllNum(auctionOfferDaoEx.countOrderManagement(map));
		map.put("submitStatus", 1);//未提交
		pm.setNoSubmitNum(auctionOfferDaoEx.countOrderManagement(map));
		map.put("submitStatus", 2);//已提交
		pm.setHasSubmitedNum(auctionOfferDaoEx.countOrderManagement(map));
		return pm;
	}

	
	@Override
	public int updateAuctionIsFinishedAt24() {
		return auctionOfferDaoEx.updateAuctionIsFinishedAt24();
	}

	 

	/**
	 * 某个场次的最高中标价
	 */
	@Override
	public B2bAuctionOfferDto getMaxBidByAuctionPk(String auctionPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auctionPk", auctionPk);
		return auctionOfferDaoEx.getMaxBidByAuctionPk(map);
	}


	
	/**
	 * 出价记录
	 */
	@Override
	public PageModelOfferRecord<B2bAuctionOfferDtoEx> offerRecords(Map<String, Object> map,B2bMemberDto memberDto,Integer isAdmin) {
		PageModelOfferRecord<B2bAuctionOfferDtoEx> pm = new PageModelOfferRecord<B2bAuctionOfferDtoEx>();
		// 超级管理员查看所有,业务员查看自己的出价记录,业务助理查看对应业务员的出价记录
		if (isAdmin != 1) {
			if (null == memberDto.getParentPk() || "".equals(memberDto.getParentPk())
					|| "-1".equals(memberDto.getParentPk())) {
				map.put("saleManPk", memberDto.getPk());
			} else {
				map.put("saleManPk", memberDto.getParentPk());
			}
		}
		List<B2bAuctionOfferDtoEx> list = auctionOfferDaoEx.offerRecords(map);
		int count = auctionOfferDaoEx.countOfferRecords(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		map.put("acStatus", 0);//全部数量
		pm.setAllNum(auctionOfferDaoEx.countOfferRecords(map));
		map.put("acStatus", 1);//进行中数量
		pm.setOnGoingNum(auctionOfferDaoEx.countOfferRecords(map));
		map.put("acStatus", 2);//已结束数量
		pm.setHasFinishedNum(auctionOfferDaoEx.countOfferRecords(map));
		map.put("acStatus", 3);//已过期数量
		pm.setDatedNum(auctionOfferDaoEx.countOfferRecords(map));
		return pm;
	}

	@Override
	public void insert(B2bAuctionOfferDto dto) {
		B2bAuctionOffer model=new B2bAuctionOffer();
		model.UpdateDTO(dto);
		auctionOfferDaoEx.insert(model);
	}
	
	@Override
	public B2bAuctionOfferDto searchMaxPrice(Map<String, Object> map) {
		return auctionOfferDaoEx.searchMaxPrice(map);
	}

	@Override
	public B2bAuctionOfferDto getLatestAuctionPriceForCompany(String auctionPk,String companyPk) {
		Map<String, Object> map=new HashMap<>();
		map.put("auctionPk", auctionPk);
		map.put("companyPk", companyPk);
		return auctionOfferDaoEx.getLatestAuctionPriceForCompany(map);
	}
	
	
	@Override
	public List<B2bAuctionOfferDtoEx> searchAuctionOfferRecordsByMember(Map<String, Object> map) {
		return auctionOfferDaoEx.searchAuctionOfferRecordsByMember(map);
	}
	
	@Override
	public int countAuctionOfferRecordsByMember(Map<String, Object> map) {
		return auctionOfferDaoEx.countAuctionOfferRecordsByMember(map);
	}
	
	/**
	 * 取消出价  1：取消该条，2：取消所有
	 */
	@Override
	public RestCode cancelOfferRecord(String recordPk, int type) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			B2bAuctionOfferDto recordsDto = auctionOfferDaoEx.getByPk(recordPk);
			//判断活动是否已经结束，已经结束的活动不可取消出价
			if (null!=recordsDto && null!=recordsDto.getPk()) {
				B2bAuctionGoodsDto auctionGoodsDto= auctionGoodsDaoEx.IsAuctionEffective(recordsDto.getAuctionPk());
				if (null==auctionGoodsDto||null==auctionGoodsDto.getPk()||"".equals(auctionGoodsDto.getPk())) {
					return RestCode.CODE_U000;
				}
			}
			//取消该条
			if (type==1) {
				if (null!=recordsDto && null!=recordsDto.getPk()) {
					//删除offer表数据
					auctionOfferDaoEx.delete(recordPk);
					//更新活动场次表的当前最高出价
					updateAuctionGoods(recordsDto.getAuctionPk());
				}
			}
			// 取消所有
			if (type == 2) {
				Map<String, Object> map = new HashMap<>();
				map.put("auctionPk", recordsDto.getAuctionPk());
				map.put("memberPk", recordsDto.getMemberPk());
				map.put("companyPk", recordsDto.getCompanyPk());
				//删除offer表数据
				auctionOfferDaoEx.deleteAuctionOffer(map);
				// 更新活动场次表的当前最高出价
				updateAuctionGoods(recordsDto.getAuctionPk());
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode=RestCode.CODE_S999;
		}
		return restCode;
	}
	
	
	private void updateAuctionGoods(String auctionPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auctionPk", auctionPk);
		B2bAuctionOfferDto offerDto = auctionOfferDaoEx.searchMaxPrice(map);
		B2bAuctionGoodsDto goodsDto = auctionGoodsDaoEx.getByPk(auctionPk);
		if (null != goodsDto) {
			B2bAuctionGoods auctionGoods = new B2bAuctionGoods();
			auctionGoods.setPk(goodsDto.getPk());
			if (null != offerDto) {
				auctionGoods.setMaximumPrice(offerDto.getAuctionPrice());
			} else {
				auctionGoods.setMaximumPrice(goodsDto.getStartingPrice());
			}
			auctionGoodsDaoEx.updateEx(auctionGoods);
		}
	}
	
	
	/**
	 * 出价管理
	 */
	@Override
	public PageModel<B2bAuctionOfferDtoEx> offerManagement(Map<String, Object> map) {
		PageModel<B2bAuctionOfferDtoEx> pm = new PageModel<B2bAuctionOfferDtoEx>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		map.put("isDelete", 1);
		List<B2bAuctionOfferDtoEx> dataList = auctionOfferDaoEx.searchGridEx(map);
		Integer amount = auctionOfferDaoEx.searchGridCountEx(map);
		pm.setTotalCount(amount);
		pm.setDataList(dataList);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}
	
	/**
	 * 中标管理
	 */
	@Override
	public PageModelAuctionBid<B2bAuctionOfferDtoEx> bidManagement(Map<String, Object> map) {
		PageModelAuctionBid<B2bAuctionOfferDtoEx> pm = new PageModelAuctionBid<B2bAuctionOfferDtoEx>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		map.put("isDelete", 1);
		List<B2bAuctionOfferDtoEx> dataList = auctionOfferDaoEx.searchGridEx2(map);
		Integer amount = auctionOfferDaoEx.searchGridCountEx2(map);
		pm.setDataList(dataList);
		pm.setTotalCount(amount);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		map.put("bidStatus", 0);//全部数量
		pm.setAllNum(auctionOfferDaoEx.searchGridCountEx2(map));
		map.put("bidStatus", 1);//已中标数量
		pm.setBidNum(auctionOfferDaoEx.searchGridCountEx2(map));
		map.put("bidStatus", 2);//未中标数量
		pm.setNoBidNum(auctionOfferDaoEx.searchGridCountEx2(map));
		return pm;
	}

	@Override
	public Integer searchStockByAuctionPk(String pk) {
		 int stock=0;
		 int goodStock=auctionOfferDaoEx.findGoodsStockByAuctionPk(pk);
		 int auctionOfferBoxes=auctionOfferDaoEx.findAuctionOfferBoxes(pk);
		 stock=(int) ArithUtil.sub(goodStock, auctionOfferBoxes);
		return stock;
	}

	@Override
	public RestCode updateAuctionOffer(B2bAuctionOffer offer) {
		if (null==offer.getPk()||"".equals(offer.getPk())) {
			return RestCode.CODE_A001;
		}
		if(null!=offer.getProvideBoxes()){
			if(offer.getProvideBoxes()==0){
				return RestCode.CODE_G009;
			}
			if(offer.getProvideBoxes()>0){
				int stock=searchStockByAuctionPk(offer.getPk());
				int realStock=(int) ArithUtil.sub(offer.getProvideBoxes(), stock);
				if(realStock>0){
					return 	RestCode.CODE_G004;
				}
				B2bGoodsDto goodsDto = auctionOfferDaoEx.findGoodByAuctionPk(offer.getPk());
				offer.setBidStatus(1);
				offer.setBidTime(new Date());
				if (goodsDto.getBlock().equals(Block.CF.getValue())) {
					offer.setProvideWeight(ArithUtil.div(ArithUtil.mul(offer.getProvideBoxes(), (null==goodsDto.getTareWeight()?0d:goodsDto.getTareWeight())), 1000));
				}else {
					offer.setProvideWeight(ArithUtil.mul(offer.getProvideBoxes(),(null==goodsDto.getTareWeight()?0d:goodsDto.getTareWeight())));
				}
			}
		}
		if(null!=offer.getBidStatus()&&offer.getBidStatus()==2){
			offer.setProvideBoxes(0);
			offer.setProvideWeight(0.0);
		}
		auctionOfferDaoEx.updateOffer(offer);
		return RestCode.CODE_0000;
		 
	}

	@Override
	public void setOfferOverDate(String auctionPk) {
		auctionOfferDaoEx.setOfferOverDate(auctionPk);
	}


}
