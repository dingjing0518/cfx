package cn.cf.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.cf.PageModel;
import cn.cf.dao.B2bDimensionalityPresentExDao;
import cn.cf.dao.B2bMemberDaoEx;
import cn.cf.dao.B2bMemberGradeDao;
import cn.cf.dao.B2bMemberGroupDaoEx;
import cn.cf.dao.B2bMemberRoleDaoEx;
import cn.cf.dao.B2bRoleDaoEx;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.dto.B2bMemberGradeDto;
import cn.cf.dto.B2bMemberRoleDto;
import cn.cf.dto.B2bRoleDto;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.entity.MemberPointPeriod;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bDimensionalityPresent;
import cn.cf.model.B2bMember;
import cn.cf.service.B2bMemberService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.RandomUtil;

@Service
public class B2bMemberServiceImpl  implements B2bMemberService {

    @Autowired
    private B2bMemberDaoEx b2bMemberDao;

    @Autowired
    private B2bRoleDaoEx b2bRoleDao;

    @Autowired
    private B2bMemberRoleDaoEx b2bMemberRoleDao;

    @Autowired
    private B2bMemberGradeDao b2bMemberGradeDao;
    
	@Autowired
	private B2bMemberGroupDaoEx memberGroupDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private B2bDimensionalityPresentExDao dimensionalityPresentDao;

    @Override
    public B2bMemberDto getMember(String mobile) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("isDelete", 1);
        List<B2bMemberDto> list = b2bMemberDao.searchList(map);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String addMember(B2bMember member) {
        String pk = KeyUtils.getUUID();
        member.setPk(pk);
        member.setInsertTime(new Date());
        member.setAuditStatus(1);
        member.setIsVisable(1);
        member.setUpdateTime(new Date());
        //新注册的用户
        member.setLevel(1);
        B2bMemberGradeDto  gradeDto = b2bMemberGradeDao.getDtoByGradeNumber(1);
        if (gradeDto!=null) {
        	member.setLevelName(gradeDto.getGradeName());
		}
        // 如果是总公司注册绑定会员角色
        // ------------------------------------------------
        // 绑定邀请码 当公司会员数注册达到100W时,此方法得修改
        Map<String, Object> map = new HashMap<String, Object>();
        String invitationCode = null;
        for (int i = 0; i < 1000000; i++) {
            invitationCode = RandomUtil.getRandomPassword();
            map.put("invitationCode", invitationCode);
            int count = b2bMemberDao.searchGridCount(map);
            if (count == 0) {
                break;
            }
        }
        member.setInvitationCode(invitationCode);
        int result = b2bMemberDao.insert(member);
        if (result == 1) {
            return pk;
        } else {
            return null;
        }
    }

    @Override
    public int updateMember(B2bMember member) {
        return b2bMemberDao.update(member);
    }

    @Override
    public List<B2bMemberDto> searchMembersByCompany(Map<String, Object> map) {
        return b2bMemberDao.searchMemberByCompany(map);
    }

    @Override
    public B2bMemberDtoEx getByPk(String memberPk) {
        B2bMemberDtoEx memberDto = b2bMemberDao.getMemberByPk(memberPk);
        if (memberDto != null) {
            List<B2bMemberRoleDto> list = b2bMemberRoleDao.searchMemberRolesByMemberPk(memberPk);
            String temp = "";
            memberDto.setIsAdmin("0");
            for (B2bMemberRoleDto b2bMemberRoleDto : list) {
                temp = temp + "," + b2bMemberRoleDto.getRolePk();
                //判断该会员是否有超级管理员权限
                B2bRoleDto tempDto = b2bRoleDao.getByPk(b2bMemberRoleDto.getRolePk());
                if (null != tempDto && null != tempDto.getCompanyType() && tempDto.getCompanyType() == 0) {
                    memberDto.setIsAdmin("1");
                }
            }
            if (temp.length() > 1) {
                memberDto.setRolePk(temp.substring(1, temp.length()));
            } else {
                memberDto.setRolePk("");
            }
            if(!"-1".equals(memberDto.getParentPk())){
            	memberDto.setParentPk(searchParents(memberDto.getPk()));
            }
        }
        return memberDto;
    }
    
/**
 * 查询用户绑定的多个组长
 * @param memberPk
 * @return
 */
    private String searchParents(String memberPk) {
		return memberGroupDao.searchParents(memberPk);
	}

	@Override
    public B2bMemberDto getByMobile(String mobile) {
        return b2bMemberDao.getByMobile(mobile);
    }

    @Override
    public String getInvitationCode() {
        String invitationCode = RandomUtil.getRandomPassword();
        for (int i = 0; i < 1000000; i++) {
            B2bMemberDto dto = b2bMemberDao.getByInvitationCode(invitationCode);
            if (dto == null || "".equals(dto)) {
                break;
            }
            invitationCode = RandomUtil.getRandomPassword();
        }
        return invitationCode;
    }

    @Override
    public void insert(B2bMemberDtoEx dto) {
        B2bMember model = new B2bMember();
        model.UpdateDTO(dto);
        b2bMemberDao.insert(model);
    }

    @Override
    public boolean isSupAdmin(String companyPk, String currentMemPk) {
        List<B2bMemberDto> mem = b2bMemberDao.getAdmin(companyPk);
        if (mem.size() == 0) {
            return false;
        } else {
            for (B2bMemberDto b2bmemberdto : mem) {
                if (b2bmemberdto.getPk().equals(currentMemPk)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void update(B2bMemberDtoEx member) throws Exception {
        B2bMember model = new B2bMember();
        BeanUtils.copyProperties(model, member);
        b2bMemberDao.update(model);
    }

    @Override
    public void update(B2bMemberDto member) {
        B2bMember model = new B2bMember();
        model.UpdateDTO(member);
        b2bMemberDao.update(model);
    }

    @Override
    public List<B2bRoleDto> searchRoleList(int companyType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyType", companyType);
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        return b2bRoleDao.searchAllRoleList(map);
    }


    @Override
    public void updateParentPk(String parantPk) {
        b2bMemberDao.updateParentPk(parantPk);

    }

    @Override
    public PageModel<B2bMemberDtoEx> searchMemberList(Map<String, Object> map, Integer companyType) {
        map.put("isDelete", 1);
        Map<String, Object> param = new HashMap<String, Object>();
        PageModel<B2bMemberDtoEx> pm = new PageModel<B2bMemberDtoEx>();
        List<B2bMemberDtoEx> list = b2bMemberDao.searchMemberGrid(map);
        for (B2bMemberDtoEx m : list) {
            param.put("memberPk", m.getPk());
            param.put("companyType", companyType);
            List<B2bMemberRoleDto> l = b2bMemberRoleDao.searchMemberRoles(param);
            m.setMemberRoles(l);
        }
        int count = b2bMemberDao.memberGridCount(map);
        pm.setTotalCount(count);
        pm.setDataList(list);
        pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
        pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
        return pm;
    }

    @Override
    public boolean isRepeat(B2bMemberDtoEx member) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pk", member.getPk());
        map.put("mobile", member.getMobile());
        B2bMemberDto dto = b2bMemberDao.isRepeat(map);
        if (dto == null || dto.equals("")) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public List<B2bMemberDto> searchMemberByParentPk(Map<String, Object> map) {
        map.put("isDelete", 1);
        map.put("parentPk", -1);
        map.put("auditStatus", 2);
        List<B2bMemberDto> list = b2bMemberDao.searchGrid(map);
        return list;
    }

    @Override
    public void upgradeMembers() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        List<B2bMemberGradeDto> list = b2bMemberGradeDao.searchList(map);
        for (B2bMemberGradeDto b2bMemberGradeDto : list) {
            Integer numberStart = b2bMemberGradeDto.getNumberStart();
            Integer numberEnd = b2bMemberGradeDto.getNumberEnd()-1;
            Integer gradeNumber = b2bMemberGradeDto.getGradeNumber();
            String gradeName = b2bMemberGradeDto.getGradeName();
            b2bMemberDao.upgradeMember2(numberStart, numberEnd, gradeNumber,gradeName);
        }

        for (B2bMemberGradeDto b2bMemberGradeDto : list) {
            Integer numberStart = b2bMemberGradeDto.getNumberStart();
            Integer numberEnd = b2bMemberGradeDto.getNumberEnd()-1;
            Integer gradeNumber = b2bMemberGradeDto.getGradeNumber();
            String gradeName = b2bMemberGradeDto.getGradeName();
            b2bMemberDao.upgradeMember(numberStart, numberEnd, gradeNumber,gradeName);
        }

    }

	@Override
	public List<B2bMemberDto> searchList(Map<String, Object> map) {
		return b2bMemberDao.searchList(map);
	}
	
	/**
	 * 一段时间内的累计添加人数
	 */
	@Override
	public boolean isAccumulativeAddMember(B2bDimensionalityExtrewardDto b2bDimensionalityExtrewardDto, String addPk) {
		Map<String, Object> map =new  HashMap<String, Object>();
		map.put("addPk", addPk);
		map.put("periodTime", b2bDimensionalityExtrewardDto.getPeriodTime()+1);
		map.put("periodTimeStart", b2bDimensionalityExtrewardDto.getUpdateTime());
		int count = b2bMemberDao.searchAccumulativeMember(map);
		if (count==b2bDimensionalityExtrewardDto.getNumberTimes()) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void addPointPeriod() {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Criteria c = new Criteria();
			c.andOperator(Criteria.where("periodType").is(2),Criteria.where("isDelete").is(1));
		 //每天
		Query query = new Query(c);
		List<MemberPointPeriod> list = mongoTemplate.find(query, MemberPointPeriod.class);
		if (list.size()>0) {
			for (MemberPointPeriod p : list) {
				addPoint(p);
			}
		}
		
		//每周
		 c = new Criteria();
			c.andOperator(Criteria.where("periodType").is(3),Criteria.where("isDelete").is(1));
		 query = new Query(c);
		 query.with(new Sort(Direction.ASC,   "insertTime"));
		List<MemberPointPeriod> weeklist = mongoTemplate.find(query, MemberPointPeriod.class);
		if (weeklist.size()>0) {
			String firstTime =format.format(weeklist.get(0).getInsertTime());
			//获取当前时间完整7天的时间点
			try {
				for (int i = 1; format.parse(firstTime).before(getPastDate(7*i))||firstTime.equals(format.format(getPastDate(7*i))); i++) {
					for (MemberPointPeriod p : weeklist) {
						if (format.format(p.getInsertTime()).equals(format.format(getPastDate(7*i)))) {
							addPoint(p);
						}
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void addPoint(MemberPointPeriod p) {
		//增加会员积分
		MangoMemberPoint point = new  MangoMemberPoint();
		point.setDimenType(p.getDimenCategory()+"_"+p.getDimenType());
		point.setCompanyPk(p.getCompanyPk());
		point.setMemberPk(p.getMemberPk());
		point.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
		point.setExpValue(p.getExpValue());
		point.setFiberValue(p.getFiberValue());
		point.setOrderNumber(p.getOrderNumber());
		mongoTemplate.insert(point);
		
		B2bMemberDto memberDto = b2bMemberDao.getByPk(p.getMemberPk());
		B2bMember member = new B2bMember();
		member.setPk(p.getCompanyPk());
		member.setShell(ArithUtil.round(((memberDto.getShell()==null?0:memberDto.getShell())+point.getFiberValue()),2));
		member.setExperience(ArithUtil.round(((memberDto.getExperience()==null?0:memberDto.getExperience())+point.getExpValue()),2));
		b2bMemberDao.update(member);
		
		//插入积分明细
		
		B2bDimensionalityPresent   d = new B2bDimensionalityPresent();
		
		d.setRewardPk(p.getRewardPk());
		d.setCompanyPk(p.getCompanyPk());
		d.setCompanyName(p.getCompanyName());
		d.setOrderNumber(p.getOrderNumber());
		d.setMemberPk(p.getMemberPk());
		d.setContactsTel(p.getContactsTel());
		d.setPeriodType(p.getPeriodType());
		d.setFbGradeRatio(p.getFbGradeRatio());
		d.setEmGradeRatio(p.getEmGradeRatio());
		d.setType(p.getType());
		d.setFbShellNumberWeighting(p.getFbShellNumberWeighting());
		d.setEmpiricalValueWeighting(p.getEmpiricalValueWeighting());
		if (p.getJson()!=null && p.getJson()!="") {
			
			 JSONArray json = JSONArray.parseArray(p.getJson());
		        JSONObject object = null;
		        MemberPointPeriod t = null;
		        List<MemberPointPeriod> jsonlist = new ArrayList<MemberPointPeriod>();
		        for (int i = 0; i < json.size(); i++) {
		            object = JSONObject.parseObject(json.get(i).toString());
		            t =  JsonUtils.toBean(object.toString(), MemberPointPeriod.class);
		            jsonlist.add(t);
		        }
			for (MemberPointPeriod m : jsonlist) {
				d.setPk(KeyUtils.getUUID());
				d.setDimenCategory(m.getDimenCategory());
				d.setDimenName(m.getDimenName());
				d.setDimenType(m.getDimenType());
				d.setDimenTypeName(m.getDimenTypeName());
				d.setFibreShellRatio(m.getFibreShellRatio());
				d.setFibreShellNumber(m.getFibreShellNumber());
				d.setEmpiricalValue(m.getEmpiricalValue());
				d.setEmpiricalRatio(m.getEmGradeRatio());
				dimensionalityPresentDao.insert(d);
			}	
		}else{
			d.setPk(KeyUtils.getUUID());
			d.setDimenCategory(p.getDimenCategory());
			d.setDimenName(p.getDimenName());
			d.setDimenType(p.getDimenType());
			d.setDimenTypeName(p.getDimenTypeName());
			d.setFibreShellRatio(p.getFibreShellRatio());
			d.setFibreShellNumber(p.getFibreShellNumber());
			d.setEmpiricalValue(p.getEmpiricalValue());
			d.setEmpiricalRatio(p.getEmGradeRatio());
			d.setFbShellNumberWeighting(p.getFbShellNumberWeighting());
			d.setEmpiricalValueWeighting(p.getEmpiricalValueWeighting());
			dimensionalityPresentDao.insert(d);
		}
	}
	
	//过去几天的时间
	 private Date getPastDate(int past) throws ParseException {
	      Calendar calendar = Calendar.getInstance();
	      calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
	      Date today = calendar.getTime();
	      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	      String result = format.format(today);
	      return format.parse(result);
	  }

	@Override
	public void insert(B2bMember member) {
		b2bMemberDao.insert(member);
	}
}
