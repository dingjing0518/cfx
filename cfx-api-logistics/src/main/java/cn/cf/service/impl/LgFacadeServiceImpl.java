package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.common.RestCode;
import cn.cf.common.utils.StringUtils;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.dto.LgRoleDto;
import cn.cf.model.LgMember;
import cn.cf.model.LgMemberRole;
import cn.cf.service.LgFacadeService;
import cn.cf.service.LgMemberRoleService;
import cn.cf.service.LgMemberService;
import cn.cf.service.LgRoleService;
import cn.cf.util.KeyUtils;
import cn.cf.util.MD5Utils;

@Service
public class LgFacadeServiceImpl implements LgFacadeService {

	@Autowired
	private LgRoleService lgRoleService;

	@Autowired
	private LgMemberService lgMemberService;

	@Autowired
	private LgMemberRoleService lgMemberRoleService;

	@Override
	@Transactional
	public RestCode addRole(LgRoleDto dto) {
		RestCode restCode = RestCode.CODE_0000;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("name", dto.getName());
		map.put("companyPk", dto.getCompanyPk());
		map.put("pk", dto.getPk());
		// 角色是否已存在
		if (lgRoleService.isReapet(map)) {
			if (null == dto.getPk() || "".equals(dto.getPk())) {
				// 新增
				dto.setPk(KeyUtils.getUUID());
				lgRoleService.addRole(dto);
			} else {
				// 更新
				lgRoleService.updateRole(dto);
			}
		} else {
			restCode = RestCode.CODE_M005;
		}
		return restCode;
	}

	@Override
	@Transactional
	public RestCode addRoleMember(LgMemberDtoEx dto, LgCompanyDto company, LgMemberDto member) {
		RestCode restCode = RestCode.CODE_0000;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("mobile", dto.getMobile());
		map.put("pk", dto.getPk());
		try {
			// 手机号是否已存在
			if (lgMemberService.isReapetMobile(map)) {
				if (null == dto.getPk() || "".equals(dto.getPk())) {
					// 新增
					String memberPk = KeyUtils.getUUID();
					LgMember model = new LgMember();
					model.setPk(memberPk);
					addMember(model, dto, company, member);
					addMemberRole(memberPk, dto.getRolePk());
				} else {
					// 更新
					Map<String, Object> par = new HashMap<>();
					par.put("memberPk", dto.getPk());
					lgMemberRoleService.delMemberRole(dto.getPk());
					LgMember model = new LgMember();
					model.setPk(dto.getPk());
					model.setMobile(dto.getMobile());
					model.setPassword(MD5Utils.MD5Encode(dto.getMobile(), "utf-8"));
					model.setUpdateTime(new Date());
					lgMemberService.update(model);
					addMemberRole(dto.getPk(), dto.getRolePk());
				}
			} else {
				restCode = RestCode.CODE_M008;
			}
		} catch (Exception e) {
			restCode = RestCode.CODE_S999;
			e.printStackTrace();
		}
		return restCode;
	}

	private void addMemberRole(String memberPk, String rolePk) {
		if (null != rolePk && !"".equals(rolePk)) {
			String role[] = StringUtils.splitStrs(rolePk);
			for (int i = 0; i < role.length; i++) {
				LgMemberRole model = new LgMemberRole();
				model.setPk(KeyUtils.getUUID());
				model.setMemberPk(memberPk);
				model.setRolePk(role[i]);
				lgMemberRoleService.addMemberRole(model);
			}
		}
	}

	private void addMember(LgMember model, LgMemberDtoEx dto, LgCompanyDto company, LgMemberDto member) {
		model.setCompanyPk(company.getPk());
		model.setMobile(dto.getMobile());
		model.setCompanyName(company.getName());
		model.setPassword(MD5Utils.MD5Encode(dto.getMobile(), "utf-8"));
		model.setParantPk(member.getPk());
		lgMemberService.addMember(model);
	}

	@Override
	@Transactional
	public RestCode updateLgRoleMember(LgMemberDtoEx dto) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			LgMember model = new LgMember();
			model.setPk(dto.getPk());
			model.setIsDelete(dto.getIsDelete());
			model.setIsVisable(dto.getIsVisable());
			lgMemberService.update(model);
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode;
	}

}
