/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillCustomerApply;
import cn.cf.dto.B2bBillCustomerApplyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCustomerApplyDao {
	int insert(B2bBillCustomerApply model);
	int update(B2bBillCustomerApply model);
	int delete(String id);
	List<B2bBillCustomerApplyDto> searchGrid(Map<String, Object> map);
	List<B2bBillCustomerApplyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillCustomerApplyDto getByPk(java.lang.String pk); 
	 B2bBillCustomerApplyDto getByCompanyPk(java.lang.String companyPk); 
	 B2bBillCustomerApplyDto getByCompanyName(java.lang.String companyName); 
	 B2bBillCustomerApplyDto getByContacts(java.lang.String contacts); 
	 B2bBillCustomerApplyDto getByContactsTel(java.lang.String contactsTel); 
	 B2bBillCustomerApplyDto getByAddress(java.lang.String address); 
	 B2bBillCustomerApplyDto getByAssidirPk(java.lang.String assidirPk); 
	 B2bBillCustomerApplyDto getByAssidirName(java.lang.String assidirName); 
	 B2bBillCustomerApplyDto getByProcessInstanceId(java.lang.String processInstanceId); 

}
