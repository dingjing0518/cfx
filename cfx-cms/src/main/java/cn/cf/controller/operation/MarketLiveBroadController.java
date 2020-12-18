package cn.cf.controller.operation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.dto.SysMarketLivebroadDto;
import cn.cf.model.SysLivebroadCategory;
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
import cn.cf.dto.B2bMarketLiveBroadExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.B2bBroadCastCategoryService;
import cn.cf.service.operation.B2bMarketLiveBroadService;
import cn.cf.util.ServletUtils;

/**
 * 行情直播
 * 
 * @author why
 *
 */
@Controller
@RequestMapping("/")
public class MarketLiveBroadController extends BaseController {

	@Autowired
	private B2bMarketLiveBroadService b2bMarketLiveBroadService;

	@Autowired
	private B2bBroadCastCategoryService b2bBroadCastCategoryService;

	/**
	 * 行情直播显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("marketLiveBroad")
	public ModelAndView marketLiveBroad(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/marketLiveBroad");
        List<B2bLiveBroadCastCategoryExDto> mrList = b2bBroadCastCategoryService.getAllBroadCastCategory();
		mav.addObject("broadCategoryList", mrList);
		return mav;
	}

	/**
	 * 行情直播查询列表
	 * 
	 * @param request
	 * @param response
	 * @param b2bMarketLiveBroadExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("marketLiveBroad_data")
	@ResponseBody
	public String getAllLiveBroad(HttpServletRequest request, HttpServletResponse response,
			B2bMarketLiveBroadExtDto b2bMarketLiveBroadExtDto) throws Exception {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

		QueryModel<B2bMarketLiveBroadExtDto> qm = new QueryModel<B2bMarketLiveBroadExtDto>(b2bMarketLiveBroadExtDto,
				start, limit, orderName, orderType);
		PageModel<B2bMarketLiveBroadExtDto> pm = b2bMarketLiveBroadService.searchMarketLiveBroaddata(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 修改行情直播状态
	 * 
	 * @param request
	 * @param response
	 * @param b2bMarketLiveBroadExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMarketLiveBroadStatus")
	@ResponseBody
	public String updateMarketLiveBroadStatus(HttpServletRequest request, HttpServletResponse response,
			B2bMarketLiveBroadExtDto b2bMarketLiveBroadExtDto) throws Exception {

		int result = b2bMarketLiveBroadService.updateMarketLiveBroad(b2bMarketLiveBroadExtDto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result == 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 修改行情直播状态
	 *
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMarketLiveBroadByPk")
	@ResponseBody
	public String getMarketLiveBroadByPk(HttpServletRequest request, HttpServletResponse response,String pk) throws Exception {
		SysMarketLivebroadDto dto = b2bMarketLiveBroadService.getMarketLiveBroadByPk(pk);
		return JsonUtils.convertToString(dto);
	}

	/**
	 * 修改行情直播状态
	 *
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getLivebroadcategoryByPk")
	@ResponseBody
	public String getLiveBroadCategoryByPk(HttpServletRequest request, HttpServletResponse response,String pk) throws Exception {
		SysLivebroadCategoryDto dto = b2bMarketLiveBroadService.getLiveCategoryByPk(pk);
		return JsonUtils.convertToString(dto);
	}

}
