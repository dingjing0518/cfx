/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bKeywordHot;
import cn.cf.dto.B2bKeywordHotDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bKeywordHotDao {
	int insert(B2bKeywordHot model);
	int update(B2bKeywordHot model);
	int delete(String id);
	List<B2bKeywordHotDto> searchGrid(Map<String, Object> map);
	List<B2bKeywordHotDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bKeywordHotDto getByPk(java.lang.String pk); 
	 B2bKeywordHotDto getByName(java.lang.String name); 
	 B2bKeywordHotDto getByLinkUrl(java.lang.String linkUrl); 

}
