package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bAuctionOfferDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.model.B2bAuctionOffer;

public interface B2bAuctionOfferDaoEx  extends B2bAuctionOfferDao{

	void updateOffer(B2bAuctionOffer offer);
	
	List<B2bAuctionOfferDtoEx> bidRecords(Map<String, Object> map);
	
	int countBidRecords(Map<String, Object> map);
	
	List<B2bAuctionOfferDtoEx> orderRecords(Map<String, Object> map);
	
	int countOrderRecords(Map<String, Object> map);
	
	List<B2bAuctionOfferDtoEx> orderManagement(Map<String, Object> map);
	
	int countOrderManagement(Map<String, Object> map);
	
	int updateAuctionIsFinishedAt24();

	int deleteAuctionOffer(Map<String, Object> map);
	
	B2bAuctionOfferDto getMaxBidByAuctionPk(Map<String, Object> map);

	int findGoodsStockByAuctionPk(String pk);

	int findAuctionOfferBoxes(String pk);

	B2bGoodsDto findGoodByAuctionPk(String pk);
	
	/**
	 * 出价记录
	 * @param map
	 * @return
	 */
	List<B2bAuctionOfferDtoEx> offerRecords(Map<String, Object> map);
	
	/**
	 * 出价记录数量
	 * @param map
	 * @return
	 */
	int countOfferRecords(Map<String, Object> map);
	
	B2bAuctionOfferDto searchMaxPrice( Map<String, Object> map);
	
	/**
	 * 某个竞拍场次中某家公司的最新出价
	 * @param map
	 * @return
	 */
	B2bAuctionOfferDto getLatestAuctionPriceForCompany( Map<String, Object> map);
	
	/**
	 * 某个业务员的某个场次的出价记录
	 * @param map
	 * @return
	 */
	List<B2bAuctionOfferDtoEx> searchAuctionOfferRecordsByMember(Map<String, Object> map);
	
	/**
	 * 某个业务员的某个场次的出价记录数量
	 * @param map
	 * @return
	 */
	int countAuctionOfferRecordsByMember(Map<String, Object> map);
	
	/**
	 * 出价管理
	 * @param pk
	 * @param searchKey
	 * @return
	 */
	List<B2bAuctionOfferDtoEx> searchGridEx(Map<String, Object> map);
	
	/**
	 * 出价管理数量
	 * @param pk
	 * @param searchKey
	 * @return
	 */
	int searchGridCountEx(Map<String, Object> map);
	
	/**
	 * 中标管理
	 * @param pk
	 * @param searchKey
	 * @return
	 */
	List<B2bAuctionOfferDtoEx> searchGridEx2(Map<String, Object> map);
	
	/**
	 * 中标管理-数量
	 * @param map
	 * @return
	 */
	int searchGridCountEx2(Map<String, Object> map);
	
	int setOfferOverDate(String auctionPk);
}
