package cn.cf.controller;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bBillCustomerApplyDtoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.service.bill.BillCusGoodsService;
import cn.cf.service.bill.BillGoodsService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 票据管理
 * @description: 
 * @author FJM
 * @date 2019-7-25 上午9:04:06
 */
@RestController
@RequestMapping("economics")
public class BillController extends BaseController{
	@Autowired
	private BillCusGoodsService  cusGoodsService;
	@Autowired
	private BillGoodsService billGoodsService;
	
	

	/**
	 *查询票据客户申请详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchBillCusApply", method = RequestMethod.POST)
	public String searchBillStatus(HttpServletRequest request) {
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		return RestCode.CODE_0000.toJson(cusGoodsService.searchBillCusApply(company));
	}
	
	
	/**
	 *票付通客户申请
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "applyForBill", method = RequestMethod.POST)
	public String applyForBill(HttpServletRequest request) {
		B2bBillCustomerApplyDtoEx billCus=new B2bBillCustomerApplyDtoEx();
		billCus.bind(request);
		B2bCompanyDto company = this.getCompanyBysessionsId(request);
		return cusGoodsService.applyForBill(billCus, company);
	}
	

	/**
	 * 获取客户票付通产品
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchBillCusGoods", method = RequestMethod.POST)
	public String searchBillCusGoods(HttpServletRequest req) {
		String companyPk = ServletUtils.getStringParameter(req, "companyPk","");
		if("".equals(companyPk)){
			B2bCompanyDto company = this.getCompanyBysessionsId(req);
			companyPk = company.getPk();
		}
		return RestCode.CODE_0000.toJson(cusGoodsService.searchBillCusGoodsByCompany(companyPk));
	}
 
	
	/**
	 * 获取票据产品
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchBillGoods", method = RequestMethod.POST)
	public String searchBillGoods(HttpServletRequest req) {
		Map<String, Object> map=this.paramsToMap(req);
		return RestCode.CODE_0000.toJson(billGoodsService.searchBillGoods(map));
	}
}
