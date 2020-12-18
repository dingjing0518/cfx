package cn.cf.dao;

import java.util.Map;

public interface LgMemberRoleDaoEx extends LgMemberRoleDao {

	void deleteByMember(String memberPk);

	void deleteMemberRole(Map<String, Object> map);

}
