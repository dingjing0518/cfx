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
import cn.cf.dto.EconomicsGuaranteeCountDto;
import cn.cf.entity.EconomicsGuaranteeCount;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionGuaranteeService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionGuaranteeCountController  extends BaseController {

    @Autowired
    EconomicsDimensionGuaranteeService economicsDimensionGuaranteeService;


    /**
     * 担保数量：列表页面
     *
     * @return
     */
    @RequestMapping("guaranteeCount")
    public ModelAndView guaranteeCountIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/guaranteeCount");
        List<EconomicsGuaranteeCount> guaranteeCountList = economicsDimensionGuaranteeService.getGuaranteeCountList();
        mav.addObject("guaranteeCountList", guaranteeCountList);
        return mav;
    }



    /**
     * 担保数量：维护页面
     *
     * @return
     */
    @RequestMapping("addGuaranteeCount")
    public ModelAndView addGuaranteeCount(HttpServletRequest request, HttpServletResponse response, EconomicsGuaranteeCount economicsGuaranteeCount) throws Exception {


        EconomicsGuaranteeCount guaranteeCount = new EconomicsGuaranteeCount();
        ModelAndView mav = new ModelAndView("dimension/addGuaranteeCount");
        if (economicsGuaranteeCount.getPk() != null && !"".equals(economicsGuaranteeCount.getPk())) {
            guaranteeCount = economicsDimensionGuaranteeService.getGuaranteeCountByPk(economicsGuaranteeCount.getPk());
        }
        mav.addObject("guaranteeCount", guaranteeCount);
        return mav;
    }




    @ResponseBody
    @RequestMapping("guaranteeCount_list")
    public String guaranteeCountList(HttpServletRequest request, HttpServletResponse response, EconomicsGuaranteeCountDto economicsGuaranteeCount) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsGuaranteeCountDto> qm = new QueryModel<EconomicsGuaranteeCountDto>(economicsGuaranteeCount, start, limit, orderName, orderType);
        PageModel<EconomicsGuaranteeCountDto> pm = economicsDimensionGuaranteeService.getGuaranteeCountData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }



    @ResponseBody
    @RequestMapping("updateGuaranteeCountStatus")
    public String updateGuaranteeCountStatus(EconomicsGuaranteeCount economicsGuaranteeCount) {
        int result = economicsDimensionGuaranteeService.updateGuaranteeCountStatus(economicsGuaranteeCount);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }


    @ResponseBody
    @RequestMapping("updateGuaranteeCount")
    public String updateGuaranteeCount(EconomicsGuaranteeCount economicsGuaranteeCount) {
        int result = 0;
        if (economicsGuaranteeCount.getPk() != null && !Objects.equals(economicsGuaranteeCount.getPk(), ""))
            result = economicsDimensionGuaranteeService.updateGuaranteeCount(economicsGuaranteeCount);
        else
            result = economicsDimensionGuaranteeService.insertGuaranteeCount(economicsGuaranteeCount);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

}
