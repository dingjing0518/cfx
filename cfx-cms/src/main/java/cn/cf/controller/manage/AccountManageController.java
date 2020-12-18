/**
 * 
 */
package cn.cf.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.model.ManageAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.ManageRoleDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.manage.AccountManageService;
import cn.cf.util.ServletUtils;

/**
 * 账号，权限，Token管理功能控制Controller
 * @author bin
 */
@Controller
@RequestMapping("/")
public class AccountManageController extends BaseController {
	
	@Autowired
	private AccountManageService accountManageService;
	
	
	/**
	 * 账号列表页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("account")
	public ModelAndView account(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("manage/account");
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		ManageAuthorityDto dto = accountManageService.getManageAuthorityByPk(pk);
		mav.addObject("dto", dto);
		List<ManageRoleDto> mrList = accountManageService.getManageRoleByName(null);
		mav.addObject("mrList", mrList);
		return mav;
	}
	
	/**
	 * 查询账号集合
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("searchManageAccountList")
	@ResponseBody
	public String searchManageAccountList(HttpServletRequest request,
			HttpServletResponse response) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		ManageAccountExtDto entity = new ManageAccountExtDto();
		QueryModel<ManageAccountExtDto> qm = new QueryModel<ManageAccountExtDto>(entity,
				start, limit, orderName, orderType);
		PageModel<ManageAccountExtDto> pm = accountManageService
				.searchManageAccountList(qm);
		return JsonUtils.convertToString(pm);
	}
	
	/**
	 * 更新后台用户删除或者禁用
	 * @param account
	 * @return
	 */
	@RequestMapping("updateIsVisableOrDelete")
	@ResponseBody
	public String updateIsVisable(ManageAccount account)  {

		int result = accountManageService.updateIsVisableOrDelete(account);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	/**
	 * 后台账号 密码重置
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@RequestMapping("updateRePassword")
	@ResponseBody
	public String updateRePassword(HttpServletRequest request,
			HttpServletResponse response,String pk){
		int result = accountManageService.updateRePassword(pk);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}


	/**
	 * 更新后台用户删除或者禁用
	 * @param account
	 * @return
	 */
	@RequestMapping("updateManageAccount")
	@ResponseBody
	public String updateManageAccount(ManageAccount account) {
		accountManageService.updateManageAccount(account);
		return JsonUtils.convertToString(1);
	}




}
