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
import cn.cf.entity.EconCreditOrginze;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsCreditOrginzeService;
import cn.cf.util.ServletUtils;
@Controller
@RequestMapping("/")
public class CreditOrginzeCountController extends BaseController  {
	
	@Autowired
	private EconomicsCreditOrginzeService economicsDimensionService;
	

	/**
	 * 列表页面
	 *
	 * @return
	 */
	@RequestMapping("creditOrginze")
	public ModelAndView creditOrginze(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("dimension/creditOrginze");
		List<EconCreditOrginze> list = economicsDimensionService.getCreditOrginzeList();
		mav.addObject("creditOrginzeList", list);
		return mav;
	}

	/**
	 * 维护页面
	 *
	 * @return
	 */
	@RequestMapping("addCreditOrginze")
	public ModelAndView addCreditOrginze(HttpServletRequest request, HttpServletResponse response,
			EconCreditOrginze econCreditOrginze) throws Exception {
		EconCreditOrginze economics = new EconCreditOrginze();
		ModelAndView mav = new ModelAndView("dimension/addCreditOrginze");
		if (econCreditOrginze.getPk() != null && !"".equals(econCreditOrginze.getPk())) {
			economics = economicsDimensionService.getByPk(econCreditOrginze.getPk());
		}
		mav.addObject("economics", economics);
		return mav;
	}

	@ResponseBody
	@RequestMapping("creditOrginze_data")
	public String creditOrginzeData(HttpServletRequest request, HttpServletResponse response,
			EconCreditOrginze econCreditOrginze) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<EconCreditOrginze> qm = new QueryModel<EconCreditOrginze>(econCreditOrginze, start,
				limit, orderName, orderType);
		PageModel<EconCreditOrginze> pm = economicsDimensionService.getCreditOrginze(qm);
		return JsonUtils.convertToString(pm);
	}

	 @ResponseBody
	 @RequestMapping("updateCreditOrginzeStatus")
	 public String updateCreditOrginzeStatus(EconCreditOrginze  EconCreditOrginze) {
        int result = economicsDimensionService.updateStatus(EconCreditOrginze);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

	@ResponseBody
	@RequestMapping("updateCreditOrginze")
	public String updateCreditOrginze(EconCreditOrginze EconCreditOrginze) {
		int result = 0;
		if (EconCreditOrginze.getPk() != null && !Objects.equals(EconCreditOrginze.getPk(), ""))
			result = economicsDimensionService.update(EconCreditOrginze);
		else
			result = economicsDimensionService.insert(EconCreditOrginze);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
}
