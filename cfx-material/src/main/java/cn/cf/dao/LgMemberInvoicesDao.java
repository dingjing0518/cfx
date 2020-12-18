/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgMemberInvoices;
import cn.cf.dto.LgMemberInvoicesDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgMemberInvoicesDao {
	int insert(LgMemberInvoices model);
	int update(LgMemberInvoices model);
	int delete(String id);
	List<LgMemberInvoicesDto> searchGrid(Map<String, Object> map);
	List<LgMemberInvoicesDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgMemberInvoicesDto getByPk(java.lang.String pk); 
	 LgMemberInvoicesDto getByMonth(java.lang.String month); 
	 LgMemberInvoicesDto getByContactTel(java.lang.String contactTel); 
	 LgMemberInvoicesDto getByContactName(java.lang.String contactName); 
	 LgMemberInvoicesDto getByContactAddress(java.lang.String contactAddress); 
	 LgMemberInvoicesDto getByBankAccount(java.lang.String bankAccount); 
	 LgMemberInvoicesDto getByBankName(java.lang.String bankName); 
	 LgMemberInvoicesDto getByRegTel(java.lang.String regTel); 
	 LgMemberInvoicesDto getByRegAddress(java.lang.String regAddress); 
	 LgMemberInvoicesDto getByTaxId(java.lang.String taxId); 
	 LgMemberInvoicesDto getByName(java.lang.String name); 

}
