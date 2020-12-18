package cn.cf.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.JedisMaterialUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bStoreExtDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.B2bTokenExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.ManageRoleDto;
import cn.cf.entity.BtnTree;
import cn.cf.json.ExtTreeNode;
import cn.cf.json.ExtTreeState;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.ManageRole;
import cn.cf.service.manage.AuthorityManageService;
import cn.cf.service.operation.CustomerManageService;
import cn.cf.util.ServletUtils;

/**
 * 账号权限管理控制Controller
 * 
 * @author bin
 */
@Controller
@RequestMapping("/")
public class AuthorityManageController extends BaseController {
    @Autowired
    private AuthorityManageService managementService;

    @Autowired
    private CustomerManageService customerManageService;

    @Autowired
    private AuthorityManageService authorityManageService;


    /**
     * 显示权限头部菜单列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("manage")
    public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/manage");
        String pk = ServletUtils.getStringParameter(request, "pk", null);
        ManageAccount adto = this.getAccount(request);
        mav.addObject("adto", adto);
        List<ExtTreeNode> list = this.getChildren(pk, adto.getRolePk(), 0, null, 0, adto.getAccount());
        mav.addObject("list", list);

//        //清除redis中所有已存在的当前用户下的权限
//        ManageAccount account = this.getAccount(request);
//        List<String> authNameList = authorityManageService.getAuthManageIsBtnToThree();
//        if(account != null && authNameList!= null && authNameList.size() > 0){
//            for (String name:authNameList) {
//                if (CommonUtil.isExistAuthName(account.getPk(),name))
//                JedisMaterialUtils.del(account.getPk()+"_"+name);
//            }
//        }

        isShowButton(mav, adto.getRolePk(), pk,adto.getPk());
        return mav;
    }
    private void isShowButton(ModelAndView mav, String rolePk, String partentPk,String accountPk) {
        // 处理button
        List<BtnTree> childMenu = new ArrayList<BtnTree>();

        this.getButtonChildren(childMenu, partentPk, rolePk,accountPk);
        String btns = "";
        if (childMenu != null && childMenu.size() > 0) {
            for (BtnTree mus : childMenu) {
                btns += mus.getName() + ",";
            }
        }
        mav.addObject("btnList", btns.length() > 0 ? btns.substring(0, btns.length() - 1) : btns);
    }

    private List<BtnTree> getButtonChildren(List<BtnTree> childMenu, String partentPk, String rolePk,String accountPk) {
        List<BtnTree> childList = managementService.getBtnByPartentPk(partentPk, rolePk);
        for (BtnTree mu : childList) {
            // 遍历出父PK等于参数的PK，add进子节点集合
            if (mu.getParentPk().equals(partentPk)) {
                if (mu.getIsBtn() != null && mu.getIsBtn() == Constants.ONE) {
                    childMenu.add(mu);
                }
                /******************添加列显示权限判断*****************/
                //ToDo
//                if(mu.getIsBtn() != null && mu.getIsBtn() == Constants.THREE){
//                    try {
//                        //JedisMaterialUtils.set(accountPk+"_"+mu.getName() , mu.getName());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                // 递归遍历下一级
                getButtonChildren(childMenu, mu.getPk(), rolePk,accountPk);
            }
        }
        return childMenu;

    }

    private List<ExtTreeNode> getChildren(String pk, String rolePk, int type, List<ManageAuthorityDto> slist, int inCouldeBtn, String account) {
        List<ManageAuthorityDto> list = managementService.getManageAuthorityByAccount(pk, rolePk, type, inCouldeBtn, account);
        List<ExtTreeNode> tlist = new ArrayList<ExtTreeNode>();
        ExtTreeNode tree = null;
        for (ManageAuthorityDto dto : list) {
            tree = new ExtTreeNode();
            tree.setId(dto.getPk());
            tree.setText(dto.getDisplayName());
            tree.setImage(dto.getImage());
            if (null != slist) {
                for (ManageAuthorityDto d : slist) {
                    if (dto.getPk().equals(d.getPk())) {
                        ExtTreeState ex = new ExtTreeState();
                        ex.setSelected(true);
                        tree.setState(ex);
                    }
                }
            }
            tree.setSrcObj(dto);
            tree.setUrl(dto.getUrl());

            List<ExtTreeNode> tl = this.getChildren(dto.getPk(), rolePk, type, slist, inCouldeBtn, null);

            if (null != tl && tl.size() > 0) {
                tree.setChildren(tl);
            }
            tlist.add(tree);
        }
        return tlist;
    }

    /**
     * 欢迎页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        ManageAccount adto = this.getAccount(request);
        mav.addObject("adto", adto);
        return mav;
    }

    /**
     * 角色权限列表页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("role")
    public ModelAndView role(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/role");
        return mav;
    }

    /**
     * 权限管理列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("roleGrid")
    @ResponseBody
    public String roleGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
        String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
        ManageRoleDto entity = new ManageRoleDto();
        QueryModel<ManageRoleDto> qm = new QueryModel<ManageRoleDto>(entity, start, limit, orderName, orderType);
        PageModel<ManageRoleDto> pm = managementService.searchRoleGrid(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * token列表页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("tokenManage")
    public ModelAndView tokenManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("manage/tokenManage");
        ManageAccount adto = this.getAccount(request);
        List<B2bStoreExtDto> list = customerManageService.getB2bStore();
        mav.addObject("adto", adto);
        mav.addObject("companyList", list);
        return mav;
    }

    /**
     * 查询店铺
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("tokenManageGetB2bStore")
    @ResponseBody
    public String tokenManageGetB2bStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return JsonUtils.convertToString(customerManageService.getB2bStore());
    }

    /**
     * 新增/编辑token
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("updateToken")
    @ResponseBody
    public String insertOrUpdateToken(HttpServletRequest request, HttpServletResponse response, B2bTokenExtDto dto) {
        B2bTokenDto token = managementService.searchB2bTokenByName(dto);
        if (dto.getIsEdit() == 2) {// 新增时判断
            if (token != null) {
                return "{\"success\":false,\"msg\":\"公司名称已绑定token!\"}";
            }
            dto.setStorePk(dto.getStorePk());
        }
        int retVal = managementService.updateB2bToken(dto);
        String msg = "";
        if (retVal > 0) {
            msg =Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    /**
     * 修改token状态
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     */
    @RequestMapping("updateTokenStatus")
    @ResponseBody
    public String updateTokenStatus(HttpServletRequest request, HttpServletResponse response, B2bTokenExtDto dto) {
        int retVal = managementService.updateB2bTokenStatus(dto.getPk(), dto.getIsVisable(), dto.getIsDelete());
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    /**
     * token列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("tokenData")
    @ResponseBody
    public String tokenData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int start = ServletUtils.getIntParameter(request, "start", 0);
        int limit = ServletUtils.getIntParameter(request, "limit", 10);
        String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
        String storeName = ServletUtils.getStringParameter(request, "storeName", "");
        B2bTokenDto entity = new B2bTokenDto();
        entity.setStoreName(storeName);
        QueryModel<B2bTokenDto> qm = new QueryModel<B2bTokenDto>(entity, start, limit, orderName, null);
        PageModel<B2bTokenDto> pm = customerManageService.searchB2bToken(qm);
        String json = JsonUtils.convertToString(pm);
        return json;
    }

    /**
     * 权限树
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("authorityTree")
    @ResponseBody
    public String authorityTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ManageAccount adto = this.getAccount(request);
        String id = ServletUtils.getStringParameter(request, "id", "-1");
        String RolePk = ServletUtils.getStringParameter(request, "pk", " ");
        ExtTreeNode tree = new ExtTreeNode();
        tree.setId("-1");
        tree.setText("全部");
        List<ManageAuthorityDto> list = null;
        if (null != RolePk && !"".equals(RolePk)) {
            list = managementService.getManageAuthorityByRolePk(RolePk);
        }
        List<ExtTreeNode> tlist = this.getChildren(id, adto.getRolePk(), -1, list, 1, null);
        if (null != tlist && tlist.size() > 0) {
            tree.setChildren(tlist);
        }
        String json = JsonUtils.convertToString(tree);
        return json;
    }

    /**
     * 检查角色名
     * 
     * @param name
     * @return
     */
    @RequestMapping("CheckRoleName")
    @ResponseBody
    public String getManageByroleName(String name) {
        List<ManageRoleDto> list = managementService.getManageRoleByName(name);
        if (list.size() > 0) {
            return JsonUtils.convertToString(list);
        } else {
            return "";
        }
    }

    /**
     * 修改角色权限
     * 
     * @param role
     * @param node
     * @return
     */
    @RequestMapping("updateManaegRole")
    @ResponseBody
    public String updateManaegRole(HttpServletRequest request,ManageRole role, String node) {

        int retVal = managementService.updateManaegRole(role, node);
        String msg = "";
        if (retVal > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }
}
