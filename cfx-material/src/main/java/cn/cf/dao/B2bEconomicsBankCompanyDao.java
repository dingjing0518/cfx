/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsBankCompany;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsBankCompanyDao {
	int insert(B2bEconomicsBankCompany model);
	int update(B2bEconomicsBankCompany model);
	int delete(String id);
	List<B2bEconomicsBankCompanyDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsBankCompanyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsBankCompanyDto getByPk(java.lang.String pk); 
	 B2bEconomicsBankCompanyDto getByCompanyPk(java.lang.String companyPk); 
	 B2bEconomicsBankCompanyDto getByBankPk(java.lang.String bankPk); 
	 B2bEconomicsBankCompanyDto getByBankName(java.lang.String bankName); 
	 B2bEconomicsBankCompanyDto getByCustomerNumber(java.lang.String customerNumber); 
	 B2bEconomicsBankCompanyDto getByContractNumber(java.lang.String contractNumber); 
	 B2bEconomicsBankCompanyDto getByAgreementNumber(java.lang.String agreementNumber); 
	 B2bEconomicsBankCompanyDto getByAmountType(java.lang.String amountType); 

}
