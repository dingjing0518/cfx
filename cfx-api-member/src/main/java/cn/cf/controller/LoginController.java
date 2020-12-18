package cn.cf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.jedis.JedisUtils;
import cn.cf.service.MemberCompanyService;
import cn.cf.service.MemberFacadeService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * 
 * @description:用户登录控制层
 * @author FJM
 * @date 2018-4-9 下午5:01:01
 */
@RestController
@RequestMapping("member")
public class LoginController extends BaseController{

	@Autowired
	private MemberFacadeService memberFacadeService;
	
	@Autowired
	private MemberCompanyService companyService;
	
	
	/**
	 * 验证用户登录接口 返回session对象
	 * @param req
	 * @param resp
	 * @return 
	 */
	@RequestMapping(value = "verificationLogin", method = RequestMethod.POST)
	public String login(HttpServletRequest req,HttpServletResponse resp){
		RestCode restCode = RestCode.CODE_0000;
		String sessionId = req.getSession().getId();
		String mobile = ServletUtils.getStringParameter(req, "mobile","");
		String password = ServletUtils.getStringParameter(req, "password","");
		if (null == mobile || "".equals(mobile)) {
			restCode = RestCode.CODE_A001;
			return restCode.toJson();
		}
		if (null == password || "".equals(password)) {
			restCode = RestCode.CODE_A001;
			return restCode.toJson();
		}
		return memberFacadeService.login(mobile,password,sessionId,this.getSource(req));
	}
	
	
	/**
	 * crm token登录
	 * @param req
	 * @param resp
	 * @return 
	 */
	@RequestMapping(value = "verificationLoginByToken", method = RequestMethod.POST)
	public String verificationLoginByToken(HttpServletRequest req,HttpServletResponse resp){
		String sessionId = req.getSession().getId();
		String mobile = ServletUtils.getStringParameter(req, "mobile","");
		String erpToken = ServletUtils.getStringParameter(req, "erpToken","");
		String rest = null;
		if (null == mobile || "".equals(mobile) || null == erpToken || "".equals(erpToken)) {
			rest =   RestCode.CODE_A001.toJson();
		}else{
			rest = memberFacadeService.noVerificationLogin(mobile, erpToken, sessionId,this.getSource(req));
		}
		return rest;
	}
	
	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "doLogout", method = RequestMethod.POST)
	public String doLogout(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		B2bMemberDto member = this.getMemberBysessionsId(request);
		if(member ==null){
			return restCode.toJson();
		}
		Sessions session = this.getSessions(request);
		return memberFacadeService.logout(session);

	}
	
	/**
	 * 根据登录状态获取用户公司信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getCompanyBySession", method = RequestMethod.POST)
	public String getCompanySession(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		B2bCompanyDto dto = null;
		String companyPk = ServletUtils.getStringParameter(request,
				"companyPk", "");
		if (null != companyPk && !"".equals(companyPk)) {
			dto = companyService.getByPk(companyPk);
		}else{
			dto = this.getCompanyBysessionsId(request);
		}
		if(null==dto){
			return RestCode.CODE_M008.toJson();
		}
		return restCode.toJson(dto);

	}
	
	
	/**
	 * 获取总公司信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getParentCompanyBySession", method = RequestMethod.POST)
	public String getParentCompanyBySessin(HttpServletRequest request) {
		RestCode restCode = RestCode.CODE_0000;
		B2bCompanyDto dto = this.getCompanyBysessionsId(request);
		B2bCompanyDtoEx dtoEx = new B2bCompanyDtoEx();
		String parentName = null;
		if(null != dto.getParentPk() && !"".equals(dto.getParentPk())){
			if("-1".equals(dto.getParentPk())){
				dto.setParentPk(dto.getPk());
				parentName = dto.getName();
			}else{
				B2bCompanyDtoEx company = companyService.getCompany(dto.getParentPk());
				dto.setParentPk(company.getPk());
				parentName = company.getName();
			}
			dtoEx.UpdateMine(dto);
			dtoEx.setParentName(parentName);
		}
		return restCode.toJson(dtoEx);

	}
	
	/**
	 * 获取session信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getSessionById", method = RequestMethod.POST)
	public String getSessionById(HttpServletRequest request) {
		String sessionId = ServletUtils.getStringParameter(request, "sessionId","");
		Sessions session = JedisUtils.get(sessionId, Sessions.class);
		if(null != session){
			return RestCode.CODE_0000.toJson(session);
		}else{
			return RestCode.CODE_S003.toJson();
		}

	}
}
