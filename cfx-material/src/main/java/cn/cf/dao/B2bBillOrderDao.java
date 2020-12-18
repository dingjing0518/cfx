/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillOrder;
import cn.cf.dto.B2bBillOrderDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillOrderDao {
	int insert(B2bBillOrder model);
	int update(B2bBillOrder model);
	int delete(String id);
	List<B2bBillOrderDto> searchGrid(Map<String, Object> map);
	List<B2bBillOrderDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillOrderDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bBillOrderDto getBySerialNumber(java.lang.String serialNumber); 
	 B2bBillOrderDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bBillOrderDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bBillOrderDto getBySupplierPk(java.lang.String supplierPk); 
	 B2bBillOrderDto getBySupplierName(java.lang.String supplierName); 
	 B2bBillOrderDto getByStorePk(java.lang.String storePk); 
	 B2bBillOrderDto getByStoreName(java.lang.String storeName); 
	 B2bBillOrderDto getByGoodsPk(java.lang.String goodsPk); 
	 B2bBillOrderDto getByGoodsName(java.lang.String goodsName); 
	 B2bBillOrderDto getByGoodsShotName(java.lang.String goodsShotName); 
	 B2bBillOrderDto getByPayerAccount(java.lang.String payerAccount); 
	 B2bBillOrderDto getByPayerBankNo(java.lang.String payerBankNo); 
	 B2bBillOrderDto getByPayerOrzaCode(java.lang.String payerOrzaCode); 
	 B2bBillOrderDto getByPayeeAccount(java.lang.String payeeAccount); 
	 B2bBillOrderDto getByPayeeBankNo(java.lang.String payeeBankNo); 
	 B2bBillOrderDto getByPayeeOrzaCode(java.lang.String payeeOrzaCode); 
	 B2bBillOrderDto getByReturnUrl(java.lang.String returnUrl); 

}
