/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bCustomerSalesman;
import cn.cf.dto.B2bCustomerSalesmanDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCustomerSalesmanDao {
	int insert(B2bCustomerSalesman model);
	int update(B2bCustomerSalesman model);
	int delete(String id);
	List<B2bCustomerSalesmanDto> searchGrid(Map<String, Object> map);
	List<B2bCustomerSalesmanDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCustomerSalesmanDto getByPk(java.lang.String pk); 
	 B2bCustomerSalesmanDto getByCustomerPk(java.lang.String customerPk); 
	 B2bCustomerSalesmanDto getByMemberPk(java.lang.String memberPk); 
	 B2bCustomerSalesmanDto getByMobile(java.lang.String mobile); 
	 B2bCustomerSalesmanDto getByProductPk(java.lang.String productPk); 
	 B2bCustomerSalesmanDto getByProductName(java.lang.String productName); 
	 B2bCustomerSalesmanDto getByFiliale(java.lang.String filiale); 
	 B2bCustomerSalesmanDto getByFilialeName(java.lang.String filialeName); 
	 B2bCustomerSalesmanDto getByEmployeeNumber(java.lang.String employeeNumber); 
	 B2bCustomerSalesmanDto getByEmployeeName(java.lang.String employeeName); 
	 B2bCustomerSalesmanDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bCustomerSalesmanDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bCustomerSalesmanDto getByStorePk(java.lang.String storePk); 
	 
	 List<B2bCustomerSalesmanDto> searchGoodsBySaleMan(Map<String, Object> map);
}
