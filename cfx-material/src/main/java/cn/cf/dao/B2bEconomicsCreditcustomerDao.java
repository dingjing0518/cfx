/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsCreditcustomer;
import cn.cf.dto.B2bEconomicsCreditcustomerDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsCreditcustomerDao {
	int insert(B2bEconomicsCreditcustomer model);
	int update(B2bEconomicsCreditcustomer model);
	int delete(String id);
	List<B2bEconomicsCreditcustomerDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsCreditcustomerDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsCreditcustomerDto getByPk(java.lang.String pk); 
	 B2bEconomicsCreditcustomerDto getByCompanyPk(java.lang.String companyPk); 
	 B2bEconomicsCreditcustomerDto getByCompanyName(java.lang.String companyName); 
	 B2bEconomicsCreditcustomerDto getByContacts(java.lang.String contacts); 
	 B2bEconomicsCreditcustomerDto getByContactsTel(java.lang.String contactsTel); 
	 B2bEconomicsCreditcustomerDto getByAssidirPk(java.lang.String assidirPk); 
	 B2bEconomicsCreditcustomerDto getByAssidirName(java.lang.String assidirName); 
	 B2bEconomicsCreditcustomerDto getByProductPks(java.lang.String productPks); 
	 B2bEconomicsCreditcustomerDto getByGoodsName(java.lang.String goodsName); 

}
