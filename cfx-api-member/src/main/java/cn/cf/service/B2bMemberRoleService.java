package cn.cf.service;

public interface B2bMemberRoleService {

	void insert(String rolePk, String pk);
	/**
	 * 删除业务员角色
	 * @param pk
	 * @param companyType 1采购商 2供应商 0是超级管理员
	 */
	void deleteByMemberPkAndType(String pk, String companyType);
	/**
	 * 删除相关业务员的全部信息
	 * @param pk
	 */
	void deleteByMemberPk(String pk);
	/**
	 * 设置一个超级管理员
	 * @param memberPk
	 */
	void setAdmin(String memberPk);

}
