package cn.cf.controller.logistics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import cn.cf.common.ExportUtil;
import cn.cf.common.QueryModel;
import cn.cf.dto.LgLogisticsTrackingDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.DeliveryForm;
import cn.cf.entity.LogisticsOrderAddress;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.LgOrderGridModel;
import cn.cf.model.LgOrderSearchModel;
import cn.cf.model.ManageAccount;
import cn.cf.service.logistics.LgLogisticsTrackingService;
import cn.cf.service.logistics.LogisticsOrderService;
import cn.cf.service.logistics.LogisticsService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.service.operation.SysRegionsService;
import cn.cf.task.schedule.DynamicTask;
import cn.cf.task.schedule.logistics.LogisticsOrderRunnable;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.jxls.exception.ParsePropertyException;

/**
 * 物流承运商运货单管理
 */
@Controller
@RequestMapping("/")
public class LogisticsOrderController extends BaseController {

	@Autowired
	private LogisticsService logisticsService;

	@Autowired
	private SysRegionsService sysRegionsService;

	@Autowired
	private LogisticsOrderService lgOrderService;

	@Autowired
	private LgLogisticsTrackingService trackingService;

	@Autowired
	private OperationManageService operationManageService;

	@Autowired
	private DynamicTask dynamicTask;

	/**
	 * 页面跳转：正常运货单
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lgNormalOrder")
	public ModelAndView lgNormalOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("logistics/lgNormalOrder");
		mav.addObject("lgCompanyList", logisticsService.getLgCompanyList());// 物流公司名
		mav.addObject("province", sysRegionsService.getSysregisByCityList("-1"));
		return mav;
	}

	/**
	 * 页面跳转：异常运货单
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lgAbNormalOrder")
	public ModelAndView lgAbNormalOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("logistics/lgAbNormalOrder");
		return mav;
	}

	/**
	 * 正常运货单：列表展示
	 *
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("lgOrder_data")
	@ResponseBody
	public String lgOrder_data(HttpServletRequest request, HttpServletResponse response, LgOrderSearchModel dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		dto.setIsAbnormal(1);
		// 去除空格
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		ManageAccount account = getAccount(request);
		QueryModel<LgOrderSearchModel> qm = new QueryModel<LgOrderSearchModel>(dto, start, limit, orderName, orderType);
		PageModel<LgOrderGridModel> pm = lgOrderService.getlgOrder(qm, 1, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 异常运货单：列表对象
	 *
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("lgAbOrder_data")
	@ResponseBody
	public String lgAbOrder_data(HttpServletRequest request, HttpServletResponse response, LgOrderSearchModel dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		dto.setIsAbnormal(2);
		ManageAccount account = getAccount(request);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		QueryModel<LgOrderSearchModel> qm = new QueryModel<LgOrderSearchModel>(dto, start, limit, orderName, orderType);
		PageModel<LgOrderGridModel> pm = lgOrderService.getlgOrder(qm, 1, account.getPk());
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 运货单详情
	 *
	 * @param request
	 * @param response
	 * 
	 * @return
	 */
	@RequestMapping("lgOrderDetail")
	@ResponseBody
	public String deliveryOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		ManageAccount account = getAccount(request);
		HashMap<String, Object> data = lgOrderService.selectDetailByDeliveryPk(pk, account.getPk());
		return JsonUtils.convertToString(data);
	}

	/**
	 * 正常运货单导出
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "exportLgOrderForm")
	@ResponseBody
	public String exportLgOrderForm(HttpServletRequest request, HttpServletResponse response,
			OrderDataTypeParams params) throws Exception {
		if (params == null) {
			params = new OrderDataTypeParams();
		}
		ManageAccount account = getAccount(request);
		params.setIsAbnormal("1");
		String uuid = KeyUtils.getUUID();
		operationManageService.saveOrderExcelToOss(params, account, "exportLgOrderForm_"+uuid, "物流中心-订单管理-正常订单", 1);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new LogisticsOrderRunnable(name,uuid,1), name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 异常运货单导出
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "exportLgAbOrderForm")
	@ResponseBody
	public String exportLgAbOrderForm(HttpServletRequest request, HttpServletResponse response,
			OrderDataTypeParams params)  {
		if (params == null) {
			params = new OrderDataTypeParams();
		}
		ManageAccount account = getAccount(request);
		params.setIsAbnormal("2");
		String uuid = KeyUtils.getUUID();
		operationManageService.saveOrderExcelToOss(params, account, "exportLgAbOrderForm_"+uuid, "物流中心-订单管理-异常订单", 2);
		String name = Thread.currentThread().getName();// 获取当前执行线程
		dynamicTask.startCron(new LogisticsOrderRunnable(name,uuid,2), name);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 匹配 物流供应商的线路价格
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("matchLgCompanyRoute")
	@ResponseBody
	public String matchLgCompanyRoute(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		String logisticsCompanyPk = ServletUtils.getStringParameter(request, "logisticPk", null);
		List<LogisticsRouteDto> list = lgOrderService.matchLgCompanyRoute(pk, logisticsCompanyPk);
		return JsonUtils.convertToString(list);
	}

	/**
	 * 订单推送
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("savePushLgCompanyNameExt")
	@ResponseBody
	public String savePushLgCompanyNameExt(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		String logisticsCompanyPk = ServletUtils.getStringParameter(request, "logisticsCompanyPk", null);
		String logisticsCompanyName = ServletUtils.getStringParameter(request, "logisticsCompanyName", null);
		Double presentTotalFreight = ServletUtils.getDoubleParameter(request, "presentTotalFreight", 0.0);
		Double originalTotalFreight = ServletUtils.getDoubleParameter(request, "originalTotalFreight", 0.0);

		Double settleWeight = ServletUtils.getDoubleParameter(request, "settleWeight", 0.0);
		String linePricePk = ServletUtils.getStringParameter(request, "linePricePk", null);
		Double basisLinePrice = ServletUtils.getDoubleParameter(request, "basisLinePrice", 0.0);
		Double originalFreight = ServletUtils.getDoubleParameter(request, "originalFreight", 0.0);
		Integer isMatched = ServletUtils.getIntParameter(request, "isMatched", 0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", pk);
		map.put("logisticsCompanyPk", logisticsCompanyPk);
		map.put("logisticsCompanyName", logisticsCompanyName);
		map.put("presentTotalFreight", presentTotalFreight);// 对内总价
		map.put("originalTotalFreight", originalTotalFreight);// 对外总价
		map.put("settleWeight", settleWeight);// 结算吨数
		map.put("originalFreight", originalFreight);// 对外单价
		map.put("presentFreight", basisLinePrice);// 对内单价
		map.put("linePricePk", linePricePk);
		map.put("basisLinePrice", basisLinePrice);// 对内单价
		map.put("isMatched", isMatched);
		int retVal = lgOrderService.savePushLgCompanyName(map);
		String msg = "";
		if (retVal > 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 待财务确认
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("sureMoney")
	@ResponseBody
	public String sureMoney(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		Integer orderStatus = ServletUtils.getIntParameter(request, "orderStatus", 0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", pk);
		map.put("orderStatus", orderStatus);

		int retVal = lgOrderService.sureMoney(map);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 异常反馈
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("submitFeedBack")
	@ResponseBody
	public String submitFeedBack(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		String imgs = ServletUtils.getStringParameter(request, "imgs", null);
		String abnormalRemark = ServletUtils.getStringParameter(request, "abnormalRemark", null);
		int retVal = lgOrderService.submitFeedBack(pk, imgs, abnormalRemark);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 运货单地址详情
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("logOrderAddrDetail")
	@ResponseBody
	public String LogOrderAddrDetail(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", null);
		return JsonUtils.convertToString(lgOrderService.selectAddress(pk));
	}

	/**
	 * 修改运货单地址
	 *
	 * @param request
	 * @param response
	 * @param logisticsOrderAddress
	 * @return
	 */
	@RequestMapping("saveOrder")
	@ResponseBody
	public String saveOrder(HttpServletRequest request, HttpServletResponse response,
			LogisticsOrderAddress logisticsOrderAddress) {
		int retVal = lgOrderService.saveOrder(logisticsOrderAddress);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 运货单异常反馈详情
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("lgOrderFeedBackDetail")
	@ResponseBody
	public String lgOrderFeedBackDetail(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		ManageAccount account = getAccount(request);
		HashMap<String, Object> data = lgOrderService.lgOrderFeedBackDetail(pk, account.getPk());
		return JsonUtils.convertToString(data);
	}

	/**
	 * 保存异常反馈
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("sureFeedback")
	@ResponseBody
	public String sureFeedback(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		int retVal = lgOrderService.sureFeedback(pk);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 打印提货单
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "exportDeliveryForm")
	@ResponseBody
	public String exportDeliveryForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		ExportUtil<DeliveryForm> export = new ExportUtil<DeliveryForm>();
		// 导出excel
		DeliveryForm form = lgOrderService.exportDeliveryForm(pk);
		List<DeliveryForm> tempList = new ArrayList<>();
		tempList.add(form);
		// 导出PDF
		String path = "";
		if (form != null) {
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");// 格式化设置
			String five = form.getWeight() == null ? "0.0" : decimalFormat.format(form.getWeight());
			String[] fieldsArray = { form.getDeliveryNumber(), form.getOrderPk(), form.getFromAddress(),
					form.getFromContacts(), form.getFromCompanyName(), form.getFromContactsTel(),
					form.getDeliveryTime(), form.getProductName(), form.getSpecName(), form.getBatchNumber(),
					form.getGradeName(), form.getBoxes() == null ? "0" : String.valueOf(form.getBoxes()), five,
					form.getToAddress(), form.getToContacts(), form.getFromContacts(), form.getToContactsTel(),
					form.getArrivedTime(), form.getRemark(), form.getDeliveryNumber(), form.getOrderPk(),
					form.getFromAddress(), form.getFromContacts(), form.getFromCompanyName(), form.getFromContactsTel(),
					form.getDeliveryTime(), form.getProductName(), form.getSpecName(), form.getBatchNumber(),
					form.getGradeName(), form.getBoxes() == null ? "0" : String.valueOf(form.getBoxes()), five,
					form.getToAddress(), form.getToContacts(), form.getFromContacts(), form.getToContactsTel(),
					form.getArrivedTime(), form.getRemark(), form.getDeliveryNumber(), form.getOrderPk(),
					form.getFromAddress(), form.getFromContacts(), form.getFromCompanyName(), form.getFromContactsTel(),
					form.getDeliveryTime(), form.getProductName(), form.getSpecName(), form.getBatchNumber(),
					form.getGradeName(), form.getBoxes() == null ? "0" : String.valueOf(form.getBoxes()), five,
					form.getToAddress(), form.getToContacts(), form.getFromContacts(), form.getToContactsTel(),
					form.getArrivedTime(), form.getRemark() };

			path = export.exportPDFUtil("deliveryFormForm", fieldsArray, 3, response, request);
		}
		return path;
	}

	/**
	 * 下载pdf
	 * 
	 * @param request
	 * @param response
	 * @param path
	 */
	@RequestMapping(value = "downLoandsPDF")
	@ResponseBody
	public void downLoandsPDF(HttpServletRequest request, HttpServletResponse response, String path) {
		FileInputStream is = null;
		OutputStream out = null;
		try {
			String[] name = path.split("/");
			response.setContentType("application/vnd.ms-pdf");
			response.setHeader("Content-Disposition", "attachment;filename=" + name[name.length - 1]);
			is = new FileInputStream(path);
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 页面跳转：货物所在地
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lgGoodsLocationPage")
	public ModelAndView lgGoodsLocationPage(HttpServletRequest request, HttpServletResponse response,
			LgLogisticsTrackingDto dto) throws Exception {
		ModelAndView mav = new ModelAndView("logistics/lgGoodsLocation");
		mav.addObject("dto", dto);
		mav.addObject("province", sysRegionsService.getSysregisByCityList("-1"));
		return mav;
	}

	/**
	 * 货物所在地列表
	 * 
	 * @param request
	 * 
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping("lgGoodsLocation")
	@ResponseBody
	public String lgGoodsLocation(HttpServletRequest request, HttpServletResponse response,
			LgLogisticsTrackingDto dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 5);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "updateTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<LgLogisticsTrackingDto> qm = new QueryModel<LgLogisticsTrackingDto>(dto, start, limit, orderName,
				orderType);
		PageModel<LgLogisticsTrackingDto> pm = trackingService.getLogisticsTracking(qm);
		return JsonUtils.convertToString(pm);

	}

	/**
	 * 保存货物所在地
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveLgLogisticsTrack")
	@ResponseBody
	public String saveLgLogisticsTrack(HttpServletRequest request, HttpServletResponse response,
			LgLogisticsTrackingDto dto) {
		int retVal = trackingService.saveLgLogisticsTrack(dto);
		String msg = "";
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 删除货物所在地
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("delLgLogisticsTrack")
	@ResponseBody
	public String delLgLogisticsTrack(HttpServletRequest request, HttpServletResponse response,
			LgLogisticsTrackingDto dto) {
		int retVal = trackingService.delLgLogisticsTrack(dto);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (retVal > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
}
