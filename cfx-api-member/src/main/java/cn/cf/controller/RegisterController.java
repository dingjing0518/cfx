package cn.cf.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bMemberService;
import cn.cf.service.MemberCompanyService;
import cn.cf.service.MemberFacadeService;
import cn.cf.util.RegexUtils;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:注册管理
 * @author FJM
 * @date 2018-4-11 上午11:31:26
 */
@RestController
@RequestMapping("member")
public class RegisterController extends BaseController {
	@Autowired
	private B2bMemberService b2bMemberService;

	@Autowired
	private MemberFacadeService memberFacadeService;
	
	@Autowired
	private MemberCompanyService memberCompanyService;
	
	/**
	 * 注册接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(HttpServletRequest req) {
		B2bMemberDtoEx memberDto = new B2bMemberDtoEx();
		memberDto.bind(req);
		boolean flag =  memberDto.verifyUtils();
		if (!flag) {
			return RestCode.CODE_A001.toJson();
		}
		try {
			String sessionId = req.getSession().getId();
			//注册来源
			memberDto.setRegisterSource(this.getSource(req));
			String str =  memberFacadeService.register(memberDto, sessionId,this.getSource(req));
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 完善公司信息接口
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "perfectCompany", method = RequestMethod.POST)
	public String perfectCompany(HttpServletRequest req) {
		String result = "";
		try {
			B2bMemberDto memberDto = this.getMemberBysessionsId(req);
			Sessions session = this.getSessions(req);
			B2bCompanyDtoEx dtoEx = new B2bCompanyDtoEx();
			dtoEx.bind(req);
			String companyPk = req.getParameter("companyPk");
			if (null!=companyPk && !"".equals(companyPk)) {
				dtoEx.setPk(companyPk);
			}
			if (null!=dtoEx.getName() && dtoEx.getName().length()>=40) {
				return RestCode.CODE_S007.toJson();
			}
			if (null!=dtoEx.getOrganizationCode()&&dtoEx.getOrganizationCode().length()>=80) {
				return RestCode.CODE_S008.toJson();
			}
			if (null!=dtoEx.getContacts() && dtoEx.getContacts().length()>=80) {
				return RestCode.CODE_S009.toJson();
			}
			if (null!=dtoEx.getContactsTel() && dtoEx.getContactsTel().length()>=20) {
				return RestCode.CODE_S010.toJson();
			}
			dtoEx.setOrganizationCode(dtoEx.getOrganizationCode().toUpperCase());
			Boolean flag =  dtoEx.verifyUtils();
			if(!flag){
				result = RestCode.CODE_A001.toJson();
			}else{
				//公司名称是否含有"()"以外的特殊字符
				if(null != dtoEx.getName() && !RegexUtils.isSpecialChar(dtoEx.getName().trim())){
					result =  RestCode.CODE_S005.toJson();
				}else{
					result = memberFacadeService.prefectCompany(dtoEx, memberDto, session);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 密码重置
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	public String resetPassword(HttpServletRequest req) {
		try {
			String mobile = ServletUtils.getStringParameter(req, "mobile", "");
			String password = ServletUtils.getStringParameter(req, "password","");
			if("".equals(mobile)){
				return RestCode.CODE_A001.toJson();
			}
			if("".equals(password)){
				return RestCode.CODE_A001.toJson();
			}
			return memberFacadeService.resetPassword(mobile, password);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	/**
	 * 修改密码
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public String updatePassword(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		try {
			Sessions session = this.getSessions(req);
			String password = ServletUtils.getStringParameter(req, "password",
					"");
			String newPassword = ServletUtils.getStringParameter(req,
					"newPassword", "");
			if (null == password || "".equals(password)) {
				restCode = RestCode.CODE_A001;
				return restCode.toJson();
			}
			if (null == newPassword || "".equals(newPassword)) {
				restCode = RestCode.CODE_A001;
				return restCode.toJson();
			}
			return memberFacadeService.updatePassword(session.getMobile(), password, newPassword);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_S999.toJson();
		}
	}
	
	/**
	 * 获取会员信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getMember", method = RequestMethod.POST)
	public String isRegisted(HttpServletRequest req) {
		RestCode restCode = RestCode.CODE_0000;
		String mobile = ServletUtils.getStringParameter(req, "mobile","");
		if("".equals(mobile)){
			return RestCode.CODE_A001.toJson();
		}
		B2bMemberDto member = null;
		try {
			member =  b2bMemberService.getMember(mobile.trim());
			if(null == member){
				restCode =  RestCode.CODE_M002;
			}
		} catch (Exception e) {
			e.printStackTrace();
			restCode =  RestCode.CODE_S999;
		}
		return restCode.toJson(member);
	}
	
	
	/**
	 * 供应商入驻
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "supplierSettled", method = RequestMethod.POST)
	public String supplierSettled(HttpServletRequest req) {
		B2bCompanyDto dto = this.getCompanyBysessionsId(req);
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
		Sessions session = this.getSessions(req);
		String sessionId = ServletUtils.getStringParameter(req, "sessionId","");
		return memberCompanyService.supplierSettled(dto,memberDto,sessionId,session);
	}
	


}
