/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsDatum;
import cn.cf.dto.B2bEconomicsDatumDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsDatumDao {
	int insert(B2bEconomicsDatum model);
	int update(B2bEconomicsDatum model);
	int delete(String id);
	List<B2bEconomicsDatumDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsDatumDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsDatumDto getByPk(java.lang.String pk); 
	 B2bEconomicsDatumDto getByEconCustmerPk(java.lang.String econCustmerPk); 
	 B2bEconomicsDatumDto getByCompanyPk(java.lang.String companyPk); 
	 B2bEconomicsDatumDto getByCompanyName(java.lang.String companyName); 
	 B2bEconomicsDatumDto getByContacts(java.lang.String contacts); 
	 B2bEconomicsDatumDto getByContactsTel(java.lang.String contactsTel); 
	 B2bEconomicsDatumDto getByRemarks(java.lang.String remarks); 
	 B2bEconomicsDatumDto getByRule(java.lang.String rule); 
	 B2bEconomicsDatumDto getByUrl(java.lang.String url); 

}
