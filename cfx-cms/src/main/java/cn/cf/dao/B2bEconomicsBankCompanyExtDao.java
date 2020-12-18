package cn.cf.dao;


import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankCompanyExtDto;
import cn.cf.model.B2bEconomicsBankCompany;

public interface B2bEconomicsBankCompanyExtDao {
    List<B2bEconomicsBankCompany> getBankCompanyByCompanyPk(java.lang.String companyPk);
    List<B2bEconomicsBankCompanyDto> getByCompanyPk(java.lang.String companyPk);
    List<B2bEconomicsBankCompanyDto> getEconomicsBankCompanyByMap(Map<String, Object> map);
    B2bEconomicsBankCompanyDto getByCreditPk(java.lang.String creditPk);
    int update(B2bEconomicsBankCompanyDto dto);
	int insertEx(Map<String, String> param1);
	B2bEconomicsBankCompanyDto isExist(Map<String,String> param);
	int deleteByCompanyPk(String companyPk);
	int updateCustomer(Map<String,Object> map);
	List<B2bEconomicsBankCompanyExtDto> searchBankApproveAmount(Map<String, Object> map);
	List<B2bEconomicsBankCompanyExtDto> countTotalAmount(Map<String, Object> map);
	List<B2bEconomicsBankCompanyExtDto> searchAmountByMonth(Map<String, Object> map);
	List<B2bEconomicsBankCompanyDto> getBank();
	List<B2bEconomicsBankCompanyExtDto> countTotalAmountMonth(Map<String, Object> map);
	List<B2bEconomicsBankCompanyExtDto> countAvailableAmount(Map<String, Object> map);
	
	/**
	 * 查询公司之前银行授信的信息
	 * @param map
	 * @return
	 */
	List<B2bEconomicsBankCompanyExtDto> searchListByCreditTime(Map<String, Object> map);
	void updateCreditTime(Map<String, Object> map);
	
}
