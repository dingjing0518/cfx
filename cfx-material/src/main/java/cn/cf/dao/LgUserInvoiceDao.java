/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgUserInvoice;
import cn.cf.dto.LgUserInvoiceDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgUserInvoiceDao {
	int insert(LgUserInvoice model);
	int update(LgUserInvoice model);
	int delete(String id);
	List<LgUserInvoiceDto> searchGrid(Map<String, Object> map);
	List<LgUserInvoiceDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgUserInvoiceDto getByPk(java.lang.String pk); 
	 LgUserInvoiceDto getByUserPk(java.lang.String userPk); 
	 LgUserInvoiceDto getByCompanyPk(java.lang.String companyPk); 
	 LgUserInvoiceDto getByCompanyName(java.lang.String companyName); 
	 LgUserInvoiceDto getByTaxidNumber(java.lang.String taxidNumber); 
	 LgUserInvoiceDto getByRegPhone(java.lang.String regPhone); 
	 LgUserInvoiceDto getByBankAccount(java.lang.String bankAccount); 
	 LgUserInvoiceDto getByBankName(java.lang.String bankName); 
	 LgUserInvoiceDto getByRegAddress(java.lang.String regAddress); 
	 LgUserInvoiceDto getByProvince(java.lang.String province); 
	 LgUserInvoiceDto getByProvinceName(java.lang.String provinceName); 
	 LgUserInvoiceDto getByCity(java.lang.String city); 
	 LgUserInvoiceDto getByCityName(java.lang.String cityName); 
	 LgUserInvoiceDto getByArea(java.lang.String area); 
	 LgUserInvoiceDto getByAreaName(java.lang.String areaName); 
	 LgUserInvoiceDto getByRecipt(java.lang.String recipt); 

}
