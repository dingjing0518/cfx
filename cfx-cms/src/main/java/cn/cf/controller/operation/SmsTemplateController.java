package cn.cf.controller.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.dto.SysSmsTemplateDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.operation.SmsTemplateService;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

/**
 * 管理中心：短信记录
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/")
public class SmsTemplateController extends BaseController {

    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("smsTemplate")
    public ModelAndView smsTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/smsTemplate");
        return mav;
    }

    /**
     * 列表
     * 
     * @param request
     * @param response
     * @param sms
     * @return
     */
    @RequestMapping("smsTemplateList")
    @ResponseBody
    public String smsTemplateList(HttpServletRequest request, HttpServletResponse response, SysSmsTemplateDto sms) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "templateCode");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "DESC");
        QueryModel<SysSmsTemplateDto> qm = new QueryModel<SysSmsTemplateDto>(sms, start, limit, orderName, orderType);
        PageModel<SysSmsTemplateDto> pm = smsTemplateService.searchsmsTemplateList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 编辑/新增短信模板
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("updateSmsTemplate")
    @ResponseBody
    public String updateSmsTemplate(HttpServletRequest request, HttpServletResponse response) {
        SysSmsTemplateDto dto = new SysSmsTemplateDto();
        dto.bind(request);
        int retVal = smsTemplateService.updateSmsTemplate(dto);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (retVal <= 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    /**
     * 获取短信模板的角色
     * 
     * @param request
     * @param memberPk
     * @return
     */
    @RequestMapping("getRoleListBySms")
    @ResponseBody
    public String getRoleListBySms(HttpServletRequest request, String smsName) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        B2bRoleExtDto dto = new B2bRoleExtDto();
        dto.bind(request);
        QueryModel<B2bRoleExtDto> qm = new QueryModel<B2bRoleExtDto>(dto, start, limit, orderName, orderType);
        PageModel<B2bRoleExtDto> pm = smsTemplateService.getRoleListBySms(qm, smsName);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 会员添加角色
     * 
     * @param request
     * @param memberPk
     * @param rolePks
     * @return
     */
    @RequestMapping("insertSmsRole")
    @ResponseBody
    public String insertSmsRole(HttpServletRequest request, String smsName, String rolePks, String type) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String[] rolePk = rolePks.split(",");
        if (!"".equals(rolePk)) {
            for (int j = 0; j < rolePk.length; j++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rolePk", rolePk[j]);
                map.put("smsName", smsName);
                map.put("pk", KeyUtils.getUUID());
                map.put("type", type);
                list.add(map);
            }
        }
        int retVal = smsTemplateService.insertSmsRole(list);
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (retVal <= 0) {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }
}
