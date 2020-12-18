package cn.cf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.dto.B2bCreditDtoEx;
import cn.cf.property.PropertyConfig;
import cn.cf.service.platform.B2bCreditGoodsService;
import cn.cf.service.platform.B2bCreditService;
import cn.cf.service.platform.B2bEconomicsGoodsService;
import cn.cf.service.platform.B2bRepaymentService;
import cn.cf.service.platform.EconomicsFacadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.common.RiskControlType;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.json.JsonUtils;
import cn.cf.service.bill.BillOrderService;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.common.HuaxianhuiBillService;
import cn.cf.service.common.HuaxianhuiReportService;
import cn.cf.service.creditreport.ZhongWangFinancialService;
import cn.cf.service.foreign.ForeignBankService;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.onlinepay.OnlinepayService;
import cn.cf.service.platform.B2bLoanNumberService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("economics")
public class ForeginController extends BaseController{

	
	@Autowired
	private ForeignBankService foreignBankService;
	
	@Autowired
	private ForeignCompanyService foreignCompanyService;
	
	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;
	
	@Autowired
	private HuaxianhuiReportService huaxianhuiReportService;
	
	@Autowired
	private HuaxianhuiBillService huaxianhuiBillService;
	
	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private OnlinepayService onlinepayService;
	
	@Autowired
	private BillOrderService billOrderService;
	
	@Autowired
	private ZhongWangFinancialService zhongWangFinancialService;

	@Autowired
	private EconomicsFacadeService economicsFacadeService;

	@Autowired
	private B2bEconomicsGoodsService economicsGoodsService;

	@Autowired
	private B2bCreditService creditService;

	@Autowired
	private B2bRepaymentService b2bRepaymentService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 取消借款单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delLoanOrder", method = RequestMethod.POST)
	public String delLoanOrder(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		Map<String,Object> map = foreignBankService.delLoanOrder(orderNumber);
		logger.error("economics delLoanOrder orderNumber is {}", orderNumber);
		return JsonUtils.convertToString(map);
	}
	
	/**
	 * 查询银行客户号/授信额度(此方法给后台系统调用)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchCustomerAmount", method = RequestMethod.POST)
	public String searchCustomerAmount(HttpServletRequest req,HttpServletResponse response) {
		RestCode code = RestCode.CODE_0000;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			String companyPk =  ServletUtils.getStringParameter(req, "companyPk","");
			if(!"".equals(companyPk)){
				B2bCompanyDto company =  foreignCompanyService.getCompany(companyPk);
					try {
						huaxianhuiBankService.updateCreditAmount(company);
					} catch (Exception e) {
						code = RestCode.CODE_S999;
						logger.error("errorCustomerAmount",e);
					}
			}else{
				code = RestCode.CODE_A001;
			}
		} catch (Exception e) {
			code = RestCode.CODE_S999;
			logger.error("errorCustomerAmount",e);
		}
		return code.toJson();
	}
	
	/**
	 * 根据订单号查询放款(此方法给后台系统调用)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searhLoanByOrderNumber", method = RequestMethod.POST)
	public String searhLoanByOrderNumber(HttpServletRequest req,HttpServletResponse response) {
		RestCode code = RestCode.CODE_0000;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			String orderNumber =  ServletUtils.getStringParameter(req, "orderNumber","");
			B2bLoanNumberDto o =  b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
			if(null != o){
				//只有订单在申请中的时候可以调用
				if(o.getLoanStatus() == 2){
					huaxianhuiBankService.updateLoanDetails(o);
				}
			}
		} catch (Exception e) {
			code = RestCode.CODE_S999;
			logger.error("searhLoanByOrderNumber",e);
		}
		return code.toJson();
	}
	
	/**
	 * 根据订单号查询还款(此方法给后台系统调用)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searhRepaymentByOrderNumber", method = RequestMethod.POST)
	public String searhRepaymentByOrderNumber(HttpServletRequest req,HttpServletResponse response) {
		RestCode code = RestCode.CODE_0000;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			String orderNumber =  ServletUtils.getStringParameter(req, "orderNumber","");
			B2bLoanNumberDto o =  b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
			if(null != o){
				//只有订单在部分还款或者申请成功的时候可以调用
				if(o.getLoanStatus() == 3 || o.getLoanStatus() == 6){
					huaxianhuiBankService.updateRepaymentDetails(o);
				}
			}
		} catch (Exception e) {
			code = RestCode.CODE_S999;
			logger.error("searhRepaymentByOrderNumber",e);
		}
		return code.toJson();
	}
	
	/**
	 * 查询征信文件(此方法给后台系统调用)
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "downLoadWzFile",method = RequestMethod.POST)
	public String downLoadWzFile(HttpServletRequest req,HttpServletResponse response){
		String rest = null;
		response.setHeader("Access-Control-Allow-Origin", "*");
		String companyPk =  ServletUtils.getStringParameter(req, "companyPk","");
		String shotName =  ServletUtils.getStringParameter(req, "shotName",RiskControlType.WZSY.getValue());
		B2bCompanyDto company = foreignCompanyService.getCompany(companyPk);
		if(null != company){
			if(shotName.equals(RiskControlType.WZSY.getValue())){
				rest = huaxianhuiReportService.searchWzFile(company);
			}
			if(shotName.equals(RiskControlType.RAYX.getValue())){
				rest = huaxianhuiReportService.searchRayx(company);
			}
		}else{
			rest = RestCode.CODE_A001.toJson();
		}
		return rest;
	}
	
	
	/**
	 * 代扣服务费接口 (此方法给后台系统调用)
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateAgentPay",method = RequestMethod.POST)
	public String updateAgentPay(HttpServletRequest req,HttpServletResponse response){
		String rest = RestCode.CODE_A001.toJson();
		response.setHeader("Access-Control-Allow-Origin", "*");
		String id =  ServletUtils.getStringParameter(req, "id","");
		if(!"".equals(id)){
			Criteria c = new Criteria();
			c.and("id").is(id);
			c.and("agentPayStatus").is(3);
			List<B2bRepaymentRecord> list = mongoTemplate.find(new Query(c), B2bRepaymentRecord.class);
			if(null != list && list.size()>0){
				B2bRepaymentRecord dto = list.get(0);
				B2bLoanNumberDto loanNumberDto  = b2bLoanNumberService.getB2bLoanNumberDto(dto.getOrderNumber());
				if(null != loanNumberDto){
					rest = RestCode.CODE_0000.toJson();
					try {
						huaxianhuiBankService.updateAgentPay(loanNumberDto, list);
					} catch (Exception e) {
						rest = RestCode.CODE_S999.toJson();
						logger.error("updateAgentPay",e);
					}
				}
			}
		}
		return rest;
	}
	
	/**
	 * 查询线上支付订单状态(此方法给后台系统调用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateOnlineRecord", method = RequestMethod.POST)
	public String updateOnlineRecord(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		String rest = RestCode.CODE_0000.toJson();
		B2bOnlinepayRecordDto record =  onlinepayService.getByOrderNumer(orderNumber);
		if(null != record && record.getStatus() ==1){
			try {
				huaxianhuiBankService.onlinePaySearch(record);
			} catch (Exception e) {
				logger.error("updateOnlineRecord",e);
			}
		}
		return rest;
	}
	
	/**
	 * 关闭线上支付订单状态(此方法暂时未用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cancelOnlineRecord", method = RequestMethod.POST)
	public String cancelOnlineRecord(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		String rest = RestCode.CODE_0000.toJson();
		B2bOnlinepayRecordDto record =  onlinepayService.getByOrderNumer(orderNumber);
		if(null != record && record.getStatus() !=3){
			try {
				rest = JsonUtils.convertToString(huaxianhuiBankService.onlinePayCancel(record));
			} catch (Exception e) {
				logger.error("updateOnlineRecord",e);
			}
		}
		return rest;
	}
	/**
	 * 确认放款/取消放款(此方法给后台系统调用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "insertLoan", method = RequestMethod.POST)
	public String insertLoan(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		//status 1 确认放款  2取消放款
		Integer status = ServletUtils.getIntParameter(request, "status",1);
		String rest = null;
		B2bLoanNumberDto o =  b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		if(null != o){
			rest = huaxianhuiBankService.updateEntrustLoanDetails(o,status);
		}
		return rest;
	}
	/**
	 * 手动还款接口(此方法给后台系统调用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "insertRepayment", method = RequestMethod.POST)
	public String insertRepayment(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		Double principal = ServletUtils.getDoubleParameter(request, "principal", 0d);
		Double interest = ServletUtils.getDoubleParameter(request, "interest",0d);
		B2bLoanNumberDto o =  b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		String rest = null;
		if(null != o){
			rest = huaxianhuiBankService.updateEntrusRepayment(o, principal, interest);
		}
		return rest;
	}
	
	/**
	 * 绑定票据产品(此方法给后台系统调用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bindBillGoods", method = RequestMethod.POST)
	public String bindBillGoods(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String companyPk=ServletUtils.getStringParameter(request, "companyPk", "");
		String account=ServletUtils.getStringParameter(request, "account", "");
		String bankNo=ServletUtils.getStringParameter(request, "bankNo", "");
		String bindType=ServletUtils.getStringParameter(request, "bindType", "");//1绑定  2解绑 3供应商解签
		B2bCompanyDto company = foreignCompanyService.getCompany(companyPk);
		if(null!=company){
			return huaxianhuiBillService.bindBill(companyPk, company.getName(), company.getOrganizationCode(), account, bankNo, bindType);

		}else{
			return  RestCode.CODE_A001.toJson();
		}
	}
	
	/**
	 * 票据供应商签约(此方法提供给后台使用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "billSinging", method = RequestMethod.POST)
	public String billSinging(HttpServletRequest request) {
		String companyPk = ServletUtils.getStringParameter(request, "companyPk","");
		return huaxianhuiBillService.billSinging(companyPk);
	}
	
	
	/**
	 * 根据票据单号查询票据(此方法给后台系统调用)
	 * @param req
	 * @return
	 */
//	@RequestMapping(value = "searchBillByOrderNumber", method = RequestMethod.POST)
//	public String searchBillByOrderNumber(HttpServletRequest req,HttpServletResponse response) {
//		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");//票据单号
//		return huaxianhuiBillService.searchBillInfo(orderNumber);
//	}
	/***
	 * 票据查询(此方法暂未使用)
	 * @param req
	 * @return
	 */
//	@RequestMapping(value = "searchBillPft", method = RequestMethod.POST)
//	public String searchBillPft(HttpServletRequest req) {
//		String companyPk=ServletUtils.getStringParameter(req, "companyPk", "");
////		String organizationCode=ServletUtils.getStringParameter(req, "organizationCode", "");
////		
//
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("bindStatus", 1);
//		map.put("isVisable", 1);
//		map.put("companyPk", companyPk);
//		try {
//			List<B2bBillCusgoodsDtoEx> list = cusGoodsService.searchBillCusGoodsList(map);
//			if(null != list && list.size()>0){
//				for(B2bBillCusgoodsDtoEx dto : list){
//					huaxianhuiBillService.searchBillPft(dto);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("errorbillcusgoods:",e);
//		}
//		try {
//			map.put("status", 1);
//			map.put("isDelete", 1);
//			List<B2bBillSigningDto> signlist = billSingingService.getByMap(map);
//			if(null != signlist && signlist.size()>0){
//				for(B2bBillSigningDto sign:signlist){
//					huaxianhuiBillService.searchBillSignPft(sign);
//				}
//			}
//		} catch (Exception e) {
//			logger.error("errorbillcusgoods:",e);
//		}
//	
//		return RestCode.CODE_0000.toJson();
//	}
	
	/**
	 * 关闭票据订单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "cancelBillOrder", method = RequestMethod.POST)
	public String cancelBillOrder(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		String rest = RestCode.CODE_0000.toJson();
		B2bBillOrderDto order =  billOrderService.getBillOrder(orderNumber);
		if(null != order && order.getStatus() !=3){
			try {
				rest = JsonUtils.convertToString(huaxianhuiBillService.billPayCancel(order));
			} catch (Exception e) {
				logger.error("updateOnlineRecord",e);
			}
		}
		return rest;
	}
	
	/**
	 * 已完成的订单执行票据解锁及完成订单操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "completeBillOrder", method = RequestMethod.POST)
	public String completeBillOrder(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		return huaxianhuiBillService.searchBillInfo(orderNumber);
	}
	
	/**
	 * 贴现额度查询(此方法暂未使用)
	 * @param request
	 * @return
	 */
//	@RequestMapping(value = "searchAmountTx", method = RequestMethod.POST)
//	public String searchAmountTx(HttpServletRequest request) {
//		return huaxianhuiBillService.searchAmountTx();
//	}
	
	
	/**
	 * 授信客户发票同步(此方法给后台系统调用)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "syncCreditInvoice", method = RequestMethod.POST)
	public String syncCreditInvoice(HttpServletRequest req,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String creditPk = ServletUtils.getStringParameter(req, "creditPk","");//pk
		String companyPk = ServletUtils.getStringParameter(req, "companyPk","");//公司pk
		Integer dataType = ServletUtils.getIntParameterr(req, "dataType", 1);//1进项 2销项
		String startDate = ServletUtils.getStringParameter(req, "startDate", "");
		String endDate = ServletUtils.getStringParameter(req, "endDate","");
		return zhongWangFinancialService.syncCreditInvoice(creditPk, companyPk, dataType, startDate, endDate);
				
	}
	
	/**
	 * 客户申请
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "creditCustomerApply", method = RequestMethod.POST)
	public String creditCustomerApply(HttpServletRequest req,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String companyPk = ServletUtils.getStringParameter(req, "companyPk","");//公司pk
		return huaxianhuiBankService.creditCustomerApply(companyPk);
				
	}

	/**
	 * 客户额度查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchCustomerBySh", method = RequestMethod.POST)
	public String searchCustomerBySh(HttpServletRequest req,HttpServletResponse response) {
		String rest = null;
		response.setHeader("Access-Control-Allow-Origin", "*");
		String companyName = ServletUtils.getStringParameter(req, "companyName","");//公司名称
		String sign = ServletUtils.getStringParameter(req, "sign","");//公司名称
		if(null == sign || "".equals(sign) || !"sign".equals(sign)){
			rest = RestCode.CODE_S999.toJson();
		}else{
			B2bCompanyDto company = foreignCompanyService.getCompanyByName(companyName);
			if(null == company){
				rest = RestCode.CODE_A004.toJson();
			}else{
				 rest= RestCode.CODE_0000.toJson(economicsFacadeService.searchCredit(company));
				 huaxianhuiBankService.updateCreditAmount(company);
			}
		}
		return rest;

	}

	/**
	 * 金融产品查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchEconomicsGoodsBySh", method = RequestMethod.POST)
	public String searchEconomicsGoodsBySh(HttpServletRequest req,HttpServletResponse response) {
		return RestCode.CODE_0000.toJson(economicsGoodsService.searchList());
	}


	/**
	 * 金融产品申请
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "applyEconomicBySh", method = RequestMethod.POST)
	public String applyEconomicBySh(HttpServletRequest req,HttpServletResponse response) {
		String rest = RestCode.CODE_0000.toJson();
		String sign = ServletUtils.getStringParameter(req, "sign","");//公司名称
		if(null == sign || "".equals(sign) || !"sign".equals(sign)){
			rest = RestCode.CODE_S999.toJson();
		}else{
			B2bCompanyDto company = foreignCompanyService.getCompanyByName(ServletUtils.getStringParameter(req, "companyName",""));
			if(null == company){
				rest = RestCode.CODE_A004.toJson();
			}else{
				B2bCreditDtoEx credit = new B2bCreditDtoEx();
				credit.bind(req);
				int result = creditService.addCredit(credit, company);
				if(1!= result){
					rest =  RestCode.CODE_S999.toJson();
				}
			}
		}
		return rest;
	}

	/**
	 * 放款记录列表查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "loanOrderListBySh", method = RequestMethod.POST)
	public String loanOrderListBySh(HttpServletRequest req,HttpServletResponse response) {
		String rest = null;
		String sign = ServletUtils.getStringParameter(req, "sign","");//公司名称
		if(null == sign || "".equals(sign) || !"sign".equals(sign)){
			rest = RestCode.CODE_S999.toJson();
		}else{
			B2bCompanyDto company = foreignCompanyService.getCompanyByName(ServletUtils.getStringParameter(req, "companyName",""));
			if(null == company){
				rest = RestCode.CODE_A004.toJson();
			}else{
				Map<String,Object> map = this.paramsToMap(req);
				map.put("purchaserPk",company.getPk());
				rest = RestCode.CODE_0000.toJson(b2bLoanNumberService.searchListByPage(map));
			}
		}
		return rest;
	}

	/**
	 * 放款记录详情查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "loanOrderBySh", method = RequestMethod.POST)
	public String loanOrderBySh(HttpServletRequest req,HttpServletResponse response) {
		String rest = null;
		String sign = ServletUtils.getStringParameter(req, "sign","");//公司名称
		if(null == sign || "".equals(sign) || !"sign".equals(sign)){
			rest = RestCode.CODE_S999.toJson();
		}else{
			B2bCompanyDto company = foreignCompanyService.getCompanyByName(ServletUtils.getStringParameter(req, "companyName",""));
			if(null == company){
				rest = RestCode.CODE_A004.toJson();
			}else{
				String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
				rest = RestCode.CODE_0000.toJson(b2bLoanNumberService.getB2bLoanNumberDto(orderNumber));
			}
		}
		return rest;
	}

	/**
	 * 还款记录列表查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "repaymentListBySh", method = RequestMethod.POST)
	public String repaymentListBySh(HttpServletRequest req,HttpServletResponse response) {
		String rest = null;
		String sign = ServletUtils.getStringParameter(req, "sign","");//公司名称
		if(null == sign || "".equals(sign) || !"sign".equals(sign)){
			rest = RestCode.CODE_S999.toJson();
		}else{
			B2bCompanyDto company = foreignCompanyService.getCompanyByName(ServletUtils.getStringParameter(req, "companyName",""));
			if(null == company){
				rest = RestCode.CODE_A004.toJson();
			}else{
				Map<String,Object> map = this.paramsToMap(req);
				System.out.println(map.toString());
				map.put("companyPk",company.getPk());
				rest = RestCode.CODE_0000.toJson(b2bRepaymentService.searchListByPage(map));
			}
		}
		return rest;
	}

	/**
	 * 还款记录详情查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "repaymentBySh", method = RequestMethod.POST)
	public String repaymentBySh(HttpServletRequest req,HttpServletResponse response) {
		String rest = null;
		String sign = ServletUtils.getStringParameter(req, "sign","");//公司名称
		if(null == sign || "".equals(sign) || !"sign".equals(sign)){
			rest = RestCode.CODE_S999.toJson();
		}else{
			B2bCompanyDto company = foreignCompanyService.getCompanyByName(ServletUtils.getStringParameter(req, "companyName",""));
			if(null == company){
				rest = RestCode.CODE_A004.toJson();
			}else{
				String id = ServletUtils.getStringParameter(req, "id","");
				rest = RestCode.CODE_0000.toJson(b2bRepaymentService.getById(id));
			}
		}
		return rest;
	}
}
