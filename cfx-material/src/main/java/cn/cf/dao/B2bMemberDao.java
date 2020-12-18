/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bMember;
import cn.cf.dto.B2bMemberDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bMemberDao {
	int insert(B2bMember model);
	int update(B2bMember model);
	int delete(String id);
	List<B2bMemberDto> searchGrid(Map<String, Object> map);
	List<B2bMemberDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bMemberDto getByPk(java.lang.String pk); 
	 B2bMemberDto getByMobile(java.lang.String mobile); 
	 B2bMemberDto getByPassword(java.lang.String password); 
	 B2bMemberDto getByCompanyPk(java.lang.String companyPk); 
	 B2bMemberDto getByCompanyName(java.lang.String companyName); 
	 B2bMemberDto getByRolePk(java.lang.String rolePk); 
	 B2bMemberDto getByAuditPk(java.lang.String auditPk); 
	 B2bMemberDto getByInvitationCode(java.lang.String invitationCode); 
	 B2bMemberDto getByBeInvitedCode(java.lang.String beInvitedCode); 
	 B2bMemberDto getByHeadPortrait(java.lang.String headPortrait); 
	 B2bMemberDto getByRefuseReason(java.lang.String refuseReason); 
	 B2bMemberDto getByEmployeeNumber(java.lang.String employeeNumber); 
	 B2bMemberDto getByEmployeeName(java.lang.String employeeName); 
	 B2bMemberDto getByParentPk(java.lang.String parentPk);
	 
	 


}
