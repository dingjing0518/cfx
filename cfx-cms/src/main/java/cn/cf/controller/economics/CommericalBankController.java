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
import cn.cf.entity.EconomicsBank;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsCommericalBankService;
import cn.cf.util.ServletUtils;
@Controller
@RequestMapping("/")
public class CommericalBankController extends BaseController {

	@Autowired
	private EconomicsCommericalBankService economicsDimensionService;

	/**
	 * 商业银行：列表页面
	 *
	 * @return
	 */
	@RequestMapping("commericalBank")
	public ModelAndView buildPropertyIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("dimension/commericalBank");
		List<EconomicsBank> list = economicsDimensionService.getCommericalBankList();
		mav.addObject("commericalBankList", list);
		return mav;
	}

	/**
	 * 商业银行：维护页面
	 *
	 * @return
	 */
	@RequestMapping("addCommericalBank")
	public ModelAndView addCommericalBank(HttpServletRequest request, HttpServletResponse response,
			EconomicsBank economicsCommericalBank) throws Exception {
		EconomicsBank economics = new EconomicsBank();
		ModelAndView mav = new ModelAndView("dimension/addCommericalBank");
		if (economicsCommericalBank.getPk() != null && !"".equals(economicsCommericalBank.getPk())) {
			economics = economicsDimensionService.getByPk(economicsCommericalBank.getPk());
		}
		mav.addObject("economics", economics);
		return mav;
	}

	@ResponseBody
	@RequestMapping("commericalBank_data")
	public String commericalBankData(HttpServletRequest request, HttpServletResponse response,
			EconomicsBank economicsCommericalBank) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<EconomicsBank> qm = new QueryModel<EconomicsBank>(economicsCommericalBank, start,
				limit, orderName, orderType);
		PageModel<EconomicsBank> pm = economicsDimensionService.getCommericalBank(qm);
		return JsonUtils.convertToString(pm);
	}

	@ResponseBody
	 @RequestMapping("updateCommericalBankStatus")
	 public String updateCommericalBankStatus(EconomicsBank  economicsCommericalBank) {
        int result = economicsDimensionService.updateStatus(economicsCommericalBank);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

	@ResponseBody
	@RequestMapping("updateCommericalBank")
	public String updateCommericalBank(EconomicsBank economicsCommericalBank) {
		int result = 0;
		if (economicsCommericalBank.getPk() != null && !Objects.equals(economicsCommericalBank.getPk(), ""))
			result = economicsDimensionService.update(economicsCommericalBank);
		else
			result = economicsDimensionService.insert(economicsCommericalBank);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}


}
