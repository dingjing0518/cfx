package cn.cf.controller.operation;

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
import cn.cf.dto.SysVersionManagementDto;
import cn.cf.dto.SysVersionManagementExtDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.VersionService;
import cn.cf.util.ServletUtils;

/**
 * 运营中心：版本管理
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class VersionController extends BaseController {
    
    @Autowired
    private VersionService versionService;
    
    /**
     * 
     * app版本管理页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("versionPage")
    public ModelAndView versionPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mvn = new ModelAndView("operation/version");
        return mvn;
    }
    
    /**
     * 列表
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("searchVersionList")
    @ResponseBody
    public String searchVersionList(HttpServletRequest request, HttpServletResponse response,
            SysVersionManagementExtDto dto) {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 5);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "");
        QueryModel<SysVersionManagementExtDto> qm = new QueryModel<SysVersionManagementExtDto>(dto,
                start, limit, orderName, orderType);
        PageModel<SysVersionManagementExtDto> pm = versionService.searchVersionList(qm);
        return JsonUtils.convertToString(pm);

    }
    
    
    
    @RequestMapping("updateVersion")
    @ResponseBody
    public String updateVersion(HttpServletRequest request, HttpServletResponse response,
            SysVersionManagementDto dto) {
        ManageAccount account = getAccount(request);
        dto.setPublisher(account.getName());
        int result = versionService.updateVersion(dto);
        String msg = "";
        if (result > 0) {
            if (result==2) {
                msg = Constants.RESULT_SUCCESS_VERSIONUM;
            }else{
                msg = Constants.RESULT_SUCCESS_MSG;
            }
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    

    }
}
