package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bAuctionOfferDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.PageModelAuctionBid;
import cn.cf.dto.PageModelAuctionOrder;
import cn.cf.dto.PageModelBidRecord;
import cn.cf.dto.PageModelOfferRecord;
import cn.cf.dto.PageModelOrderRecord;
import cn.cf.model.B2bAuctionOffer;

public interface B2bAuctionOfferService {

	List<B2bAuctionOfferDto> searchAuctionOffer(Map<String, Object> auctionMap);
	
	/**
	 * 修改出价记录
	 * @param offer
	 * @return 
	 */
	RestCode updateAuctionOffer(B2bAuctionOffer offer);
	
	/**
	 * 查询出价记录
	 * @param pk
	 * @return
	 */
	B2bAuctionOfferDto getAuctionOffer(String pk);
	
 
	 
 
	 
	/**
	 * 中标记录
	 * @param map
	 * @return
	 */
	PageModelBidRecord<B2bAuctionOfferDtoEx> bidRecords(Map<String, Object> map,B2bMemberDto memberDto,Integer isAdmin);
	
	/**
	 * 订单记录
	 * @param map
	 * @return
	 */
	PageModelOrderRecord<B2bAuctionOfferDtoEx> orderRecords(Map<String, Object> map,B2bMemberDto memberDto,Integer isAdmin);
	
	/**
	 * 订单管理
	 * @param map
	 * @return
	 */
	PageModelAuctionOrder<B2bAuctionOfferDtoEx> orderManagement(Map<String, Object> map);
	
	//定时器，所有未提交的记录设置成已过期
	int updateAuctionIsFinishedAt24();
	
	
	
	/**
	 * 查询某个场次的最高中标价
	 * @param auctionPk
	 * @return
	 */
	B2bAuctionOfferDto getMaxBidByAuctionPk(String auctionPk);

	
	
	/**
	 * 出价记录
	 * @param map
	 * @return
	 */
	PageModelOfferRecord<B2bAuctionOfferDtoEx> offerRecords(Map<String, Object> map,B2bMemberDto memberDto,Integer isAdmin);
	
	void insert(B2bAuctionOfferDto dto);
	
	B2bAuctionOfferDto searchMaxPrice(Map<String, Object> map);
	
	/**
	 * 某家公司代拍的在某个活动场次中的最新出价
	 * @param auctionPk
	 * @param memberPk
	 * @return
	 */
	B2bAuctionOfferDto getLatestAuctionPriceForCompany(String auctionPk,String companyPk);
	
	/**
	 * 某个业务员  某场竞拍活动的出价记录
	 * @param map
	 * @return
	 */
	List<B2bAuctionOfferDtoEx> searchAuctionOfferRecordsByMember(Map<String, Object> map);
	
	/**
	 * 某个业务员  某场竞拍活动的记录的数量
	 * @param map
	 * @return
	 */
	int countAuctionOfferRecordsByMember(Map<String, Object> map);
	
	/**
	 * 取消出价
	 * @param recordPk
	 * @param type
	 * @return
	 */
	RestCode cancelOfferRecord(String recordPk,int type);
	
	/**
	 * 出价管理
	 * @return
	 */
	PageModel<B2bAuctionOfferDtoEx> offerManagement(Map<String, Object> map);
	
	/**
	 * 中标管理
	 * @param map
	 * @return
	 */
	PageModelAuctionBid<B2bAuctionOfferDtoEx> bidManagement(Map<String, Object> map);

	/**
	 * 查询可用库存
	 * @param pk
	 * @return
	 */
	Integer searchStockByAuctionPk(String pk);
	
	void setOfferOverDate(String auctionPk);
	 

}
