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
import cn.cf.entity.EconomicsOfflineInvoice;
import cn.cf.json.JsonUtils;
import cn.cf.service.enconmics.EconomicsDimensionOfflineService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class DimensionOfflineInvoiceController extends BaseController {

    @Autowired
    EconomicsDimensionOfflineService economicsDimensionOfflineService;


    /**
     * 线下数据发票：列表页面
     *
     * @return
     */
    @RequestMapping("offlineInvoice")
    public ModelAndView offlineInvoiceIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("dimension/offlineInvoice");
        List<EconomicsOfflineInvoice> offlineInvoiceList = economicsDimensionOfflineService.getOfflineInvoiceList();
        mav.addObject("offlineInvoiceList", offlineInvoiceList);
        return mav;
    }


    /**
     * 线下数据发票：维护页面
     *
     * @return
     */
    @RequestMapping("addOfflineInvoice")
    public ModelAndView addOfflineInvoice(HttpServletRequest request, HttpServletResponse response, EconomicsOfflineInvoice economicsOfflineInvoice) throws Exception {


        EconomicsOfflineInvoice offlineInvoice = new EconomicsOfflineInvoice();
        ModelAndView mav = new ModelAndView("dimension/addOfflineInvoice");
        if (economicsOfflineInvoice.getPk() != null && !"".equals(economicsOfflineInvoice.getPk())) {
            offlineInvoice = economicsDimensionOfflineService.getOfflineInvoiceByPk(economicsOfflineInvoice.getPk());
        }
        mav.addObject("offlineInvoice", offlineInvoice);
        return mav;
    }


    @ResponseBody
    @RequestMapping("offlineInvoice_list")
    public String offlineInvoiceList(HttpServletRequest request, HttpServletResponse response, EconomicsOfflineInvoice economicsOfflineInvoice) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "sort");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");

        QueryModel<EconomicsOfflineInvoice> qm = new QueryModel<EconomicsOfflineInvoice>(economicsOfflineInvoice, start, limit, orderName, orderType);
        PageModel<EconomicsOfflineInvoice> pm = economicsDimensionOfflineService.getOfflineInvoiceData(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }




    @ResponseBody
    @RequestMapping("updateOfflineInvoiceStatus")
    public String updateOfflineInvoiceStatus(EconomicsOfflineInvoice economicsOfflineInvoice) {
        int result = economicsDimensionOfflineService.updateOfflineInvoiceStatus(economicsOfflineInvoice);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("updateOfflineInvoice")
    public String updateOfflineInvoice(EconomicsOfflineInvoice economicsOfflineInvoice) {
        int result = 0;
        if (economicsOfflineInvoice.getPk() != null && !Objects.equals(economicsOfflineInvoice.getPk(), ""))
            result = economicsDimensionOfflineService.updateOfflineInvoice(economicsOfflineInvoice);
        else
            result = economicsDimensionOfflineService.insertOfflineInvoice(economicsOfflineInvoice);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (result < 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }



}
