package cn.cf.controller.economics;

import java.util.HashMap;
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
import cn.cf.dto.ActGroupDto;
import cn.cf.dto.ActMemberShipDto;
import cn.cf.json.JsonUtils;
import cn.cf.model.ActGroup;
import cn.cf.model.ActUser;
import cn.cf.model.ActUserGroup;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.GroupService;
import cn.cf.service.enconmics.MemberShipService;
import cn.cf.service.manage.AccountManageService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class UserController extends BaseController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private MemberShipService memberShipService;

    /**
     * ActGroup列表页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("actGroup")
    public ModelAndView account(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("economics/actGroup");
        return mav;
    }

    /**
     * 账户管理列表
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("searchActGroupList")
    @ResponseBody
    public String searchManageAccountList(HttpServletRequest request, HttpServletResponse response) {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
        ActGroupDto entity = new ActGroupDto();
        QueryModel<ActGroupDto> qm = new QueryModel<ActGroupDto>(entity, start, limit, orderName, orderType);
        PageModel<ActGroupDto> pm = groupService.searchManageAccountList(qm);
        return JsonUtils.convertToString(pm);
    }

    /**
     * 删除
     * 
     * @param account
     * @return
     */
    @RequestMapping("deleteActGroup")
    @ResponseBody
    public String deleteActGroup(ActGroup actGroup) {
        String msg = "";
        int result;
        try {
            result = groupService.deleteActGroup(actGroup);
            
            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }
        } catch (Exception e) {
            msg = Constants.RESULT_FAIL_GROUP_MSG;
            //e.printStackTrace();
        }
        
        return msg;
    }

    /**
     * 新增/更新 角色
     * 
     * @param actGroup
     * @return
     */
    @RequestMapping("updateActGroup")
    @ResponseBody
    public String updateActGroup(ActGroup actGroup) {

        int result = 0;
        result = groupService.updateActGroup(actGroup);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            if (result == -2) {
                msg = "{\"success\":false,\"msg\":\"已经存在相同的角色名!\"}";
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }

        }
        return msg;
    }

    /**
     * 金融：账户页面列表
     * 
     * @param user
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/listWithGroups")
    @ResponseBody
    public String listWithGroups(ActMemberShipDto user, HttpServletRequest request) throws Exception {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
        ManageAccount account = getAccount(request);
        QueryModel<ActMemberShipDto> qm = new QueryModel<ActMemberShipDto>(user, start, limit, orderName, orderType);
        PageModel<ActMemberShipDto> pm = memberShipService.searchActUserList(qm,account.getPk());
        return JsonUtils.convertToString(pm);
    }

    /**
     * 金融：账户页面
     * 
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/actListGroup")
    public ModelAndView actListGroup(ActUser user) throws Exception {
        ModelAndView mav = new ModelAndView("economics/actListGroup");
        mav.addObject("roleList", groupService.getAllGroupList());
        mav.addObject("accountList", accountManageService.getAllAccountList());
        return mav;
    }

    /**
     * 金融：编辑/新增账户
     * 
     * @param actUserGroup
     * @return
     */
    @RequestMapping("/updateActListGroup")
    @ResponseBody
    public String updateActListGroup(ActUserGroup actUserGroup) {
        int result = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", actUserGroup.getAccountId());
        map.put("groupId", actUserGroup.getGroupId());
        map.put("type", actUserGroup.getPk());
        result = groupService.updateActUserGroup(map);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            if (result == -2) {
                msg = "{\"success\":false,\"msg\":\"该帐号已经分配角色!\"}";
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }

        }
        return msg;
    }

    /**
     * 金融： 删除
     * 
     * @param account
     * @return
     */
    @RequestMapping("deleteActListGroup")
    @ResponseBody
    public String deleteActListGroup(ActUserGroup actUserGroup) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", actUserGroup.getAccountId());
        map.put("groupId", actUserGroup.getGroupId());
        int result = memberShipService.deleteActListGroup(map);
        String msg = "";
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

}
