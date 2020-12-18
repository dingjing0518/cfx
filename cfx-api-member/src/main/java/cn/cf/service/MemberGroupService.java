package cn.cf.service;

import cn.cf.dto.B2bMemberDtoEx;

public interface MemberGroupService {

	public void insert(B2bMemberDtoEx member);

	/**
	 * 根据组长删除
	 * @param pk
	 */
	public  void deleteByParentPk(String pk);
	/**
	 * 根据用户删除
	 * @param pk
	 */
	public void deleteByMemberPk(String pk);

	/**
	 * 解除组长组员关联关系
	 * @param pk
	 */
	public void deleteByParentPkAndMemberPk(String pk);

}
