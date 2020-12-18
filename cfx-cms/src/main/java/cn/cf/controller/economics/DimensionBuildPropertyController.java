package cn.cf.controller.economics;

import java.util.List;
import java.util.Objects;

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
import cn.cf.entity.EconomicsBuildProperty;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionBuildPropertyService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionBuildPropertyController extends BaseController {

	@Autowired
	private EconomicsDimensionBuildPropertyService economicsDimensionService;

	/**
	 * 房地产抵押金额：列表页面
	 *
	 * @return
	 */
	@RequestMapping("buildProperty")
	public ModelAndView buildPropertyIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("dimension/buildProperty");
		List<EconomicsBuildProperty> list = economicsDimensionService.getBuildPropertyList();
		mav.addObject("buildPropertyList", list);
		return mav;
	}

	/**
	 * 房地产抵押金额：维护页面
	 *
	 * @return
	 */
	@RequestMapping("addBuildProperty")
	public ModelAndView addBuildProperty(HttpServletRequest request, HttpServletResponse response,
			EconomicsBuildProperty economicsBuildProperty) throws Exception {
		EconomicsBuildProperty economics = new EconomicsBuildProperty();
		ModelAndView mav = new ModelAndView("dimension/addBuildProperty");
		if (economicsBuildProperty.getPk() != null && !"".equals(economicsBuildProperty.getPk())) {
			economics = economicsDimensionService.getByPk(economicsBuildProperty.getPk());
		}
		mav.addObject("economics", economics);
		return mav;
	}

	@ResponseBody
	@RequestMapping("buildProperty_data")
	public String buildPropertyData(HttpServletRequest request, HttpServletResponse response,
			EconomicsBuildProperty economicsBuildProperty) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<EconomicsBuildProperty> qm = new QueryModel<EconomicsBuildProperty>(economicsBuildProperty, start,
				limit, orderName, orderType);
		PageModel<EconomicsBuildProperty> pm = economicsDimensionService.getbuildProperty(qm);
		return JsonUtils.convertToString(pm);
	}

		@ResponseBody
	 @RequestMapping("updateStatus")
	 public String updateStatus(EconomicsBuildProperty  economicsBuildProperty) {
        int result = economicsDimensionService.updateStatus(economicsBuildProperty);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

	@ResponseBody
	@RequestMapping("updateBuildProperty")
	public String updateBuildProperty(EconomicsBuildProperty economicsBuildProperty) {
		int result = 0;
		if (economicsBuildProperty.getPk() != null && !Objects.equals(economicsBuildProperty.getPk(), ""))
			result = economicsDimensionService.update(economicsBuildProperty);
		else
			result = economicsDimensionService.insert(economicsBuildProperty);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}


}
