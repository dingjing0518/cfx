/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsPurposecust;
import cn.cf.dto.B2bEconomicsPurposecustDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsPurposecustDao {
	int insert(B2bEconomicsPurposecust model);
	int update(B2bEconomicsPurposecust model);
	int delete(String id);
	List<B2bEconomicsPurposecustDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsPurposecustDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsPurposecustDto getByPk(java.lang.String pk); 
	 B2bEconomicsPurposecustDto getByCompanyName(java.lang.String companyName); 
	 B2bEconomicsPurposecustDto getByContacts(java.lang.String contacts); 
	 B2bEconomicsPurposecustDto getByContactsTel(java.lang.String contactsTel); 
	 B2bEconomicsPurposecustDto getByRemarks(java.lang.String remarks); 
	 B2bEconomicsPurposecustDto getByGoodsName(java.lang.String goodsName); 

}
