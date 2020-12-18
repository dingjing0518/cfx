/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bLoanNumber;
import cn.cf.dto.B2bLoanNumberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bLoanNumberDao {
	int insert(B2bLoanNumber model);
	int update(B2bLoanNumber model);
	int delete(String id);
	List<B2bLoanNumberDto> searchGrid(Map<String, Object> map);
	List<B2bLoanNumberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bLoanNumberDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bLoanNumberDto getByLoanNumber(java.lang.String loanNumber); 
	 B2bLoanNumberDto getByContractNumber(java.lang.String contractNumber); 
	 B2bLoanNumberDto getByBankPk(java.lang.String bankPk); 
	 B2bLoanNumberDto getByBankName(java.lang.String bankName); 
	 B2bLoanNumberDto getByEconomicsGoodsName(java.lang.String economicsGoodsName); 
	 B2bLoanNumberDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bLoanNumberDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bLoanNumberDto getBySupplierPk(java.lang.String supplierPk); 
	 B2bLoanNumberDto getBySupplierName(java.lang.String supplierName); 
	 B2bLoanNumberDto getByOrganizationCode(java.lang.String organizationCode); 
	 B2bLoanNumberDto getByCustomerNumber(java.lang.String customerNumber); 
	 B2bLoanNumberDto getByLoanAccount(java.lang.String loanAccount); 
	 B2bLoanNumberDto getByQrCode(java.lang.String qrCode); 
	 B2bLoanNumberDto getByReturnUrl(java.lang.String returnUrl); 

}
