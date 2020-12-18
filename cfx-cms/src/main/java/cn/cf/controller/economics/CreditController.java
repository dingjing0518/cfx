package cn.cf.controller.economics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.property.PropertyConfig;
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
import cn.cf.constant.SmsCode;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dto.B2bBillCusgoodsExtDto;
import cn.cf.dto.B2bBillCustomerExtDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditExtDto;
import cn.cf.dto.B2bCreditGoodsExtDto;
import cn.cf.dto.B2bCreditInvoiceExtDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.dto.B2bEconomicsBankExtDto;
import cn.cf.dto.B2bTaxAuthorityDto;
import cn.cf.dto.ManageAccountDto;
import cn.cf.dto.ManageAccountExtDto;
import cn.cf.entity.CreditInfo;
import cn.cf.entity.CreditInvoiceDataTypeParams;
import cn.cf.entity.RegionJson;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bEconomicsBank;
import cn.cf.model.ManageAccount;
import cn.cf.service.CuccSmsService;
import cn.cf.service.enconmics.EconomicsBankCompanyService;
import cn.cf.service.enconmics.EconomicsCreditService;
import cn.cf.service.marketing.McustomerManageService;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class CreditController extends BaseController {

	@Autowired
	private EconomicsBankCompanyService economicsBankCompanyService;

	@Autowired
	private EconomicsCreditService economicsCreditService;

	private static final Logger logger = LoggerFactory.getLogger(CreditController.class);

	@Autowired
	private CuccSmsService cuccSmsService;

	@Autowired
	private McustomerManageService mcustomerManageService;

	@Autowired
	private B2bCompanyExtDao companyExtDao;

	/**
	 * 授信金额管理页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("creditAmount")

	public ModelAndView creditAmount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("economics/creditAmount");
		return mav;
	}

	/**
	 * 授信银行页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("economicsBank")
	public ModelAndView economicsBank(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("economics/economicsBank");
		return mav;
	}

	/**
	 * 授信银行列表
	 *
	 * @param request
	 * @param response
	 * @param economicsBank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("economicsBank_data")
	public String economicsBankList(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsBankExtDto economicsBank) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "creditTotalAmount");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		// 查询条件
		QueryModel<B2bEconomicsBankExtDto> qm = new QueryModel<B2bEconomicsBankExtDto>(economicsBank, start, limit,
				orderName, orderType);
		PageModel<B2bEconomicsBankExtDto> pm = economicsCreditService.economicsBankList(qm);
		String json = JsonUtils.convertToString(pm);
		return json;
	}

	/**
	 * 新增/编辑银行信息
	 *
	 * @param request
	 * @param response
	 * @param economicsBank
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveEconomicsBank")
	public String saveEconomicsBank(HttpServletRequest request, HttpServletResponse response,
			B2bEconomicsBank economicsBank) {

		int retVal = economicsCreditService.inserOrUpdateEconomicsBank(economicsBank);
		String msg = "";
		if (retVal == 1) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else if (retVal == 2) {
			msg = "{\"success\":true,\"msg\":\"银行名称已存在,请勿重复增加!\"}";
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;

	}

	/**
	 * 根据公司pk获取银行信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getEconomicsBycompanyPk")
	public String getEconomicsBycompanyPk(HttpServletRequest request, HttpServletResponse response, String companyPk) {

		List<B2bEconomicsBankCompanyDto> list = economicsCreditService.getEconomicsBankCompanyByPk(companyPk);
		String msg = "{\"success\":false,\"msg\":\"\"}";
		if (list != null && list.size() > 0) {
			B2bEconomicsBankCompanyDto dto = list.get(0);
			msg = "{\"success\":true,\"msg\":\"" + dto.getCustomerNumber() + "\"}";
		}
		return msg;

	}

	/**
	 * 
	 * 根据creditPk查询金融产品页面
	 * 
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchCreditGoods")
	public String searchCreditGoods(HttpServletRequest request, HttpServletResponse response, String pk,
			String companyPk, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "desc");
		map.put("creditPk", pk);
		map.put("start", start);
		map.put("limit", limit);
		map.put("orderName", orderName);
		map.put("orderType", orderType);
		map.put("status", 2);
		if (companyPk != null && !"".equals(companyPk)) {
			map.put("companyPk", companyPk);
		}
		if (status != null) {
			map.put("status", status);
		}
		PageModel<B2bCreditGoodsExtDto> list = economicsCreditService.searchCreditGoods(map);
		String json = JsonUtils.convertToString(list);
		return json;
	}

	/**
	 * 
	 * 根据creditPk查询金融产品
	 * 
	 * @param request
	 * @param response
	 * @param creditPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchCreditGoodsByCreditPk")
	public String searchCreditGoodsByCreditPk(HttpServletRequest request, HttpServletResponse response,
			String creditPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("creditPk", creditPk);
		return JsonUtils.convertToString(economicsCreditService.searchCreditGoodsByCreditPk(map));

	}

	/**
	 * 授信客户管理页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("creditCustomer")
	public ModelAndView creditCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("economics/creditCustomer");
		return mav;
	}

	/**
	 * 授信客户管理页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("creditInvoice")
	public ModelAndView creditInvoice(HttpServletRequest request, HttpServletResponse response, B2bCreditExtDto dto)
			throws Exception {

		ModelAndView mav = new ModelAndView("economics/creditInvoice");
		mav.addObject("creditExtDot", dto);
		return mav;
	}

	/**
	 * 授信客户管理列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("creditCustomerList")
	public String creditCustomerList(HttpServletRequest request, HttpServletResponse response, B2bCreditExtDto credit) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		ManageAccount account = getAccount(request);
		credit.setAccountPk(account.getPk());
		QueryModel<B2bCreditExtDto> qm = new QueryModel<B2bCreditExtDto>(credit, start, limit, orderName, orderType);
		PageModel<B2bCreditExtDto> pm = economicsCreditService.searchCreditList(qm);
		return JsonUtils.convertToString(pm);
	}

	@ResponseBody
	@RequestMapping("exportCreditInvoiceList")
	public String exportCreditCustomerList(HttpServletRequest request, HttpServletResponse response,
			CreditInvoiceDataTypeParams params) {
		String uuid = KeyUtils.getUUID();
		ManageAccount account = getAccount(request);
		params.setUuid(uuid);
		economicsCreditService.saveCreditInvioceExcelToOss(params, account);
		return Constants.EXPORT_MSG;
	}

	/**
	 * 删除供应商签约账户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("syncCreditInvoice")
	public String syncCreditInvoice(HttpServletRequest request, HttpServletResponse response,
			B2bCreditInvoiceExtDto dto) throws Exception {
		String msg = "";
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		String dataTypeStr = "1";
		if (dto != null && dto.getDataType() != null) {
			dataTypeStr = String.valueOf(dto.getDataType());
		}
		map.put("creditPk", dto.getCreditPk());
		map.put("dataType", dataTypeStr);
		map.put("companyPk", dto.getCompanyPk());
		map.put("startDate", dto.getBillingDateStart());
		map.put("endDate", dto.getBillingDateEnd());
		if (dateValid(dto.getBillingDateStart(),dto.getBillingDateEnd())){
			sb.append("syncCreditInvoice.param:" + map + "\r\n");
			String url= PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/syncCreditInvoice";
			sb.append("syncCreditInvoice.url:" + url + "\r\n");
			String data = HttpHelper.sendPostRequest(url, map, null, null);
			sb.append("syncCreditInvoice.data:" + data + "\r\n");
			data = CommonUtil.deciphData(data);// 解密
			sb.append("syncCreditInvoice.doPost_deciphData:" + data + "\r\n");
			logger.error("returnValue:" + sb);
			if (data != null) {
				JSONObject obj = JSONObject.fromObject(data);
				if (obj.get("code") == null || !"0000".equals(obj.get("code"))) {
					logger.error(sb.append("syncCreditInvoice_CODE_FAIL\r\n").toString());
					if (obj.get("msg") == null || !"".equals(obj.get("msg"))) {
						msg = "{\"success\":false,\"msg\":\"" + obj.get("msg").toString() + "\"}";
					}else {
						msg = Constants.RESULT_FAIL_MSG;
					}
				} else {
					msg = Constants.RESULT_SUCCESS_MSG;
				}
			} else {
				logger.error(sb.append("未返回结果\r\n").toString());
				msg = Constants.RESULT_FAIL_MSG;
			}
		}else{
			msg = "{\"success\":false,\"msg\":\"申请时间范围请选择半年以内!\"}";
		}
		return msg;
	}



	/**
	 * 验证日期的合法性
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return ture:合法；false:不合法
	 */
	private boolean dateValid(String startDate,String endDate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		boolean result = true;
		try {
			Date startDateTemp = formatter.parse(startDate);
			Date endDateTemp = formatter.parse(endDate);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(startDateTemp);
			rightNow.add(Calendar.MONTH, 6);
			startDateTemp = rightNow.getTime();
			if (startDateTemp.getTime() < endDateTemp.getTime()) {
				result = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询发票列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("creditInvoiceSearch")
	public String creditInvoiceSearch(HttpServletRequest request, HttpServletResponse response,
			B2bCreditInvoiceExtDto dto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "billingDate");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<B2bCreditInvoiceExtDto> qm = new QueryModel<>(dto, start, limit, orderName, orderType);
		PageModel<B2bCreditInvoiceExtDto> pm = economicsCreditService.searchCreditInvoiceList(qm);
		return JsonUtils.convertToString(pm);
	}

	/**
	 * 获取税务机关数据
	 *
	 * @param request
	 * @param response
	 * @param taxAuthorityName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getB2bTaxAuthorityCode")
	public String getSysBankNameCode(HttpServletRequest request, HttpServletResponse response,
			String taxAuthorityName) {

		List<B2bTaxAuthorityDto> dtoList = economicsCreditService.getB2bTaxAuthority(taxAuthorityName);
		String json = "";
		if (dtoList != null && dtoList.size() > 0) {
			json = JsonUtils.convertToString(dtoList);
		}
		return json;
	}

	@RequestMapping(value = "importTaxAuthority")
	@ResponseBody
	public String importTaxAuthority(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "filename") MultipartFile file, String pk, String taxNature, String taxAuthorityName,
			String taxAuthorityCode) throws Exception {

		String path = economicsCreditService.importTaxAuthority(pk, taxNature, taxAuthorityName, taxAuthorityCode,
				file);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if ("".equals(path)) {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 
	 * 编辑合同号
	 * 
	 * @param request
	 * 
	 * @param response
	 * @param companyPk
	 * @param customerNumber
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "updateCustomerNumber", method = RequestMethod.POST)
	public String updateCustomerNumber(HttpServletRequest request, HttpServletResponse response, String companyPk,
			String customerNumber) {

		int result = 0;
		String msg = null;
		result = economicsBankCompanyService.updateCustomerNumber(companyPk, customerNumber);
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 更新银行客户号
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBankAccountNumber", method = RequestMethod.POST)
	public String updateBankAccountNumber(HttpServletRequest request, HttpServletResponse response,
			B2bCreditGoodsExtDto dto, String bankAccountNumber) {
		String msg = null;
		boolean flag = economicsBankCompanyService.updateBankAccountNumber(dto);
		if (flag) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 订单放款查询
	 *
	 * @param request
	 * @param response
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "searhLoanByOrderNumber", method = RequestMethod.POST)
	public String searhLoanByOrderNumber(HttpServletRequest request, HttpServletResponse response, String orderNumber)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNumber", orderNumber);
		sb.append("searhLoanByOrderNumber_HttpHelper.param:" + map + "\r\n");
		String data = HttpHelper.sendPostRequest(
				PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/searhLoanByOrderNumber", map, null,
				null);
		sb.append("searhLoanByOrderNumber_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("searhLoanByOrderNumber_HttpHelper.deciphData:" + data + "\r\n");
		logger.error("returnValue:" + sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			if (obj.get("code") != null && "0000".equals(obj.get("code"))) {
			} else {
				logger.error(sb.append("auditCreditCompay_CODE_FAIL\r\n").toString());
				msg = Constants.RESULT_FAIL_MSG;
			}
		} else {
			logger.error(sb.append("银行未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 订单还款查询
	 *
	 * @param request
	 * @param response
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "searhRepaymentByOrderNumber", method = RequestMethod.POST)
	public String searhRepaymentByOrderNumber(HttpServletRequest request, HttpServletResponse response,
			String orderNumber) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNumber", orderNumber);
		sb.append("searhRepaymentByOrderNumber_HttpHelper.param:" + map + "\r\n");
		String data = HttpHelper.sendPostRequest(
				PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/searhRepaymentByOrderNumber", map,
				null, null);

		sb.append("searhRepaymentByOrderNumber_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("searhRepaymentByOrderNumber_HttpHelper.deciphData:" + data + "\r\n");
		logger.error("returnValue:" + sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			if (obj.get("code") != null && "0000".equals(obj.get("code"))) {
			} else {
				logger.error(sb.append("auditCreditCompay_CODE_FAIL\r\n").toString());
				msg = Constants.RESULT_FAIL_MSG;
			}

		} else {
			logger.error(sb.append("银行未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 授信额度查询
	 *
	 * @param request
	 * @param response
	 * @param companyPk
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "searchCustomerAmount", method = RequestMethod.POST)
	public String searchCustomerAmount(HttpServletRequest request, HttpServletResponse response, String companyPk)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyPk", companyPk);
		sb.append("searchCustomerAmount_HttpHelper.param:" + map + "\r\n");
		String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/searchCustomerAmount";
		sb.append("searchCustomerAmount_HttpHelper.url:" + url + "\r\n");
		String data = HttpHelper.sendPostRequest(url, map, null, null);
		sb.append("searchCustomerAmount_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("searchCustomerAmount_HttpHelper.doPost_deciphData:" + data + "\r\n");
		logger.error("returnValue:" + sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			if (obj.get("code") != null && "0000".equals(obj.get("code"))) {
			} else {
				logger.error(sb.append("searchCustomerAmount_CODE_FAIL\r\n").toString());
				msg = Constants.RESULT_FAIL_MSG;
			}
		} else {
			logger.error(sb.append("银行未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 票据解绑/绑定
	 *
	 * @param request
	 * @param response
	 * @param extDto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "searchCustGoodsBind", method = RequestMethod.POST)
	public String searchCustGoodsAmount(HttpServletRequest request, HttpServletResponse response,
			B2bBillCusgoodsExtDto extDto) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", extDto.getAccount());
		map.put("companyPk", extDto.getCompanyPk());
		map.put("bankNo", extDto.getBankNo());
		map.put("bindType", String.valueOf(extDto.getBindStatus()));
		sb.append("searchCustGoodsBind_HttpHelper.param:" + map + "\r\n");
		String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/bindBillGoods";
		sb.append("searchCustGoodsBind_HttpHelper.url:" + url + "\r\n");
		String data = HttpHelper.sendPostRequest(url, map, null, null);
		sb.append("searchCustGoodsBind_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("searchCustGoodsBind_HttpHelper.doPost_deciphData:" + data + "\r\n");
		logger.error("returnValue:" + sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			if (obj.get("code") != null && !"0000".equals(obj.get("code"))) {
				logger.error(sb.append("searchCustGoodsBind_CODE_FAIL\r\n").toString());
				msg = obj.toString();
			}
		} else {
			logger.error(sb.append("未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 审核授信状态更新（b2b_credit，b2b_credit_goods，b2b_economics_customer）
	 * 
	 * @param request
	 * @param response
	 * @param companyPk
	 * @param creditStatus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCredit", method = RequestMethod.POST)
	public String updateCredit(HttpServletRequest request, HttpServletResponse response, String companyPk,
			Integer creditStatus,int isMessage) {
		
		int result = 0;
		String msg = null;
		result = economicsCreditService.updateCreditStatus(companyPk, creditStatus);
		// 审批通过需发送短信
		if (creditStatus == 2&& isMessage==1) {
			StringBuilder sb = new StringBuilder();
			B2bCreditDto creditExtDto = economicsCreditService.getByCompanyPk(companyPk);
			if (creditExtDto != null) {
				// 发送给申请人
				Map<String, String> smsMap = new HashMap<String, String>();
				smsMap.put("pname", creditExtDto.getCompanyName());
				sb.append("申请人"+creditExtDto.getCreditContactsTel()+ "\r\n");
				sb.append("申请人"+creditExtDto.getCompanyName()+ "\r\n");
				sendMsmEconNotification(smsMap, creditExtDto.getCreditContactsTel(),companyPk,creditExtDto.getCompanyName());
				// 发送给业务经理
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyPk", companyPk);
				map.put("companyType", 1);
				ManageAccountDto accountDto = mcustomerManageService.getAccountByMap(map);
				if (accountDto != null && accountDto.getMobile() != null && !accountDto.getMobile().equals("")) {
					sb.append("业务经理"+accountDto.getMobile()+ "\r\n");
					sendMsmEconNotification(smsMap, accountDto.getMobile(),companyPk,creditExtDto.getCompanyName());
				}
				// 发送给区域经理
				List<ManageAccountExtDto> reAccountDtos = mcustomerManageService.getRegionalAccountByMap();
				if (reAccountDtos != null && reAccountDtos.size() > 0) {
					for (ManageAccountExtDto dto : reAccountDtos) {
						if (accountDto==null||!accountDto.getPk().equals(dto.getPk())) {
							B2bCompanyDto companyDto = companyExtDao.getByPkExt(companyPk);
							if (dto.getArea() != null && !dto.getArea().equals("")) {
								List<RegionJson> regionJsons = JSON.parseArray(dto.getArea(), RegionJson.class);
								for (RegionJson re : regionJsons) {
									if (re.getArea() != null && re.getArea().equals("")) {
										if (companyDto.getProvince().equals(re.getProvince())
												&& companyDto.getCity().equals(re.getCity())) {
											sb.append("区域经理"+dto.getMobile()+ "\r\n");
											sendMsmEconNotification(smsMap, dto.getMobile(),companyPk,creditExtDto.getCompanyName());
											break;
										}
									} else {
										if (companyDto.getProvince().equals(re.getProvince())
												&& companyDto.getCity().equals(re.getCity())
												&& companyDto.getArea().equals(re.getArea())) {
											sb.append("区域经理"+dto.getMobile()+ "\r\n");
											sendMsmEconNotification(smsMap, dto.getMobile(),companyPk,creditExtDto.getCompanyName());
											break;
										}
									}
								}
							}
						}
					}
				}
				logger.error(sb+"\r\n");
			}
		}
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * @param smsMap
	 * @param mobile
	 * @param companName
	 * @param companyPk 
	 */
	private void sendMsmEconNotification(Map<String, String> smsMap, String mobile, String companyPk, String companName) {
		SmsCode.ECON_NOTIFICATION.getValue();
		String content = cuccSmsService.getCUCCMsgContent(smsMap,SmsCode.ECON_NOTIFICATION.getValue());
		cuccSmsService.sendCUCCMsg(mobile, SmsCode.ECON_NOTIFICATION.getValue(),content, companyPk,companName);
	}

	/**
	 * 将b2b_economics_customer表最新审核通过的信息更新至b2b_credit,b2b_credit_goods
	 * 
	 * @param request
	 * @param response
	 * @param companyPk
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateEconCustomerToCredit", method = RequestMethod.POST)
	public String updateEconCustomerToCredit(HttpServletRequest request, HttpServletResponse response,
			String companyPk) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		try {
			economicsCreditService.updateEconCustomerToCredit(companyPk);
		} catch (Exception e) {
			e.printStackTrace();
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 授信平台额度
	 *
	 * @param request
	 * @param response
	 * @param creditGoods
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCreditCustomer", method = RequestMethod.POST)
	public String updateCreditCustomer(HttpServletRequest request, HttpServletResponse response,
			B2bCreditGoodsExtDto creditGoods) {

		int result = 0;
		result = economicsCreditService.updateCreditCustomer(creditGoods);
		String msg = Constants.RESULT_SUCCESS_MSG;
		if (result > 0) {
//			Map<String, String> smsMap = new HashMap<String, String>();
//			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//			String creditPlatformAmount = creditGoods.getPlatformAmount() == null ? null
//					: String.valueOf(decimalFormat.format(creditGoods.getPlatformAmount()));
//			smsMap.put("money", creditPlatformAmount);
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
//			String startTime = "";
//			if (creditGoods.getCreditStartTime() != null) {
//				startTime = format.format(creditGoods.getCreditStartTime());
//			}
//			String endTime = "";
//			if (creditGoods.getCreditEndTime() != null) {
//				endTime = format.format(creditGoods.getCreditEndTime());
//			}
//			smsMap.put("start_time", startTime);
//			smsMap.put("end_time", endTime);
//			StringBuilder sb = new StringBuilder();
//			sb.append("授信平台额度保存_company"+creditGoods.getCompanyPk()+"\r\n");
//			sb.append("授信平台额度保存_param"+smsMap+"\r\n");
//			cuccSmsService.sendMessage(null, creditGoods.getCompanyPk(), false, SmsCode.CREDIT_PASS.getValue(), smsMap);
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 确认贷款是否成功
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "apiInsertLoan", method = RequestMethod.POST)
	public String apiInsertLoan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber", "");
		String status = ServletUtils.getStringParameter(request, "status", "");

		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNumber", orderNumber);
		map.put("status", status);
		sb.append("apiInsertLoan_HttpHelper.param:" + map + "\r\n");
		String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/insertLoan";
		sb.append("apiInsertLoan_HttpHelper.url:" + url + "\r\n");
		String data = HttpHelper.sendPostRequest(url, map, null, null);
		sb.append("apiInsertLoan_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("apiInsertLoan_HttpHelper.doPost_deciphData:" + data + "\r\n");
		logger.error("returnValue:" + sb);
		if (data != null) {
			JSONObject obj = JSONObject.fromObject(data);
			msg = obj.toString();
		} else {
			logger.error(sb.append("银行未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "apiInsertRepayment", method = RequestMethod.POST)
	public String apiInsertRepayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber", "");
		String principal = ServletUtils.getStringParameter(request, "principal", "");
		String interest = ServletUtils.getStringParameter(request, "interest", "");

		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderNumber", orderNumber);
		map.put("principal", principal);
		map.put("interest", interest);

		sb.append("apiInsertRepayment_HttpHelper.param:" + map + "\r\n");
		String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/insertRepayment";
		sb.append("apiInsertRepayment_HttpHelper.url:" + url + "\r\n");
		String data = HttpHelper.sendPostRequest(url, map, null, null);
		sb.append("apiInsertRepayment_HttpHelper.data:" + data + "\r\n");
		String msg = Constants.RESULT_SUCCESS_MSG;
		data = CommonUtil.deciphData(data);// 解密
		sb.append("apiInsertRepayment_HttpHelper.doPost_deciphData:" + data + "\r\n");

		logger.error("returnValue:" + sb);
		if (data != null) {

			JSONObject obj = JSONObject.fromObject(data);
			msg = obj.toString();

		} else {
			logger.error(sb.append("银行未返回结果\r\n").toString());
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping(value = "updateCreditIsVisable", method = RequestMethod.POST)
	public String updateCreditIsVisable(HttpServletRequest request, HttpServletResponse response,
			B2bCreditGoodsExtDto cExtDto) {
		String msg = "";
		int result = economicsCreditService.updateCreditIsVisable(cExtDto);

		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		
		return msg;
	}

	/**
	 * 授信客户管理页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("billCustomer")
	public ModelAndView billCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("economics/billCustomer");
		return mav;
	}

	
	/**
	 * 授信客户管理列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("billCustomerList")
	public String billCustomerList(HttpServletRequest request, HttpServletResponse response,
			B2bBillCustomerExtDto customerExtDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		ManageAccount account = getAccount(request);
		customerExtDto.setAccountPk(account.getPk());
		QueryModel<B2bBillCustomerExtDto> qm = new QueryModel<>(customerExtDto, start, limit, orderName, orderType);
		PageModel<B2bBillCustomerExtDto> pm = economicsCreditService.searchBillList(qm);
		return JsonUtils.convertToString(pm);
	}

	@ResponseBody
	@RequestMapping(value = "updateBillAndGoods", method = RequestMethod.POST)
	public String updateBillAndGoods(HttpServletRequest request, HttpServletResponse response,
			B2bBillCustomerExtDto cExtDto) {
		String msg = "";
		int result = economicsCreditService.updateBillAndGoods(cExtDto);
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}

	/**
	 * 授信客户管理列表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("billCustGoodsList")
	public String billCustomerList(HttpServletRequest request, HttpServletResponse response,
			B2bBillCusgoodsExtDto customerExtDto) {
		int start = ServletUtils.getIntParameter(request, "start", 0);
		int limit = ServletUtils.getIntParameter(request, "limit", 10);
		String orderName = ServletUtils.getStringParameter(request, "orderName", "insertTime");
		String orderType = ServletUtils.getStringParameter(request, "orderType", "asc");
		QueryModel<B2bBillCusgoodsExtDto> qm = new QueryModel<>(customerExtDto, start, limit, orderName, orderType);
		PageModel<B2bBillCusgoodsExtDto> pm = economicsCreditService.searchBillCustGoodsList(qm);
		return JsonUtils.convertToString(pm);
	}

	@ResponseBody
	@RequestMapping(value = "updateCustGoodsStatus", method = RequestMethod.POST)
	public String updateCustGoodsStatus(HttpServletRequest request, HttpServletResponse response,
			B2bBillCusgoodsExtDto cExtDto) {
		String msg = "";
		int result = economicsCreditService.updateCustGoodsStatus(cExtDto);
		if (result > 0) {
			msg = Constants.RESULT_SUCCESS_MSG;
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	/**
	 * 根据pk查询b2b_credit
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCreditByPk", method = RequestMethod.POST)
	public String getCreditByPk(HttpServletRequest request, HttpServletResponse response) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");

		return JsonUtils.convertToString(economicsCreditService.getcreditByPk(pk));
	}
	/**
	 * 客户申请
	 * @param request
	 * @param response
	 * @param info
	 * @param pk
	 * @param companyPk
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "updateCreditApply", method = RequestMethod.POST)
	public String updateCreditApply(HttpServletRequest request, HttpServletResponse response,CreditInfo  info
			,String pk,String companyPk) throws Exception {
		String msg = "";
		int result = economicsCreditService.updateCreditApply(info,pk);
		if (result > 0) {
			
			StringBuilder sb = new StringBuilder();
			Map<String, String> map = new HashMap<String, String>();
			map.put("companyPk", companyPk);

			sb.append("updateCreditApply_HttpHelper.param:" + map + "\r\n");
			String url = PropertyConfig.getProperty("do_cf_api_bank_credit") + "/economics/creditCustomerApply";
			sb.append("updateCreditApply_HttpHelper.url:" + url + "\r\n");
			String data = HttpHelper.sendPostRequest(url, map, null, null);
			sb.append("updateCreditApply_HttpHelper.data:" + data + "\r\n");
			data = CommonUtil.deciphData(data);// 解密
			sb.append("updateCreditApply_HttpHelper.doPost_deciphData:" + data + "\r\n");
			logger.error("returnValue:" + sb);
			if (data != null) {
				JSONObject obj = JSONObject.fromObject(data);
				msg = obj.toString();
			} else {
				logger.error(sb.append("银行未返回结果\r\n").toString());
				msg = Constants.RESULT_FAIL_MSG;
			}
		} else {
			msg = Constants.RESULT_FAIL_MSG;
		}
		return msg;
	}
	
	
}
