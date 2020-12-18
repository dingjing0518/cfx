/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bTaxAuthority;
import cn.cf.dto.B2bTaxAuthorityDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bTaxAuthorityDao {
	int insert(B2bTaxAuthority model);
	int update(B2bTaxAuthority model);
	int delete(String id);
	List<B2bTaxAuthorityDto> searchGrid(Map<String, Object> map);
	List<B2bTaxAuthorityDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bTaxAuthorityDto getByPk(java.lang.String pk); 
	 B2bTaxAuthorityDto getByTaxAuthorityCode(java.lang.String taxAuthorityCode); 
	 B2bTaxAuthorityDto getByTaxAuthorityName(java.lang.String taxAuthorityName); 

}
