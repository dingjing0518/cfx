package cn.cf.controller.logistics;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.cf.PageModel;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.dto.DataReportDto;
import cn.cf.entity.DataReportExtDto;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.SearchReport;
import cn.cf.service.logistics.DataReportService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.logistics.CustomerReportRunnable;
import cn.cf.task.schedule.logistics.GrossProfitReportRunnable;
import cn.cf.task.schedule.logistics.SupplierDataReportRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.jxls.exception.ParsePropertyException;

/**
 * 数据报表
 * 
 * @author xht
 *
 */

@Controller
@RequestMapping("/")
public class DataReportController extends BaseController {

	@Autowired
	private DataReportService dataReportService;

	@Autowired
	private DynamicTask dynamicTask;

	@Autowired
	private OperationManageService operationManageService;

	/**
	 * 毛利：列表页面
	 * 
	 * @return
	 */
	@RequestMapping("gpDataReport")
	public ModelAndView purchaserInvoiceManager() {
		ModelAndView mav = new ModelAndView("logistics/report/grossProfitReport");
		return mav;
	}

	/**
	 * 客户报表：列表页面
	 * 
	 * @return
	 */
	@RequestMapping("customerDataReport")
	public ModelAndView customReportManager() {
		ModelAndView mav = new ModelAndView("logistics/report/customerDataReport");
		return mav;
	}

	/**
	 * 物流供应商报表：列表页面
	 * 
	 * @return
	 */
	@RequestMapping("supplierDataReport")
	public ModelAndView supplierDataReportManager() {
		ModelAndView mav = new ModelAndView("logistics/report/supplierDataReport");
		return mav;
	}

	/**
	 * 毛利 ：列表展示
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("grossProfitReport_data")
	@ResponseBody
	public String grossProfitReport_data(HttpServletRequest request, HttpServletResponse response,
			SearchReport searchReport) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<SearchReport> qm = new QueryModel<SearchReport>(searchReport, start, limit, orderName, orderType);
		PageModel<DataReportExtDto> pm = dataReportService.searchGrossProfitList(qm, 1, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 客户报表:列表展示
	 * 
	 * @param request
	 * @param response
	 * @param searchReport
	 * @return
	 */
	@RequestMapping("customerDataReport_data")
	@ResponseBody
	public String customerDataReport_data(HttpServletRequest request, HttpServletResponse response,
			SearchReport searchReport) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<SearchReport> qm = new QueryModel<SearchReport>(searchReport, start, limit, orderName, orderType);
		PageModel<DataReportDto> pm = dataReportService.searchCustomerDataReportList(qm, 1, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 物流供应商报表:列表展示
	 * 
	 * @param request
	 * @param response
	 * @param searchReport
	 * @return
	 */
	@RequestMapping("supplierDataReport_data")
	@ResponseBody
	public String supplierDataReport_data(HttpServletRequest request, HttpServletResponse response,
			SearchReport searchReport) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<SearchReport> qm = new QueryModel<SearchReport>(searchReport, start, limit, orderName, orderType);
		PageModel<DataReportDto> pm = dataReportService.searchSupplierDataReportList(qm, 1, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出 ：毛利
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 */
	@RequestMapping(value = "exportGrossProfitReport")
	@ResponseBody
	public String exportGrossProfitReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws ParsePropertyException, InvalidFormatException, IOException {
		if (params == null) {
			params = new ReportFormsDataTypeParams();
		}
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		operationManageService.saveReportExcelToOss(params, account, "exportGrossProfitReport_"+uuid, "物流中心-数据报表-毛利报表",6);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new GrossProfitReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 导出 ：客户报表
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "exportCustomerReport")
	@ResponseBody
	public String exportCustomerReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws ParsePropertyException, InvalidFormatException, IOException {
		if (params == null) {
			params = new ReportFormsDataTypeParams();
		}
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		operationManageService.saveReportExcelToOss(params, account, "exportCustomerReport_"+uuid, "物流中心-数据报表-客户报表",7);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new CustomerReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 导出 ：物流供应商报表
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 */
	@RequestMapping(value = "exportSupplierDataReport")
	@ResponseBody
	public String exportSupplierDataReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws ParsePropertyException, InvalidFormatException, IOException {
		ManageAccount account = getAccount(request);
		String uuid = KeyUtils.getUUID();
		// 用线程跑物流供应商报表导出数据
		operationManageService.saveReportExcelToOss(params, account, "exportSupplierDataReport_"+uuid, "物流中心-数据报表-物流供应商报表",1);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new SupplierDataReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
	}
}
