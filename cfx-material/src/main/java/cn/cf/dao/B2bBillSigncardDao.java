/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillSigncard;
import cn.cf.dto.B2bBillSigncardDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillSigncardDao {
	int insert(B2bBillSigncard model);
	int update(B2bBillSigncard model);
	int delete(String id);
	List<B2bBillSigncardDto> searchGrid(Map<String, Object> map);
	List<B2bBillSigncardDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillSigncardDto getByPk(java.lang.String pk); 
	 B2bBillSigncardDto getByCompanyPk(java.lang.String companyPk); 
	 B2bBillSigncardDto getByBankAccount(java.lang.String bankAccount); 
	 B2bBillSigncardDto getByBankName(java.lang.String bankName); 
	 B2bBillSigncardDto getByBankNo(java.lang.String bankNo); 
	 B2bBillSigncardDto getByEcbankPk(java.lang.String ecbankPk); 
	 B2bBillSigncardDto getByEcbankName(java.lang.String ecbankName); 
	 B2bBillSigncardDto getByBankclscode(java.lang.String bankclscode); 

}
