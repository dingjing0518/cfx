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
import cn.cf.service.enconmics.EconomicsNationBankService;
import cn.cf.util.ServletUtils;
@Controller
@RequestMapping("/")
public class NationalBankController extends BaseController {

	@Autowired
	private EconomicsNationBankService economicsDimensionService;

	/**
	 * 商业银行：列表页面
	 *
	 * @return
	 */
	@RequestMapping("nationBank")
	public ModelAndView nationBankIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("dimension/nationBank");
		List<EconomicsBank> list = economicsDimensionService.getNationBankList();
		mav.addObject("NationBankList", list);
		return mav;
	}

	/**
	 * 商业银行：维护页面
	 *
	 * @return
	 */
	@RequestMapping("addNationBank")
	public ModelAndView addNationBank(HttpServletRequest request, HttpServletResponse response,
			EconomicsBank economicsBank) throws Exception {
		EconomicsBank economics = new EconomicsBank();
		ModelAndView mav = new ModelAndView("dimension/addNationBank");
		if (economicsBank.getPk() != null && !"".equals(economicsBank.getPk())) {
			economics = economicsDimensionService.getByPk(economicsBank.getPk());
		}
		mav.addObject("economics", economics);
		return mav;
	}

	@ResponseBody
	@RequestMapping("nationBank_data")
	public String nationBankData(HttpServletRequest request, HttpServletResponse response,
			EconomicsBank economicsBank) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<EconomicsBank> qm = new QueryModel<EconomicsBank>(economicsBank, start,
				limit, orderName, orderType);
		PageModel<EconomicsBank> pm = economicsDimensionService.getNationBank(qm);
		return JsonUtils.convertToString(pm);
	}

		@ResponseBody
	 @RequestMapping("updateNationBankStatus")
	 public String updateNationBankStatus(EconomicsBank  economicsBank) {
        int result = economicsDimensionService.updateStatus(economicsBank);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

	@ResponseBody
	@RequestMapping("updateNationBank")
	public String updateNationBank(EconomicsBank economicsBank) {
		int result = 0;
		if (economicsBank.getPk() != null && !Objects.equals(economicsBank.getPk(), ""))
			result = economicsDimensionService.update(economicsBank);
		else
			result = economicsDimensionService.insert(economicsBank);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result < 0) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}


}
