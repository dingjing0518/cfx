package cn.cf.controller.logistics;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.QueryModel;
import cn.cf.dto.LgCompanyExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.LgCompanyEx;
import cn.cf.model.ManageAccount;
import cn.cf.service.logistics.LogisticsService;
import cn.cf.util.ServletUtils;

/**
 * 物流承运商管理
 */
@Controller
@RequestMapping("/")
public class LogisticsController extends BaseController {

    @Autowired
    private LogisticsService logisticsService;

    /**
     * 物流承运商管理
     * 
     * @return
     */
    @RequestMapping("logisticsSupplierManager")
    public ModelAndView personnel() {
        ModelAndView mav = new ModelAndView("logistics/supplierManager");
        return mav;
    }

    /**
     * 物流承运商管理列表
     * 
     * @param request
     * @param response
     * @param entity
     * @return
     */
    @RequestMapping("logisticsCompanyList")
    @ResponseBody
    public String logisticsCompanyData(HttpServletRequest request, HttpServletResponse response, LgCompanyExtDto entity) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
       ManageAccount account =getAccount(request);
        QueryModel<LgCompanyExtDto> qm = new QueryModel<LgCompanyExtDto>(entity, start, limit, orderName, orderType);
        PageModel<LgCompanyExtDto> pm = logisticsService.searchCompanyList(qm,account.getPk());
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 修改或新增物流承运商信息
     * 
     * @param request
     * @param company
     * @return
     */
    @ResponseBody
    @RequestMapping("updateLogisticsCompany")
    public String updateCompay(HttpServletRequest request, LgCompanyEx company) {
        String msg = Constants.RESULT_SUCCESS_MSG;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pk", company.getPk());
        if (company.getName() != null && company.getName() != "") {
            map.put("name", company.getName().trim());
            int counts = logisticsService.getLgCompanyByVaildCompany(map);
            if (counts > 0) {
                return "{\"success\":false,\"msg\":\"公司名称已被注册!\"}";
            }
        }
        if (company.getMobile() != null && company.getMobile() != "") {
            map.put("mobile", company.getMobile().trim());
            int counts = logisticsService.getLgCompanyByVaildMobile(map);
            if (counts > 0) {
                return "{\"success\":false,\"msg\":\"手机号码已被注册!\"}";
            }
        }

        int result = logisticsService.LgCompany(company);
        if (result > 0) {
         
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }

        return msg;
    }

    /**
     * 禁用（启用）操作
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("editLogisticsCompanyState")
    public String editLogisticsCompanyState(HttpServletRequest request) {
        String pk = ServletUtils.getStringParameter(request, "pk", null);
        Integer isVisable = ServletUtils.getIntParameter(request, "isVisable", 0);
        int retVal = logisticsService.editLogisticsCompanyState(isVisable, pk);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (retVal > 0) {
            
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

}
