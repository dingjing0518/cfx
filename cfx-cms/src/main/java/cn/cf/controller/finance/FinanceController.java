
package cn.cf.controller.finance;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import cn.cf.common.BaseController;
import cn.cf.common.Constants;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.ManageAccountExtDao;
import cn.cf.dto.MemberShip;
import cn.cf.entity.AddressInfo;
import cn.cf.entity.DeliveryBasic;
import cn.cf.entity.DeliveryGoods;
import cn.cf.entity.OrderRecord;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entity.SupplierInfo;
import cn.cf.json.JsonUtils;
import cn.cf.model.ManageAccount;
import cn.cf.property.PropertyConfig;
import cn.cf.service.CommonService;
import cn.cf.service.operation.OperationManageService;
import cn.cf.util.DateUtil;

/**
 * 财务管理模块（订单只取化纤贷订单）
 * 
 * @author xht
 *
 */
@Controller
@RequestMapping("/")
public class FinanceController extends BaseController {

	@Autowired
	private OperationManageService operationManageService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ManageAccountExtDao manageAccountExtDao;

	/**
	 * 订单管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("order_F")
	public ModelAndView account(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("finance/orderF");
		return mav;
	}

	/**
	 * 订单提货单
	 * 
	 * @param request
	 * @param response
	 * @param flag
	 *            1：金融中心 2:营销中心
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("openBillOfLoading")
	public ModelAndView openBillOfLoading(HttpServletRequest request, HttpServletResponse response, String orderNumber,
			Integer flag) throws Exception {
		String url = "";
		if (flag == 1) {
			url = "economics/eOrderBillOfLoading";
		} else if (flag == 2) {
			url = "marketing/orderBillOfLoading";
		} else if (flag == 3) {
			url = "finance/fOrderBillOfLoading";
		}
		ModelAndView mav = new ModelAndView(url);
		DeliveryBasic basic = commonService.getDeliveryBasic(orderNumber);
		if (basic!=null) {
			if (basic.getOrder().getAddressInfo() != null && "".equals(basic.getOrder().getAddressInfo())) {
				AddressInfo address = JSONObject.parseObject(basic.getOrder().getAddressInfo(), AddressInfo.class);
				basic.getOrder().setAddress(address);
			}

			if (basic.getOrder().getPurchaserInfo() != null && "".equals(basic.getOrder().getPurchaserInfo())) {
				PurchaserInfo purchaserInfo = JSONObject.parseObject(basic.getOrder().getPurchaserInfo(),
						PurchaserInfo.class);
				basic.getOrder().setPurchaser(purchaserInfo);
			}
			if (basic.getOrder().getSupplierInfo() != null && "".equals(basic.getOrder().getSupplierInfo())) {
				SupplierInfo supplierInfo = JSONObject.parseObject(basic.getOrder().getSupplierInfo(), SupplierInfo.class);
				basic.getOrder().setSupplier(supplierInfo);
			}

			mav.addObject("deliveryBasic", basic);
		}
		mav.addObject("orderGoods", operationManageService.searchOrderGoods(orderNumber));
		mav.addObject("payVoucherList", commonService.searchPayVoucherList(orderNumber, Constants.TWO));
		if (flag == Constants.ONE) {
			ManageAccount account = getAccount(request);
			MemberShip currentMemberShip = this.getMemberShipByAccount(account.getAccount());
			if (currentMemberShip != null && null != currentMemberShip.getGroupId()) {
				mav.addObject("roleName", currentMemberShip.getGroupId());
			}
		}
		mav.addObject("flag", flag);
		return mav;
	}

	/**
	 * 订单导出
	 * 
	 * @param req
	 * @param resp
	 * @param orderNumber
	 * @param block
	 * @return
	 */
	@RequestMapping(value = "/uploadPDF_F", method = RequestMethod.GET)
	@ResponseBody
	public String uploadPDF(HttpServletRequest req, HttpServletResponse resp, String orderNumber) {
		try {
			operationManageService.exportOrderPDF(orderNumber, resp, req);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提货明细
	 * 
	 * @param req
	 * @param resp
	 * @param orderNumber
	 * @return
	 */
	@RequestMapping(value = "/searchDeliveryOrder")
	@ResponseBody
	public String searchDeliveryOrder(HttpServletRequest req, HttpServletResponse resp, String orderNumber,
			String status) {
		List<Integer> list = new ArrayList<Integer>();
		if (status != null && !"".equals(status)) {
			String[] a = status.split(",");
			for (int i = 0; i < a.length; i++) {
				list.add(Integer.parseInt(a[i]));
			}
		}

		return JsonUtils.convertToString(commonService.searchDeliveryOrderList(orderNumber, list));
	}

	/**
	 * 打印提货委托单
	 * 
	 * @param req
	 * @param resp
	 * @param deliveryNumber
	 * @return
	 */
	@RequestMapping(value = "/uploadPDF_deliveryReq", method = RequestMethod.GET)
	@ResponseBody
	public String uploadPDF_DeliveryReq(HttpServletRequest req, HttpServletResponse resp, String deliveryNumber) {
		try {
			// 告诉浏览器用什么软件可以打开此文件
			resp.setHeader("content-Type", "application/pdf");
			// 涓嬭浇鏂囦欢鐨勯粯璁ゅ悕绉�
			String fileName = deliveryNumber + ".pdf";
			resp.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859_1"));
			resp.setCharacterEncoding("UTF-8");
			// 下载文件的默认名称
			String fliePath = "";
			String tempPath = PropertyConfig.getProperty("FILE_PATH");
			File file = new File(tempPath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			fliePath = tempPath + "/" + fileName;
			commonService.exportDeliveryReq(deliveryNumber, fliePath, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出提货委托单
	 * 
	 * @param req
	 * @param resp
	 * @param deliveryNumber
	 * @return
	 */
	@RequestMapping(value = "/uploadPDF_trust", method = RequestMethod.GET)
	@ResponseBody
	public String uploadPDF_trust(HttpServletRequest req, HttpServletResponse resp, String deliveryNumber) {
		try {
			// 告诉浏览器用什么软件可以打开此文件
			resp.setHeader("content-Type", "application/pdf");
			resp.setCharacterEncoding("UTF-8");
			String tempPath = PropertyConfig.getProperty("FILE_PATH");
			String fileName = deliveryNumber+"_2.pdf";
			resp.setHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
			commonService.downloadDeliveryEntrust(deliveryNumber, tempPath, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传提货凭证
	 * 
	 * @param request
	 * @param file
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "uploadPhotoPay")
	public String uploadPhotoPay(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file,
			String orderNumber) throws Exception {
		// TODO閼惧嘲褰囨稉濠佺炊閸ュ墽澧栭崷鏉挎絻
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		int randomCode = (new Random()).nextInt(8999) + 1000;// 閼惧嘲褰囬梾蹇旀簚閺侊拷
		fileName = randomCode + fileName;
		File targetFile0 = new File(path, fileName);
		//
		String payId = "";
		try {
			if (!targetFile0.exists()) {
				targetFile0.mkdirs();
			}
			file.transferTo(targetFile0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (targetFile0 != null) {
				fileName = OSSUtils.ossMangerUpload(targetFile0, Constants.IMG);
				if (fileName != null) {
					payId = commonService.savePayvoucher(orderNumber, Constants.picURL + fileName, 2);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", Constants.picURL + fileName);
			map.put("payId", payId);
			targetFile0.delete();
			return JsonUtils.convertToString(map);
		}
	}

	/**
	 * 删除付款凭证
	 * 
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/delPayvoucher")
	@ResponseBody
	public String delPayvoucher(HttpServletRequest req, HttpServletResponse resp, String id) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		try {
			commonService.delPayvoucher(id);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 提货
	 * 
	 * @param req
	 * @param resp
	 * @param orderNumber
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCreditDeliveryOrder", method = RequestMethod.POST)
	@ResponseBody
	public String updateCreditDeliveryOrder(HttpServletRequest req, HttpServletResponse resp, String orderNumber,
			String json) {
		List<DeliveryGoods> list = JSONObject.parseArray(json, DeliveryGoods.class);
		return commonService.creditDeliveryOrder(orderNumber, list);
	}

	/**
	 * 更新提货状态
	 * 
	 * @param req
	 * @param resp
	 * @param deliveryNumber
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sureDeliveryOrderStatus")
	@ResponseBody
	public String sureDeliveryOrderStatus(HttpServletRequest req, HttpServletResponse resp, String deliveryNumber,
			Integer status) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		try {
			operationManageService.sureDeliveryOrderStatus(deliveryNumber, status);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}

	@RequestMapping(value = "/sureDeliveryOrder")
	@ResponseBody
	public String sureDeliveryOrder(HttpServletRequest req, HttpServletResponse resp, String deliveryNumber) {
		String msg;
		try {
			msg = commonService.sendDeliverOrder(deliveryNumber);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		JSONObject obj = JSONObject.parseObject(msg);
		if ("0000".equals(obj.get("code"))) {
			operationManageService.sureDeliveryOrderStatus(deliveryNumber,Constants.THREE);
		}
		return msg;
	}
}
