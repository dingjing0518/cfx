package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.LgMemberDaoEx;
import cn.cf.dao.LgRoleDaoEx;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.dto.LgRoleDtoEx;
import cn.cf.model.LgMember;
import cn.cf.service.LgMemberService;
import cn.cf.util.StringUtils;

@Service
public class LgMemberServiceImpl implements LgMemberService {


  	@Autowired
    private LgMemberDaoEx lgMemberDaoExt;
  	
  	@Autowired
  	private LgRoleDaoEx lgRoleDaoEx;
 
    @Override
    public LgMemberDtoEx getMemberByMobile(String mobile) {
        return this.lgMemberDaoExt.getByMobile(mobile);
    }

    @Override
    public Integer updatePassword(String memberPk, String password) {
        LgMember member = new LgMember();
        member.setPk(memberPk);
        member.setPassword(password);
        member.setUpdateTime(new Date());
        int result=lgMemberDaoExt.update(member);
        return result;
    }

    @Override
    public RestCode verificationMobile(String mobile) {
        if (mobile == null || "".equals(mobile)) {
            return RestCode.CODE_A001;
        }
        LgMemberDto member = lgMemberDaoExt.getByMobile(mobile);
        if (member == null) {
            return RestCode.CODE_R001;
        }
        return RestCode.CODE_0000;
    }
    /**
     * 找回密码
     */
    @Override
    public RestCode backPassWord(String mobile, String password) {

        if (mobile == null || "".equals(mobile)) {
            return RestCode.CODE_A001;
        }
        if (password == null || "".equals(password)) {
            return RestCode.CODE_A001;
        }
        LgMemberDto m = lgMemberDaoExt.getByMobile(mobile);
        if (m == null) {
            return RestCode.CODE_R001;
        }
        LgMember newMember = new LgMember();
        newMember.setPk(m.getPk());
        newMember.setPassword(password);
        newMember.setUpdateTime(new Date());
        int result=   lgMemberDaoExt.update(newMember);
        if (result == 0) {
            return RestCode.CODE_A004 ;
        }
        return RestCode.CODE_0000;
    }

    /**
     * 退出登录
     */
    @Override
    public RestCode loginOut(String sessionId) {
        // TODO Auto-generated method stub
        return null;
    }

    //根据memberPk查询Member
	@Override
	public LgMemberDtoEx getMemberByPk(String memberPk) {
		return lgMemberDaoExt.getMemberByPk(memberPk);
	}

	@Override
	public boolean isReapetMobile(Map<String, Object> map) {
	int count =	lgMemberDaoExt.isReapetMobile(map);
	if (count>0) {
		return false;
	} else {
		return true;
	}
		
	}

	@Override
	public void addMember(LgMember model) {
		lgMemberDaoExt.insert(model);
		
	}

	@Override
	public PageModel<LgMemberDtoEx> searchRoleMember(Map<String, Object> map, 
			LgMemberDto member) {
		map.put("parantPk", member.getPk());
		PageModel<LgMemberDtoEx> pm = new PageModel<LgMemberDtoEx>();
		List<LgMemberDtoEx> list = lgMemberDaoExt.searchRoleMember(map);
		Map<String, Object> par =  new HashMap<>();
		if (list.size()>0) {
			for (LgMemberDtoEx dto : list) {
				String name = "";
				par.put("memberPk", dto.getPk());
				List<LgRoleDtoEx>   roleDtoExs  = lgRoleDaoEx.searchRole(par);
				if (roleDtoExs.size()>0) {
					for (LgRoleDtoEx lgRoleDtoEx : roleDtoExs) {
						if (null!=lgRoleDtoEx.getName()) {
							name = name + lgRoleDtoEx.getName()+",";
						}
					}
					dto.setRoleName(StringUtils.subStrs(name));
				}
			}
		}
		int count = lgMemberDaoExt.countRoleMember(map);
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}


	@Override
	public void update(LgMember model) {
		lgMemberDaoExt.update(model);
	}

	@Override
	public LgMemberDtoEx getLgRoleMember(String pk) {
		Map<String, Object> par =  new HashMap<>();
		
		LgMemberDto dto =lgMemberDaoExt.getByPk(pk);
		LgMemberDtoEx dtoEx =new LgMemberDtoEx();
		dtoEx.UpdateMine(dto);
		
		par.put("memberPk", dto.getPk());
		List<LgRoleDtoEx>   list  = lgRoleDaoEx.searchRole(par);
		dtoEx.setList(list);
		return dtoEx;
	}


}
