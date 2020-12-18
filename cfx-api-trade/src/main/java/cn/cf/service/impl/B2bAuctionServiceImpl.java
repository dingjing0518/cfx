package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bAuctionDaoEx;
import cn.cf.dao.B2bAuctionGoodsDaoEx;
import cn.cf.dao.B2bAuctionOfferDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.PageModelAuction;
import cn.cf.model.B2bAuction;
import cn.cf.model.B2bAuctionOffer;
import cn.cf.service.B2bAuctionService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;


@Service
public class B2bAuctionServiceImpl implements B2bAuctionService{
	
	@Autowired
	private B2bAuctionDaoEx auctionDaoEx;
	
	@Autowired
	private B2bAuctionGoodsDaoEx b2bAuctionGoodsDaoEx;
	
	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;
	
	@Autowired
	private B2bAuctionOfferDaoEx auctionOfferDao;
	
	/**
	 * 竞拍场次管理
	 */
	@Override
	public PageModelAuction<B2bAuctionDto> searchAuctionList(Map<String, Object> map) {
		PageModelAuction<B2bAuctionDto> pm = new PageModelAuction<B2bAuctionDto>();
		map.put("orderName", "updateTime");
		map.put("orderType", "desc");
		map.put("isDelete", 1);
		List<B2bAuctionDto> dataList = auctionDaoEx.searchGrid(map);
		Integer amount = auctionDaoEx.searchGridCount(map);
		pm.setTotalCount(amount);
		pm.setDataList(dataList);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		//页签数量
		//pm.setCountMap(auctionDaoEx.searchAuctionListCount(map));
		map.put("isVisable", null);//全部
		pm.setAllNum(auctionDaoEx.searchGridCount(map));
		map.put("isVisable", 1);//启用
		pm.setVisableNum(auctionDaoEx.searchGridCount(map));
		map.put("isVisable", 2);//禁用
		pm.setForbiddenNum(auctionDaoEx.searchGridCount(map));
		return pm;
	}
	
	/**
	 * 添加场次
	 */
	@Override
	public RestCode addAuction(String auctionName, String startTime, String endTime, Integer sort, String storePk) {
		B2bAuctionDto dto=new B2bAuctionDto();
		dto.setStartTime(startTime);
		dto.setEndTime(endTime);
		dto.setStorePk(storePk);
		dto.setIsDelete(1);
		if (auctionDaoEx.searchEntity(dto)>0) {
			return RestCode.CODE_P003;
		}
		B2bAuction model=new B2bAuction();
		model.setPk(KeyUtils.getUUID());
		model.setAuctionName(auctionName);
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		model.setStartTime(startTime);
		model.setEndTime(endTime);
		model.setIsDelete(1);
		model.setIsVisable(1);
		model.setSort(sort);
		model.setStorePk(storePk);
		if (auctionDaoEx.insert(model)>0) {
			return RestCode.CODE_0000;
		} else{
			return RestCode.CODE_S999;
		}
	}

	
	/**
	 * 删除竞拍场次
	 */
	@Override
	public RestCode delAuction(String pk) {
		B2bAuctionDto dto= auctionDaoEx.getByPk(pk);
		if (null==dto||null==dto.getPk()||"".equals(dto.getPk())) {
			return RestCode.CODE_S999;
		}
		B2bAuction model=new B2bAuction();
		model.setPk(pk);
		model.setIsDelete(2);
		if (auctionDaoEx.updateEx(model)>0) {
			return RestCode.CODE_0000;
		}else{
			return RestCode.CODE_S999;
		}
	}

	/**
	 * 编辑竞拍场次
	 * 
	 */
	@Override
	public RestCode editAuction(B2bAuctionDto dto) {
		dto.setIsDelete(1);
		if (auctionDaoEx.searchEntity(dto)>0) {
			return RestCode.CODE_P003;
		}
		B2bAuction model=new B2bAuction();
		model.UpdateDTO(dto);
		model.setUpdateTime(new Date());
		if (auctionDaoEx.updateEx(model)>0) {
			return RestCode.CODE_0000;
		} else{
			return RestCode.CODE_S999;
		}
		
	}

	/**
	 * 启用/禁用  场次活动
	 */
	@Override
	public B2bAuctionDto visableAuction(String pk, int isVisable) {
		B2bAuctionDto dto= auctionDaoEx.getByPk(pk);
		if (null==dto||null==dto.getPk()||"".equals(dto.getPk())) {
			return null;
		}
		B2bAuction model=new B2bAuction();
		model.setPk(pk);
		model.setIsVisable(isVisable);
		model.setUpdateTime(new Date());
		dto.setIsVisable(isVisable);
		if (auctionDaoEx.updateEx(model)>0) {
			return dto;
		}else{
			return null;
		}
	}

	/**
	 * 根据storePk查询场次list orderBy sort
	 */
	@Override
	public List<B2bAuctionDto> getAucListSort(String storePk) {
		Map<String, Object> map=new HashMap<>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("storePk", storePk);
		map.put("orderName", "sort");
		map.put("orderType", "DESC");
		return auctionDaoEx.searchGrid(map);
	}

	/**
	 * 根据pk查询场次
	 */
	@Override
	public B2bAuctionDto getByPk(String pk) {
		return auctionDaoEx.getByPk(pk);
	}

	
	/**
	 * 定时器 自动中标
	 */
	@Override
	public String automaticBidauctionOffer() {
		String companyPks = "";
		//查询所有当天距当前时间最近结束场次的所有活动
		List<B2bAuctionGoodsDtoEx> list = b2bAuctionGoodsDaoEx.getAuctionGoodsByLately();
		if(null!= list && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < list.size(); i++) {
				//更加每个活动检索所有出价(未分配过的) 按价格高到低 量高到低 时间低到高进行排序
				map.put("auctionPk", list.get(i).getPk());
				map.put("bidStatus", 0);
				List<B2bAuctionOfferDto> offers = auctionOfferDao.searchList(map);
				if(null == offers || offers.size() == 0){
					continue;
				}
				//总重量
				Double weight = list.get(i).getTotalWeight();
				//总箱数
				Integer boxes = list.get(i).getTotalBoxes();
				//分箱数
				for (int j = 0; j < offers.size(); j++) {
					B2bAuctionOffer model  = new B2bAuctionOffer();
					B2bAuctionOfferDto offerDto = offers.get(j);
					//判断剩余重量是否满足当前分货
					weight = ArithUtil.sub(weight, offerDto.getWeight());
					boxes -= offerDto.getBoxes();
					model.UpdateDTO(offerDto);
					//中标
					if(weight >= 0 && boxes >= 0){
						model.setProvideBoxes(offerDto.getBoxes());
						model.setProvideWeight(offerDto.getWeight());
						model.setBidStatus(1);
						model.setIsFinished(2);
						model.setBidTime(new Date());
						companyPks += model.getCompanyPk()+",";
					//不中标
					}else{
						model.setBidStatus(2);
						model.setProvideBoxes(0);
						model.setProvideWeight(0d);
						model.setIsFinished(2);
						weight = ArithUtil.add(weight, offerDto.getWeight());
						boxes += offerDto.getBoxes();
					}
					//更新offer表
					auctionOfferDao.update(model);
				}
			}
		}
		return companyPks;
		
		
		
		
	}





}
