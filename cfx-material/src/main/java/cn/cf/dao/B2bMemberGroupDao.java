/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bMemberGroup;
import cn.cf.dto.B2bMemberGroupDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberGroupDao {
	int insert(B2bMemberGroup model);
	int update(B2bMemberGroup model);
	int delete(String id);
	List<B2bMemberGroupDto> searchGrid(Map<String, Object> map);
	List<B2bMemberGroupDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bMemberGroupDto getByPk(java.lang.String pk); 
	 B2bMemberGroupDto getByMemberPk(java.lang.String memberPk); 
	 B2bMemberGroupDto getByParentPk(java.lang.String parentPk); 

}
