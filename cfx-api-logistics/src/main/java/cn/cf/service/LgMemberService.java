package cn.cf.service;

import java.util.Map;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.model.LgMember;

/**
 * Created by Thinkpad on 2017/10/16.
 */
public interface LgMemberService {

	/**
	 * 根据手机号查询会员
	 * @param mobile  手机号
	 * @return
	 */
	LgMemberDtoEx getMemberByMobile(String mobile);

	/**
	 * 更新密码
	 * @param memberPk 会员pk
	 * @param password 密码
	 * @return
	 */
	Integer updatePassword(String memberPk, String password);

	/**
	 * 退出登录
	 * @param sessionId sessionId
	 * @return
	 */
	RestCode loginOut(String sessionId);

	/**
	 * 验证手机号
	 * @param mobile  手机号
	 * @return
	 */
	RestCode verificationMobile(String mobile);

	/**
	 * 找回密码
	 * @param mobile 手机号
	 * @param password 密码
	 * @return
	 */
	RestCode backPassWord(String mobile, String password);

	/**
	 * 根据pk查询会员
	 * @param memberPk 会员pk
	 * @return
	 */
	LgMemberDtoEx getMemberByPk(String memberPk);

	/**
	 * 验证手机号是否重复
	 * @param map
	 * @return
	 */
	boolean isReapetMobile(Map<String, Object> map);

	
	/**
	 * 添加会员
	 * @param model
	 */
	void addMember(LgMember model);

	/**
	 * 查询业务员
	 * 
	 * @param par
	 * @param member
	 * @return
	 */
	PageModel<LgMemberDtoEx> searchRoleMember(Map<String, Object> par, LgMemberDto member);

	/**
	 * 更新会员
	 * @param model
	 */
	void update(LgMember model);

	/**
	 * 查询会员信息及所属角色
	 * @param pk 会员pk
	 * @return
	 */
	LgMemberDtoEx getLgRoleMember(String pk);

}
