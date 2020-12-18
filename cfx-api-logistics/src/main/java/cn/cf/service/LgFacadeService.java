package cn.cf.service;

import org.springframework.transaction.annotation.Transactional;
import cn.cf.common.RestCode;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.dto.LgRoleDto;

public interface LgFacadeService {
	/**
	 * 承运商添加角色
	 * @param dto
	 * @return
	 */
	@Transactional
	RestCode addRole(LgRoleDto dto);
	
	/**
	 * 添加业务员
	 * @param dto
	 * @param company
	 * @param member
	 * @return
	 */
	@Transactional
	RestCode addRoleMember(LgMemberDtoEx dto, LgCompanyDto company, LgMemberDto member);
	
	/**
	 * 编辑，删除禁用业务员
	 * @param dto
	 * @return
	 */
	@Transactional
	RestCode updateLgRoleMember(LgMemberDtoEx dto);

}
