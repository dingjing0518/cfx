/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bFuturesType;
import cn.cf.dto.B2bFuturesTypeDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bFuturesTypeDao {
	int insert(B2bFuturesType model);
	int update(B2bFuturesType model);
	int delete(String id);
	List<B2bFuturesTypeDto> searchGrid(Map<String, Object> map);
	List<B2bFuturesTypeDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bFuturesTypeDto getByPk(java.lang.String pk); 
	 B2bFuturesTypeDto getByName(java.lang.String name); 

}
