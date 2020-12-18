package cn.cf.controller.logistics;

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
import cn.cf.dto.PurchaserInvoiceDto;
import cn.cf.dto.SettleAccountDto;
import cn.cf.dto.SupplierInvoiceDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.model.SearchInvoice;
import cn.cf.service.logistics.PurchaserInvoiceService;
import cn.cf.service.logistics.SupplierInvoiceService;
import cn.cf.service.logistics.SupplierSettleAccountService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.logistics.PurchaserInvoiceFormRunnable;
import cn.cf.task.schedule.logistics.SupplierInvoiceFormRunnable;
import cn.cf.task.schedule.logistics.SupplierSettleFormRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
/**
 * 财务管理
 * @author xht
 *
 */
@Controller
@RequestMapping("/")
public class InvoiceManageController extends BaseController {
	
	
	@Autowired
	private  SupplierInvoiceService  supplierInvoiceService;
	
	@Autowired
	private  SupplierSettleAccountService  supplierSettleAccountService;
	
	
	@Autowired
	private PurchaserInvoiceService purchaserInvoiceService;
	
	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private DynamicTask dynamicTask;
	
	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("supplierInvoiceManager")
	public ModelAndView supplierInvoiceManager() {
		ModelAndView mav = new ModelAndView("logistics/supplierInvoice");
		return mav;
	}

	/**
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping("supplierSettleAccount")
	public ModelAndView supplierSettleAccount() {
		ModelAndView mav = new ModelAndView("logistics/supplierSettleAccount");
		return mav;
	}

	/**
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping("purchaserInvoiceManager")
	public ModelAndView purchaserInvoiceManager() {
		ModelAndView mav = new ModelAndView("logistics/purchaserInvoice");
		return mav;
	}

	
	/**
	 * 供应商：列表展示
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("supplierInvoice_data")
	@ResponseBody
	public String supplierInvoice_data(HttpServletRequest request, HttpServletResponse response, SearchInvoice searchInvoice) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		searchInvoice.setAccountPk(account.getPk());
		QueryModel<SearchInvoice> qm = new QueryModel<SearchInvoice>(searchInvoice, start, limit, orderName, orderType);
		PageModel<SupplierInvoiceDto> pm = supplierInvoiceService.searchSupplierInvoiceList(qm,1);
		return JsonUtils.convertToString(pm);
	}


	/**
	 * 编辑/新增供应商发票
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("updateSupplierInvoice")
	@ResponseBody
	public String updateSupplierInvoice(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		Integer supplierInvoiceStatus = ServletUtils.getIntParameterr(request, "supplierInvoiceStatus", null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", pk);
		map.put("supplierInvoiceStatus", supplierInvoiceStatus);
		int retVal = supplierInvoiceService.updateSupplierInvoice(map);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	
	/**
	 * 导出供应商发票
	 * @param request
	 * @param response
	 * @param lgOrderSearchModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "exportSupplierInvoiceForm")
	@ResponseBody
	public String exportSupplierInvoiceForm(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) throws Exception {
//		if(searchInvoice == null){
//			searchInvoice = new SearchInvoice();
//		}
//		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
//		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
//		  ManageAccount account = getAccount(request);
//	        searchInvoice.setAccountPk(account.getPk());
//		QueryModel<SearchInvoice> qm = new QueryModel<SearchInvoice>(searchInvoice, 0, 0, orderName, orderType);
//		PageModel<SupplierInvoiceDto> pm = supplierInvoiceService.searchSupplierInvoiceList(qm,2);
//		ExportUtil<SupplierInvoiceDto> export = new ExportUtil<SupplierInvoiceDto>();
//	    export.exportUtil("supplierInvoiceForm", pm.getDataList(), response,request);
		String uuid = KeyUtils.getUUID();
	    
	    ManageAccount account = getAccount(request);
	    operationManageService.saveCustomerExcelToOss(params, account, "exportSupplierInvoiceForm_"+uuid, "物流中心-财务管理-发票管理（供应商）",1);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new SupplierInvoiceFormRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
	}
	

	

	/**
	 * 物流供应商结算：列表展示
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("supplierSettleAccount_data")
	@ResponseBody
	public String supplierSettleAccount_data(HttpServletRequest request, HttpServletResponse response, SearchInvoice searchInvoice) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
        searchInvoice.setAccountPk(account.getPk());
		QueryModel<SearchInvoice> qm = new QueryModel<SearchInvoice>(searchInvoice, start, limit, orderName, orderType);
		PageModel<SettleAccountDto> pm = supplierSettleAccountService.searchSupplierSettleAccountList(qm,1);
		return JsonUtils.convertToString(pm);
	}

	
	/**
	 * 更新：结算
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("updateSettleFreight")
	@ResponseBody
	public String updateSettleFreight(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		Integer isSettleFreight = ServletUtils.getIntParameterr(request, "isSettleFreight", null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", pk);
		map.put("isSettleFreight", isSettleFreight);
		int retVal = supplierSettleAccountService.updateSettleFreight(map);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	
	
	
	
	/**
	 * 导出：物流供应商结算列表
	 * @param request
	 * @param response
	 * @param lgOrderSearchModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "exportSettleForm")
	@ResponseBody
	public String exportSettleForm(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) throws Exception {
		ManageAccount account = getAccount(request);
		String uuid = KeyUtils.getUUID();
		operationManageService.saveCustomerExcelToOss(params, account, "exportSettleForm_"+uuid, "物流中心-财务管理-物流供应商结算",2);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new SupplierSettleFormRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
	}
	

	/**
	 * 采购商：列表展示
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("purchaserInvoice_data")
	@ResponseBody
	public String purchaserInvoice_data(HttpServletRequest request, HttpServletResponse response,
			SearchInvoice searchInvoice) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		searchInvoice.setAccountPk(account.getPk());
		QueryModel<SearchInvoice> qm = new QueryModel<SearchInvoice>(searchInvoice, start, limit, orderName, orderType);
		PageModel<PurchaserInvoiceDto> pm = purchaserInvoiceService.searchPurchaserInvoiceList(qm,1);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 开票
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("updateInvoice")
	@ResponseBody
	public String updateInvoice(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		Integer memberInvoiceStatus = ServletUtils.getIntParameterr(request, "memberInvoiceStatus", null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", pk);
		map.put("memberInvoiceStatus", memberInvoiceStatus);
		int retVal = purchaserInvoiceService.updateInvoice(map);
		String msg = "";
		if (retVal > 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	/**
     * 导出采购商发票
     * 
     * @param request
     * @param response
     * @param lgOrderSearchModel
     * @return
     * @throws Exception
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "exportPurchaserInvoiceForm")
	@ResponseBody
	public String exportPurchaserInvoiceForm(HttpServletRequest request, HttpServletResponse response,
			CustomerDataTypeParams params) throws Exception {
		String uuid = KeyUtils.getUUID();
		 ManageAccount account = getAccount(request);
	    operationManageService.saveCustomerExcelToOss(params, account, "exportPurchaserInvoiceForm_"+uuid, "物流中心-财务管理-发票管理（采购商）",3);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new PurchaserInvoiceFormRunnable(name,uuid), name);
		return Constants.EXPORT_MSG;
	}
}
