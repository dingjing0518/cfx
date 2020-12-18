/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsCustomerExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsCustomerExtDao extends B2bEconomicsCustomerDao{
	List<B2bEconomicsCustomerExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);
	int updateEconomicsCustomer(B2bEconomicsCustomerExtDto dto);
	int insertEconomicsCustomer(B2bEconomicsCustomerExtDto dto);
	B2bEconomicsCustomerExtDto getByMap(Map<String, Object> map);
	List<B2bEconomicsCustomerExtDto> getCustomersMap(Map<String, Object> map);
	List<B2bEconomicsCustomerExtDto> getByCompanyPkEx(Map<String, Object> map);
	Integer ecoCustApplyYesterdayCounts(Map<String, Object> map);
	
	Integer ecoCustApplyLastWeekCounts(Map<String, Object> map);
	int isProcess(String companyPk);
	List<B2bEconomicsCustomerExtDto> getExtImpInfoByEconomCustPk(Map<String, Object> orderMap);
}
