package cn.cf.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.common.BaseController;
import cn.cf.service.manage.LogManageService;

/**
 * 后台管理中心操作日志，和短信站内信操作及参数配置等Controller
 * 
 * @author hxh
 *
 */
@Controller
@RequestMapping("/")
public class LogManageController extends BaseController {

	@Autowired
	private LogManageService logManageService;

	/**
	 * 系统参数配置页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sysParams")
	public ModelAndView sysParams(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("sysParams");

		return mav;
	}
	
	/**
	 * 系统配置参数列表
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("searchSysParams")
//	public String searchSysParams(HttpServletRequest request, HttpServletResponse response,SysParamsDto dto) throws Exception {
//		int start = ServletUtils.getIntParameter(request, "start", 0);
//		int limit = ServletUtils.getIntParameter(request, "limit", 10);
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
//		QueryModel<SysParamsDto> qm = new QueryModel<SysParamsDto>(dto,
//				start, limit, orderName, orderType);
//
//		return JsonUtils.convertToString(logManageService.searchSysParamsList(qm));
//	}
	
	/**
	 * 系统配置参数修改
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("updateSysParams")
//	public String updateSysParams(HttpServletRequest request, HttpServletResponse response,SysParamsDto dto) throws Exception {
//		
//		int result = logManageService.updateSysParams(dto);
//		String msg = "";
//		if(result > 0 ){
//			msg = Constants.RESULT_SUCCESS_MSG;
//		}else{
//			msg = Constants.RESULT_FAIL_MSG;
//		}
//		return JsonUtils.convertToString(msg);
//	}
}
