/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillInventory;
import cn.cf.dto.B2bBillInventoryDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillInventoryDao {
	int insert(B2bBillInventory model);
	int update(B2bBillInventory model);
	int delete(String id);
	List<B2bBillInventoryDto> searchGrid(Map<String, Object> map);
	List<B2bBillInventoryDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillInventoryDto getByPk(java.lang.String pk); 
	 B2bBillInventoryDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bBillInventoryDto getByBillNumber(java.lang.String billNumber); 
	 B2bBillInventoryDto getByBillCode(java.lang.String billCode); 
	 B2bBillInventoryDto getByDrawer(java.lang.String drawer); 
	 B2bBillInventoryDto getByDrawerCode(java.lang.String drawerCode); 
	 B2bBillInventoryDto getByPayee(java.lang.String payee); 
	 B2bBillInventoryDto getByPayeeCode(java.lang.String payeeCode); 
	 B2bBillInventoryDto getByAcceptor(java.lang.String acceptor); 
	 B2bBillInventoryDto getByAcceptorCode(java.lang.String acceptorCode); 
	 B2bBillInventoryDto getByAcceptorAccount(java.lang.String acceptorAccount); 
	 B2bBillInventoryDto getByAcceptorBankNo(java.lang.String acceptorBankNo); 

}
