/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bCustomerManagement;
import cn.cf.dto.B2bCustomerManagementDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCustomerManagementDao {
	int insert(B2bCustomerManagement model);
	int update(B2bCustomerManagement model);
	int delete(String id);
	List<B2bCustomerManagementDto> searchGrid(Map<String, Object> map);
	List<B2bCustomerManagementDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCustomerManagementDto getByPk(java.lang.String pk); 
	 B2bCustomerManagementDto getByStorePk(java.lang.String storePk); 
	 B2bCustomerManagementDto getByPurchaserPk(java.lang.String purchaserPk);
	B2bCustomerManagementDto getByStorePkAndPurchaserPk(Map<String, Object> map); 

}
