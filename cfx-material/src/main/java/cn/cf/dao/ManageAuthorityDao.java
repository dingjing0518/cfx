/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.ManageAuthority;
import cn.cf.dto.ManageAuthorityDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface ManageAuthorityDao {
	int insert(ManageAuthority model);
	int update(ManageAuthority model);
	int delete(String id);
	List<ManageAuthorityDto> searchGrid(Map<String, Object> map);
	List<ManageAuthorityDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 ManageAuthorityDto getByPk(java.lang.String pk); 
	 ManageAuthorityDto getByParentPk(java.lang.String parentPk); 
	 ManageAuthorityDto getByName(java.lang.String name); 
	 ManageAuthorityDto getByDisplayName(java.lang.String displayName); 
	 ManageAuthorityDto getByUrl(java.lang.String url); 
	 ManageAuthorityDto getByImage(java.lang.String image); 

}
