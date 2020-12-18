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
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bCreditGoodsDtoExt;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.MemberShip;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.CustomerService;
import cn.cf.service.enconmics.EconomicsFacadeService;
import cn.cf.util.ServletUtils;

@Controller
@RequestMapping("/")
public class TaskController extends BaseController {

	

	@Autowired
	private EconomicsFacadeService economicsFacadeService;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * 待审批客户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("approveTask")
	public ModelAndView approveTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/customerTask");
		return mv;
	}

	/**
	 * 审批管理分页查询
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/approveTaskData")
	public String approveTaskData(HttpServletResponse response, HttpServletRequest request) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		ManageAccountExtDto manageAccount = this.getAccountExt(request);
		
		MemberShip memberShip = this.getMemberShipByAccount(manageAccount.getAccount());
		PageModel<B2bEconomicsCustomerDto> pm = new PageModel<B2bEconomicsCustomerDto>();
		String groupId = memberShip.getGroupId();
		pm = economicsFacadeService.searchCustomer(start, limit, groupId,manageAccount.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 查詢流程正常走完的历史流程表 
	 * @param response
	 * @param groupId
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/finishedList")
	public String finishedList(HttpServletResponse response, HttpServletRequest request,B2bCreditGoodsDtoExt b2bCreditGoodsDtoExt) throws Exception{
	
		EconCustomerMongo  econCustomerMongo = new EconCustomerMongo();
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request,"orderName", "approveTime");
		String orderType = ServletUtils.getStringParameter(request,"orderType", "desc");
		QueryModel<EconCustomerMongo> qm = new QueryModel<EconCustomerMongo>(econCustomerMongo, start, limit, orderName, orderType);
       ManageAccount account = getAccount(request);
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("orderName", orderName);
        map.put("orderType", orderType);
        map.put("auditStatus", 3);
		PageModel<EconCustomerMongo> pm = customerService.getByMap(map,qm,account.getPk(),1);
		return JsonUtils.convertToString(pm);
	}
	
	/**
	 * 已完成审批客户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("finishedTask")
	public ModelAndView finishedTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/finishedTask");
		return mv;
	}
	
	/**
	 * 审批驳回
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("rejectTask")
	public ModelAndView rejectTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/rejectTask");
		return mv;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/rejectList")
	public String rejectList(HttpServletResponse response, HttpServletRequest request,B2bCreditGoodsDtoExt b2bCreditGoodsDtoExt) throws Exception{
		
		EconCustomerMongo  econCustomerMongo = new EconCustomerMongo();
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request,"orderName", "approveTime");
		String orderType = ServletUtils.getStringParameter(request,"orderType", "desc");
		QueryModel<EconCustomerMongo> qm = new QueryModel<EconCustomerMongo>(econCustomerMongo, start, limit, orderName, orderType);
		ManageAccount account = getAccount(request);
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("orderName", orderName);
        map.put("orderType", orderType);
        map.put("auditStatus", 4);
		PageModel<EconCustomerMongo> pm = customerService.getByMap(map,qm,account.getPk(),2);
		return JsonUtils.convertToString(pm);
	}
	
}
