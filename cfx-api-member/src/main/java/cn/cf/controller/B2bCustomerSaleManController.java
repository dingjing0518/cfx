package cn.cf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bMemberDto;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * 
 * @description:业务员管理
 * @author FJM
 * @date 2018-4-20 上午11:09:17
 */
@RestController
@RequestMapping("member")
public class B2bCustomerSaleManController extends BaseController{
	
	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;
	
	/**
	 * 获取业务员信息(此接口提供给下订单时候调用)
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "getSalesman", method = RequestMethod.POST)
	public String getSalesman(HttpServletRequest req,HttpServletResponse resp){
		String rest = RestCode.CODE_S999.toJson();
		String companyPk = ServletUtils.getStringParameter(req, "companyPk", "");
		String purchaserPk = ServletUtils.getStringParameter(req, "purchaserPk", "");
		String productPk = ServletUtils.getStringParameter(req, "productPk", "");
		String storePk = ServletUtils.getStringParameter(req, "storePk", "");
		B2bMemberDto dto =  b2bCustomerSaleManService.getSaleMan(companyPk, purchaserPk, productPk, storePk);
		if(null != dto){
			rest = RestCode.CODE_0000.toJson(dto);
		}
		return rest;
	}
	

	
}
