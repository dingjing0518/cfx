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
import cn.cf.dto.B2bLiveBroadCastCategoryExDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.B2bBroadCastCategoryService;
import cn.cf.util.ServletUtils;

/**
 * 直播分类
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class LiveBroadCastCategoryController extends BaseController {

	@Autowired
	private B2bBroadCastCategoryService b2bBroadCastCategoryService;

	/**
	 * 直播分类显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("livebroadcastcategory")
	public ModelAndView articlecatogy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  ModelAndView mav = new ModelAndView("operation/livebroadcastcategory");
		return mav;
	}

	/**
	 * 直播分类查询
	 * 
	 * @param request
	 * @param response
	 * @param b2bLiveBraodCastCategoryExDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("livebroadcastcategory_data")
	@ResponseBody
	public String getAllLiveBroadCastCategory(HttpServletRequest request, HttpServletResponse response,
			B2bLiveBroadCastCategoryExDto b2bLiveBraodCastCategoryExDto) throws Exception {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

		QueryModel<B2bLiveBroadCastCategoryExDto> qm = new QueryModel<B2bLiveBroadCastCategoryExDto>(
				b2bLiveBraodCastCategoryExDto, start, limit, orderName, orderType);
		PageModel<B2bLiveBroadCastCategoryExDto> pm = b2bBroadCastCategoryService.searchLiveBroadCastCategorydata(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 直播分类修改
	 * 
	 * @param b2bLiveBraodCastCategoryExDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("updateLiveBroadCastCategory")
	public String updatesysCategory(B2bLiveBroadCastCategoryExDto b2bLiveBraodCastCategoryExDto) throws Exception {

		int result = b2bBroadCastCategoryService.updateLiveBroadCastCategory(b2bLiveBraodCastCategoryExDto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.CATEGORY_NAME_EXIST;
		}
		if (result == 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

}
