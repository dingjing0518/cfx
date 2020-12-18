/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgMember;
import cn.cf.dto.LgMemberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgMemberDao {
	int insert(LgMember model);
	int delete(String id);
	List<LgMemberDto> searchGrid(Map<String, Object> map);
	List<LgMemberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgMemberDto getByPk(java.lang.String pk); 
	 LgMemberDto getByCompanyPk(java.lang.String companyPk); 
	 LgMemberDto getByPassword(java.lang.String password); 
	 LgMemberDto getByCompanyName(java.lang.String companyName); 
	 LgMemberDto getByRememberToken(java.lang.String rememberToken); 
	 LgMemberDto getByHeadPortrait(java.lang.String headPortrait); 

}
