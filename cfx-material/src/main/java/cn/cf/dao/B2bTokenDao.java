/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bToken;
import cn.cf.dto.B2bTokenDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bTokenDao {
	int insert(B2bToken model);
	int update(B2bToken model);
	int delete(String id);
	List<B2bTokenDto> searchGrid(Map<String, Object> map);
	List<B2bTokenDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bTokenDto getByPk(java.lang.String pk); 
	 B2bTokenDto getByAppId(java.lang.String appId); 
	 B2bTokenDto getByAppSecret(java.lang.String appSecret); 
	 B2bTokenDto getByStorePk(java.lang.String storePk); 
	 B2bTokenDto getByStoreName(java.lang.String StoreName); 
	 B2bTokenDto getByTokenType(java.lang.String tokenType); 

}
