package cn.cf.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.LgMemberRoleDaoEx;
import cn.cf.model.LgMemberRole;
import cn.cf.service.LgMemberRoleService;

@Service
public class LgMemberRoleServiceImpl implements LgMemberRoleService {

	@Autowired
	private LgMemberRoleDaoEx lgMemberRoleDaoEx;

	
	@Override
	public void addMemberRole(LgMemberRole model) {
		lgMemberRoleDaoEx.insert(model);
	}

	@Override
	public void delMemberRole(String memberPk) {
		lgMemberRoleDaoEx.delete(memberPk);
	}
	

}
