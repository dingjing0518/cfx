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
import cn.cf.dto.EconomicsGuaranteeTargetDto;
import cn.cf.entity.EconomicsGuaranteeTarget;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionGuaranteeService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionGuaranteeTargetController   extends BaseController {

    @Autowired
    EconomicsDimensionGuaranteeService economicsDimensionGuaranteeService;

    /**
     * 担保目标：列表页面
     *
     * @return
     */
    @RequestMapping("guaranteeTarget")
    public ModelAndView guaranteeTargetIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/guaranteeTarget");
        List<EconomicsGuaranteeTarget> guaranteeTargetList = economicsDimensionGuaranteeService.getGuaranteeTargetList();
        mav.addObject("guaranteeTargetList", guaranteeTargetList);
        return mav;
    }

    /**
     * 担保类型：维护页面
     *
     * @return
     */
    @RequestMapping("addGuaranteeTarget")
    public ModelAndView addGuaranteeTarget(HttpServletRequest request, HttpServletResponse response, EconomicsGuaranteeTarget economicsGuaranteeTarget) throws Exception {


        EconomicsGuaranteeTarget guaranteeTarget = new EconomicsGuaranteeTarget();
        ModelAndView mav = new ModelAndView("dimension/addGuaranteeTarget");
        if (economicsGuaranteeTarget.getPk() != null && !"".equals(economicsGuaranteeTarget.getPk())) {
            guaranteeTarget = economicsDimensionGuaranteeService.getGuaranteeTargetByPk(economicsGuaranteeTarget.getPk());
        }
        mav.addObject("guaranteeTarget", guaranteeTarget);
        return mav;
    }


    @ResponseBody
    @RequestMapping("guaranteeTarget_list")
    public String guaranteeTargetList(HttpServletRequest request, HttpServletResponse response, EconomicsGuaranteeTargetDto economicsGuaranteeTarget) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsGuaranteeTargetDto> qm = new QueryModel<EconomicsGuaranteeTargetDto>(economicsGuaranteeTarget, start, limit, orderName, orderType);
        PageModel<EconomicsGuaranteeTargetDto> pm = economicsDimensionGuaranteeService.getGuaranteeTargetData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }


    @ResponseBody
    @RequestMapping("updateGuaranteeTargetStatus")
    public String updateGuaranteeTargetStatus(EconomicsGuaranteeTarget economicsGuaranteeTarget) {
        int result = economicsDimensionGuaranteeService.updateGuaranteeTargetStatus(economicsGuaranteeTarget);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateGuaranteeTarget")
    public String updateGuaranteeTarget(EconomicsGuaranteeTarget economicsGuaranteeTarget) {
        int result = 0;
        if (economicsGuaranteeTarget.getPk() != null && !Objects.equals(economicsGuaranteeTarget.getPk(), ""))
            result = economicsDimensionGuaranteeService.updateGuaranteeTarget(economicsGuaranteeTarget);
        else
            result = economicsDimensionGuaranteeService.insertGuaranteeTarget(economicsGuaranteeTarget);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }
}
