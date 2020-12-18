package cn.cf.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.common.HuaxianhuiBillService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 支付接口
 * @description:
 * @author FJM
 * @date 2019-7-16 下午4:21:43
 */
@RestController
@RequestMapping("economics")
public class PayController extends BaseController{
	
	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;
	
	@Autowired
	private HuaxianhuiBillService huaxianhuiBillService;

	
	/**
	 * 金融产品支付
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "payOrder", method = RequestMethod.POST)
	public String payOrder(HttpServletRequest request) {
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber","");
		String password = ServletUtils.getStringParameter(request, "password","");
		String paymentType=ServletUtils.getStringParameter(request, "paymentType", "3");
		String paymentName = ServletUtils.getStringParameter(request, "paymentName","");
		String creditGoodsPk = ServletUtils.getStringParameter(request, "creditGoodsPk","");
		String contractNo = ServletUtils.getStringParameter(request, "contractNo","");
		B2bMemberDto member = this.getMemberBysessionsId(request);
		String rest = null;
		if(("".equals(orderNumber) && "".equals(contractNo)) || "".equals(password) || null==paymentType || "".equals(paymentName) || "".equals(creditGoodsPk)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			Map<String,Object> resMap = huaxianhuiBankService.updateOrder(orderNumber,contractNo,password, company, Integer.parseInt(paymentType), paymentName,creditGoodsPk,member);
			rest = resMap.get("rest").toString();
		}
		return rest;
	}
	
	
	/**
	 * 线上支付
	 * @param req
	 */
	@RequestMapping(value = "onlinePay", method = RequestMethod.POST)
	public String onlinePay(HttpServletRequest req){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		String onlineGoodsPk = ServletUtils.getStringParameter(req, "onlineGoodsPk","");
		String paymentName = ServletUtils.getStringParameter(req, "paymentName","");
		Integer paymentType = ServletUtils.getIntParameterr(req, "paymentType", 4);
		if((!"".equals(orderNumber) || !"".equals(contractNo)) && !"".equals(onlineGoodsPk)
				&& !"".equals(paymentName)){
			rest = huaxianhuiBankService.onlinePay(orderNumber, contractNo, onlineGoodsPk,paymentName,paymentType);
		}else{
			rest =  RestCode.CODE_A001.toJson();
		}
		return rest;
	}
	
	
	/**
	 * 票据支付
	 * @param req
	 */
	@RequestMapping(value = "billPay", method = RequestMethod.POST)
	public String billPay(HttpServletRequest req){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		String billGoodsPk = ServletUtils.getStringParameter(req, "billGoodsPk","");
		String paymentName = ServletUtils.getStringParameter(req, "paymentName","");
		Integer paymentType = ServletUtils.getIntParameterr(req, "paymentType", 5);
		if((!"".equals(orderNumber) || !"".equals(contractNo)) && !"".equals(billGoodsPk)
				&& !"".equals(paymentName)){
			rest =	huaxianhuiBillService.payOrder(orderNumber, contractNo, paymentType,paymentName,billGoodsPk);
		}else{
			rest =  RestCode.CODE_A001.toJson();
		}
		return rest;
	}
	
	/**
	 * 在线贴现
	 * @param req
	 */
	@RequestMapping(value = "acceptTx", method = RequestMethod.POST)
	public String acceptTx(HttpServletRequest req){
		String rest = null;
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		String billGoodsPk = ServletUtils.getStringParameter(req, "billGoodsPk","");
		if(!"".equals(billGoodsPk)){
			rest = huaxianhuiBillService.acceptTx(company.getPk(),billGoodsPk);
		}else{
			rest =  RestCode.CODE_A001.toJson();
		}
		return rest;
	}
}
