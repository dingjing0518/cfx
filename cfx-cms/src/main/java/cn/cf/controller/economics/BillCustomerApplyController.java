package cn.cf.controller.economics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.http.HttpHelper;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.ActGroupDto;
import cn.cf.dto.ActUserDto;
import cn.cf.dto.B2bBillCustomerApplyExtDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bBillSigncardExtDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bBillSigningExtDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.dto.MemberShip;
import cn.cf.entity.BillCustomerMongo;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bBillSigning;
import cn.cf.model.ManageAccount;
import cn.cf.property.PropertyConfig;
import cn.cf.service.enconmics.B2bBillCustomerService;
import cn.cf.service.enconmics.BillCustomerApplyService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.service.manage.AccountManageService;
import cn.cf.util.ServletUtils;
import net.sf.json.JSONObject;

/**
 * 票据客户管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class BillCustomerApplyController extends BaseController {

	@Autowired
	private BillCustomerApplyService billCustomerApplyService;

	@Autowired
	private AccountManageService accountManageService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private FormService formService;

	@Resource
	private HistoryService historyService;

	@Autowired
	private B2bBillCustomerService billCustomerService;
	
	@Autowired
	private EconomicsBankService  economicsBankService;
	
	private static final Logger logger = LoggerFactory.getLogger(BillCustomerApplyController.class);

	/**
	 * 票据管理/客户管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("billCustomerManage")
	public ModelAndView BillCustomerManage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/bill/billCustomerManage");
		mv.addObject("acccountList", accountManageService.getAllAccountList());
		return mv;
	}

	/**
	 * 客户管理
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchBillCustomerApplyList")
	@ResponseBody
	public String searchBillCustomerApplyList(HttpServletRequest request, HttpServletResponse response,
			B2bBillCustomerApplyExtDto dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		ManageAccount account = getAccount(request);
		dto.setAccountPk(account.getPk());
		QueryModel<B2bBillCustomerApplyExtDto> qm = new QueryModel<B2bBillCustomerApplyExtDto>(dto, start, limit, sort,
				dir);
		MemberShip currentMemberShip = this.getMemberShipByAccount(account.getAccount());
		PageModel<B2bBillCustomerApplyExtDto> pm = billCustomerApplyService.searchBillCustomerApplyList(qm,
				currentMemberShip);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 更新申请表
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("updateBillCustomer")
	@ResponseBody
	public String updateBillCustomer(HttpServletRequest request, HttpServletResponse response,
			B2bBillCustomerApplyExtDto dto) {
		int result = billCustomerApplyService.updateBillCustomer(dto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 提交票据流程申請
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startBillApply")
	@ResponseBody
	public String startBillApply(HttpServletResponse response, B2bBillCustomerApplyExtDto billCustomerApplyExtDto)
			throws Exception {

		if (null != billCustomerApplyExtDto.getIsReApply() && billCustomerApplyExtDto.getIsReApply().equals("1")) {
			if (billCustomerApplyExtDto != null) {
				billCustomerApplyExtDto.setProcessInstanceId("");
				billCustomerApplyService.updateBillCustomer(billCustomerApplyExtDto);
			}
		}

		// 启动流程
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("billCustPk", billCustomerApplyExtDto.getPk());
		// 启动流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("billProcess", variables);
		// 根据流程实例Id查询任务
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		taskService.complete(task.getId());

		if (billCustomerApplyExtDto != null) {
			// 设置状态
			billCustomerApplyExtDto.setStatus(Constants.TWO);
			billCustomerApplyExtDto.setProcessInstanceId(pi.getProcessInstanceId());
			// 审批时间
			billCustomerApplyExtDto.setApprovalTime(new Date());
			// 修改状态
			billCustomerApplyService.updateBillCustomer(billCustomerApplyExtDto);
			// 插入芒果
			if (billCustomerApplyExtDto.getPk() != null && !"".equals(billCustomerApplyExtDto.getPk())) {
				billCustomerApplyService.insertBillCustomerMongo(billCustomerApplyExtDto.getPk());
			}
		}

		return Constants.RESULT_SUCCESS_MSG;
	}

	/**
	 * 申请历史记录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("approveBillHistory")
	public ModelAndView approveBillHistory(HttpServletRequest request, HttpServletResponse response) {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		ModelAndView mv = new ModelAndView("economics/bill/approveBillHistory");
		mv.addObject("companyPk", companyPk);
		return mv;
	}

	/**
	 * 待审批客户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("approveBillTask")
	public ModelAndView approveBillTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/bill/billCustomerTask");
		return mv;
	}

	@ResponseBody
	@RequestMapping("/approveBillTaskData")
	public String approveBillTaskData(HttpServletResponse response, HttpServletRequest request) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		ManageAccountExtDto manageAccount = this.getAccountExt(request);
		MemberShip memberShip = this.getMemberShipByAccount(manageAccount.getAccount());
		PageModel<B2bBillCustomerApplyExtDto> pm = new PageModel<B2bBillCustomerApplyExtDto>();
		String groupId = memberShip.getGroupId();
		pm = billCustomerApplyService.searchBillTask(start, limit, groupId, manageAccount.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 审批驳回
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("rejectBillTask")
	public ModelAndView rejectBillTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/bill/billRejectTask");
		return mv;
	}

	@ResponseBody
	@RequestMapping("/rejectBillList")
	public String rejectBillList(HttpServletResponse response, HttpServletRequest request, BillCustomerMongo dto)
			throws Exception {

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "approveTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<BillCustomerMongo> qm = new QueryModel<BillCustomerMongo>(dto, start, limit, orderName, orderType);
		ManageAccount account = getAccount(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("orderName", orderName);
		map.put("orderType", orderType);
		map.put("status", 4);
		PageModel<BillCustomerMongo> pm = billCustomerApplyService.getByMap(map, qm, account.getPk(), 2);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 已完成审批客户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("finishedBillTask")
	public ModelAndView finishedTask(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/bill/billFinishedTask");
		return mv;
	}

	@ResponseBody
	@RequestMapping("/finishedBillList")
	public String finishedBillList(HttpServletResponse response, HttpServletRequest request, BillCustomerMongo dto)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "approveTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<BillCustomerMongo> qm = new QueryModel<BillCustomerMongo>(dto, start, limit, orderName, orderType);
		ManageAccount account = getAccount(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("orderName", orderName);
		map.put("orderType", orderType);
		map.put("status", 3);
		PageModel<BillCustomerMongo> pm = billCustomerApplyService.getByMap(map, qm, account.getPk(), 1);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 待审批页面跳转
	 * 
	 * @param taskId
	 * @param billCustPk
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("redirectBillPage")
	public ModelAndView redirectBillPage(String taskId, String billCustPk, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		TaskFormData formData = formService.getTaskFormData(taskId);
		String url = formData.getFormKey();
		ManageAccount account = getAccount(request);
		ModelAndView mv = new ModelAndView("economics/bill/" + url);
		mv.addObject("taskId", taskId);
		mv.addObject("economicsCustDto",
				billCustomerApplyService.getCustGoodsByCustomerPk(billCustPk, account.getPk()));
		return mv;
	}

	/**
	 * 审批
	 * 
	 * @param taskId
	 *            任务id
	 * @param comment
	 *            批注信息
	 * @param state
	 *            审核状态 1 通过 2 驳回
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAuditBillCust")
	@ResponseBody
	public String updateAuditBillCust(String taskId, String comment, B2bBillCustomerApplyExtDto customerExtDto,
			Integer state, HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws Exception {
		// 首先根据ID查询任务
		Task task = taskService.createTaskQuery() // 创建任务查询
				.taskId(taskId) // 根据任务id查询
				.singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		// 取得角色用户登入的session对象
		ManageAccount account = getAccount(request);
		MemberShip currentMemberShip = this.getMemberShipByAccount(account.getAccount());
		// 取出用户，角色信息
		ActUserDto currentUser = currentMemberShip.getActUserDto();
		ActGroupDto currentGroup = currentMemberShip.getActGroupDto();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", customerExtDto.getPk());
		B2bBillCustomerApplyExtDto billCustomerApplyExtDto = billCustomerApplyService.getDtoByMap(map);

		billCustomerApplyExtDto.setRemarks(customerExtDto.getRemarks());
		billCustomerApplyExtDto.setPks(customerExtDto.getPks());
		billCustomerApplyExtDto.setLimits(customerExtDto.getLimits());

		int result = 0;
		if (currentGroup.getId().equals("zongjingli")) {
			if (state == 1) {
				// 更新审核信息
				customerExtDto.setUpdateTime(new Date());
				customerExtDto.setApprovalTime(new Date());
				result = auditBill(billCustomerApplyExtDto, Constants.THREE, true);
				if (result > 0) {
					variables.put("msg", "通过");
				}
			} else {
				result = auditBill(billCustomerApplyExtDto, Constants.FOUR, true);
				if (result > 0) {
					variables.put("msg", "驳回");
				}
			}
		} else {
			if (state == 1) {
				result = auditBill(billCustomerApplyExtDto, Constants.TWO, false);
				if (result > 0) {
					variables.put("msg", "通过");
				}

			} else {
				result = auditBill(billCustomerApplyExtDto, Constants.FOUR, false);
				if (result > 0) {
					variables.put("msg", "驳回");
				}
			}
		}
		// 获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		// 设置用户id
		Authentication.setAuthenticatedUserId(currentUser.getFirstName() + "[" + currentGroup.getName() + "]");
		// 添加批注信息
		taskService.addComment(taskId, processInstanceId, customerExtDto.getRemarks());
		// 完成任务
		taskService.complete(taskId, variables);

		return Constants.RESULT_SUCCESS_MSG;
	}

	private int auditBill(B2bBillCustomerApplyExtDto dto, int status, boolean isLast) {
		int result = 0;
		if (dto != null) {
			dto.setApprovalTime(new Date());
			if (status == Constants.FOUR) {// 如果是驳回
				dto.setStatus(Constants.FOUR);
				dto.setProcessInstanceId("");//驳回更新掉processInstanceId
			} else {
				dto.setStatus(status);
			}

			result = billCustomerApplyService.updateAuditBillCustomer(dto, status);
			// 插入客户产品表
			billCustomerApplyService.updateBillMongo(dto.getPk());
			if (isLast && status != Constants.FOUR) {
				dto.setApprovalTime(new Date());
					// 插入客户产品表
					billCustomerService.insertCustomerInfo(dto, Constants.TWO);
			
			}
		}
		return result;
	}

	/**
	 * 申请历史记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("billApproveHistoryPage")
	public ModelAndView billApproveHistory(HttpServletRequest request, HttpServletResponse response) {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		ModelAndView mv = new ModelAndView("economics/bill/billApproveHistory");
		mv.addObject("companyPk", companyPk);
		return mv;
	}

	/**
	 * 历史记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchApproveBillHistoryList")
	@ResponseBody
	public String searchApproveBillHistoryList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		BillCustomerMongo billCustomerMongo = new BillCustomerMongo();
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<BillCustomerMongo> qm = new QueryModel<BillCustomerMongo>(billCustomerMongo, start, limit, orderName,
				orderType);
		ManageAccount account = getAccount(request);
		PageModel<BillCustomerMongo> pm = billCustomerApplyService.getByCompanyPk(companyPk, qm, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * @param request
	 * @param response
	 * @param companyPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBillCustomerCredit", method = RequestMethod.POST)
	public String updateBillCustomerCredit(HttpServletRequest request, HttpServletResponse response, String companyPk) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		try {
			billCustomerService.updateCustomerInfo(companyPk);
		} catch (Exception e) {
			e.printStackTrace();
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 供应商签约
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("billSuppSigning")
	public ModelAndView billSuppSigning(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/bill/billSuppSigning");
		mv.addObject("supCompanyList", billCustomerService.supCompanyList());
		return mv;
	}
	
	/**
	 * 供应商签约列表
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchBillSuppSigningList")
	@ResponseBody
	public String searchBillSuppSigningList(HttpServletRequest request, HttpServletResponse response,B2bBillSigningDto dto)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bBillSigningDto> qm = new QueryModel<B2bBillSigningDto>(dto, start, limit, orderName,orderType);
		ManageAccount account = getAccount(request);
		PageModel<B2bBillSigningExtDto> pm = billCustomerService.searchBillSuppSigningList(qm, account.getPk());
		return JsonUtils.convertToString(pm);
	}
	
	/**
	 * 编辑供应商签约
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBillSuppSigning", method = RequestMethod.POST)
	public String updateBillSuppSigning(HttpServletRequest request, HttpServletResponse response, B2bBillSigning model) {
			String  msg = "";
		try {
			 msg = billCustomerService.updateBillSuppSigning(model);
		} catch (Exception e) {
			e.printStackTrace();
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "apiBillSinging", method = RequestMethod.POST)
	public String apiBillSinging(HttpServletRequest request, HttpServletResponse response, String companyPk)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyPk", companyPk);
		sb.append("apiBillSinging_HttpHelper.param:" + map + "\r\n");
		String data = HttpHelper.sendPostRequest(PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/billSinging", map, null, null);
		sb.append("apiBillSinging_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("apiBillSinging_HttpHelper.deciphData:" + data + "\r\n");
		logger.error("returnValue:"+sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			if (obj.get("code") != null && "0000".equals(obj.get("code"))) {
			} else {
				logger.error(sb.append("apiBillSinging_CODE_FAIL\r\n").toString());
				msg = obj.toString();
			}
		} else {
			logger.error(sb.append("银行未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	
	@RequestMapping("jumpBillSupSigningAccount")
	public ModelAndView jumpBillSupSigningAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk","");
		String companyName = ServletUtils.getStringParameter(request, "companyName","");
		ModelAndView mav = null;
		mav = new ModelAndView("economics/bill/billSupplierAccount");
		mav.addObject("companyPk", companyPk);
		mav.addObject("companyName", companyName);
		mav.addObject("bankList", economicsBankService.searchBankList());
		return mav;
	}
	/**
	 * 获取签约供应商的实体账户
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBillSigncardByPk")
	public String getBillSigncardByPk(HttpServletRequest request, HttpServletResponse response) {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		// 查询条件
		B2bBillSigncardExtDto dto = new B2bBillSigncardExtDto();
		dto.setCompanyPk(companyPk);
		QueryModel<B2bBillSigncardExtDto> qm = new QueryModel<B2bBillSigncardExtDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bBillSigncardExtDto> bankCardDto = billCustomerService.getBankCard(qm,account);
		String json = null;
		if (bankCardDto != null) {
			json = JsonUtils.convertToString(bankCardDto);
		}
		return json;
	}
	
	/**
	 * 删除供应商签约账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("delBillSigncard")
	public String delBillSigncard(HttpServletRequest request, HttpServletResponse response,B2bBillSigncardDto dto) throws Exception {
		String msg = "";
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", dto.getBankAccount());
		map.put("companyPk", dto.getCompanyPk());
		map.put("bankNo", dto.getBankNo());
		map.put("bindType", String.valueOf(Constants.THREE));
		sb.append("delBillSigncard_HttpHelper.param:" + map + "\r\n");
		String url=PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/bindBillGoods";
		sb.append("delBillSigncard_HttpHelper.url:" + url + "\r\n");
		String data = HttpHelper.sendPostRequest(url,map,null,null);
		sb.append("delBillSigncard_HttpHelper.data:" + data + "\r\n");
		data = CommonUtil.deciphData(data);// 解密
		sb.append("delBillSigncard_HttpHelper.doPost_deciphData:" + data + "\r\n");
		logger.error("returnValue:"+sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			if (obj.get("code") == null || !"0000".equals(obj.get("code"))) {
				logger.error(sb.append("searchCustGoodsBind_CODE_FAIL\r\n").toString());
			}
		} else {
			logger.error(sb.append("未返回结果\r\n").toString());
		}
		int retVal =  billCustomerService.delBillSigncard(dto.getPk());
		 if (retVal > 0) {
	            msg = Constants.RESULT_SUCCESS_MSG;
	        } else {
	            msg = Constants.RESULT_FAIL_MSG;
	        }
		return msg;
	}
	
	/**
	 * 修改实体账户
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateBillSigncard")
	public String updateBillSigncard(HttpServletRequest request, HttpServletResponse response,
			B2bBillSigncardDto dto) {
		String msg = "";
		//公司和银行是否已存在
		Boolean flag = billCustomerService.checkCompanyAndBank(dto);
		if (flag) {
			msg = "{\"success\":true,\"msg\":\"已存在该银行绑定记录!\",\"code\":\"0001\"}";
		}else{
			Map<String, Object> map = billCustomerService.updateBillSigncard(dto);

			String pk = map.get("pk").toString();
			
			if (Integer.valueOf(map.get("result").toString()) > 0) {
				msg = "{\"success\":true,\"msg\":\"操作成功!\",\"pk\":\"" + pk + "\"}";
			} else {
				msg = "{\"success\":false,\"msg\":\"操作失败!\",\"pk\":\"" + pk + "\"}";
			}	
		}
		return msg;
	}
}