/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsCustomer;
import cn.cf.dto.B2bEconomicsCustomerDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsCustomerDao {
	int insert(B2bEconomicsCustomer model);
	int update(B2bEconomicsCustomer model);
	int delete(String id);
	List<B2bEconomicsCustomerDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsCustomerDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsCustomerDto getByPk(java.lang.String pk); 
	 B2bEconomicsCustomerDto getByCompanyPk(java.lang.String companyPk); 
	 B2bEconomicsCustomerDto getByCompanyName(java.lang.String companyName); 
	 B2bEconomicsCustomerDto getByContacts(java.lang.String contacts); 
	 B2bEconomicsCustomerDto getByContactsTel(java.lang.String contactsTel); 
	 B2bEconomicsCustomerDto getByAssidirPk(java.lang.String assidirPk); 
	 B2bEconomicsCustomerDto getByAssidirName(java.lang.String assidirName); 
	 B2bEconomicsCustomerDto getByProcessInstanceId(java.lang.String processInstanceId); 
	 B2bEconomicsCustomerDto getByGoodsName(java.lang.String goodsName); 

}
