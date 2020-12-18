/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bFriendLink;
import cn.cf.dto.B2bFriendLinkDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bFriendLinkDao {
	int insert(B2bFriendLink model);
	int update(B2bFriendLink model);
	int delete(String id);
	List<B2bFriendLinkDto> searchGrid(Map<String, Object> map);
	List<B2bFriendLinkDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bFriendLinkDto getByPk(java.lang.String pk); 
	 B2bFriendLinkDto getByName(java.lang.String name); 
	 B2bFriendLinkDto getByLinkUrl(java.lang.String linkUrl); 

}
