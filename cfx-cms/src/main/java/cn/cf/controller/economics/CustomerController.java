package cn.cf.controller.economics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.cf.dto.*;
import cn.cf.entity.*;
import cn.cf.property.PropertyConfig;
import cn.cf.task.schedule.economics.ImprovingdataRunnable;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.http.HttpHelper;
import cn.cf.common.utils.CommonUtil;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.B2bEconomicsGoodsService;
import cn.cf.service.enconmics.CustomerService;
import cn.cf.service.enconmics.EconomicsBankService;
import cn.cf.service.enconmics.EconomicsCreditService;
import cn.cf.service.enconmics.EconomicsImprovingdataService;
import cn.cf.service.manage.AccountManageService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.json.JSONObject;

/**
 * 用户提交金融产品申请
 * 
 * @author hxh
 */
@Controller
@RequestMapping("/")
public class CustomerController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

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
	private EconomicsCreditService economicsService;

	@Autowired
	private EconomicsBankService conomicsBankService;
    @Autowired
    private OperationManageService operationManageService;
	@Autowired
	private B2bEconomicsGoodsService b2bEconomicsGoodsService;
	
	@Autowired
	private EconomicsImprovingdataService economicsImprovingdataService;
	
	@Autowired
	private DynamicTask dynamicTask;
	/**
	 * 申请金融产品用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("economicsCustomerManage")
	public ModelAndView economicsCustomer(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("economics/economicsCustomerManage");
		mv.addObject("acccountList", accountManageService.getAllAccountList());
		mv.addObject("bankList", conomicsBankService.searchBankList());
		List<EconomicsCustomerSource> sources = new ArrayList<EconomicsCustomerSource>();
		sources.add(new EconomicsCustomerSource("1", "盛虹推荐"));
		sources.add(new EconomicsCustomerSource("2", "自主申请"));
		sources.add(new EconomicsCustomerSource("3", "新凤鸣推荐"));
		sources.add(new EconomicsCustomerSource("4", "营销推荐"));
		sources.add(new EconomicsCustomerSource("5", "其他供应商推荐"));
		sources.add(new EconomicsCustomerSource("6", "CMS后台申请"));
		mv.addObject("sourceList", sources);
		mv.addObject("creditReportGoodsList", b2bEconomicsGoodsService.searchCreditReportGoods());
		return mv;
	}

    /**
     * 完善资料页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("perfectCustomerDatum")
    public ModelAndView perfectCustomerDatum(HttpServletRequest request, HttpServletResponse response, B2bEconomicsCustomerExtDto extDto) {
    	   ModelAndView mv = new ModelAndView("economics/perfectCustomerDatum");
           mv.addObject("brandList", operationManageService.searchBrandList());// 品牌
           //线上近12个月累计交易金额
           mv.addObject("purchaserSalesAmount", b2bEconomicsGoodsService.countPurchaserSalesAmount(extDto.getCompanyPk()));
           mv.addObject("onlineAddupNumber",b2bEconomicsGoodsService.addUpOnlineSalesNumbers(extDto.getCompanyPk()));//企业线上近12个月交易月数
           mv.addObject("econAddUpNumbers",b2bEconomicsGoodsService.addUpEconSalesNumbers(extDto.getCompanyPk()));//企业近12个月金融产品使用月数
           mv.addObject("countEconSalesAmount",b2bEconomicsGoodsService.countEconSalesAmount(extDto.getCompanyPk()));//企业近12个月金融产品累计发生额
           mv.addObject("countEconGoodsIsOver",b2bEconomicsGoodsService.countEconGoodsIsOver(extDto.getCompanyPk()));//企业近12个月金融产品还款逾期次数
   		mv.addObject("countStoreSalesNum",b2bEconomicsGoodsService.countStoreSalesNum(extDto.getCompanyPk()));//线上企业近12个月发生业务店铺数
           //B2bEconomicsImprovingdataDto improvingdataDto = b2bEconomicsGoodsService.getperfectCustDatum(extDto.getPk());
   		B2bEconomicsCustomerDto economicsCustomer = b2bEconomicsGoodsService.getEconomicsCustomer(extDto.getPk());
   		if(economicsCustomer != null){
   			String improvingdataInfo = economicsCustomer.getImprovingdataInfo();
   			if (CommonUtil.isNotEmpty(improvingdataInfo)){
   				B2bEconomicsImprovingdataEntity improvingdataDto = com.alibaba.fastjson.JSONObject.parseObject(improvingdataInfo, B2bEconomicsImprovingdataEntity.class);
   				mv.addObject("datum",improvingdataDto);
   					mv.addObject("updateTime",improvingdataDto.getUpdateTime());
   					String device = improvingdataDto.getDeviceStatus();
   					if (CommonUtil.isNotEmpty(device)){
   						com.alibaba.fastjson.JSONObject obj = JSON.parseObject(device);
   						String deviceType = (String)obj.get("deviceType");
   						String deviceNum = (String)obj.get("deviceNum");
   						mv.addObject("deviceType",deviceType);
   						mv.addObject("deviceNum",deviceNum);
   					}
   			}
   		}
   		mv.addObject("countPlatformAmount",b2bEconomicsGoodsService.getPlatformCustDatum(extDto.getCompanyPk()));//平台收入
   		mv.addObject("countCreditAmount",b2bEconomicsGoodsService.getCreditCustDatum(extDto.getCompanyPk()));//企业授信额度
           mv.addObject("economicsCustomer", extDto);
           return mv;
    }

    @RequestMapping("updateperfectCustDatum")
    @ResponseBody
    public String updateperfectCustDatum(HttpServletRequest request, HttpServletResponse response, B2bEconomicsImprovingdataEntity improvingdata)
            throws Exception {
        return b2bEconomicsGoodsService.updateperfectCustDatum(improvingdata);
    }


    @RequestMapping(value = "importPerfectCustDatumScore")
    @ResponseBody
    public String importPerfectCustDatumScore(HttpServletRequest request,
                HttpServletResponse response, @RequestParam(value="filename") MultipartFile file,String path,String companyPk,String processInstanceId)
            throws Exception {

        return b2bEconomicsGoodsService.importPerfectCustDatumScoreFile(file,companyPk,processInstanceId);
    }

    /**
     * 提交审核页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("economicsCustomerAudit")
    public ModelAndView economicsCustomerAudit(HttpServletRequest request, HttpServletResponse response, B2bEconomicsCustomerExtDto extDto) {
        ModelAndView mv = new ModelAndView("economics/economicsCustomerAudit");
        mv.addObject("economicsCustomer", extDto);
        return mv;
    }


	/**
	 * 申请客户管理列表
	 * 
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchEconCustList")
	@ResponseBody
	public String searchGoodsList(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCustomerExtDto customerExtDto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String sort = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String dir = ServletUtils.getStringParameter(request, "orderType", "DESC");
		ManageAccount account = getAccount(request);
		customerExtDto.setAccountPk(account.getPk());
		QueryModel<B2bEconomicsCustomerExtDto> qm = new QueryModel<B2bEconomicsCustomerExtDto>(customerExtDto, start,
				limit, sort, dir);

		MemberShip currentMemberShip = this.getMemberShipByAccount(account.getAccount());
		PageModel<B2bEconomicsCustomerExtDto> pm = customerService.searchEconCustList(qm, currentMemberShip);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * //查询公司资料
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @param datumType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByEconCustPk")
	@ResponseBody
	public String getByEconCustPk(HttpServletRequest request, HttpServletResponse response, String pk, String datumType)
			throws Exception {
		List<B2bEconomicsDatumDto> list = customerService.getByEconomicsCustomerPk(pk, datumType);
		String json = "0";
		if (list != null && list.size() > 0) {
			json = JsonUtils.convertToString(list.get(0));
		}
		return json;
	}

	/**
	 * 新增/编辑金融产品完善资料
	 * 
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateEconomicsCustomer")
	@ResponseBody
	public String updateEconomicsCustomer(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsDatumExtDto customerExtDto) throws Exception {
		int result = customerService.updateEconomicsCustomer(customerExtDto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 新增/编辑金融产品完善资料
	 * 
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateEconomicsCustomerEx")
	@ResponseBody
	public String updateEconomicsCustomerEx(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsDatumExtDto customerExtDto) throws Exception {
		int result = customerService.updateEconomicsCustomerEx(customerExtDto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 编辑产品所属银行
	 * 
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateEconomicsAssidirName")
	@ResponseBody
	public String updateEconomicsAssidirName(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCustomerExtDto customerExtDto) throws Exception {

		B2bEconomicsCustomerDto b2bEconomicsCustomerDto = customerService.getByPk(customerExtDto.getPk());

		if (null != b2bEconomicsCustomerDto.getSource()) {
			if (b2bEconomicsCustomerDto.getSource().intValue() != customerExtDto.getSource().intValue()) {
				customerExtDto.setStaticTime(new Date());
			}
		}

		int result = customerService.updateEconomicsAssidirName(customerExtDto);
		String msg = "";
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 提交金融产品流程申請
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startApply")
	@ResponseBody
	public String startApply(HttpServletResponse response, B2bEconomicsCustomerExtDto customerExtDto) throws Exception {
		B2bEconomicsCustomerDto customer = customerService.getByEconomCustPk(customerExtDto.getPk());
		if (null != customerExtDto.getIsReApply() && customerExtDto.getIsReApply().equals("1")) {
			if (customer != null) {
				customer.setProcessInstanceId("");
				customerService.updateEconomics(customer);
			}
		}
		// 启动流程
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("economCustPk", customerExtDto.getPk());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("economicsAuditProcess", variables);
		// 根据流程实例Id查询任务
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		taskService.complete(task.getId());

		if (customer != null) {
			// 设置状态
			customerExtDto.setAuditStatus(Constants.TWO);
			customerExtDto.setProcessInstanceId(pi.getProcessInstanceId());
			// 审批时间
			customerExtDto.setApprovalTime(new Date());
			// 修改状态
			customerService.updateEconomicsAssidirName(customerExtDto);
			// 插入芒果
			if (customerExtDto.getPk() != null && !"".equals(customerExtDto.getPk())) {
				customerService.insertEconCustomerMongo(customerExtDto.getPk());
			}
		}
		return Constants.RESULT_SUCCESS_MSG;
	}

	/**
	 * 重定向审核处理页面
	 * 
	 * @param taskId
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/redirectPage")
	public ModelAndView redirectPage(String taskId, String economCustPk, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		TaskFormData formData = formService.getTaskFormData(taskId);
		String url = formData.getFormKey();
		ManageAccount account = getAccount(request);
		ModelAndView mv = new ModelAndView("economics/" + url);
		mv.addObject("taskId", taskId);
		mv.addObject("economicsCustDto", customerService.getCustGoodsByEconomCustPk(economCustPk, account.getPk()));
		B2bEconomicsCustomerExtDto customerExtDto =	customerService.getByEconomCustPk(economCustPk);
		B2bEconomicsImprovingdataEntity  improvingdataDto =null;
		if (customerExtDto!=null&&customerExtDto.getImprovingdataInfo()!=null &&!customerExtDto.getImprovingdataInfo().equals("")) {
			  improvingdataDto = JsonUtils.toBean(customerExtDto.getImprovingdataInfo(),
					B2bEconomicsImprovingdataEntity.class);
		}else{
			  improvingdataDto = new B2bEconomicsImprovingdataEntity();
		}
		mv.addObject("improvingdata",improvingdataDto);
		return mv;
	}

	/**
	 * 
	 * 根据主键pk查询审批表
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getByEconomicsCustomerPk")
	@ResponseBody
	public String getByEconomicsCustomerPk(HttpServletRequest request, HttpServletResponse response, String pk)
			throws Exception {
		B2bEconomicsCustomerExtDto dto = customerService.getByEconomCustPk(pk);
		String json = "";
		if (dto != null) {
			json = JsonUtils.convertToString(dto);
		}
		return json;
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
	@RequestMapping("/updateAuditEconomCust")
	@ResponseBody
	public String audit_bz(String taskId, String comment, B2bEconomicsCustomerExtDto customerExtDto, Integer state,
			HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception {
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
		B2bEconomicsCustomerExtDto b2bEconomicsCustomerExtDto = customerService.getByEconomCustPk(customerExtDto.getPk());
		b2bEconomicsCustomerExtDto.setRemarks(customerExtDto.getRemarks());
		b2bEconomicsCustomerExtDto.setEconGoodsPkD(customerExtDto.getEconGoodsPkD());
		b2bEconomicsCustomerExtDto.setEffectEndTimesD(customerExtDto.getEffectEndTimesD());
		b2bEconomicsCustomerExtDto.setEffectStartTimesD(customerExtDto.getEffectStartTimesD());
		b2bEconomicsCustomerExtDto.setLimitsD(customerExtDto.getLimitsD());
		b2bEconomicsCustomerExtDto.setTotalRateD(customerExtDto.getTotalRateD());
		b2bEconomicsCustomerExtDto.setBankRateD(customerExtDto.getBankRateD());
		b2bEconomicsCustomerExtDto.setTermD(customerExtDto.getTermD());
		b2bEconomicsCustomerExtDto.setSevenRateD(customerExtDto.getSevenRateD());
		b2bEconomicsCustomerExtDto.setEconGoodsPkBt(customerExtDto.getEconGoodsPkBt());
		b2bEconomicsCustomerExtDto.setEffectEndTimesBt(customerExtDto.getEffectEndTimesBt());
		b2bEconomicsCustomerExtDto.setEffectStartTimesBt(customerExtDto.getEffectStartTimesBt());
		b2bEconomicsCustomerExtDto.setLimitsBt(customerExtDto.getLimitsBt());
		b2bEconomicsCustomerExtDto.setTotalRateBt(customerExtDto.getTotalRateBt());
		b2bEconomicsCustomerExtDto.setBankRateBt(customerExtDto.getBankRateBt());
		b2bEconomicsCustomerExtDto.setTermBt(customerExtDto.getTermBt());
		
		int result = 0;
		if (currentGroup.getId().equals("zongjingli")) {
			if (state == 1) {
				// 更新审核信息
				customerExtDto.setUpdateTime(new Date());
				customerExtDto.setApprovalTime(new Date());
				result = auditEconomics(b2bEconomicsCustomerExtDto, Constants.THREE, true);
				if (result > 0) {
					variables.put("msg", "通过");
					customerService.updateEconomicsMongoForContent(b2bEconomicsCustomerExtDto.getPk());

				}
				// 最终审核更新mongo
				customerService.updateEconomicsMongo(b2bEconomicsCustomerExtDto.getPk());
			} else {

				result = auditEconomics(b2bEconomicsCustomerExtDto, Constants.FOUR, true);
				if (result > 0) {
					b2bEconomicsCustomerExtDto.setProcessInstanceId("");//驳回更新掉processInstanceId
					customerService.updateEconomics(b2bEconomicsCustomerExtDto);
					variables.put("msg", "驳回");
				}
			}
		} else {
			if (state == 1) {
				result = auditEconomics(b2bEconomicsCustomerExtDto, Constants.TWO, false);
				if (result > 0) {
					variables.put("msg", "通过");
					customerService.updateEconomicsMongoForContent(b2bEconomicsCustomerExtDto.getPk());
				}

			} else {
				result = auditEconomics(b2bEconomicsCustomerExtDto, Constants.FOUR, false);
				if (result > 0) {
					b2bEconomicsCustomerExtDto.setProcessInstanceId("");//驳回更新掉processInstanceId
					customerService.updateEconomics(b2bEconomicsCustomerExtDto);
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

		taskService.setVariablesLocal(task.getId(), variables);
		// 完成任务
		taskService.complete(taskId, variables);

		return Constants.RESULT_SUCCESS_MSG;
	}

	private int auditEconomics(B2bEconomicsCustomerExtDto dto, Integer status, boolean isLast) {
		B2bEconomicsCustomerDto custDto = customerService.getByEconomCustPk(dto.getPk());
		int result = 0;
		if (custDto != null) {
			if (status == Constants.FOUR) {// 如果是驳回
				dto.setAuditStatus(Constants.FOUR);
				dto.setApprovalTime(new Date());
				result = customerService.updateEconomicsAudit(dto, status);

				List<B2bEconomicsCustomerExtDto> lst = customerService.getByCompanyPkEx(custDto.getCompanyPk());
				if (null != lst) {
					for (B2bEconomicsCustomerExtDto b2bEconomicsCustomerExtDto : lst) {
						b2bEconomicsCustomerExtDto.setAuditStatus(1);
						customerService.updateEconomics(b2bEconomicsCustomerExtDto);
					}
				}

				// 最终审核更新mongo
				customerService.updateEconomicsMongo(dto.getPk());

			} else {
				dto.setAuditStatus(status);

				result = customerService.updateEconomicsAudit(dto, status);
				//最终审核更新mongo
				customerService.updateEconomicsMongo(dto.getPk());

				if (isLast) {
					dto.setApprovalTime(new Date());
						// 插入客户产品表
						economicsService.insertCreditInfo(dto, Constants.TWO);
				}
			}
		}
		return result;
	}

	/**
	 * 审核明细查询
	 * 
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchEconCustAuditDetailList")
	@ResponseBody
	public String searchEconCustAuditDetailList(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCustomerExtDto customerExtDto) throws Exception {
		String processInstanceId = ServletUtils.getStringParameter(request, "processInstanceId", "");
		PageModel<CommentEntityEx> pm = new PageModel<CommentEntityEx>();
		List<CommentEntityEx> commentList = new ArrayList<CommentEntityEx>();
		List<Comment> clist = taskService.getProcessInstanceComments(processInstanceId);
		if (clist != null && clist.size() > 0) {
			for (Comment c : clist) {
				CommentEntityEx cx = new CommentEntityEx();
				cx.setId(c.getId());
				cx.setType(c.getType());
				cx.setUserId(c.getUserId());
				cx.setTime(c.getTime());
				cx.setTaskId(c.getTaskId());
				cx.setProcessInstanceId(c.getProcessInstanceId());
				cx.setFullMessage(c.getFullMessage());
				
				List<HistoricVariableInstance> list = (List<HistoricVariableInstance>) historyService
						.createHistoricVariableInstanceQuery().variableName("msg").taskId(cx.getTaskId()).list();
				if (list!=null&&list.size()>0) {
					cx.setState(list.get(0).getValue().toString());
				}

				commentList.add(cx);
			}
		}

		pm.setTotalCount(commentList.size());
		pm.setDataList(commentList);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 申请历史记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchApproveHistoryList")
	@ResponseBody
	public String searchApproveHistoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		EconCustomerMongo econCustomerMongo = new EconCustomerMongo();
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<EconCustomerMongo> qm = new QueryModel<EconCustomerMongo>(econCustomerMongo, start, limit, orderName,
				orderType);
		ManageAccount account = getAccount(request);
		PageModel<EconCustomerMongo> pm = customerService.getByCompanyPk(companyPk, qm, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 申请历史记录页面
	 * 
	 * @param request
	 * @param response
	 * @param extDto
	 * @return
	 */
	@RequestMapping("approveHistory")
	public ModelAndView approveHistory(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCustomerExtDto extDto) {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk", "");
		ModelAndView mv = new ModelAndView("economics/approveHistory");
		mv.addObject("companyPk", companyPk);
		return mv;
	}

	/**
	 * 调用API接口：查询银行文件是否存在
	 * 
	 * @param request
	 * @param response
	 * @param companyPk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkEconCustomerFile", method = RequestMethod.POST)
	@ResponseBody
	public String checkEconCustomerFile(HttpServletRequest request, HttpServletResponse response, String companyPk,
			String shotName) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("companyPk", companyPk);
		map.put("shotName", shotName);
		String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/downLoadWzFile";
		String data = HttpHelper.sendPostRequest(url, map, null, null);
		String msg = "{\"code\":\"0000\",\"msg\":\"操作成功!\"}";

		StringBuilder sb = new StringBuilder();
		
		sb.append("checkEconCustomerFile_HttpHelper.doPost_param:" + map + "\r\n");
		sb.append("checkEconCustomerFile_HttpHelper.doPost_url:" + url + "\r\n");
		sb.append("checkEconCustomerFile_HttpHelper.doPost_data:" + data + "\r\n");
		data = CommonUtil.deciphData(data);// 解密
		sb.append("checkEconCustomerFile_HttpHelper.doPost_deciphData:" + data + "\r\n");
		logger.error("returnValue:" + sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			msg = obj.toString();
		} else {
			msg = "{\"code\":\"0001\",\"msg\":\"操作失败!\"}";
		}
		return msg;
	}

	/**
	 * 
	 * 根据文件地址下载文件
	 * 
	 * @param req
	 * @param response
	 * @param url
	 * @throws FileNotFoundException
	 */
	@RequestMapping("download")
	public void download(HttpServletRequest req, HttpServletResponse response, String url)
			throws FileNotFoundException {
		try {
			// 读到流中
			File file = new File(url);
			if (!file.exists()) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
				URL newurl = new URL(url);
				BufferedInputStream inStream = new BufferedInputStream(newurl.openStream());
				byte[] b = new byte[1024];
				int len;
				// 循环取出流中的数据
				while ((len = inStream.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
				inStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据申请pk查询申请的金融产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchCustomerGoods")
	@ResponseBody
	public String searchCustomerGoods(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String economicsCustomerPk = ServletUtils.getStringParameter(request, "economicsCustomerPk", "");
		return JsonUtils.convertToString(customerService.getGoodsByEconCustomerPk(economicsCustomerPk));
	}

	/**
	 * 
	 * 查询全部的有效银行
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 
	 * 
	 * @throws Exception
	 */
	@RequestMapping("searchBankList")
	@ResponseBody
	public String searchBankList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return JsonUtils.convertToString(conomicsBankService.searchBankList());
	}

	/**
	 * 资料录入及查看历史
	 * 
	 * @param request
	 * @param response
	 * @param customerExtDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchProcess")
	@ResponseBody
	public String searchProcess(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsCustomerExtDto customerExtDto) throws Exception {
		String processInstanceId = ServletUtils.getStringParameter(request, "processInstanceId", "");
		List<Comment> commentList = taskService.getProcessInstanceComments(processInstanceId);

		List<ProcessEntity> list = customerService.searchProcess(processInstanceId, commentList);
		return JsonUtils.convertToString(list);
	}
	
	
	@RequestMapping("searchProcessDetail")
	@ResponseBody
	public String searchProcessDetail(HttpServletRequest request, HttpServletResponse response,
			String  processInstanceId ) throws Exception {
		B2bEconomicsImprovingdataEntity dto = economicsImprovingdataService.getMongoByEconomCustPk(processInstanceId);
		
		if (dto==null) {
			dto = new B2bEconomicsImprovingdataEntity();
		}
		return JsonUtils.convertToString(dto);
	}
	
	
    @RequestMapping(value = "exportImprovingdata")
    @ResponseBody
    public String exportImprovingdata(HttpServletRequest request, HttpServletResponse response, CustomerDataTypeParams params) throws Exception {
        ManageAccount account = getAccount(request);
        String uuid = KeyUtils.getUUID();
      /*  MemberShip currentMemberShip = getMemberShipByAccount(account.getAccount());
		if (currentMemberShip != null && null != currentMemberShip.getGroupId()) {
			if ("jingrongzhuanyuan".equals(currentMemberShip.getActGroupDto().getId())) {
				params.setAssidirPk(manageAccountExtDao.getByAccount(currentMemberShip.getUserId()).getPk());
			}
		}*/
    	operationManageService.saveCustomerExcelToOss(params, account, "exportImprovingdata_"+uuid, "金融中心-金融管理-申请客户管理",8);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new ImprovingdataRunnable(name,uuid), name);
        return Constants.EXPORT_MSG;
    }
}
