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
import cn.cf.dto.EconomicsLimitDto;
import cn.cf.entity.EconomicsLimit;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionLimitService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionLimitController extends BaseController {

    @Autowired
    private EconomicsDimensionLimitService economicsDimensionService;

    /**
     * 最高分：列表页面
     *
     * @return
     */
    @RequestMapping("limit")
    public ModelAndView capacityIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/limit");
        List<EconomicsLimit> limitList = economicsDimensionService.getLimitList();
        mav.addObject("limitList", limitList);
        return mav;
    }

    /**
     * 最高分：维护页面
     *
     * @return
     */
    @RequestMapping("addLimit")
    public ModelAndView addLimit(HttpServletRequest request, HttpServletResponse response, EconomicsLimit economicsLimit) throws Exception {


        EconomicsLimit limit = new EconomicsLimit();
        ModelAndView mav = new ModelAndView("dimension/addLimit");
        if (economicsLimit.getPk() != null && !"".equals(economicsLimit.getPk())) {
            limit = economicsDimensionService.getLimitByPk(economicsLimit.getPk());
        }
        mav.addObject("limit", limit);
        return mav;
    }


    @ResponseBody
    @RequestMapping("limit_list")
    public String capacityList(HttpServletRequest request, HttpServletResponse response, EconomicsLimitDto economicsLimit) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsLimitDto> qm = new QueryModel<EconomicsLimitDto>(economicsLimit, start, limit, orderName, orderType);
        PageModel<EconomicsLimitDto> pm = economicsDimensionService.getLimitData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }



    @ResponseBody
    @RequestMapping("updateLimitStatus")
    public String updateLimitStatus(EconomicsLimit economicsLimit) {
        int result = economicsDimensionService.updateLimitStatus(economicsLimit);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }


    @ResponseBody
    @RequestMapping("updateLimit")
    public String updateLimit(EconomicsLimit economicsLimit) {
        int result = 0;
        if (economicsLimit.getPk() != null && !Objects.equals(economicsLimit.getPk(), ""))
            result = economicsDimensionService.updateLimit(economicsLimit);
        else
            result = economicsDimensionService.insertLimit(economicsLimit);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

}
