package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsCreditcustomerDtoEx;
import cn.cf.model.B2bEconomicsCreditcustomer;

public interface B2bEconomicsCreditcustomerExDao extends B2bEconomicsCreditcustomerDao{
	
	List<B2bEconomicsCreditcustomerDtoEx> searchEcList(Map<String,Object> map);
	
	int searchEcCounts(Map<String,Object> map);
	
	int updateCreditCustomer(B2bEconomicsCreditcustomer customer);

	List<B2bEconomicsCreditcustomerDtoEx> searchOneDayApplyCompany(String day);

	List<B2bEconomicsCreditcustomerDtoEx> searchAccDayApplyCompany(String day);

	int isExitCreditcustomer(String companyPk);

	
}
