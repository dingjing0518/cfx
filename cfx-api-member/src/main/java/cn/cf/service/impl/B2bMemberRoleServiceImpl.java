package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bMemberRoleDaoEx;
import cn.cf.dao.B2bRoleDaoEx;
import cn.cf.dto.B2bRoleDto;
import cn.cf.model.B2bMemberRole;
import cn.cf.service.B2bMemberRoleService;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;
@Service
public class B2bMemberRoleServiceImpl implements B2bMemberRoleService {
	@Autowired
	private B2bMemberRoleDaoEx memberRoleDao;
	
	@Autowired
	private B2bRoleDaoEx b2bRoleDaoEx;
	
	@Override
	public void insert(String rolePk, String pk) {
	String[] roles = StringUtils.splitStrs(rolePk);
	if (roles != null) {
		for (String rPk : roles) {
			B2bMemberRole memberrole = new B2bMemberRole();
			memberrole.setPk(KeyUtils.getUUID());
			memberrole.setMemberPk(pk);
			memberrole.setRolePk(rPk);
			memberRoleDao.insert(memberrole);
		}
	}}

	@Override
	public void deleteByMemberPkAndType(String memberPk, String companyType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyType", companyType);
		map.put("memberPk", memberPk);
		memberRoleDao.deleteByMemberPkAndType(map);
		
	}

	@Override
	public void deleteByMemberPk(String memberPk) {
		memberRoleDao.deleteByMemberPk(memberPk);
		
	}

	@Override
	public void setAdmin(String memberPk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyType", 0);
		List<B2bRoleDto> roleList =b2bRoleDaoEx.searchList(map);
		if(null != roleList && roleList.size()>0){
			B2bMemberRole memberrole = new B2bMemberRole();
			memberrole.setPk(KeyUtils.getUUID());
			memberrole.setMemberPk(memberPk);
			memberrole.setRolePk(roleList.get(0).getPk());
			memberRoleDao.insert(memberrole);
		}
		
	}

}
