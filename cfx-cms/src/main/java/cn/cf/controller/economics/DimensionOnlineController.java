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
import cn.cf.dto.EconomicsOnlineDto;
import cn.cf.entity.EconomicsOnline;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionOnlineService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionOnlineController extends BaseController {

    @Autowired
    private EconomicsDimensionOnlineService economicsDimensionService;

    /**
     * 线上数据：列表页面
     *
     * @return
     */
    @RequestMapping("onlineData")
    public ModelAndView onlineIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/online");
        List<EconomicsOnline> onlineList = economicsDimensionService.getOnlineList();
        mav.addObject("onlineList", onlineList);
        return mav;
    }

    /**
     *  线上数据：维护页面
     *
     * @return
     */
    @RequestMapping("addOnline")
    public ModelAndView addOnline(HttpServletRequest request, HttpServletResponse response, EconomicsOnline economicsOnline) throws Exception {

        EconomicsOnline online = new EconomicsOnline();
        ModelAndView mav = new ModelAndView("dimension/addOnline");
        if (economicsOnline.getPk() != null && !"".equals(economicsOnline.getPk())) {
            online = economicsDimensionService.getOnlineByPk(economicsOnline.getPk());
        }
        mav.addObject("online", online);
        return mav;
    }



    @ResponseBody
    @RequestMapping("online_list")
    public String capacityList(HttpServletRequest request, HttpServletResponse response, EconomicsOnlineDto economicsOnlineDto) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsOnlineDto> qm = new QueryModel<EconomicsOnlineDto>(economicsOnlineDto, start, limit, orderName, orderType);
        PageModel<EconomicsOnlineDto> pm = economicsDimensionService.getOnlineData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }



    @ResponseBody
    @RequestMapping("updateOnlineStatus")
    public String updateLimitStatus(EconomicsOnline economicsOnline) {
        int result = economicsDimensionService.updateOnlineStatus(economicsOnline);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateOnline")
    public String updateLimit(EconomicsOnline economicsOnline) {
        int result = 0;
        if (economicsOnline.getPk() != null && !Objects.equals(economicsOnline.getPk(), ""))
            result = economicsDimensionService.updateOnline(economicsOnline);
        else
            result = economicsDimensionService.insertOnline(economicsOnline);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }



}
