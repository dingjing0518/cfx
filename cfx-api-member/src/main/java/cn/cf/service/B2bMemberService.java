package cn.cf.service;


import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.dto.B2bRoleDto;
import cn.cf.model.B2bMember;


public interface B2bMemberService {
	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	B2bMemberDto getMember(String mobile);
	/**
	 * 新增会员 返回pk
	 * @param member
	 * @return
	 */
	String addMember(B2bMember member);
	/**
	 * 编辑会员
	 * @param member
	 * @return
	 */
	int updateMember(B2bMember member);
	/**
	 * 获取公司业务员
	 * @param map
	 * @return
	 */
	List<B2bMemberDto> searchMembersByCompany(Map<String, Object> map);
	
	B2bMemberDtoEx getByPk(String memberPk);
	
	B2bMemberDto getByMobile(String mobile);
	/**
	 * 注册用户获取邀请码
	 * @return
	 */
	String getInvitationCode();
	
	void insert(B2bMemberDtoEx member);
	
	void insert(B2bMember member);
	/**
	 * 是否是超级管理员
	 * @param companyPk
	 * @return
	 */
	boolean isSupAdmin(String companyPk,String currentMemPk);

	void update(B2bMemberDto member);

	void update(B2bMemberDtoEx member)throws Exception;
	/**
	 * 获取角色权限
	 * @param companyType
	 * @return
	 */
	List<B2bRoleDto> searchRoleList(int companyType);
	
	void updateParentPk(String pk);
	
	PageModel<B2bMemberDtoEx> searchMemberList(Map<String, Object> map, Integer companyType);
	/**
	 * 是否已注册
	 * @param member
	 * @return
	 */
	boolean isRepeat(B2bMemberDtoEx member);
	
	/**
	 * 查询当前账户的上级
	 * @param map
	 * @return
	 */
	List<B2bMemberDto> searchMemberByParentPk(Map<String, Object> map);
	

	void upgradeMembers();
	/**
	 * 根据条件查询会员
	 * @param map
	 * @return
	 */
	List<B2bMemberDto> searchList(Map<String,Object> map);
	
	/**
	 * 查询一段时间内的累计添加人数
	 * @param b2bDimensionalityExtrewardDto
	 * @param addPk
	 * @return
	 */
	boolean isAccumulativeAddMember(B2bDimensionalityExtrewardDto b2bDimensionalityExtrewardDto, String addPk);
	
	void addPointPeriod();
	
}
