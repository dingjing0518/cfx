/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bAuctionOffer;
import cn.cf.dto.B2bAuctionOfferDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bAuctionOfferDao {
	int insert(B2bAuctionOffer model);
	int update(B2bAuctionOffer model);
	int delete(String id);
	List<B2bAuctionOfferDto> searchGrid(Map<String, Object> map);
	List<B2bAuctionOfferDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bAuctionOfferDto getByPk(java.lang.String pk); 
	 B2bAuctionOfferDto getByAuctionPk(java.lang.String auctionPk); 
	 B2bAuctionOfferDto getByCompanyPk(java.lang.String companyPk); 
	 B2bAuctionOfferDto getByCompanyName(java.lang.String companyName); 
	 B2bAuctionOfferDto getByContacts(java.lang.String contacts); 
	 B2bAuctionOfferDto getByContactsTel(java.lang.String contactsTel); 
	 B2bAuctionOfferDto getByMemberPk(java.lang.String memberPk); 

}
