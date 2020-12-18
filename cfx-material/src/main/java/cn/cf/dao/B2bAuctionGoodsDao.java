/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bAuctionGoods;
import cn.cf.dto.B2bAuctionGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bAuctionGoodsDao {
	int insert(B2bAuctionGoods model);
	int update(B2bAuctionGoods model);
	int delete(String id);
	List<B2bAuctionGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bAuctionGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bAuctionGoodsDto getByPk(java.lang.String pk); 
	 List<B2bAuctionGoodsDto> getByGoodsPk(java.lang.String goodsPk); 
	 B2bAuctionGoodsDto getByAuctionPk(java.lang.String auctionPk); 
	 B2bAuctionGoodsDto getByStartTime(java.lang.String startTime); 
	 B2bAuctionGoodsDto getByEndTime(java.lang.String endTime); 
	 B2bAuctionGoodsDto getByCompanyPk(java.lang.String companyPk); 
	 B2bAuctionGoodsDto getByCompanyName(java.lang.String companyName); 
	 B2bAuctionGoodsDto getByStorePk(java.lang.String storePk); 
	 B2bAuctionGoodsDto getByStoreName(java.lang.String storeName); 

}
