/**
 * 
 */
package cn.cf.controller.operation;

import cn.cf.PageModel;
import cn.cf.common.*;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dto.*;
import cn.cf.entity.B2bFutures;
import cn.cf.entity.B2bFuturesEx;
import cn.cf.entity.B2bInvoiceEntity;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.json.ExtTreeNode;
import cn.cf.json.ExtTreeState;
import cn.cf.json.JsonUtils;
import cn.cf.model.*;
import cn.cf.property.PropertyConfig;
import cn.cf.service.operation.*;
import cn.cf.service.yarn.YarnHomeDisplayService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.chemifiber.OrderGoodsRunnable;
import cn.cf.task.schedule.chemifiber.OrderRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author why 运营管理
 */
@Controller
@RequestMapping("/")
public class OperationManageController extends BaseController {

	@Autowired
	private OperationManageService operationManageService;

	@Autowired
	private GoodsManageService goodsManageService;

	@Autowired
	SysRegionsService sysRegionsService;

	@Autowired
	private B2bSpecHotService b2bSpecHotService;

	@Autowired
	private AddMemberSysPoint addMemberSysPoint;

	@Autowired
	private B2bFuturesService b2bFuturesService;
	
   @Autowired
    private YarnHomeDisplayService  yarnHomeDisplayService;

	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 厂家品牌页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("goodsBrand")
	public ModelAndView goodsBrand(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/goodsBrand");
		mav.addObject("storeList", operationManageService.searchStoreList());
		mav.addObject("brandList", operationManageService.searchBrandList());// 品牌
		return mav;
	}

	/**
	 * 厂家品牌查询
	 * 
	 * @param request
	 * @param response
	 * @param b2bGoodsBrandExtDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("searchGoodsBrandList")
	public String searchGoodsBrandList(HttpServletRequest request, HttpServletResponse response,
			B2bGoodsBrandExtDto b2bGoodsBrandExtDto) throws Exception {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bGoodsBrandExtDto> qm = new QueryModel<B2bGoodsBrandExtDto>(b2bGoodsBrandExtDto, start, limit,
				orderName, orderType);
		PageModel<B2bGoodsBrandExtDto> pm = operationManageService.searchGoodsBrandList(qm,account);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 修改厂家品牌，包括审核
	 * 
	 * @param request
	 * @param response
	 * @param b2bGoodsBrandExtDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("updateGoodsBrand")
	public String updateGoodsBrand(HttpServletRequest request, HttpServletResponse response,
			B2bGoodsBrandExtDto b2bGoodsBrandExtDto) throws Exception {
		return operationManageService.updateGoodsBrand(b2bGoodsBrandExtDto);
	}

	/**
	 * 新增厂家品牌
	 * 
	 * @param request
	 * @param response
	 * @param b2bGoodsBrand
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("insertGoodsBrand")
	public String insertGoodsBrand(HttpServletRequest request, HttpServletResponse response,
			B2bGoodsBrandExtDto b2bGoodsBrand) throws Exception {
		return operationManageService.insertGoodsBrand(b2bGoodsBrand);
	}

	/**
	 * 订单显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("order")
	public ModelAndView order(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/order");
		mav.addObject("paymentList", operationManageService.getPaymentList());
		return mav;
	}

	/**
	 * 查询订单
	 * 
	 * @param request
	 * @param response
	 * @param b2bOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchOrderList")
	public String searchOrderList(HttpServletRequest request, HttpServletResponse response, B2bOrderExtDto b2bOrder) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		ManageAccount account = this.getAccount(request);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, start, limit, orderName, orderType);
		PageModel<B2bOrderExtDto> pm = operationManageService.searchOrderList(qm,account);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 订单合同导出PDF文档
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/uploadPDF", method = RequestMethod.GET)
	public String uploadPDF(HttpServletRequest req, HttpServletResponse resp, String orderNumber,String block) {
		try {
			if (Constants.BLOCK_CF.equals(block)) {
				operationManageService.exportOrderPDF(orderNumber, resp, req);
			}
			if (Constants.BLOCK_SX.equals(block)) {
				operationManageService.exportYarnOrderPDF(orderNumber, resp, req);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传付款凭证
	 * 
	 * @param request
	 * @param response
	 * @param b2bOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadOrderPaymentVoucher")
	public String uploadOrderPaymentVoucher(HttpServletRequest request, HttpServletResponse response,
			B2bOrderExtDto b2bOrder) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (b2bOrder != null) {
			try {
				operationManageService.exportPaymentVoucher(b2bOrder);
				msg = Constants.RESULT_SUCCESS_MSG;
			} catch (Exception e) {
				msg = Constants.RESULT_EXCEPTION_MSG;
			}
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 关闭订单 wangcheng
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("closeOrder")
	@ResponseBody
	public String closeOrder(HttpServletRequest request, HttpServletResponse response) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber", null);
		String closeReason = ServletUtils.getStringParameter(request, "closeReason", null);
		String msg = "";
		if (orderNumber == null || orderNumber.equals("")) {
			msg = Constants.RESULT_SUCCESS_MSG;
			return msg;
		}
		int retVal = operationManageService.closeOrder(orderNumber,closeReason);
		if (retVal == 1) {// 操作成功
			msg = Constants.RESULT_SUCCESS_MSG;
			// 会员体系，减去积分
			try {
				addMemberSysPoint.cancelOrder(orderNumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// 操作失败
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 查询订单商品集合
	 * 
	 * @param request
	 * @param orderNumber
	 * @return
	 */
	@RequestMapping("searchOrderGoodsList")
	@ResponseBody
	public String searchOrderGoodsList(HttpServletRequest request, B2bOrderGoodsExtDto dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = this.getAccount(request);
		dto.setAccountPk(account.getPk());
		QueryModel<B2bOrderGoodsExtDto> qm = new QueryModel<B2bOrderGoodsExtDto>(dto, start, limit, orderName,
				orderType);
		PageModel<B2bOrderGoodsExtDto> pm = operationManageService.searchOrderGoodsList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 导出订单商品之前先查询，如果超过5000条，提示导出小于5000
	 * 
	 * @param request
	 * @param response
	 * @param b2bOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getExportOrderGoodsNumbers")
	public String getExportOrderGoodsNumbers(HttpServletRequest request, HttpServletResponse response,
			B2bOrderExtDto b2bOrder) {
		ManageAccount account = this.getAccount(request);
		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, 0, 0, null, null);
		List<B2bOrderExtDto> list = operationManageService.exportOrderGoodsList(qm,account);
		if (list.size() > 5000) {
			return "fail";
		} else {
			return "success";
		}
	}

	/**
	 * 导出订单商品
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportOrderGoods")
	@ResponseBody
	public String exportOrderGoods(HttpServletRequest request, HttpServletResponse response,
								   OrderDataTypeParams params) throws Exception {
		ManageAccount account = this.getAccount(request);
//		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, 0, 0, null, null);
		//List<B2bOrderExtDto> list = operationManageService.exportOrderGoodsList(qm,account);
//		ExportUtil<B2bOrderExtDto> export = new ExportUtil<B2bOrderExtDto>();
//		String exportFile = "";
//		if (Constants.BLOCK_CF.equals(b2bOrder.getBlock())){
//			exportFile = "orderGoods";
//		}
//		if (Constants.BLOCK_SX.equals(b2bOrder.getBlock())){
//			exportFile = "yarnOrderGoods";
//		}
//		export.exportUtil(exportFile, list, response, request);

		//用线程跑订单导出数据

		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		operationManageService.saveExcelToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new OrderGoodsRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 订单导出
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportOrder")
	@ResponseBody
	public String exportOrder(HttpServletRequest request, HttpServletResponse response, OrderDataTypeParams params)
			throws Exception {

		ManageAccount account = this.getAccount(request);
//		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, 0, 0, null, null);
		//List<B2bOrderExtDto> list = operationManageService.exportOrderList(qm,account,2);
//		ExportUtil<B2bOrderExtDto> export = new ExportUtil<B2bOrderExtDto>();
//		String exportFile = "";
//
//		if (Constants.BLOCK_CF.equals(b2bOrder.getBlock())){
//			exportFile = "order";
//		}
//		if (Constants.BLOCK_SX.equals(b2bOrder.getBlock())){
//			exportFile = "yarnOrder";
//		}
//		export.exportUtil(exportFile, list, response, request);
		String uuid = KeyUtils.getUUID();
		params.setUuid(uuid);
		operationManageService.saveOrderGoodsExcelToOss(params,account);
		String name = Thread.currentThread().getName();//获取当前执行线程
		dynamicTask.startCron(new OrderRunnable(name,uuid),name);
		return Constants.EXPORT_MSG;
	}



	/**
	 * 导出订单之前先查询，如果超过5000条，提示导出小于5000
	 * 
	 * @param request
	 * @param response
	 * @param b2bOrder
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("getExportOrderNumbers")
	public String getExportOrderNumbers(HttpServletRequest request, HttpServletResponse response,
			B2bOrderExtDto b2bOrder) {
		ManageAccount account = this.getAccount(request);
		QueryModel<B2bOrderExtDto> qm = new QueryModel<B2bOrderExtDto>(b2bOrder, 0, 0, null, null);
		List<B2bOrderExtDto> list = operationManageService.exportOrderList(qm,account,1);
		if (list.size() > 5000) {
			return "fail";
		} else {
			return "success";
		}
	}*/
	/**
	 * banner图列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("banner")
	public ModelAndView banner(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/banner");
		return mav;
	}

	/**
	 * 更新banner图列表
	 * 
	 * @param banner
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateBanner")
	public String updateBanner(SysBanner banner) {
		int result = operationManageService.updateBanner(banner);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 新增banner图列表
	 * 
	 * @param banner
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertBanner")
	public String insertBanner(SysBanner banner) {
		int result = operationManageService.insertBanner(banner);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 查询banner图列表
	 * 
	 * @param request
	 * @param banner
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchBannerList")
	public String searchBannerList(HttpServletRequest request, SysBannerExtDto banner) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<SysBannerExtDto> qm = new QueryModel<SysBannerExtDto>(banner, start, limit, orderName, orderType);
		PageModel<SysBannerExtDto> pm = operationManageService.searchBannerList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 供应商推荐页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("supplierRecommed")
	public ModelAndView supplierRecommed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/supplierRecommed");

		//默认进入列表传化纤
		// 推荐所有总公司
//		mav.addObject("companyList", goodsManageService.getB2bCompayDtoByType(2, "-1","cf"));
//		mav.addObject("productList",goodsManageService.getB2bProductList());// 品名
		mav.addObject("storeList", operationManageService.searchAllStoreList());
		mav.addObject("brandList", operationManageService.searchBrandList());// 品牌
		mav.addObject("disGoodsBrandList", operationManageService.searchDistinctGoodsBrandList());// 品牌关联店铺
		return mav;
	}


	@ResponseBody
	@RequestMapping("getAllStoreList")
	public String getAllStoreList() {
		return JsonUtils.convertToString(operationManageService.searchAllStoreList());
	}

	@ResponseBody
	@RequestMapping("getAllBrandList")
	public String getAllBrandList() {

		return JsonUtils.convertToString(operationManageService.searchBrandList());
	}

	@ResponseBody
	@RequestMapping("getGoodsBrandListByBrandPk")
	public String getGoodsBrandListByBrandPk(String brandPk) {

		return JsonUtils.convertToString(operationManageService.searchGoodsBrandListByBrandPk(brandPk));
	}


	/**
	 * 根据block获取需要的品名或原料
	 *
	 * @param block
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getProductNameOrRawmaterial")
	public String getProductNameOrRawmaterial(String block) {
		if (Constants.BLOCK_CF.equals(block)){
			return JsonUtils.convertToString(goodsManageService.getB2bProductList());

		}else{
			return JsonUtils.convertToString(yarnHomeDisplayService.getNoIsVisMaterialList("-1"));
		}
	}

	/**
	 * 根据block获取需要的公司
	 *
	 * @param block
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCompanyByBlock")
	public String getCompanyByBlock(String block) {
		return JsonUtils.convertToString(goodsManageService.getB2bCompayDtoByType(2, "-1",block));
	}

	/**
	 * 修改供应商推荐
	 * 
	 * @param supplierRecommed
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSupplierRecommed")
	public String SaveSupplierRe(SysSupplierRecommedExtDto supplierRecommed) {

		int result = operationManageService.updateSupplierRecommed(supplierRecommed);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = Constants.SUPPLIER_RECOMMED_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 修改供应商推荐
	 *
	 * @param supplierRecommed
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSupplierRecommedStatus")
	public String updateSupplierRecommedStatus(SysSupplierRecommedExtDto supplierRecommed) {

		int result = operationManageService.updateSupplierRecommedStatus(supplierRecommed);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping("insertSupplierRecommed")
	public String insertSupplierRecommed(SysSupplierRecommed supplierRecommed) {

		int result = operationManageService.insertSupplierRecommed(supplierRecommed);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (result == 2) {
			msg = Constants.SUPPLIER_RECOMMED_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	@ResponseBody
	@RequestMapping("searchSupplierRecommedList")
	public String searchSupplierRecommedList(HttpServletRequest request, HttpServletResponse response,
			SysSupplierRecommedExtDto sysSuRe) {
		ManageAccount account = this.getAccount(request);
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<SysSupplierRecommedExtDto> qm = new QueryModel<SysSupplierRecommedExtDto>(sysSuRe, start, limit,
				orderName, orderType);
		PageModel<SysSupplierRecommedExtDto> pm = operationManageService.searchSupplierRecommedList(qm,account);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 物流方式
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logisticsModel")
	public ModelAndView logistics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/logisticsModel");
		return mav;
	}

	/**
	 * 物流方式列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("searchLogisticsModelList")
	public String logisticsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		LogisticsModelDto dto = new LogisticsModelDto();
		QueryModel<LogisticsModelDto> qm = new QueryModel<LogisticsModelDto>(dto, start, limit, orderName, orderType);
		PageModel<LogisticsModelDto> pm = operationManageService.searchLogisticsModelList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 启用/禁用
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("updateLogisticsModel")
	@ResponseBody
	public String updateLogisticsModel(HttpServletRequest request, LogisticsModel model) {

		int retVal = operationManageService.updateLogisticModel(model);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 付款方式页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("payment")
	public ModelAndView payment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/payment");
		return mav;
	}

	/**
	 * 付款方式列表
	 * 
	 * @param request
	 * @param response
	 * @param b2bPayment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("searchPaymentList")
	@ResponseBody
	public String paymentdata(HttpServletRequest request, HttpServletResponse response, B2bPaymentDto b2bPayment)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "pk");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<B2bPaymentDto> qm = new QueryModel<B2bPaymentDto>(b2bPayment, start, limit, orderName, orderType);
		PageModel<B2bPaymentDto> pm = operationManageService.searchb2bPaymentList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	@RequestMapping("updatePayment")
	@ResponseBody
	public String updatePayment(B2bPayment b2bPayment) {

		int retVal = operationManageService.updatePayment(b2bPayment);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 角色管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("b2bRole")
	public ModelAndView b2bRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/b2bRole");

		return mav;
	}

	/**
	 * 角色管理列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("b2bRoleGrid")
	@ResponseBody
	public String b2bRoleGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		B2bRoleDto entity = new B2bRoleDto();
		QueryModel<B2bRoleDto> qm = new QueryModel<B2bRoleDto>(entity, start, limit, orderName, orderType);
		PageModel<B2bRoleDto> pm = operationManageService.searchB2bRoleGrid(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 角色目录树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("b2bRoleMenuTree")
	@ResponseBody
	public String b2bRoleMenuTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = ServletUtils.getStringParameter(request, "id", "-1");
		String RolePk = ServletUtils.getStringParameter(request, "pk", " ");
		Integer companyType = ServletUtils.getIntParameter(request, "companyType", 1);
		ExtTreeNode tree = new ExtTreeNode();
		tree.setId(id);
		tree.setText("全部");
		List<B2bRoleMenuDto> list = null;
		if (null != RolePk && !"".equals(RolePk.trim())) {
			list = operationManageService.getb2broleMenuByRolepk(RolePk);
		}
		List<ExtTreeNode> tlist = this.getChildren(id, companyType, list);
		if (null != tlist && tlist.size() > 0) {
			tree.setChildren(tlist);
		}
		String json = JsonUtils.convertToString(tree);
		return json;
	}

	/**
	 * 保存角色权限
	 * 
	 * @param role
	 * @param node
	 * @return
	 */
	@RequestMapping("saveB2bRole")
	@ResponseBody
	public String saveB2bRole(B2bRole role, String node) {
		operationManageService.saveB2bRole(role, node);
		return JsonUtils.convertToString(1);
	}

	/**
	 * 地区管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("regionsManage")
	public ModelAndView regionsManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("operation/opermg/b2bRegions");
		return view;
	}

	@RequestMapping("b2bRegionsManageMenuTree")
	@ResponseBody
	public String regionsManageMenuTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = ServletUtils.getStringParameter(request, "id", "-1");
		ExtTreeNode tree = new ExtTreeNode();
		tree.setId(id);
		tree.setText("全部地区");

		List<ExtTreeNode> tlist = this.getRegionsChildrenToMongo("-1");// 根节点
		if (null != tlist && tlist.size() > 0) {
			tree.setChildren(tlist);
		}
		return JsonUtils.convertToString(tree);
	}

	/**
	 * 保存地区到mongo
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveB2bRegionsToMongo")
	@ResponseBody
	public String saveB2bRegionsToMongo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			uploadFile(getRegionsByparentPk("-1"));
			return RestCode.CODE_0000.toJson();
		} catch (Exception e) {
			return RestCode.CODE_S999.toJson();
		}
	}

	private void uploadFile(String sourceString) {
		byte[] sourceByte = sourceString.getBytes();
		if (null != sourceByte) {
			try {
				URL url = this.getClass().getClassLoader().getResource("template");
				String filePath = url.getPath() + "regions.json"; // 文件路径
				File file = new File(filePath);
				if (!file.exists()) { // 文件不存在则创建文件，先创建目录
					File dir = new File(file.getParent());
					dir.mkdirs();
					file.createNewFile();
				}
				FileOutputStream outStream = new FileOutputStream(file); // 文件输出流用于将数据写入文件
				@SuppressWarnings("resource")
				OutputStreamWriter osw = new OutputStreamWriter(outStream, "UTF-8");
				osw.write(sourceString);
				osw.flush();
				OSSUtils.ossRegionsUpload(file, Constants.JSON);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getRegionsByparentPk(String parentPk) {
		JSONArray js = new JSONArray();
		List<SysRegionsDto> plist = sysRegionsService.getByParentPk(parentPk);
		if (null != plist && plist.size() > 0) {
			JSONObject json = new JSONObject();
			String children = "";
			for (SysRegionsDto d : plist) {
				json = new JSONObject();
				json.put("id", d.getPk());
				json.put("name", d.getName());
				children = this.getRegionsByparentPk(d.getPk());
				if (null != children && !"".equals(children)) {
					json.put("children", children);
				}
				js.add(json);
			}
		}
		String str = js.toString();
		return str;
	}

	/**
	 * 更新地区管理树
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateRegionsManageMenuTree")
	@ResponseBody
	public String updateRegionsManageMenuTree(HttpServletRequest request, HttpServletResponse response,
			SysRegionsDto dto) throws Exception {

		int result = 0;
		String msg = RestCode.CODE_S999.toJson();
		// 删除数据时
		if (dto.getIsDelete() == 2) {
			SysRegionsDto sysDto = sysRegionsService.getbyPk(dto.getPk());
			if (sysDto != null) {
				SysRegions sys = new SysRegions();
				sys.setPk(sysDto.getPk());
				sys.setParentPk(sysDto.getParentPk());
				sys.setName(sysDto.getName());
				sys.setIsDelete(2);
				sys.setIsVisable(2 + "");
				result = sysRegionsService.update(sys);
				if (result == 1) {
					msg = RestCode.CODE_0000.toJson();
				}
			}
		} else {
			SysRegionsDto ndto = sysRegionsService.getbyName(dto.getName());
			if (ndto == null) {
				SysRegions model = new SysRegions();
				int maxPk = sysRegionsService.getMaxRegionsPk();
				model.setPk(maxPk + 1 + "");
				model.setIsDelete(1);
				model.setIsVisable(1 + "");
				model.setName(dto.getName());
				model.setParentPk(dto.getParentPk());
				result = sysRegionsService.add(model);
				// 新增数据数据更新到mongo
				if (result == 1) {
					msg = RestCode.CODE_0000.toJson();
				}
			}

			if (ndto != null && ndto.getIsDelete() == 2) {
				// 如果不等于null，判断是否状态是删除，如果是修改状态
				SysRegions updateModel = new SysRegions();
				updateModel.setPk(ndto.getPk());
				updateModel.setIsDelete(1);
				updateModel.setIsVisable("1");
				updateModel.setName(ndto.getName());
				updateModel.setParentPk(ndto.getParentPk());
				result = sysRegionsService.update(updateModel);
				if (result == 1) {
					msg = RestCode.CODE_0000.toJson();
				}
			}
		}
		return msg;
	}

	/**
	 * 地区json串
	 * 
	 * @return
	 */
	private List<ExtTreeNode> getRegionsChildrenToMongo(String pk) {

		List<ExtTreeNode> treeList = new ArrayList<ExtTreeNode>();
		ExtTreeNode node = null;
		ExtTreeState state = new ExtTreeState();

		List<SysRegionsDto> allList = sysRegionsService.getByParentPk(pk);
		for (SysRegionsDto b2bSysRegions : allList) {
			node = new ExtTreeNode();
			state.setOpened(false);
			node.setId(b2bSysRegions.getPk());
			node.setText(b2bSysRegions.getName());
			node.setSrcObj(b2bSysRegions);
			node.setState(state);
			List<ExtTreeNode> plist = this.getRegionsChildrenToMongo(b2bSysRegions.getPk());
			if (plist != null && plist.size() > 0) {
				node.setChildren(plist);
			}
			treeList.add(node);
		}
		return treeList;
	}

	private List<ExtTreeNode> getChildren(String id, Integer companyType, List<B2bRoleMenuDto> slist) {
		List<B2bMenuDto> list = operationManageService.getB2bMenuByParentPk(id, companyType);
		List<ExtTreeNode> tlist = new ArrayList<ExtTreeNode>();
		ExtTreeNode tree = null;
		for (B2bMenuDto dto : list) {
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
				for (B2bRoleMenuDto d : slist) {
					if (dto.getPk().equals(d.getMenuPk())) {
						ExtTreeState ex = new ExtTreeState();
						ex.setSelected(true);
						tree.setState(ex);
					}
				}
			}
			tree.setSrcObj(dto);
			List<ExtTreeNode> tl = this.getChildren(dto.getPk(), companyType, slist);
			if (null != tl && tl.size() > 0) {
				tree.setChildren(tl);
			}
			tlist.add(tree);
		}
		return tlist;
	}

	/**
	 * 根据公司Pk查询发票信息
	 * 
	 * @param request
	 * @param companyPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getInvoiceByCompanyPk")
	public String getInvoiceByCompanyPk(HttpServletRequest request, String companyPk) {
		B2bInvoiceEntity extDto = new B2bInvoiceEntity();
		B2bCompanyDto companyDto = operationManageService.getByCompanyPk(companyPk);
		if (companyDto != null) {
			extDto.setArea(companyDto.getArea());
			extDto.setAreaName(companyDto.getAreaName());
			extDto.setCity(companyDto.getCity());
			extDto.setCityName(companyDto.getCityName());
			extDto.setProvince(companyDto.getProvince());
			extDto.setProvinceName(companyDto.getProvinceName());
			extDto.setTaxidNumber(companyDto.getOrganizationCode());
			extDto.setBankName(companyDto.getBankName());
			extDto.setBankAccount(companyDto.getBankAccount());
			extDto.setRegAddress(companyDto.getRegAddress());
			extDto.setRegPhone(companyDto.getContactsTel());
			extDto.setSuccess("true");

			List<SysRegionsDto> list = sysRegionsService.getSysregisByCityList("-1");
			if (list != null && list.size() > 0) {
				extDto.setProvinceList(list);
			}
			return JsonUtils.convertToString(extDto);
		} else {
			return "{\"success\":false}";
		}

	}

	/**
	 * 热门规格
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("specHot")
	public ModelAndView specHot(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;

		String modelType = ServletUtils.getStringParameter(request, "modelType");
		if (modelType.contains("?")) {// 数据库默认
			modelType = modelType.substring(0, modelType.indexOf("?"));
		}
		modelType = Integer.parseInt(modelType)==1 ? "cf":"sx";

		if (Constants.BLOCK_CF.equals(modelType)){
			mav = new ModelAndView("operation/opermg/specHot");
			mav.addObject("productList", goodsManageService.getB2bProductList());// 品名
			mav.addObject("varietiesList", goodsManageService.getB2bVarietiesPidList());// 品种
			mav.addObject("block",modelType);
		}
		if (Constants.BLOCK_SX.equals(modelType)){
			mav = new ModelAndView("yarn/yarnmg/sxspecHot");
			Map<String,Object> map = new HashMap<>();
			map.put("isDelete", Constants.ONE);
			map.put("isVisable", Constants.ONE);
			map.put("parentPk", "-1");
			mav.addObject("noIsVisMaterialList", yarnHomeDisplayService.getMaterialListByGrade(map));// 原料
			mav.addObject("noIsVisTechologyList",  yarnHomeDisplayService.getNoIsVisTechnologyList());//工艺（新增）
			mav.addObject("sxSpecList",  yarnHomeDisplayService.getSxSpecList());//规格查询
			mav.addObject("block",modelType);
		}
		return mav;
	}

	/**
	 * 热门规格列表
	 *
	 * @param request
	 * @param specHotDtoEx
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchSpecHotList")
	public String searchSpecHotList(HttpServletRequest request, HttpServletResponse response,
			B2bSpecHotDtoEx specHotDtoEx) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bSpecHotDtoEx> qm = new QueryModel<B2bSpecHotDtoEx>(specHotDtoEx, start, limit, orderName,
				orderType);
		PageModel<B2bSpecHotDto> pm = b2bSpecHotService.searchList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 新增编辑热门规格
	 * 
	 * @param request
	 * @param specHot
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSpecHot")
	public String updateSpecHot(HttpServletRequest request, B2bSpecHot specHot) {
		return b2bSpecHotService.update(specHot);
	}
	
	/***
	 * 热门规格的状态更新
	 * @param request
	 * @param specHot
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSpecHotStatus")
	public String updateSpecHotStatus(HttpServletRequest request, B2bSpecHot specHot) {
		return b2bSpecHotService.updateSpecHotStatus(specHot);
	}
	/**
	 * 新增编辑热门规格
	 *
	 * @param request
	 * @param pk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delSpecHot")
	public String delSpecHot(HttpServletRequest request, String pk) {
		return b2bSpecHotService.delete(pk);
	}

	/**
	 * 期货管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("futuresPage")
	public ModelAndView futuresPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/futures");
	    String modelType = ServletUtils.getStringParameter(request, "modelType");
	        if (modelType.contains("?")) {// 数据库默认
	            modelType = modelType.substring(0, modelType.indexOf("?"));
	        }
	        String flag = Integer.parseInt(modelType)==1 ? "cf":"sx";
        mav.addObject("modelType",flag);
		mav.addObject("futuresTypeList", b2bFuturesService.searchFuturesTypeList(flag));// 期货品种
		return mav;
	}

	/**
	 * 期货列表
	 * 
	 * @param request
	 * @param response
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("futuresList")
	public String futuresList(HttpServletRequest request, HttpServletResponse response, B2bFuturesEx futureEx)
			throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bFuturesEx> qm = new QueryModel<B2bFuturesEx>(futureEx, start, limit, orderName, orderType);
		PageModel<B2bFutures> pm = b2bFuturesService.searchFuturesList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 编辑/新增期货
	 * 
	 * @param request
	 * @param response
	 * @param future
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("updateFutures")
	public String updateFutures(HttpServletRequest request, HttpServletResponse response, B2bFutures future)
			throws Exception {
		String result = b2bFuturesService.updateFutures(future);
		return result;
	}

	/**
	 * 删除期货
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("delFutures")
	public String delFutures(HttpServletRequest request, HttpServletResponse response, String pk) throws Exception {
		String result = b2bFuturesService.delFutures(pk);
		return result;
	}

	/**
	 * 期货品种管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("futuresTypePage")
	public ModelAndView futuresTypePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/opermg/futuresType");
		  String modelType = ServletUtils.getStringParameter(request, "modelType");
      mav.addObject("modelType",modelType);
		return mav;
	}

	/**
	 * 期货品种列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("futuresTypeList")
	public String futuresTypeList(HttpServletRequest request, HttpServletResponse response,
			B2bFuturesTypeDtoEx futuresType) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<B2bFuturesTypeDtoEx> qm = new QueryModel<B2bFuturesTypeDtoEx>(futuresType, start, limit, orderName,
				orderType);
		PageModel<B2bFuturesTypeDtoEx> pm = b2bFuturesService.searchFuturesTypeListGrid(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 
	 * 编辑/新增期货品种
	 * 
	 * @param request
	 * @param response
	 * @param futuresType
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("updateFuturesType")
	public String updateFuturesType(HttpServletRequest request, HttpServletResponse response,
			B2bFuturesType futuresType) throws Exception {
		String result = b2bFuturesService.updateFuturesType(futuresType);
		return result;
	}

	/**
	 * 菜单栏设置页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("memberBarManage")
	public ModelAndView memberBarManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/memuBarManage");
		return mav;
	}



	/**
	 * 搜索菜单栏设置
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("searchMemuBarManage")
	public String searchMemberBarManage(HttpServletRequest request, HttpServletResponse response,
								  B2bMemubarManageExtDto extDto) throws Exception {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName");
		String orderType = ServletUtils.getStringParameter(request, "orderType");
		QueryModel<B2bMemubarManageExtDto> qm = new QueryModel<>(extDto, start, limit, orderName,
				orderType);
		PageModel<B2bMemubarManageExtDto> pm = operationManageService.searchMemuManageList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 删除菜单栏设置
	 *
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("deleteMemuBarManage")
	@ResponseBody
	public String deletememberBarManage(HttpServletRequest request, HttpServletResponse response,String pk) throws Exception {

		int result = operationManageService.delMemuManageList(pk);
		String msg = Constants.RESULT_FAIL_MSG;
		if (result > 0){
			msg = Constants.RESULT_SUCCESS_MSG;
		}
		return msg;
	}

	/**
	 * 编辑菜单栏设置
	 *
	 * @param request
	 * @param response
	 * @param extDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editMemuBarManage")
	@ResponseBody
	public String deletememberBarManage(HttpServletRequest request, HttpServletResponse response,B2bMemubarManageExtDto extDto) throws Exception {

		int result = operationManageService.editMemuManageList(extDto);
		String msg = Constants.RESULT_FAIL_MSG;
		if (result > 0){
			msg = Constants.RESULT_SUCCESS_MSG;
		}
		return msg;
	}


	/**
	 * 菜单栏设置页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("excelStore")
	public ModelAndView excelStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("operation/excelStore");
		return mav;
	}

	/**
	 * 搜索菜单栏设置
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("searchExcelStore")
	public String searchExcelStore(HttpServletRequest request, HttpServletResponse response,
										SysExcelStoreExtDto extDto) throws Exception {
		ManageAccount account = this.getAccount(request);

		if (CommonUtil.isNotEmpty(account.getRolePk())){
			extDto.setAccountPk(account.getPk());
		}
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName");
		String orderType = ServletUtils.getStringParameter(request, "orderType");
		QueryModel<SysExcelStoreExtDto> qm = new QueryModel<>(extDto, start, limit, orderName,
				orderType);
		PageModel<SysExcelStoreExtDto> pm = operationManageService.searchExcelStoreList(qm);
		return JsonUtils.convertToString(pm);
	}
	
	@RequestMapping(value = "/uploadTxT", method = RequestMethod.GET)
	public String uploadTxT(HttpServletRequest req, HttpServletResponse response, String fileName) {
        try {
            String tempPath = PropertyConfig.getProperty("FILE_PATH");
            File file = new File(tempPath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            
            String filePath = tempPath + "/" + fileName + ".txt";
            response.setContentType("application/octet-stream");  
            boolean isMSIE = isMSBrowser(req);  
             if (isMSIE) {  
                           fileName = URLEncoder.encode(fileName, "UTF-8");  
                       } else {  
                           fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");  
                       }  
            response.setHeader("Content-Disposition","attachment; filename="+fileName+".txt");
            // 读到流中
            FileInputStream inStream = new FileInputStream(filePath);// 文件的存放路径
            byte[] b = new byte[100];
            int len;
            // 循环取出流中的数据
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
            // 删除临时文件
//            delTempFile(tempPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
	
    private boolean isMSBrowser(HttpServletRequest request) {  
        String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};  
        String userAgent = request.getHeader("User-Agent");  
        for (String signal : IEBrowserSignals) {              
        	if (userAgent.contains(signal)){  
                return true;  
            }  
        }  
        return false;
}}
