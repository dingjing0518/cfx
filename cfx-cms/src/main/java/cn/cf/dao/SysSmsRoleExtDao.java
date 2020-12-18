package cn.cf.dao;

import java.util.Map;

public interface SysSmsRoleExtDao extends SysSmsRoleDao {

	void deleteBysmsName(String smsName);

	void insert(Map<String, Object> map);

}
