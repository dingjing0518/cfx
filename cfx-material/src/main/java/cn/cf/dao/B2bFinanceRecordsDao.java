/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bFinanceRecords;
import cn.cf.dto.B2bFinanceRecordsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bFinanceRecordsDao {
	int insert(B2bFinanceRecords model);
	int update(B2bFinanceRecords model);
	int delete(String id);
	List<B2bFinanceRecordsDto> searchGrid(Map<String, Object> map);
	List<B2bFinanceRecordsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bFinanceRecordsDto getByPk(java.lang.String pk); 
	 B2bFinanceRecordsDto getByCompanyPk(java.lang.String companyPk); 
	 B2bFinanceRecordsDto getByCompanyName(java.lang.String companyName); 
	 B2bFinanceRecordsDto getBySupplierPk(java.lang.String supplierPk); 
	 B2bFinanceRecordsDto getBySupplierName(java.lang.String supplierName); 
	 B2bFinanceRecordsDto getByTransactionAccount(java.lang.String transactionAccount); 
	 B2bFinanceRecordsDto getByReceivablesAccount(java.lang.String receivablesAccount); 
	 B2bFinanceRecordsDto getByTransactionAccountName(java.lang.String transactionAccountName); 
	 B2bFinanceRecordsDto getByReceivablesAccountName(java.lang.String receivablesAccountName); 
	 B2bFinanceRecordsDto getByDescription(java.lang.String description); 
	 B2bFinanceRecordsDto getByCurrentIp(java.lang.String currentIp); 
	 B2bFinanceRecordsDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bFinanceRecordsDto getBySerialNumber(java.lang.String serialNumber); 
	 B2bFinanceRecordsDto getByIouNumber(java.lang.String iouNumber); 
	 B2bFinanceRecordsDto getByReason(java.lang.String reason); 
	 B2bFinanceRecordsDto getByEmployeePk(java.lang.String employeePk); 
	 B2bFinanceRecordsDto getByEmployeeName(java.lang.String employeeName); 
	 B2bFinanceRecordsDto getByEmployeeNumber(java.lang.String employeeNumber); 

}
