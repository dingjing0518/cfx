package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bMemberGroupDaoEx;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.model.B2bMemberGroup;
import cn.cf.service.MemberGroupService;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class MemberGroupServiceImpl implements MemberGroupService {
	@Autowired
	private B2bMemberGroupDaoEx memberGroupDao;
	
	@Override
	public void insert(B2bMemberDtoEx member) {
		try {
			memberGroupDao.deleteByMemberPk(member.getPk());
			String[] parents = StringUtils.splitStrs(member.getParentPk());
			B2bMemberGroup model=new B2bMemberGroup();
			model.setMemberPk(member.getPk());
			for (int i = 0; i < parents.length; i++) {
				model.setPk(KeyUtils.getUUID());
				model.setParentPk(parents[i]);
				memberGroupDao.insert(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteByParentPk(String pk) {
		memberGroupDao.deleteByParentPk(pk);
	}

	@Override
	public void deleteByParentPkAndMemberPk(String pk) {
		memberGroupDao.deleteByParentPkAndMemberPk(pk);
		
	}

	@Override
	public void deleteByMemberPk(String pk) {
		memberGroupDao.deleteByMemberPk(pk);
		
	}

}
