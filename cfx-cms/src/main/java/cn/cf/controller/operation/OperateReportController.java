package cn.cf.controller.operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.property.PropertyConfig;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import cn.cf.entity.GoodsUpdateReport;
import cn.cf.entity.GoodsUpdateReportExt;
import cn.cf.entity.OperationSupplierSaleRF;
import cn.cf.entity.PurchaserSaleReportForms;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.entity.SalemanSaleDetailReport;
import cn.cf.entity.SalemanSaleDetailReportExt;
import cn.cf.entity.SupplierSaleDataReport;
import cn.cf.entity.SupplierSaleDataReportExt;
import cn.cf.entity.SupplierSaleDataReportForms;
import cn.cf.entity.SupplierSaleTotalData;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.service.operation.OperationManageService;
import cn.cf.service.operation.OperationReportService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.marketing.GoodsUpdateReportRunnable;
import cn.cf.task.schedule.marketing.PurchaserSaleReportRunnable;
import cn.cf.task.schedule.marketing.SalemanSaleDetailReportRunnable;
import cn.cf.task.schedule.marketing.ShopSaleDataReportRunnable;
import cn.cf.task.schedule.marketing.SupplierSaleDataReportRunnable;
import cn.cf.task.schedule.marketing.SupplierSaleReportRunnable;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 营销报表
 * 
 * @author xht
 *
 *         2018年7月18日
 */
@Controller
@RequestMapping("/")
public class OperateReportController extends BaseController {

	@Autowired
	private OperationReportService operationReportService;
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 产品更新表:页面
	 */
	@RequestMapping("goodsUpdateReport")
	public ModelAndView goodsUpdateReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("marketing/operateReport/goodsUpdate");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 产品更新表:列表
	 */
	@RequestMapping("goodsUpdateReportList")
	@ResponseBody
	public String goodsUpdateReportList(HttpServletRequest request, HttpServletResponse response,
			GoodsUpdateReportExt report) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<GoodsUpdateReportExt> qm = new QueryModel<GoodsUpdateReportExt>(report, start, limit, orderName,
				orderType);
		PageModel<GoodsUpdateReport> pm = operationReportService.searchGoodsUpdateReportList(qm, 1,account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 产品更新表:导出
	 */
	@RequestMapping("exportGoodsUpdateReport")
	@ResponseBody
	public String exportGoodsUpdateReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
		String name = Thread.currentThread().getName();// 获取当前执行线程
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportGoodsUpdateReport_"+uuid, "营销中心-数据管理-产品更新表",8);
		dynamicTask.startCron(new GoodsUpdateReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 
	 * 供应商销售报表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplierSaleReport")
	public ModelAndView supplierSaleReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("marketing/operateReport/supplierSale");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 
	 * 查询供应商销售报表
	 * 
	 * @param request
	 * @param response
	 * @param startTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchSupplierSaleReport")
	@ResponseBody
	public String searchSupplierSaleReport(HttpServletRequest request, HttpServletResponse response, String startTime,
			String endTime) throws Exception {

		// 查询结果,如果传入时间不为空，则按传入时间查询，否则查询今天
		if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
		} else {
			String yesterDay = CommonUtil.yesterDay();
			startTime = yesterDay;
			endTime = yesterDay;
		}

		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		OperationSupplierSaleRF reportForms = new OperationSupplierSaleRF();
		reportForms.setStartTime(startTime);
		reportForms.setEndTime(endTime);
		QueryModel<OperationSupplierSaleRF> qm = new QueryModel<OperationSupplierSaleRF>(reportForms, start, limit,
				orderName, orderType);
		PageModel<OperationSupplierSaleRF> pm = operationReportService.searchSupplierSaleList(qm,account.getPk());

		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出供应商销售报表
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportSupplierSaleReport")
	@ResponseBody
	public String exportSupplierSaleReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
		String name = Thread.currentThread().getName();// 获取当前执行线程
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
	    operationManageService.saveReportExcelToOss(params, account, "exportSupplierSaleReport_"+uuid, "营销中心-数据管理-供应商销售报表",9);
		dynamicTask.startCron(new SupplierSaleReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 业务员交易明细:页面
	 */
	@RequestMapping("salemanSaleDetailReport")
	public ModelAndView salemanSaleDetailReport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/operateReport/salemanSaleDetail");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 业务员交易明细:列表
	 * 
	 * @param request
	 * @param response
	 * @param report
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("salemanSaleDetailReportList")
	@ResponseBody
	public String salemanSaleDetailReportList(HttpServletRequest request, HttpServletResponse response,
			SalemanSaleDetailReportExt report) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "date");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<SalemanSaleDetailReportExt> qm = new QueryModel<SalemanSaleDetailReportExt>(report, start, limit,
				orderName, orderType);
		PageModel<SalemanSaleDetailReport> pm = operationReportService.searchSalemanSaleList(qm, 1,account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 业务员交易明细:导出
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportsalemanSaleDetailReport")
	@ResponseBody
	public String exportsalemanSaleDetailReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
		ManageAccount account = getAccount(request);
		String uuid = KeyUtils.getUUID();
		String name = Thread.currentThread().getName();// 获取当前执行线程
	    operationManageService.saveReportExcelToOss(params, account, "exportsalemanSaleDetailReport_"+uuid, "营销中心-数据管理-业务员交易明细",10);
		dynamicTask.startCron(new SalemanSaleDetailReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 采购商交易统计
	 * 
	 * @param request
	 * @param response
	 * @param years
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("purchaserSaleReport")
	public ModelAndView purchaserSaleReport(HttpServletRequest request, HttpServletResponse response, String years)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/operateReport/purchaserSale");
		mav.addObject("year", new SimpleDateFormat("yyyy").format(new Date()));
		return mav;
	}

	/**
	 * 查询采购商交易统计
	 * 
	 * @param request
	 * @param response
	 * @param year
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchPurchaserSaleList")
	@ResponseBody
	public String searchPurchaserSaleList(HttpServletRequest request, HttpServletResponse response, String year)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		PurchaserSaleReportForms reportForms = new PurchaserSaleReportForms();
		if (year == null || "".equals(year)) {
			year = new SimpleDateFormat("yyyy").format(new Date());
		}
		reportForms.setYear(year);
		QueryModel<PurchaserSaleReportForms> qm = new QueryModel<PurchaserSaleReportForms>(reportForms, start, limit,
				orderName, orderType);
		PageModel<PurchaserSaleReportForms> pm = operationReportService.searchPuchaserSaleList(qm,account.getPk());
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 导出采购商交易统计报表
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("exportPurchaserSaleList")
	@ResponseBody
	public String exportPurchaserSaleList(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params)
			throws Exception {
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		String name = Thread.currentThread().getName();// 获取当前执行线程
	    operationManageService.saveReportExcelToOss(params, account, "exportPurchaserSaleList_"+uuid, "营销中心-数据管理-采购商交易统计",11);
		dynamicTask.startCron(new PurchaserSaleReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;  
	}

	/**
	 * 供应商销售数据：页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplierSaleDataReport")
	public ModelAndView supplierSaleDataReport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/operateReport/supplierSaleData");
		mav.addObject("startTime", CommonUtil.yesterDay());
		mav.addObject("endTime", CommonUtil.yesterDay());
		return mav;
	}

	/**
	 * 供应商销售数据:列表
	 * 
	 * @param request
	 * @param response
	 * @param report
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplierSaleDataReportList")
	@ResponseBody
	public String supplierSaleDataReportList(HttpServletRequest request, HttpServletResponse response,
			SupplierSaleDataReportExt report) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<SupplierSaleDataReportExt> qm = new QueryModel<SupplierSaleDataReportExt>(report, start, limit,
				orderName, orderType);
		PageModel<SupplierSaleDataReport> pm = operationReportService.supplierSaleDataReportList(qm, 1,account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 供应商销售数据:导出
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("exportSupplierSaleDataReport")
	@ResponseBody
	public String exportSupplierSaleDataReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params) throws Exception {
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//		ManageAccount account = getAccount(request);
//		QueryModel<SupplierSaleDataReportExt> qm = new QueryModel<SupplierSaleDataReportExt>(report, 0, 0, orderName,
//				orderType);
//		PageModel<SupplierSaleDataReport> pm = operationReportService.supplierSaleDataReportList(qm, 2,account.getPk());
//		ExportUtil<SupplierSaleDataReport> export = new ExportUtil<SupplierSaleDataReport>();
//		export.exportUtil("supplierSaleData", pm.getDataList(), response, request);
//		return null;
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		String name = Thread.currentThread().getName();// 获取当前执行线程
	    operationManageService.saveReportExcelToOss(params, account, "exportSupplierSaleDataReport_"+uuid, "营销中心-数据管理-供应商销售数据",12);
		dynamicTask.startCron(new SupplierSaleDataReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG; 
	}

	/**
	 * 
	 * 平台销售数据
	 * 
	 * @param request
	 * @param response
	 * @param years
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("shopSaleDataReport")
	public ModelAndView shopSaleDataReport(HttpServletRequest request, HttpServletResponse response, String years)
			throws Exception {
		ModelAndView mav = new ModelAndView("marketing/operateReport/shopSaleData");
		Map<String, Object> map = new HashMap<String, Object>();
		if (years == null || "".equals(years)) {
			years = new SimpleDateFormat("yyyy").format(new Date());
		}
		map.put("year", years);
		Map<String, Object> resultMap = operationReportService.searchSupplierSaleData(map);
		if (resultMap != null) {
			mav.addObject("list", resultMap.get("list"));
			mav.addObject("totalData", resultMap.get("totalData"));
		} else {
			mav.addObject("list", null);
			mav.addObject("totalData", null);
		}
		mav.addObject("year", years);
		return mav;
	}

	/**
	 * 导出平台销售数据
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("exportShopSaleDataReport")
	@ResponseBody
	public String exportShopSaleDataReport(HttpServletRequest request, HttpServletResponse response,
			ReportFormsDataTypeParams params)
			throws Exception {
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		String name = Thread.currentThread().getName();// 获取当前执行线程
	    operationManageService.saveReportExcelToOss(params, account, "exportShopSaleDataReport_"+uuid, "营销中心-数据管理-平台销售数据",13);
		dynamicTask.startCron(new ShopSaleDataReportRunnable(name,uuid), name);
		return Constants.EXPORT_MSG; 
	}

	public void exportExcelReport(String name, String years, List<SupplierSaleDataReportForms> list,
			SupplierSaleTotalData totalData, HttpServletResponse response, HttpServletRequest request) {

		URL url = this.getClass().getClassLoader().getResource("template");
		String templateFileNameFilePath = url.getPath() + "/" + name + ".xls"; // 模板路径
		String tempPath = PropertyConfig.getProperty("FILE_PATH");
		File file = new File(tempPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		String fileName = name + DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss") + ".xls";
		String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("totalData", totalData);
		beans.put("year", years);

		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		FileInputStream is = null;
		OutputStream out = null;
		try {
			transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			is = new FileInputStream(destFileNamePath);
			// 3.通过response获取ServletOutputStream对象(out)
			int len = 0;
			byte[] buffer = new byte[1024];
			out = response.getOutputStream();
			while ((len = is.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			is.close();
			out.close();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
