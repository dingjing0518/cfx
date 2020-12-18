package cn.cf.controller;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.dto.B2bEconomicsPurposecustDto;
import cn.cf.dto.B2bFinanceRecordsDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.onlinepay.OnlinepayService;
import cn.cf.service.platform.B2bCompanyBankcardService;
import cn.cf.service.platform.B2bCreditService;
import cn.cf.service.platform.B2bEconomicsGoodsService;
import cn.cf.service.platform.B2bEconomicsPurposecustService;
import cn.cf.service.platform.EconomicsFacadeService;
import cn.cf.service.platform.FileOperationService;
import cn.cf.service.platform.FinanceRecordsService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 授信管理
 * @description:
 * @author FJM
 * @date 2018-5-29 下午2:54:49
 */
@RestController
@RequestMapping("economics")
public class CreditController extends BaseController {

	@Autowired
	private B2bCreditService creditService;

	@Autowired
	private EconomicsFacadeService economicsFacadeService;

	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;

	@Autowired
	private B2bCompanyBankcardService bankcardService;

	@Autowired
	private B2bEconomicsGoodsService economicsGoodsService;

	@Autowired
	private B2bEconomicsPurposecustService economicsPurposecustService;

	@Autowired
	private OnlinepayService onlinepayService;

	@Autowired
	private FinanceRecordsService financeRecordsService;

	@Autowired
	private FileOperationService fileOperationService;

	/**
	 * 意向客户申请
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "applicationPurposecust", method = RequestMethod.POST)
	public String applicationPurposecust(HttpServletRequest request) {
		B2bEconomicsPurposecustDto dto = new B2bEconomicsPurposecustDto();
		dto.bind(request);
		if (null == dto.getType()) {
			dto.setType(1);
		}
		String code = ServletUtils.getStringParameter(request, "code", "");
		return economicsPurposecustService.addEconomicsPurposecust(dto, code);
	}

	/**
	 * 白条申请
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "applicationLous", method = RequestMethod.POST)
	public String applicationLous(HttpServletRequest request) {
		B2bCreditDtoEx credit = new B2bCreditDtoEx();
		credit.bind(request);
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		B2bMemberDto member = this.getMemberBysessionsId(request);
		return economicsFacadeService.applicationLous(credit, company, member);
	}

	/**
	 * 白条额度信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "lousDetails", method = RequestMethod.POST)
	public String lousStatus(HttpServletRequest request) {
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		String rest = economicsFacadeService.searchLous(company);
		if (null != rest) {
			huaxianhuiBankService.updateCreditAmount(company);
		}
		return rest;
	}

	/**
	 * 白条申请状态信息
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "creditDetails", method = RequestMethod.POST)
	public String creditDetails(HttpServletRequest request) {
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		return economicsFacadeService.searchCredit(company);
	}

	/**
	 * 获取金融产品
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchEconomicsGoods", method = RequestMethod.POST)
	public String searchEconomicsGoods(HttpServletRequest req) {
		return RestCode.CODE_0000.toJson(economicsGoodsService.searchList());
	}

	/**
	 * 获取实体账户
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getEntityAccountByCompany", method = RequestMethod.POST)
	public String getEntityAccountByCompany(HttpServletRequest req) {
		String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
		if ("".equals(companyPk)) {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			companyPk = company.getPk();
		}
		return RestCode.CODE_0000.toJson(bankcardService.getCompanyBankCardList(companyPk));
	}


	/**
	 * 申请白条信息修改
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "updateLous", method = RequestMethod.POST)
	public String updateLous(HttpServletRequest req) {
		B2bCreditDtoEx credit = new B2bCreditDtoEx();
		credit.bind(req);
		String code = ServletUtils.getStringParameter(req, "code", "");
		if (null == credit.getCompanyPk() || "".equals(credit.getCompanyPk())) {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			credit.setCompanyPk(company.getPk());
		}
		return economicsFacadeService.updateLous(credit, code);
	}

	/***
	 * 是否已设支付密码
	 */
	@RequestMapping(value = "hasPassword", method = RequestMethod.POST)
	public String hasPassword(HttpServletRequest req) {
		RestCode code = RestCode.CODE_0000;
		String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
		if ("".equals(companyPk)) {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			companyPk = company.getPk();
		}
		B2bCreditDto dto = creditService.getCredit(companyPk, null);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != dto && null != dto.getVirtualPayPassword() && !"".equals(dto.getVirtualPayPassword())) {
			//已设置密码
			map.put("status", 1);
		} else {
			//未设置密码
			map.put("status", 2);
		}
		return code.toJson(map);
	}

	/***
	 * 设置支付密码
	 */
	@RequestMapping(value = "addPayPassword", method = RequestMethod.POST)
	public String addPayPassword(HttpServletRequest req) {
		String rest = null;
		String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
		String password = ServletUtils.getStringParameter(req, "password", "");
		if ("".equals(companyPk)) {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			companyPk = company.getPk();
		}
		if ("".equals(password)) {
			rest = RestCode.CODE_A001.toJson();
		} else {
			rest = economicsFacadeService.updatePayPassword(companyPk, null, password);
		}
		return rest;
	}

	/***
	 * 支付密码修改
	 */
	@RequestMapping(value = "editPayPassword", method = RequestMethod.POST)
	public String editPayPassword(HttpServletRequest req) {
		String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
		String oldPassword = ServletUtils.getStringParameter(req, "oldPassword", "");
		String newPassword = ServletUtils.getStringParameter(req, "newPassword", "");
		if ("".equals(companyPk)) {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			companyPk = company.getPk();
		}
		return economicsFacadeService.updatePayPassword(companyPk, oldPassword, newPassword);
	}

	/**
	 * 忘记支付密码
	 */
	@RequestMapping(value = "retrievalPayPassword", method = RequestMethod.POST)
	public String retrievalPayPassword(HttpServletRequest req) {
		B2bCreditDtoEx credit = new B2bCreditDtoEx();
		credit.bind(req);
		String code = ServletUtils.getStringParameter(req, "code", "");
		if (null == credit.getCompanyPk() || "".equals(credit.getCompanyPk())) {
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			credit.setCompanyPk(company.getPk());
		}
		return economicsFacadeService.updateLous(credit, code);
	}

	/**
	 * 跳转融资页面
	 */
	@RequestMapping(value = "jumpLoanPage", method = RequestMethod.POST)
	public void jumpLoanPage(HttpServletRequest req, HttpServletResponse resp) {
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber");
		huaxianhuiBankService.jumpLoanPage(this.getSource(req), orderNumber, resp);
	}

	/**
	 * 线上支付产品
	 *
	 * @param req
	 */
	@RequestMapping(value = "onlinePayGoods", method = RequestMethod.POST)
	public String onlinePayGoods(HttpServletRequest req) {
		return RestCode.CODE_0000.toJson(onlinepayService.searchOnlinePayGoods());
	}


	/**
	 * 查询交易记录
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchFinancesList", method = RequestMethod.POST)
	public String searchFinancesList(HttpServletRequest request) {
		Map<String, Object> map = paramsToMap(request);
		if (null == map.get("companyPk")) {
			B2bCompanyDto company = this.getCompanyBysessionsId(request);
			map.put("companyPk", company.getPk());
		}
		map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
		return RestCode.CODE_0000.toJson(financeRecordsService.searchFinanceRecordsDtoEx(map));
	}

	/**
	 * 导出交易记录
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "exportFinancesList", method = RequestMethod.POST)
	public void exportFinancesList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = paramsToMap(request);
		if (null == map.get("companyPk")) {
			B2bCompanyDto company = this.getCompanyBysessionsId(request);
			map.put("companyPk", company.getPk());
		}
		PageModel<B2bFinanceRecordsDtoEx> pm = financeRecordsService.searchFinanceRecordsDtoEx(map);
		fileOperationService.exportFinanceRecords(pm.getDataList(), request, response);
		return;
	}



}