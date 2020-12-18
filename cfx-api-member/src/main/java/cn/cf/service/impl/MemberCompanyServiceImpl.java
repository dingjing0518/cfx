package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.constant.Source;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bMemberDao;
import cn.cf.dao.B2bMemberRoleDaoEx;
import cn.cf.dao.B2bRoleDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bRoleDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.model.B2bCompany;
import cn.cf.model.B2bMember;
import cn.cf.model.B2bMemberRole;
import cn.cf.service.MemberCompanyService;
import cn.cf.util.KeyUtils;

@Service
public class MemberCompanyServiceImpl implements MemberCompanyService {

	@Autowired
	private B2bCompanyDaoEx companyDaoExt;

	@Autowired
	private B2bMemberDao b2bMemberDao;

	@Autowired
	private B2bMemberRoleDaoEx b2bMemberRoleDaoEx;

	@Autowired
	private B2bRoleDao b2bRoleDao;

	@Override
	public B2bCompanyDtoEx getCompany(String companyPk) {
		return companyDaoExt.getByCompanyPk(companyPk);
	}

	@Override
	public B2bCompany perfectCompanyDto(B2bCompanyDtoEx dto, B2bMemberDto memberDto) {
		String companyPk = null;
		B2bCompanyDto companydto = companyDaoExt.getByName(dto.getName());
		B2bCompany company = new B2bCompany();
		// 1：如果新填的公司不存在，把原来该会员所属的公司名称改成替换成新编辑过来的 同时修改会员所属的公司名称
		if (null == companydto) {
			companyPk = memberDto.getCompanyPk();
			dto.updateIsPerfect();
			company.UpdateDTO(dto);
			company.setPk(companyPk);
			company.setAuditStatus(1);
			company.setInfoUpdateTime(new Date());
			companyDaoExt.update(company);
			B2bMember member = new B2bMember();
			member.setPk(memberDto.getPk());
			member.setCompanyName(dto.getName());
			member.setAuditStatus(1);
			b2bMemberDao.update(member);
			// 2：如果新填的公司存在 如下两种场景：
		} else {
			companyPk = companydto.getPk();
			// 1.如果修改的公司还是原来自己的公司则修改该公司下的相关信息
			if (companyPk.equals(memberDto.getCompanyPk())) {
				dto.updateIsPerfect();
				company.UpdateDTO(dto);
				company.setPk(companyPk);
				company.setAuditStatus(1);
				company.setInfoUpdateTime(new Date());
				companyDaoExt.update(company);
				B2bMember member = new B2bMember();
				member.setPk(memberDto.getPk());
				member.setAuditStatus(1);
				b2bMemberDao.update(member);
				// 2.如果修改的公司已经不是原来自己的公司则把会员绑定到该公司下 同时删除改会员的角色以及客户信息
			} else {
				B2bMember member = new B2bMember();
				member.setPk(memberDto.getPk());
				member.setCompanyPk(companyPk);
				member.setCompanyName(companydto.getName());
				dto.updateIsPerfect();
				company.UpdateDTO(dto);
				company.setPk(companydto.getPk());
				company.setAuditStatus(1);
				company.setInfoUpdateTime(new Date());
				// 如果该公司已审核通过 则默认会员审核通过
				if (companydto.getAuditStatus() == 2) {
					member.setAuditStatus(2);
				} else {
					member.setAuditStatus(1);
					companyDaoExt.update(company);
				}
				b2bMemberDao.update(member);
				b2bMemberRoleDaoEx.deleteByMemberPk(memberDto.getPk());
			}
		}
		return company;
	}

	@Override
	public B2bCompany addCompany(B2bCompanyDtoEx dto, B2bMemberDto memberDto) {
		B2bCompanyDto companydto = companyDaoExt.getCompanyByNameAndIsDelete(dto.getName());
		String companyPk = null;
		String rolePk = null;
		B2bCompany company = new B2bCompany();
		if (null == companydto) {
			companyPk = KeyUtils.getUUID();
			dto.updateIsPerfect();
			company.UpdateDTO(dto);
			company.setPk(companyPk);
			company.setParentPk("-1");
			company.setIsDelete(1);
			company.setAuditStatus(1);
			company.setAuditSpStatus(0);
			company.setIsVisable(1);
			company.setCompanyType(1);// 默认注册采购商
			company.setInsertTime(new Date());
			company.setUpdateTime(new Date());
			company.setAddMemberPk(memberDto.getPk());
			company.setInfoUpdateTime(new Date());
			companyDaoExt.insert(company);
			rolePk = getAdminRole();
		} else {
			// 1:如果绑定的公司已经审核通过，则直接返回公司信息
			if (companydto.getAuditStatus() == 2) {
				company.UpdateDTO(companydto);
			} else {
				// 2：如果绑定的公司审核还没有通过，则修改公司信息
				dto.updateIsPerfect();
				company.UpdateDTO(dto);
				company.setPk(companydto.getPk());
				company.setAuditStatus(1);
				company.setInfoUpdateTime(new Date());
				companyDaoExt.update(company);
			}
			companyPk = companydto.getPk();
		}
		B2bMember member = new B2bMember();
		member.UpdateDTO(memberDto);
		member.setCompanyPk(companyPk);
		member.setCompanyName(dto.getName());
		b2bMemberDao.update(member);
		if (null != rolePk) {
			B2bMemberRole memberRole = new B2bMemberRole();
			memberRole.setRolePk(rolePk);
			memberRole.setMemberPk(member.getPk());
			memberRole.setPk(KeyUtils.getUUID());
			b2bMemberRoleDaoEx.insert(memberRole);
		}
		return company;
	}

	/**
	 * 获取超级管理员权限rolePk
	 * 
	 * @return
	 */
	private String getAdminRole() {
		String rolePk = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyType", 0);
		List<B2bRoleDto> b2bRoleDtoList = b2bRoleDao.searchList(map);
		if (null != b2bRoleDtoList && b2bRoleDtoList.size() > 0) {
			rolePk = b2bRoleDtoList.get(0).getPk();
		}
		return rolePk;
	}

	@Override
	public String[] getCompanyPks(B2bCompanyDto company, Integer companyType, Integer returnType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		if(companyType == null ){
			map.put("auditStatus", 2);
			map.put("auditSpStatus", 2);
			map.put("companyType", -1);//查询所有的
		}else {
			if (companyType == 1) {
				map.put("auditStatus", 2);
			}
			// 供应商
			if (companyType == 2) {
				map.put("auditSpStatus", 2);
			}
		}
		// 查询自己包含子公司
		if (returnType == 1) {
			map.put("parentChildPk", company.getPk());
			// 只查询子公司
		} else {
			map.put("parentPk", company.getPk());
		}
		
		String[] comPks = new String[1];
		if ("-1".equals(company.getParentPk())) {
			List<B2bCompanyDtoEx> l = companyDaoExt.searchCompanyList(map);
			comPks = new String[l.size()];
			for (int i = 0; i < l.size(); i++) {
				comPks[i] = l.get(i).getPk();
			}
		} else {
			comPks[0] = company.getPk();
		}

		return comPks;
	}

	@Override
	public PageModel<B2bCompanyDtoEx> searchCustomerList(Map<String, Object> map) {
		PageModel<B2bCompanyDtoEx> pm = new PageModel<B2bCompanyDtoEx>();
		List<B2bCompanyDtoEx> list = companyDaoExt.getCustomerList(map);
		//添加所属上级姓名
		/*for (B2bCompanyDtoEx b2bCompanyDtoEx : list) {
			if (null!=b2bCompanyDtoEx.getMemberParentPk()&&!"".equals(b2bCompanyDtoEx.getMemberParentPk())) {
				B2bMemberDto temp = b2bMemberDao.getByPk(b2bCompanyDtoEx.getMemberParentPk());
				if (null!=temp&&null!=temp.getEmployeeName()) {
					b2bCompanyDtoEx.setMemberParentName(temp.getEmployeeName());
				}
			}
		}*/
		int count = companyDaoExt.getCustomerCount(map);
		// 根据公司查询已经绑定的业务员列表
		pm.setTotalCount(count);
		pm.setDataList(list);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	// 供应商入驻
	@Override
	public String supplierSettled(B2bCompanyDto dto,B2bMemberDto memberDto,String sessionId,Sessions session) {
		String rest = null;
		if(null==dto){
			rest =  RestCode.CODE_M008.toJson();
		}
		if(memberDto.getAuditStatus()==null||memberDto.getAuditStatus()!=2){
			rest =  RestCode.CODE_M006.toJson();
		}
		if(null == rest){
			//如果是子公司 则给总公司入驻
			if(null !=dto.getParentPk() && !"".equals(dto.getParentPk()) && !"-1".equals(dto.getParentPk())){
				dto = companyDaoExt.getByCompanyPk(dto.getParentPk());
			}else{
				dto = companyDaoExt.getByCompanyPk(dto.getPk());
			}
			if(null != dto.getAuditSpStatus() && dto.getAuditSpStatus() == 2){
				RestCode.CODE_Z000.setMsg("总公司已入驻");
				rest =  RestCode.CODE_Z000.toJson();
			}
		}
		if(null ==  rest){
			B2bCompany company = new B2bCompany();
			company.setPk(dto.getPk());
			company.setAuditSpStatus(1);
			company.setEnterTime(new Date());
			int result = companyDaoExt.update(company);
			if (result > 0) {
				dto.setAuditSpStatus(1);
				session.setCompanyDto(dto);
				JedisUtils.set(sessionId, session, Source.getBySource(session.getSource()).getSessionTime());
				rest = RestCode.CODE_0000.toJson(dto);
			} else {
				rest = RestCode.CODE_S999.toJson();
			}
		}
		return rest;
	}

	@Override
	public B2bCompanyDto getCompanyByName(String name) {
		B2bCompanyDto dto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		List<B2bCompanyDto> companyList = companyDaoExt.searchList(map);
		if(null != companyList && companyList.size()>0){
			dto = companyList.get(0);
		}
		return dto;
	}

	@Override
	public void insertOrUpdateCompany(B2bCompany company, Integer type) {
		if(type == 1){
			company.setAuditSpStatus(0);
			companyDaoExt.insert(company);
		}else{
			companyDaoExt.update(company);
		}
		
	}

	@Override
	public void updateParentPk(String oldParentpk, String newParentPk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oldParentpk", oldParentpk);
		map.put("newParentPk", newParentPk);
		companyDaoExt.updateParentPk(map);
		
	}

	
	@Override
	public List<B2bCompanyDto> getCompanyList(Map<String, Object> map) {
		return companyDaoExt.searchGrid(map);
	}

	@Override
	public B2bCompanyDto getByPk(String companyPk) {
		// TODO Auto-generated method stub
		return companyDaoExt.getByPk(companyPk);
	}

}
