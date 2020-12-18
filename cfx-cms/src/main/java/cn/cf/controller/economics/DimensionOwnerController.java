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
import cn.cf.entity.EconomicsOwnership;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionOwnershipService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionOwnerController  extends BaseController {

    @Autowired
    private EconomicsDimensionOwnershipService economicsDimensionOwnershipService;


    /**
     * 权属：列表页面
     *
     * @return
     */
    @RequestMapping("ownership")
    public ModelAndView ownershipIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/ownership");
        List<EconomicsOwnership> ownershipList = economicsDimensionOwnershipService.getOwnershipList();
        mav.addObject("ownershipList", ownershipList);
        return mav;
    }


    /**
     * 产能：维护页面
     *
     * @return
     */
    @RequestMapping("addOwnership")
    public ModelAndView addOwnership(HttpServletRequest request, HttpServletResponse response, EconomicsOwnership economicsOwnership) throws Exception {

        EconomicsOwnership ownership = new EconomicsOwnership();
        ModelAndView mav = new ModelAndView("dimension/addOwnership");
        if (economicsOwnership.getPk() != null && !"".equals(economicsOwnership.getPk())) {
            ownership = economicsDimensionOwnershipService.getOwnershipByPk(economicsOwnership.getPk());
        }
        mav.addObject("ownership", ownership);
        return mav;
    }



    @ResponseBody
    @RequestMapping("ownership_list")
    public String ownershipList(HttpServletRequest request, HttpServletResponse response, EconomicsOwnership economicsOwnership) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsOwnership> qm = new QueryModel<EconomicsOwnership>(economicsOwnership, start, limit, orderName, orderType);
        PageModel<EconomicsOwnership> pm = economicsDimensionOwnershipService.getOwnershipData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }



    @ResponseBody
    @RequestMapping("updateOwnershipStatus")
    public String updateOwnershipStatus(EconomicsOwnership economicsOwnership) {
        int result = economicsDimensionOwnershipService.updateOwnershipStatus(economicsOwnership);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateOwnership")
    public String updateOwnership(EconomicsOwnership economicsOwnership) {
        int result = 0;
        if (economicsOwnership.getPk() != null && !Objects.equals(economicsOwnership.getPk(), ""))
            result = economicsDimensionOwnershipService.updateOwnership(economicsOwnership);
        else
            result = economicsDimensionOwnershipService.insertOwnership(economicsOwnership);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }




}
