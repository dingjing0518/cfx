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
import cn.cf.entity.EconomicsOfflineScale;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionOfflineService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionOfflineScaleController  extends BaseController {

    @Autowired
    EconomicsDimensionOfflineService economicsDimensionOfflineService;




    /**
     * 线下数据规模：列表页面
     *
     * @return
     */
    @RequestMapping("offlineScale")
    public ModelAndView offlineScaleIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/offlineScale");
        List<EconomicsOfflineScale> offlineScaleList = economicsDimensionOfflineService.getOfflineScaleList();
        mav.addObject("offlineScaleList", offlineScaleList);
        return mav;
    }


    /**
     * 线下数据规模：维护页面
     *
     * @return
     */
    @RequestMapping("addOfflineScale")
    public ModelAndView addOfflineScale(HttpServletRequest request, HttpServletResponse response, EconomicsOfflineScale economicsOfflineScale) throws Exception {

        EconomicsOfflineScale offlineScale = new EconomicsOfflineScale();
        ModelAndView mav = new ModelAndView("dimension/addOfflineScale");
        if (economicsOfflineScale.getPk() != null && !"".equals(economicsOfflineScale.getPk())) {
            offlineScale = economicsDimensionOfflineService.getOfflineScaleByPk(economicsOfflineScale.getPk());
        }
        mav.addObject("offlineScale", offlineScale);
        return mav;
    }



    @ResponseBody
    @RequestMapping("offlineScale_list")
    public String offlineScaleList(HttpServletRequest request, HttpServletResponse response, EconomicsOfflineScale economicsOfflineScale) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsOfflineScale> qm = new QueryModel<EconomicsOfflineScale>(economicsOfflineScale, start, limit, orderName, orderType);
        PageModel<EconomicsOfflineScale> pm = economicsDimensionOfflineService.getOfflineScaleData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }




    @ResponseBody
    @RequestMapping("updateOfflineScaleStatus")
    public String updateOfflineScaleStatus(EconomicsOfflineScale economicsOfflineScale) {
        int result = economicsDimensionOfflineService.updateOfflineScaleStatus(economicsOfflineScale);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateOfflineScale")
    public String updateOfflineScale(EconomicsOfflineScale economicsOfflineScale) {
        int result = 0;
        if (economicsOfflineScale.getPk() != null && !Objects.equals(economicsOfflineScale.getPk(), ""))
            result = economicsDimensionOfflineService.updateOfflineScale(economicsOfflineScale);
        else
            result = economicsDimensionOfflineService.insertOfflineScale(economicsOfflineScale);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }


}
