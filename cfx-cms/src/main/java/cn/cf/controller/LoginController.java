/**
 * 
 */
package cn.cf.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.StringHelper;
import cn.cf.common.BaseController;
import cn.cf.common.Global;
import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.ManageRoleDto;
import cn.cf.dto.MemberShip;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.MemberShipService;
import cn.cf.service.manage.AuthorityManageService;
import cn.cf.util.ServletUtils;

/**
 * @author bin
 * 
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	@Autowired
	private AuthorityManageService managementService;

	@Autowired
	private MemberShipService memberShipService;

	/**
	 * 登陆页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	@RequestMapping("test")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("test");
		return mav;
	}

	/**
	 * 登陆到首页（包括验证数据）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("home");
		String username = ServletUtils.getStringParameter(request, "j_username", null);
		String password = ServletUtils.getStringParameter(request, "j_password", null);
		ManageAccount opdto = null;
		if (null == username || "".equals(username)) {
			opdto = this.getAccount(request);
		}
		if (null != opdto) {
			username = opdto.getAccount();
			password = opdto.getPassword();
		}

		String msg = "";
		// 判断用户名和密码是否填写
		if (!StringHelper.hasText(username)) {
			msg += "请填写用户名。";
		}
		if (!StringHelper.hasText(password)) {
			msg += "请填写密码。";
		}
		ModelAndView model = new ModelAndView("login");
		if (msg.length() > 0) {
			model.addObject("msg", msg);
			model.addObject("username", username);
			return model;
		}
		// 用户信息
		ManageAccountExtDto dto = managementService.getManageAccountByLoginName(username);

		// 判断用户名是否存在或者是否被禁用
		if (dto == null) {
			msg += "用户[" + username + "]不存在";
			model.addObject("msg", msg);
			return model;
		}

		if (!dto.getPassword().equals(password)) {
			msg += "密码输入错误";
			model.addObject("msg", msg);
			return model;
		}
		if (dto.getIsVisable() == 2) {
			msg += "该账户已被禁用";
			model.addObject("msg", msg);
			return model;
		}
		// //查询用户角色
		if (dto.getRolePk() == null || "".equals(dto.getRolePk())) {
			dto.setRoleName("超级管理员");
		} else {
			ManageRoleDto role = managementService.getManageRoleByPk(dto.getRolePk());
			if (role != null) {
				dto.setRoleName(role.getName());

			}
			//判断登陆用户是否配置权限
			int count = managementService.isAuthority(dto.getRolePk());
			if(count == 0){
				msg += "该账户没有登陆权限";
				model.addObject("msg", msg);
				return model;
			}
		}
		String sessionId = request.getSession().getId();
		JedisMaterialUtils.set(sessionId, dto, 3600);

		MemberShip memberShip = null;
		String account = dto.getAccount();

		memberShip = memberShipService.getMemberShip(account);
		dto.setUserId(memberShip.getUserId());
		dto.setGroupId(memberShip.getGroupId());

		if (null != account && !"".equals(account)) {
			if (null != memberShip) {
				JedisMaterialUtils.set(account, memberShip, 3600);
			}
		}

		List<ManageAuthorityDto> list = managementService.getManageAuthorityByAccount("-1", dto.getRolePk(), -1, 0,
				null);
		// 如果是管理员只显示管理中心模块
		if (dto.getRolePk() == null || "".equals(dto.getRolePk())) {
			List<ManageAuthorityDto> listAdmin = new ArrayList<ManageAuthorityDto>();
			if (list != null && list.size() > 0) {
				ManageAuthorityDto adminDto = list.get(0);
				listAdmin.add(adminDto);
				mav.addObject("list", listAdmin);
			}
		} else {
			mav.addObject("list", list);
		}

		if (null != list && list.size() == 1) {
			ManageAuthorityDto ma = list.get(0);
			mav = new ModelAndView("redirect:" + ma.getUrl());// redirect模式
		}

		return mav;

	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("doLogout")
	public ModelAndView doLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Global.removeAllSession(request);

		ManageAccount account = getAccount(request);

		if (null != account) {
			JedisMaterialUtils.del(account.getAccount());
		}

		response.sendRedirect("/");
		return null;
	}

	@RequestMapping("editPassowrdyet")
	@ResponseBody
	public String editPassowrdyet(ManageAccount account) {
		managementService.updatePassword(account);
		return JsonUtils.convertToString(1);
	}

	/**
	 * 账户管理,密码重置
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setting")
	public ModelAndView setting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("manage/setting");
		ManageAccount adto = this.getAccount(request);
		mav.addObject("adto", adto);
		ManageRoleDto rdto = managementService.getManageRoleByPk(adto.getRolePk());
		if (null != rdto) {
			mav.addObject("roleName", rdto.getName());
		}
		return mav;
	}

}
