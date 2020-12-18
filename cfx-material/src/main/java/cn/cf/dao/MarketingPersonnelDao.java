/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.MarketingPersonnel;
import cn.cf.dto.MarketingPersonnelDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface MarketingPersonnelDao {
	int insert(MarketingPersonnel model);
	int update(MarketingPersonnel model);
	int delete(String id);
	List<MarketingPersonnelDto> searchGrid(Map<String, Object> map);
	List<MarketingPersonnelDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 MarketingPersonnelDto getByPk(java.lang.String pk); 
	 MarketingPersonnelDto getByAccountPk(java.lang.String accountPk); 
	 MarketingPersonnelDto getByAccountName(java.lang.String accountName); 

}
