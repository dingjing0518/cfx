/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bAuction;
import cn.cf.dto.B2bAuctionDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bAuctionDao {
	int insert(B2bAuction model);
	int update(B2bAuction model);
	int delete(String id);
	List<B2bAuctionDto> searchGrid(Map<String, Object> map);
	List<B2bAuctionDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bAuctionDto getByPk(java.lang.String pk); 
	 B2bAuctionDto getByAuctionName(java.lang.String auctionName); 
	 B2bAuctionDto getByStartTime(java.lang.String startTime); 
	 B2bAuctionDto getByEndTime(java.lang.String endTime); 
	 B2bAuctionDto getByStorePk(java.lang.String storePk); 

}
