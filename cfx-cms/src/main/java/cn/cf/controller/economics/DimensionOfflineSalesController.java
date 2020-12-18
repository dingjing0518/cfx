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
import cn.cf.entity.EconomicsOfflineSales;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionOfflineService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionOfflineSalesController extends BaseController {

    @Autowired
    EconomicsDimensionOfflineService economicsDimensionOfflineService;



    /**
     * 线下数据销售：列表页面
     *
     * @return
     */
    @RequestMapping("offlineSales")
    public ModelAndView offlineSalesIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/offlineSales");
        List<EconomicsOfflineSales> offlineSalesList = economicsDimensionOfflineService.getOfflineSalesList();
        mav.addObject("offlineSalesList", offlineSalesList);
        return mav;
    }


    /**
     * 线下数据销售：维护页面
     *
     * @return
     */
    @RequestMapping("addOfflineSales")
    public ModelAndView addOfflineSales(HttpServletRequest request, HttpServletResponse response, EconomicsOfflineSales economicsOfflineSales) throws Exception {

        EconomicsOfflineSales offlineSales = new EconomicsOfflineSales();
        ModelAndView mav = new ModelAndView("dimension/addOfflineSales");
        if (economicsOfflineSales.getPk() != null && !"".equals(economicsOfflineSales.getPk())) {
            offlineSales = economicsDimensionOfflineService.getOfflineSalesByPk(economicsOfflineSales.getPk());
        }
        mav.addObject("offlineSales", offlineSales);
        return mav;
    }




    @ResponseBody
    @RequestMapping("offlineSales_list")
    public String offlineSalesList(HttpServletRequest request, HttpServletResponse response, EconomicsOfflineSales economicsOfflineSales) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsOfflineSales> qm = new QueryModel<EconomicsOfflineSales>(economicsOfflineSales, start, limit, orderName, orderType);
        PageModel<EconomicsOfflineSales> pm = economicsDimensionOfflineService.getOfflineSalesData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }




    @ResponseBody
    @RequestMapping("updateOfflineSalesStatus")
    public String updateOfflineSalesStatus(EconomicsOfflineSales economicsOfflineSales) {
        int result = economicsDimensionOfflineService.updateOfflineSalesStatus(economicsOfflineSales);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateOfflineSales")
    public String updateOfflineSales(EconomicsOfflineSales economicsOfflineSales) {
        int result = 0;
        if (economicsOfflineSales.getPk() != null && !Objects.equals(economicsOfflineSales.getPk(), ""))
            result = economicsDimensionOfflineService.updateOfflineSales(economicsOfflineSales);
        else
            result = economicsDimensionOfflineService.insertOfflineSales(economicsOfflineSales);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }




}
