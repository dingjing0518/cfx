/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.MarketingCompany;
import cn.cf.dto.MarketingCompanyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface MarketingCompanyDao {
	int insert(MarketingCompany model);
	int update(MarketingCompany model);
	int delete(String id);
	List<MarketingCompanyDto> searchGrid(Map<String, Object> map);
	List<MarketingCompanyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 MarketingCompanyDto getByPk(java.lang.String pk); 
	 MarketingCompanyDto getByAccountPk(java.lang.String accountPk); 
	 MarketingCompanyDto getByCompanyPk(java.lang.String companyPk); 

}
