/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bCreditInvoice;
import cn.cf.dto.B2bCreditInvoiceDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCreditInvoiceDao {
	int insert(B2bCreditInvoice model);
	int update(B2bCreditInvoice model);
	int delete(String id);
	List<B2bCreditInvoiceDto> searchGrid(Map<String, Object> map);
	List<B2bCreditInvoiceDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCreditInvoiceDto getByPk(java.lang.String pk); 
	 B2bCreditInvoiceDto getByInvoiceCode(java.lang.String invoiceCode); 
	 B2bCreditInvoiceDto getByInvoiceNumber(java.lang.String invoiceNumber); 
	 B2bCreditInvoiceDto getByInvoiceType(java.lang.String invoiceType); 
	 B2bCreditInvoiceDto getByPurchaserTaxNo(java.lang.String purchaserTaxNo); 
	 B2bCreditInvoiceDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bCreditInvoiceDto getBySalesTaxNo(java.lang.String salesTaxNo); 
	 B2bCreditInvoiceDto getBySalesTaxName(java.lang.String salesTaxName); 
	 B2bCreditInvoiceDto getByCommodityName(java.lang.String commodityName); 

}
