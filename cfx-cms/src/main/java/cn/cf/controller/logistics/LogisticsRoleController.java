package cn.cf.controller.logistics;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.QueryModel;
import cn.cf.dto.LgMenuDto;
import cn.cf.dto.LgRoleMenuDto;
import cn.cf.dto.LgRoleTypeDto;
import cn.cf.json.ExtTreeNode;
import cn.cf.json.ExtTreeState;
import cn.cf.json.JsonUtils;
import cn.cf.model.LgRoleType;
import cn.cf.service.logistics.LgRoleTypeService;
import cn.cf.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 物流承运商线路管理
 */
@Controller
@RequestMapping("/")
public class LogisticsRoleController extends BaseController {

	@Autowired
	private LgRoleTypeService lgRoleTypeService;

	/**
	 * 页面跳转：角色类型管理
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logisticsRoleManager")
	public ModelAndView purchaser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("logistics/lgRole");
		return mav;
	}

	/**
	 * 角色类型管理列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lgRoleTypeGrid")
	@ResponseBody
	public String lgRoleTypeGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		LgRoleTypeDto entity = new LgRoleTypeDto();
		QueryModel<LgRoleTypeDto> qm = new QueryModel<LgRoleTypeDto>(entity, start, limit, orderName, orderType);
		PageModel<LgRoleTypeDto> pm = lgRoleTypeService.searchLgRoleTypeGrid(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}
	
	
	/**
	 * 角色目录树
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lgRoleMenuTree")
	@ResponseBody
	public String lgRoleMenuTree(HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		String id = ServletUtils.getStringParameter(request, "id", "-1");
		String RolePk = ServletUtils.getStringParameter(request, "pk", " ");
		Integer companyType = ServletUtils.getIntParameter(request,"companyType", 1);
		ExtTreeNode tree = new ExtTreeNode();
		tree.setId(id);
		tree.setText("全部");
		List<LgRoleMenuDto> list = null;
		if (null != RolePk && !"".equals(RolePk.trim())) {
			list = lgRoleTypeService.getLgRoleMenuByRolepk(RolePk);
		}
		List<ExtTreeNode> tlist = this.getChildren(id, companyType, list);
		if (null != tlist && tlist.size() > 0) {
			tree.setChildren(tlist);
		}
		String json = JsonUtils.convertToString(tree);
		return json;
	}
	
	
	private List<ExtTreeNode> getChildren(String id, Integer companyType,
			List<LgRoleMenuDto> slist) {
		List<LgMenuDto> list = lgRoleTypeService.getLgMenuByParentPk(id,
				companyType);
		List<ExtTreeNode> tlist = new ArrayList<ExtTreeNode>();
		ExtTreeNode tree = null;
		for (LgMenuDto dto : list) {
			tree = new ExtTreeNode();
			tree.setId(dto.getPk());
			if ("transactionManagementSp".equals(dto.getName())) {
				tree.setText(dto.getDisplayName() + "(供应商)");
			} else if ("transactionManagement".equals(dto.getName())) {
				tree.setText(dto.getDisplayName() + "(采购商)");
			} else {
				tree.setText(dto.getDisplayName());
			}
			if (null != slist) {
				for (LgRoleMenuDto d : slist) {
					if (dto.getPk().equals(d.getMenuPk())) {
						ExtTreeState ex = new ExtTreeState();
						ex.setSelected(true);
						tree.setState(ex);
					}
				}
			}

			tree.setSrcObj(dto);
			tree.setUrl(dto.getUrl());
			List<ExtTreeNode> tl = this.getChildren(dto.getPk(), companyType,
					slist);
			if (null != tl && tl.size() > 0) {
				tree.setChildren(tl);
			}
			tlist.add(tree);
		}
		return tlist;
	}
	
	/**
	 * 检查角色名称是否已经存在
	 * @param name
	 * @return
	 */
	@RequestMapping("checkLgRoleName")
	@ResponseBody
	public String checkB2bRoleName(String name) {
		LgRoleTypeDto role = lgRoleTypeService.getLgRoleByName(name);
		if (role != null) {
			return JsonUtils.convertToString(role);
		} else {
			return "";
		}
	}
	
	/**
	 * 保存角色权限
	 * @param role
	 * @param node
	 * @return
	 */
	@RequestMapping("saveLgRole")
	@ResponseBody
	public String saveLgRole(LgRoleType role, String node) {
		lgRoleTypeService.saveLgRole(role, node);
		return JsonUtils.convertToString(1);
	}
	

}
