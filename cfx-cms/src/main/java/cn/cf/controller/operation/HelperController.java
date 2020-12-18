package cn.cf.controller.operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysHelpsCategoryDto;
import cn.cf.dto.SysHelpsDto;
import cn.cf.dto.SysHelpsExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.HelperCategoryService;
import cn.cf.service.operation.HelperService;
import cn.cf.util.ServletUtils;

/**
 * 帮助中心
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class HelperController extends BaseController {

	@Autowired
	private HelperService helperService;
	@Autowired
	private HelperCategoryService helperCategoryService;

	/**
	 * 帮助列表显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("helps")
	public ModelAndView helps(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/helps");
		mav.addObject("helperCategoryList", helperCategoryService.getAllSysHelpCategoryData());
		return mav;
	}

	/**
	 * 帮助分类显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("helpscatogy")
	public ModelAndView helpscategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/helpscatogy");
		return mav;
	}

	/**
	 * 帮助列表查询
	 * 
	 * @param request
	 * @param response
	 * @param sysHelp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("helps_data")
	public String helpsdata(HttpServletRequest request, HttpServletResponse response, SysHelpsExtDto sysHelp) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

		QueryModel<SysHelpsExtDto> qm = new QueryModel<SysHelpsExtDto>(sysHelp, start, limit, orderName, orderType);
		PageModel<SysHelpsExtDto> pm = helperService.searchsysHelpdata(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 修改帮助数据
	 * 
	 * @param sysHelpsDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSysHelps")
	public String updateSysHelps(SysHelpsDto sysHelpsDto) {

		int result = helperService.updateSysHelps(sysHelpsDto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 删除帮助数据
	 * 
	 * @param pk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delSysHelps")
	public String deleteSysHelps(String pk) {

		int result = helperService.delSysHelps(pk);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 帮助分类搜索
	 * 
	 * @param request
	 * @param response
	 * @param sysHelpsCategoryDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("helpscategory_data")
	public String helpsCatogydata(HttpServletRequest request, HttpServletResponse response,
			SysHelpsCategoryDto sysHelpsCategoryDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

		QueryModel<SysHelpsCategoryDto> qm = new QueryModel<SysHelpsCategoryDto>(sysHelpsCategoryDto, start, limit,
				orderName, orderType);
		PageModel<SysHelpsCategoryDto> pm = helperCategoryService.searchSysHelpCategoryData(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 修改帮助分类状态
	 * 
	 * @param sysHelpsCategoryDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSysHelpsCategoryStatus")
	public String updateSysHelpsCategoryStatus(SysHelpsCategoryDto sysHelpsCategoryDto) {

		int result = helperCategoryService.updateSysHelpsCategoryStatus(sysHelpsCategoryDto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 删除帮助分类数据
	 * 
	 * @param pk
	 * @return
	 */

	@ResponseBody
	@RequestMapping("delSysHelpsCategory")
	public String delSysHelpsCategory(String pk) {

		int result = helperCategoryService.delSysHelpsCategory(pk);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 修改帮助分类
	 * 
	 * @param sysHelpsCategoryDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSysHelpsCategory")
	public String updateSysHelpsCategory(SysHelpsCategoryDto sysHelpsCategoryDto) {

		int result = helperCategoryService.updateSysHelpsCategory(sysHelpsCategoryDto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = Constants.CATEGORY_NAME_EXIST;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 新增帮助
	 * 
	 * @param request
	 * @param response
	 * @param helps
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addHelps")
	public ModelAndView addHelps(HttpServletRequest request, HttpServletResponse response, SysHelpsDto helps)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/addHelps");
		if (helps.getPk() != null && !"".equals(helps.getPk())) {
			helps = helperService.getSysHelpsByPk(helps.getPk());
		}
		mav.addObject("helps", helps);
		mav.addObject("sysHelpsCategory", helperCategoryService.getAllSysHelpCategoryData());
		return mav;
	}

	/**
	 * 增加帮助
	 * 
	 * @param sysHelpsDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertSysHelps")
	public String insertSysHelps(SysHelpsDto sysHelpsDto) {

		int result = helperService.insertSysHelps(sysHelpsDto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = Constants.CATEGORY_IS_CONTAINS;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

}
