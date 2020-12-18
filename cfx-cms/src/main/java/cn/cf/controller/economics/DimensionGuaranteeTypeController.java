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
import cn.cf.dto.EconomicsGuaranteeTypeDto;
import cn.cf.entity.EconomicsGuaranteeType;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionGuaranteeService;
import cn.cf.util.ServletUtils;


@Controller
@RequestMapping("/")
public class DimensionGuaranteeTypeController   extends BaseController {

    @Autowired
    EconomicsDimensionGuaranteeService economicsDimensionGuaranteeService;

    /**
     * 担保类型：列表页面
     *
     * @return
     */
    @RequestMapping("guaranteeType")
    public ModelAndView guaranteeTypeIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/guaranteeType");
        List<EconomicsGuaranteeType> guaranteeTypeList = economicsDimensionGuaranteeService.getGuaranteeTypeList();
        mav.addObject("guaranteeTypeList", guaranteeTypeList);
        return mav;
    }


    /**
     * 担保类型：维护页面
     *
     * @return
     */
    @RequestMapping("addGuaranteeType")
    public ModelAndView addGuaranteeType(HttpServletRequest request, HttpServletResponse response, EconomicsGuaranteeType economicsGuaranteeType) throws Exception {


        EconomicsGuaranteeType guaranteeType = new EconomicsGuaranteeType();
        ModelAndView mav = new ModelAndView("dimension/addGuaranteeType");
        if (economicsGuaranteeType.getPk() != null && !"".equals(economicsGuaranteeType.getPk())) {
            guaranteeType = economicsDimensionGuaranteeService.getGuaranteeTypeByPk(economicsGuaranteeType.getPk());
        }
        mav.addObject("guaranteeType", guaranteeType);
        return mav;
    }


    @ResponseBody
    @RequestMapping("guaranteeType_list")
    public String guaranteeTypeList(HttpServletRequest request, HttpServletResponse response, EconomicsGuaranteeTypeDto economicsGuaranteeType) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsGuaranteeTypeDto> qm = new QueryModel<EconomicsGuaranteeTypeDto>(economicsGuaranteeType, start, limit, orderName, orderType);
        PageModel<EconomicsGuaranteeTypeDto> pm = economicsDimensionGuaranteeService.getGuaranteeTypeData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }


    @ResponseBody
    @RequestMapping("updateGuaranteeTypeStatus")
    public String updateGuaranteeTypeStatus(EconomicsGuaranteeType economicsGuaranteeType) {
        int result = economicsDimensionGuaranteeService.updateGuaranteeTypeStatus(economicsGuaranteeType);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateGuaranteeType")
    public String updateGuaranteeType(EconomicsGuaranteeType economicsGuaranteeType) {
        int result = 0;
        if (economicsGuaranteeType.getPk() != null && !Objects.equals(economicsGuaranteeType.getPk(), ""))
            result = economicsDimensionGuaranteeService.updateGuaranteeType(economicsGuaranteeType);
        else
            result = economicsDimensionGuaranteeService.insertGuaranteeType(economicsGuaranteeType);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }
}
