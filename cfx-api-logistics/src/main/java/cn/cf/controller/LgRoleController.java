package cn.cf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgMemberDto;
import cn.cf.dto.LgMemberDtoEx;
import cn.cf.dto.LgRoleDto;
import cn.cf.dto.LgRoleDtoEx;
import cn.cf.service.LgFacadeService;
import cn.cf.service.LgMemberService;
import cn.cf.service.LgRoleService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * 
 * @description:人员角色相关接口
 * @author XHT
 * @date 2018-5-14 下午3:13:34
 */
@RestController
@RequestMapping("/logistics/supplier")
public class LgRoleController  extends BaseController{
	
	@Autowired
	private LgRoleService lgRoleService;
	
	@Autowired
	private LgMemberService lgMemberService;
	
	@Autowired
	private LgFacadeService lgFacadeService;
	
	/**
	 * 添加角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addLgRole", method = RequestMethod.POST)
	public String addLgRole(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			LgCompanyDto company = this.getLgCompanyBysessionsId(request);
			if (null==company) {
				restCode = RestCode.CODE_M001;
			}else{
				LgRoleDto dto = new LgRoleDto();
				dto.bind(request);
				dto.setCompanyPk(company.getPk());
				restCode = lgFacadeService.addRole(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	
	
	/**
	 * 角色列表
	 * @param request updateTimeBegin updateTimeEnd name limit start
	 * @return
	 */
	@RequestMapping(value = "lgRoleList", method = RequestMethod.POST)
	public  String lgRoleList(HttpServletRequest request) {
		String result = null;
		LgCompanyDto company = this.getLgCompanyBysessionsId(request);
		if (null==company) {
			result = RestCode.CODE_M011.toJson();
		}else{
			Map<String, Object> par = this.paramsToMap(request);
			par.put("start", ServletUtils.getIntParameterr(request, "start", 0));
			par.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
    		par.put("companyPk", company.getPk());
    		par.put("isDelete", 1);
    		par.put("orderName", "updateTime");
    		par.put("orderType","desc");
    		PageModel<LgRoleDtoEx> dataList = lgRoleService.searchLgRoleList(par);
    		result = RestCode.CODE_0000.toJson(dataList);
		}
		return result;
	}
	
	/**
	 * 角色详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getLgRoleByPk", method = RequestMethod.POST)
	public  String getLgRoleByPk(HttpServletRequest request) {
		String result = null;
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		if (pk.equals("")) {
			result = RestCode.CODE_A004.toJson();
		}else{
			result = RestCode.CODE_0000.toJson(lgRoleService.getByPk(pk));
		}
		return result;
	}

	/**
	 * 添加业务员
	 * @param request rolePk,mobile,pk
	 * @return
	 */
	@RequestMapping(value = "addLgRoleMember", method = RequestMethod.POST)
	public String addLgRoleMember(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			LgCompanyDto company = this.getLgCompanyBysessionsId(request);
			LgMemberDto member = this.getLgMemberBysessionsId(request);
			if (null==company) {
				restCode = RestCode.CODE_M011;
			}else{
				LgMemberDtoEx dto = new LgMemberDtoEx();
				dto.bind(request);
				restCode = lgFacadeService.addRoleMember(dto,company,member);
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	
	/**
	 * 禁用，删除 业务员
	 * @param request rolePk,mobile,pk
	 * @return
	 */
	@RequestMapping(value = "updateLgRoleMember", method = RequestMethod.POST)
	public String updateLgRoleMember(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		try {
				LgMemberDtoEx dto = new LgMemberDtoEx();
				dto.bind(request);
				if (dto.getPk()==null||dto.getPk().equals("")) {
					restCode = RestCode.CODE_A001;
					
				}else{
					restCode = lgFacadeService.updateLgRoleMember(dto);
				}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999;
		}
		return restCode.toJson();
	}
	
	
	
	/**
	 * 业务员列表
	 * @param  mobile,rolePk,startTime ,endTime
	 * @return
	 */
	@RequestMapping(value = "lgRoleMemberList", method = RequestMethod.POST)
	public String lgRoleMemberList(HttpServletRequest request) {
		String restCode =null;
		try {
			LgMemberDto member = this.getLgMemberBysessionsId(request);
			if (null==member) {
				restCode = RestCode.CODE_M001.toJson();
			}else{
				Map<String, Object> par = this.paramsToMap(request);
				par.put("start", ServletUtils.getIntParameterr(request, "start", 0));
				par.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
				
				PageModel<LgMemberDtoEx> dataList = lgMemberService.searchRoleMember(par,member);
				restCode = RestCode.CODE_0000.toJson(dataList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode = RestCode.CODE_S999.toJson();
		}
		return restCode;
	}
	
	/**
	 * 业务员 详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getLgRoleMember", method = RequestMethod.POST)
	public String getLgRoleMember(HttpServletRequest request) {
		String result =null;
		try {
			String pk = ServletUtils.getStringParameter(request, "pk", "");
			if (pk.equals("")) {
				result = RestCode.CODE_A004.toJson();
			}else{
				result = RestCode.CODE_0000.toJson(lgMemberService.getLgRoleMember(pk));
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 所有角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getlgRole", method = RequestMethod.POST)
	public  String getlgRole(HttpServletRequest request) {
		String result = null;
		LgCompanyDto company = this.getLgCompanyBysessionsId(request);
		if (null==company) {
			result = RestCode.CODE_M001.toJson();
		}else{
			Map<String, Object> par = this.paramsToMap(request);
    		par.put("companyPk", company.getPk());
    		par.put("isDelete", 1);
    		par.put("isVisable",1);
    		par.put("orderName", "updateTime");
    		par.put("orderType","desc");
    		List<LgRoleDtoEx> dataList = lgRoleService.searchLgRole(par);
    		result = RestCode.CODE_0000.toJson(dataList);
		}
		return result;
	}
}
