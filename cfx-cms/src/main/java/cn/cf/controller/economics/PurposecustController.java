package cn.cf.controller.economics;

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
import cn.cf.dto.B2bEconomicsPurposecustExDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bEconomicsPurposecust;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.PurposecustService;
import cn.cf.util.ServletUtils;

/**
 * 意向客户管理
 * 
 * @author xht 2018年5月23日
 */
@Controller
@RequestMapping("/")
public class PurposecustController extends BaseController {

    @Autowired
    private PurposecustService purposecustService;

    /**
     * 页面
     * 
     * @return
     */
    @RequestMapping("ecPurposecust")
    public ModelAndView ecPurposecustManager() {
        ModelAndView mav = new ModelAndView("economics/b2bEconomicsPurposecust");
        return mav;
    }

    /**
     * 列表展示
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("ecPurposecust_data")
    @ResponseBody
    public String ecPurposecust_data(HttpServletRequest request, HttpServletResponse response, B2bEconomicsPurposecustExDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        ManageAccount account = getAccount(request);
        dto.setAccountPk(account.getPk());
        QueryModel<B2bEconomicsPurposecustExDto> qm = new QueryModel<B2bEconomicsPurposecustExDto>(dto, start, limit, orderName, orderType);
        PageModel<B2bEconomicsPurposecustExDto> pm = purposecustService.searchEcPurposecustList(qm);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 更新
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("updateEcPurposecust")
    @ResponseBody
    public String updateEcPurposecust(B2bEconomicsPurposecust ecPurposecust) {
        String msg = "";
        Integer result = purposecustService.update(ecPurposecust);
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

}
