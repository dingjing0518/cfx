package cn.cf.controller.operation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import cn.cf.common.utils.CommonUtil;
import cn.cf.entity.BussEconPurchaserDataReport;
import cn.cf.entity.BussEconPurchaserDataReportExt;
import cn.cf.entity.BussStoreDataReport;
import cn.cf.entity.BussStoreDataReportExt;
import cn.cf.entity.FlowDataReport;
import cn.cf.entity.FlowDataReportExt;
import cn.cf.entity.IndustryProductSpecTopRF;
import cn.cf.entity.IndustryPurchaserTopRF;
import cn.cf.entity.IndustryStoreTopRF;
import cn.cf.entity.MemberDataReport;
import cn.cf.entity.MemberDataReportExt;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.entity.TransactionDataReportForm;
import cn.cf.entity.TransactionDataStoreReportForm;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.BussEconPurchaserDataService;
import cn.cf.service.operation.BussStoreDataService;
import cn.cf.service.operation.FlowDataReportService;
import cn.cf.service.operation.MemberDataService;
import cn.cf.service.operation.OperationCentreReportService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.operation.BussEconPurchaserDataRunnable;
import cn.cf.task.schedule.operation.BussEconStoreRunnable;
import cn.cf.task.schedule.operation.BussOverviewRunnable;
import cn.cf.task.schedule.operation.BussStoreDataRunnable;
import cn.cf.task.schedule.operation.FlowDataRunnable;
import cn.cf.task.schedule.operation.MemberDataReportRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

/**
 * 运营中心报表
 * 
 * @author xht
 *
 *         2018年10月15日
 */
@Controller
@RequestMapping("/")
public class OperationCentreReportController extends BaseController {

	@Autowired
	private OperationCentreReportService operationCentreReportService;

	@Autowired
	private BussStoreDataService bussStoreDataService;

	@Autowired
	private BussEconPurchaserDataService bussEconPurchaserDataService;

	@Autowired
	private MemberDataService memberDataService;

	@Autowired
	private FlowDataReportService flowDataReportService;
	
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 会员数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("memberData")
	public ModelAndView memberData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/memberData");
		return mav;
	}

	/**
	 * 会员数据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("memberDataList")
	@ResponseBody
	public String memberDataList(HttpServletRequest request, HttpServletResponse response,
			MemberDataReportExt reportForm) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "date");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<MemberDataReportExt> qm = new QueryModel<MemberDataReportExt>(reportForm, start, limit, orderName,
				orderType);
		PageModel<MemberDataReport> pm = memberDataService.searchMemberDataList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出会员数据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportMemberData")
	@ResponseBody
	public String exportMemberData(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
	//	String jsonData = ServletUtils.getStringParameter(request, "json", "");
	/*	ExportUtil<MemberDataReport> export = new ExportUtil<MemberDataReport>();
		List<MemberDataReport> list = memberDataService.exportMemberData(jsonData, reportForm);
		export.exportUtil("memberData", list, response, request);
		return null;*/
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		String name = Thread.currentThread().getName();// 获取当前执行线程
	    operationManageService.saveReportExcelToOss(params, account, "exportMemberData_"+uuid, "运营中心-数据报表-会员数据",14);
		dynamicTask.startCron(new MemberDataReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 交易数据/交易总览
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("bussOverview")
	public ModelAndView bussOverview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/bussOverview");
		return mav;
	}

	/**
	 * 交易数据/交易总览
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchBussOverview")
	@ResponseBody
	public String searchBussOverview(HttpServletRequest request, HttpServletResponse response,
			TransactionDataReportForm reportForm) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "countTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<TransactionDataReportForm> qm = new QueryModel<TransactionDataReportForm>(reportForm, start, limit,
				orderName, orderType);
		PageModel<TransactionDataReportForm> pm = operationCentreReportService.searchTransactionDataList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 导出交易数据/交易总览
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportBussOverview")
	@ResponseBody
	public String exportBussOverview(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
//		Map<String, Object> map = new HashMap<>();
//		map.put("countTimeStart", reportForm.getCountTimeStart());
//		map.put("countTimeEnd", reportForm.getCountTimeEnd());
//		map.put("ids", reportForm.getIds());
//		List<TransactionDataReportForm> list = operationCentreReportService.exportTransactionDataList(map);
//		ExportUtil<TransactionDataReportForm> export = new ExportUtil<>();
//		export.exportUtil("transactionDataReportForm", list, response, request);
		
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportBussOverview_"+uuid, "运营中心-数据报表-交易数据-交易总览",15);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new BussOverviewRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 交易数据/店铺日交易数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("bussStoreData")
	public ModelAndView bussStoreData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/bussStoreData");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 交易数据/店铺日交易数据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("bussStoreDataList")
	public String bussStoreDataList(HttpServletRequest request, HttpServletResponse response,
			BussStoreDataReportExt buss) throws Exception {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "date");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<BussStoreDataReportExt> qm = new QueryModel<BussStoreDataReportExt>(buss, start, limit, orderName,
				orderType);
		PageModel<BussStoreDataReport> pm = bussStoreDataService.searchBussStoreDataList(qm,account);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出店铺日交易数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportBussStoreData")
	@ResponseBody
	public String exportBussStoreData(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
//		ManageAccount account = this.getAccount(request);
//		String jsonData = ServletUtils.getStringParameter(request, "json", "");
//		ExportUtil<BussStoreDataReport> export = new ExportUtil<BussStoreDataReport>();
//		List<BussStoreDataReport> list = bussStoreDataService.exportBussStoreData(jsonData, buss,account);
//		export.exportUtil("bussStoreDataReport", list, response, request);
//		return null;
		
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportBussStoreData_"+uuid, "运营中心-数据报表-交易数据-店铺日交易数据",16);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new BussStoreDataRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 交易数据/店铺日交易金融数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("bussEconStoreData")
	public ModelAndView bussEconStoreData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/bussEconStoreData");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 导出交易数据/店铺日交易金融数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportBussEconStoreData")
	@ResponseBody
	public String exportBussEconStoreData(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
//		Map<String, Object> map = new HashMap<>();
//		ManageAccount account = this.getAccount(request);
//		map.put("countTimeStart", reportForm.getCountTimeStart());
//		map.put("countTimeEnd", reportForm.getCountTimeEnd());
//		map.put("ids", reportForm.getIds());
//		map.put("storeName", reportForm.getStoreName());
//		List<TransactionDataStoreReportForm> list = operationCentreReportService.exportTransDataStoreList(map,account);
//		ExportUtil<TransactionDataStoreReportForm> export = new ExportUtil<>();
//		export.exportUtil("transactionDataStoreReportForm", list, response, request);
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportBussEconStoreData_"+uuid, "运营中心-数据报表-交易数据-店铺日交易金融数据",17);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new BussEconStoreRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 交易数据/采购商日交易金融数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("bussEconPurchaserData")
	public ModelAndView bussEconPurchaserData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/bussEconPurchaserData");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 查询交易数据/店铺日交易金融数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchBussEconSupplierData")
	@ResponseBody
	public String searchBussEconSupplierData(HttpServletRequest request, HttpServletResponse response,
			TransactionDataStoreReportForm storeReportForm) throws Exception {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "countTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<TransactionDataStoreReportForm> qm = new QueryModel<TransactionDataStoreReportForm>(storeReportForm,
				start, limit, orderName, orderType);
		PageModel<TransactionDataStoreReportForm> pm = operationCentreReportService.searchTransDataStoreList(qm,account);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 交易数据/采购商日交易金融数据列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("bussEconPurchaserDataList")
	public String bussEconPurchaserDataList(HttpServletRequest request, HttpServletResponse response,
			BussEconPurchaserDataReportExt buss) throws Exception {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "date");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<BussEconPurchaserDataReportExt> qm = new QueryModel<BussEconPurchaserDataReportExt>(buss, start,
				limit, orderName, orderType);
		PageModel<BussEconPurchaserDataReport> pm = bussEconPurchaserDataService.searchBussEconPurchaserDataList(qm,account);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出采购商日交易金融数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportBussEconPurchaserData")
	@ResponseBody
	public String exportBussEconPurchaserData(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
//		ManageAccount account = this.getAccount(request);
//		String jsonData = ServletUtils.getStringParameter(request, "json", "");
//		ExportUtil<BussEconPurchaserDataReport> export = new ExportUtil<BussEconPurchaserDataReport>();
//		List<BussEconPurchaserDataReport> list = bussEconPurchaserDataService.exportBussEconPurchaserData(jsonData,
//				buss,account);
//		export.exportUtil("bussEconPurchaserDataReport", list, response, request);
//		return null;
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportBussEconPurchaserData_"+uuid, "运营中心-数据报表-交易数据-采购商日交易金融数据",18);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new BussEconPurchaserDataRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 行业数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("industryData")
	public ModelAndView industryData(HttpServletRequest request, HttpServletResponse response, String searchMonth)
			throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/industryData");
		Date date = new Date();
		String year = "";
		int month = 0;

		if (searchMonth != null && !"".equals(searchMonth)) {
			String[] tiems = searchMonth.split("-");
			year = tiems[0];
			String m = tiems[1];
			// 判断第一位是否是零
			String subMonth = m.substring(0, 1);
			if ("0".equals(subMonth)) {
				subMonth = m.substring(1, 2);
			} else {
				subMonth = tiems[1];
			}
			month = Integer.valueOf(subMonth);
		} else {
			Calendar calendar = Calendar.getInstance();// 日历对象
			calendar.setTime(date);// 设置当前日期
			int selfMonth = calendar.get(Calendar.MONTH);// 当前月
			if (selfMonth==0) {
				month = 12;
				year =String.valueOf(Integer.parseInt(new SimpleDateFormat("yyyy").format(date))-1);
			}else{
				month = selfMonth;
				year = new SimpleDateFormat("yyyy").format(date);
			}
			
			String returnMonth = "";
			if (month < 10) {
				returnMonth = "0" + month;
			} else {
				returnMonth = month + "";
			}
			searchMonth = year + "-" + returnMonth;
		}

		List<IndustryProductSpecTopRF> productSpecList = operationCentreReportService
				.searchIndustryProductSpecList(year, month);
		List<IndustryPurchaserTopRF> purchaserList = operationCentreReportService.searchIndustryPurchaserList(year,
				month);
		List<IndustryStoreTopRF> storeList = operationCentreReportService.searchIndustryStoreList(year, month);
		mav.addObject("productSpecList", productSpecList);
		mav.addObject("purchaserList", purchaserList);
		mav.addObject("storeList", storeList);
		mav.addObject("reMonth", searchMonth);
		return mav;
	}

	/**
	 * 流量数据
	 * 
	 * @param request
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("flowData")
	public ModelAndView flowData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/operationCentreReport/flowData");
		return mav;
	}

	/**
	 * 流量数据列表
	 * 
	 * @param request
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("flowDataList")
	@ResponseBody
	public String flowDataList(HttpServletRequest request, HttpServletResponse response, FlowDataReportExt flow)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "date");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<FlowDataReportExt> qm = new QueryModel<FlowDataReportExt>(flow, start, limit, orderName, orderType);
		PageModel<FlowDataReport> pm = flowDataReportService.searchflowDataList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出流量数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportFlowData")
	@ResponseBody
	public String exportFlowData(HttpServletRequest request, HttpServletResponse response, ReportFormsDataTypeParams params)
			throws Exception {
//		String jsonData = ServletUtils.getStringParameter(request, "json", "");
//		ExportUtil<FlowDataReport> export = new ExportUtil<FlowDataReport>();
//		List<FlowDataReport> list = flowDataReportService.exportFlowData(jsonData, flow);
//		export.exportUtil("flowData", list, response, request);
//		return null;
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportFlowData_"+uuid, "运营中心-数据报表-流量数据",19);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new FlowDataRunnable(name,uuid), name);
		return Constants.EXPORT_MSG; 
	}
}
