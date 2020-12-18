package cn.cf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.QueryModel;
import cn.cf.entity.MangoBackstageInfo;
import cn.cf.entity.MangoOperationInfo;
import cn.cf.entity.SendMails;
import cn.cf.entity.Sms;
import cn.cf.json.JsonUtils;
import cn.cf.service.manage.SmsService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class ManageController extends BaseController {

    @Autowired
    @Qualifier("CmsSmsService")
    private SmsService smsService;

    /**
     * 短信记录页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("smsRecord")
    public ModelAndView smsRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/smsRecord");
        return mav;
    }

    /**
     * 短信记录列表 
     * 
     * @param request
     * @param response
     * @param sms
     * @return
     */
    @RequestMapping("smsRecordList")
    @ResponseBody
    public String smsRecordList(HttpServletRequest request, HttpServletResponse response, Sms sms) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<Sms> qm = new QueryModel<Sms>(sms, start, limit, orderName, orderType);
        PageModel<Sms> pm = smsService.searchsmsRecordList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }
    /**
     * 
     * 站内信记录页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("emailRecord")
    public ModelAndView emailRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/emailRecord");
        return mav;
    }
    
    /**
     * 
     * 站内信列表
     * @param request
     * @param response
     * @param sendMail
     * @return
     */
    @RequestMapping("emailRecordList")
    @ResponseBody
    public String emailRecordList(HttpServletRequest request, HttpServletResponse response, SendMails sendMail) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<SendMails> qm = new QueryModel<SendMails>(sendMail, start, limit, orderName, orderType);
        PageModel<SendMails> pm = smsService.searchsendMailsList(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * API日志管理页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("operationLogsApi")
    public ModelAndView operationLogsApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/logsApiManage");
        return mav;
    }

    /**
     * 查询API日志管理列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @RequestMapping("searchOperationLogsApi")
    @ResponseBody
    public String searchOperationLogsApi(HttpServletRequest request, HttpServletResponse response, MangoOperationInfo info) throws Exception {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<MangoOperationInfo> qm = new QueryModel<MangoOperationInfo>(info, start, limit, orderName, orderType);
        PageModel<MangoOperationInfo> pm = smsService.searchOperationApiLogsList(qm);
        String json = JsonUtils.convertToString(pm);

        return json;
    }

    /**
     * 后台日志管理页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("operationLogsBack")
    public ModelAndView operationLogsBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/logsBackManage");
        return mav;
    }

    /**
     * 查询后台日志管理列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @RequestMapping("searchOperationLogsBack")
    @ResponseBody
    public String searchOperationLogsBack(HttpServletRequest request, HttpServletResponse response, MangoBackstageInfo info) throws Exception {

        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
        QueryModel<MangoBackstageInfo> qm = new QueryModel<MangoBackstageInfo>(info, start, limit, orderName, orderType);
        PageModel<MangoBackstageInfo> pm = smsService.searchOperationBackLogsList(qm);
        String json = JsonUtils.convertToString(pm);

        return json;
    }
}
