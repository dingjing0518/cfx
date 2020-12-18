/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillCustomer;
import cn.cf.dto.B2bBillCustomerDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCustomerDao {
	int insert(B2bBillCustomer model);
	int update(B2bBillCustomer model);
	int delete(String id);
	List<B2bBillCustomerDto> searchGrid(Map<String, Object> map);
	List<B2bBillCustomerDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillCustomerDto getByPk(java.lang.String pk); 
	 B2bBillCustomerDto getByCompanyPk(java.lang.String companyPk); 
	 B2bBillCustomerDto getByCompanyName(java.lang.String companyName); 
	 B2bBillCustomerDto getByContacts(java.lang.String contacts); 
	 B2bBillCustomerDto getByContactsTel(java.lang.String contactsTel); 
	 B2bBillCustomerDto getByAddress(java.lang.String address); 
	 B2bBillCustomerDto getByAssidirPk(java.lang.String assidirPk); 
	 B2bBillCustomerDto getByAssidirName(java.lang.String assidirName); 
	 B2bBillCustomerDto getByProcessInstanceId(java.lang.String processInstanceId); 

}
