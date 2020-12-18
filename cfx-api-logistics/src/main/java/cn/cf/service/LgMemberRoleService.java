package cn.cf.service;


import cn.cf.model.LgMemberRole;

public interface LgMemberRoleService {

	/**
	 * 添加会员角色
	 * @param model 会员角色信息
	 */
	void addMemberRole(LgMemberRole model);

	
	/**
	 * 删除会员角色
	 * @param pk 记录的pk
	 */
	void delMemberRole(String pk);



}
