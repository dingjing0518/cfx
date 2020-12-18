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
import cn.cf.entity.EconTotalCreditAmount;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsTotalCreditAmountService;
import cn.cf.util.ServletUtils;
@Controller
@RequestMapping("/")
public class TotalCreditAmountController extends BaseController {
	
	@Autowired
	private EconomicsTotalCreditAmountService economicsDimensionService;
	

	/**
	 * 列表页面
	 *
	 * @return
	 */
	@RequestMapping("totalCreditAmount")
	public ModelAndView TotalCreditAmountIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("dimension/totalCreditAmount");
		List<EconTotalCreditAmount> list = economicsDimensionService.getTotalCreditAmountList();
		mav.addObject("totalCreditAmountList", list);
		return mav;
	}

	/**
	 * 维护页面
	 *
	 * @return
	 */
	@RequestMapping("addTotalCreditAmount")
	public ModelAndView addTotalCreditAmount(HttpServletRequest request, HttpServletResponse response,
			EconTotalCreditAmount econTotalCreditAmount) throws Exception {
		EconTotalCreditAmount economics = new EconTotalCreditAmount();
		ModelAndView mav = new ModelAndView("dimension/addTotalCreditAmount");
		if (econTotalCreditAmount.getPk() != null && !"".equals(econTotalCreditAmount.getPk())) {
			economics = economicsDimensionService.getByPk(econTotalCreditAmount.getPk());
		}
		mav.addObject("economics", economics);
		return mav;
	}

	@ResponseBody
	@RequestMapping("totalCreditAmount_data")
	public String totalCreditAmountData(HttpServletRequest request, HttpServletResponse response,
			EconTotalCreditAmount econTotalCreditAmount) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<EconTotalCreditAmount> qm = new QueryModel<EconTotalCreditAmount>(econTotalCreditAmount, start,
				limit, orderName, orderType);
		PageModel<EconTotalCreditAmount> pm = economicsDimensionService.getTotalCreditAmount(qm);
		return JsonUtils.convertToString(pm);
	}

		@ResponseBody
	 @RequestMapping("updateTotalCreditAmountStatus")
	 public String updateTotalCreditAmountStatus(EconTotalCreditAmount  EconTotalCreditAmount) {
        int result = economicsDimensionService.updateStatus(EconTotalCreditAmount);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

	@ResponseBody
	@RequestMapping("updateTotalCreditAmount")
	public String updateTotalCreditAmount(EconTotalCreditAmount econTotalCreditAmount) {
		int result = 0;
		if (econTotalCreditAmount.getPk() != null && !Objects.equals(econTotalCreditAmount.getPk(), ""))
			result = economicsDimensionService.update(econTotalCreditAmount);
		else
			result = economicsDimensionService.insert(econTotalCreditAmount);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
}
