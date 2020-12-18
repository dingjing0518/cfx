package cn.cf.service.operation;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bMemberDto;

import cn.cf.dto.B2bRoleDto;
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.model.B2bMember;
import cn.cf.model.ManageAccount;

@Transactional
public interface B2bMemberService {
	/**
	 * 删除会员
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	String delMember(String pk) throws Exception;
	/**
	 * 根据pk获取会员信息
	 * @param pk
	 * @return
	 */
	B2bMemberDto getMemberByPk(String pk);

	
	/**
	 * 
	 * @param adto
	 * @param b2bMember
	 * @param isAuditStatusTwo判断是否是审核通过操作
	 * @return
	 * @throws Exception
	 */
	String updateMember(ManageAccount adto, B2bMember b2bMember,String isAuditStatusTwo)throws Exception;
	/**
	 * 根据会员Pk获取会员角色
	 * @param qm
	 * @param memberPk
	 * @return
	 */
	PageModel<B2bRoleExtDto> getAllRoleList(QueryModel<B2bRoleDto> qm, String memberPk);
	/**
	 * 新增会员角色
	 * @param list
	 * @return
	 * @throws Exception
	 */
	int insertMemberRole(List<Map<String, Object>> list) throws Exception;
	/**
	 * 查询会员角色集合
	 * @param memberPk
	 * @return
	 */
	List<B2bRoleDto> getRoleByMember(String memberPk);

}
