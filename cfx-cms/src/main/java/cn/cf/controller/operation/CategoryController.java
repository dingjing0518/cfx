package cn.cf.controller.operation;

import java.util.List;

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

import cn.cf.dto.SysCategoryDto;
import cn.cf.dto.SysCategoryExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.SysCategory;
import cn.cf.service.operation.SysCategoryService;
import cn.cf.util.ServletUtils;

/**
 * 文章资讯
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class CategoryController extends BaseController {

	@Autowired
	private SysCategoryService sysCategoryService;
	/**
	 * 文章分类页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("articlecatogy")
	public ModelAndView articlecatogy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/articlecatogy");
		return mav;
	}
	
	/**
	 * 文章查询列表
	 * @param request
	 * @param response
	 * @param sysCategoryDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("category_data")
	public String categorydata(HttpServletRequest request, HttpServletResponse response,
			SysCategoryDto sysCategoryDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

		QueryModel<SysCategoryDto> qm = new QueryModel<SysCategoryDto>(sysCategoryDto, start, limit, orderName,
				orderType);
		PageModel<SysCategoryDto> pm = sysCategoryService.searchSysCategorydata(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 文章修改
	 * @param sysCategory
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatesysCategory")
	public String updatesysCategory(SysCategory sysCategory) {

		int result = sysCategoryService.updatesysCategory(sysCategory);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.CATEGORY_NAME_EXIST;
		}
		if (result == 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}
	/**
	 * 根据父类PK查询文章分类
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCategoryByPid")
	public String getCategoryByPid(String parentId,String block) {
		if (!"null".equals(parentId)) {
			List<SysCategoryExtDto> list = sysCategoryService.getCategoryByPid(parentId,block);
			return JsonUtils.convertToString(list);
		} else {
			return JsonUtils.convertToString(1);
		}
	}

	/**
	 * 新增文章页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("article")
	public ModelAndView article(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/article");
		List<SysCategoryDto> categorys = sysCategoryService.getAllCategory();
		mav.addObject("categorysList", categorys);
		return mav;
	}

}
