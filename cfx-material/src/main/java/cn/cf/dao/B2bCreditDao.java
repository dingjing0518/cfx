/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bCredit;
import cn.cf.dto.B2bCreditDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCreditDao {
	int insert(B2bCredit model);
	int update(B2bCredit model);
	int delete(String id);
	List<B2bCreditDto> searchGrid(Map<String, Object> map);
	List<B2bCreditDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCreditDto getByPk(java.lang.String pk); 
	 B2bCreditDto getByCompanyPk(java.lang.String companyPk); 
	 B2bCreditDto getByCompanyName(java.lang.String companyName); 
	 B2bCreditDto getByCreditReason(java.lang.String creditReason); 
	 B2bCreditDto getByCreditContacts(java.lang.String creditContacts); 
	 B2bCreditDto getByCreditContactsTel(java.lang.String creditContactsTel); 
	 B2bCreditDto getByCreditAddress(java.lang.String creditAddress); 
	 B2bCreditDto getByMemberPk(java.lang.String memberPk); 
	 B2bCreditDto getByVirtualPayPassword(java.lang.String virtualPayPassword); 
	 B2bCreditDto getByFinancePk(java.lang.String financePk); 
	 B2bCreditDto getByFinanceContacts(java.lang.String financeContacts); 
	 B2bCreditDto getBySource(java.lang.String source); 
	 B2bCreditDto getByProcessInstanceId(java.lang.String processInstanceId); 
	 B2bCreditDto getByDelegateCertUrl(java.lang.String delegateCertUrl); 
	 B2bCreditDto getByTaxAuthorityCode(java.lang.String taxAuthorityCode); 
	 B2bCreditDto getByTaxAuthorityName(java.lang.String taxAuthorityName); 
	 B2bCreditDto getByCreditInfo(java.lang.String creditInfo); 

}
