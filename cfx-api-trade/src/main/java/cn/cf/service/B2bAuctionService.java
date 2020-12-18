package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.dto.PageModelAuction;

public interface B2bAuctionService {
	
	/**
	 * 竞拍场次管理
	 * @param map
	 * @return
	 */
	PageModelAuction<B2bAuctionDto> searchAuctionList(Map<String, Object> map);

	/**
	 * 添加竞拍场次
	 * @param auctionName 场次名称
	 * @param startTime 场次开始时间
	 * @param endTime	场次结束时间
	 * @param sort 排序
	 * @param storePk 店铺pk
	 * @return
	 */
	RestCode  addAuction(String auctionName,String startTime,String endTime,Integer sort,String storePk);
	
	/**
	 * 删除竞拍场次
	 * @param pk  竞拍场次pk
	 * @return
	 */
	RestCode delAuction(String pk);
	
	
	/**
	 * 编辑竞拍场次
	 * @param dto
	 * @return
	 */
	RestCode editAuction(B2bAuctionDto dto);
	
	/**
	 * 启用/禁用 场次活动
	 * @param pk
	 * @param isVisable
	 * @return
	 */
	B2bAuctionDto visableAuction(String pk,int isVisable);
	
	
	/**
	 * 根据storePk查询场次list orderBy sort 降序
	 * @param storePk
	 * @return
	 */
	List<B2bAuctionDto> getAucListSort(String storePk);
	
	
	/**
	 * 根据pk查询场次
	 * @param pk
	 * @return
	 */
	B2bAuctionDto getByPk(String pk);

	
	/**
	 * 竞拍活动自动中标
	 */
	String automaticBidauctionOffer();
	
}
