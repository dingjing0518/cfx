/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bBind;
import cn.cf.dto.B2bBindDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBindDao {
	int insert(B2bBind model);
	int update(B2bBind model);
	int delete(String id);
	List<B2bBindDto> searchGrid(Map<String, Object> map);
	List<B2bBindDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bBindDto getByPk(java.lang.String pk); 
	 B2bBindDto getByBindName(java.lang.String bindName); 
	 B2bBindDto getByBindProductId(java.lang.String bindProductId); 
	 B2bBindDto getByBindReason(java.lang.String bindReason); 
	 B2bBindDto getByBindProductDetails(java.lang.String bindProductDetails); 
	 B2bBindDto getByStorePk(java.lang.String storePk); 

}
