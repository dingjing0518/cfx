/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.MarketingOrderMember;
import cn.cf.dto.MarketingOrderMemberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface MarketingOrderMemberDao {
	int insert(MarketingOrderMember model);
	int update(MarketingOrderMember model);
	int delete(String id);
	List<MarketingOrderMemberDto> searchGrid(Map<String, Object> map);
	List<MarketingOrderMemberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 MarketingOrderMemberDto getByOrderPk(String orderPk);
	 MarketingOrderMemberDto getByStorePk(String storePk);
	 MarketingOrderMemberDto getByPurchaserPk(String purchaserPk);
	 MarketingOrderMemberDto getByPurProvince(String purProvince);
	 MarketingOrderMemberDto getByPurCity(String purCity);
	 MarketingOrderMemberDto getByPurArea(String purArea);
	 MarketingOrderMemberDto getByPurAddress(String purAddress);
	 MarketingOrderMemberDto getByStProvince(String stProvince);
	 MarketingOrderMemberDto getByStCity(String stCity);
	 MarketingOrderMemberDto getByStArea(String stArea);
	 MarketingOrderMemberDto getByStAddress(String stAddress);

}
