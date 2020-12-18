/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bBillSigning;
import cn.cf.dto.B2bBillSigningDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillSigningDao {
	int insert(B2bBillSigning model);
	int update(B2bBillSigning model);
	int delete(String id);
	List<B2bBillSigningDto> searchGrid(Map<String, Object> map);
	List<B2bBillSigningDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBillSigningDto getByPk(java.lang.String pk); 
	 B2bBillSigningDto getByCompanyPk(java.lang.String companyPk); 
	 B2bBillSigningDto getByCompanyName(java.lang.String companyName); 
	 B2bBillSigningDto getByOrganizationCode(java.lang.String organizationCode); 
	 B2bBillSigningDto getByAccountInfo(java.lang.String accountInfo); 

}
