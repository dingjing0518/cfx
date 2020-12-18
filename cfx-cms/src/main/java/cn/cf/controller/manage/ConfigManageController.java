package cn.cf.controller.manage;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysPropertyDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.manage.AuthorityManageService;
import cn.cf.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置Controller
 * 
 * @author bin
 */
@Controller
@RequestMapping("/")
public class ConfigManageController extends BaseController {
    @Autowired
    private AuthorityManageService managementService;


    /**
     * 角色权限列表页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("sysProperty")
    public ModelAndView role(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/sysProperty");
        return mav;
    }

    /**
     * 查询账号集合
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("searchSysProperty")
    @ResponseBody
    public String searchSysProperty(HttpServletRequest request,
                                          HttpServletResponse response,SysPropertyDto dto) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
        QueryModel<SysPropertyDto> qm = new QueryModel<>(dto,
                start, limit, orderName, orderType);
        PageModel<SysPropertyDto> pm = managementService.searchSysPropertyGrid(qm);
        return JsonUtils.convertToString(pm);
    }


    /**
     * 查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getSysPropertyBypk")
    @ResponseBody
    public String getSysProperty(HttpServletRequest request,
                                    HttpServletResponse response,String pk) {
        SysPropertyDto pm = managementService.getSysPropertyBypk(pk);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 配置管理根据不同类型获取相关配置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("getSysPropertyProduct")
    @ResponseBody
    public String getSysPropertyProduct(HttpServletRequest request,
                                 HttpServletResponse response,Integer type) {
        List<SysPropertyDto> pm = managementService.getSysPropertyByType(type,null);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 配置管理根据不同类型获取相关配置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateSysProperty")
    @ResponseBody
    public String updateSysProperty(HttpServletRequest request,
                                        HttpServletResponse response,SysPropertyDto dto) {
        int result = managementService.updateSysPropertyByType(dto);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (result == -1) {
            msg = "{\"success\":false,\"msg\":\"已存在相同数据!\"}";
        }else{
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    /**
     * 删除配置管理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("delSysProperty")
    @ResponseBody
    public String delSysProperty(HttpServletRequest request,
                                    HttpServletResponse response,String pk) {
        int result = managementService.delSysPropertyByPk(pk);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else if (result == -1) {
            msg = "{\"success\":false,\"msg\":\"已存在相同数据!\"}";
        }else{
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

}
