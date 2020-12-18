package cn.cf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.ExportOrderDto;
import cn.cf.entity.BackOrder;
import cn.cf.entity.CallBackOrder;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bBindOrder;
import cn.cf.model.B2bOrder;
import cn.cf.service.B2bContractGoodsService;
import cn.cf.service.B2bContractService;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bOrderRecordService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.B2bPaymentService;
import cn.cf.service.B2bTrancsationService;
import cn.cf.service.FileOperationService;
import cn.cf.service.ForeignBankService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * 
 * @description:订单管理控制层
 * @author FJM
 * @date 2018-4-13 下午5:20:27
 */
@RestController
@RequestMapping("trade")
public class B2bOrderController extends BaseController {

	@Autowired
	private B2bOrderService b2bOrderService;
	
	@Autowired
	private B2bFacadeService facadeService;
	
	@Autowired
	private FileOperationService fileOperationService;
	
	@Autowired
	private B2bPaymentService b2bPaymentService;
	
	@Autowired
	private B2bOrderRecordService b2bOrderRecordService;
	
	@Autowired
	private B2bTrancsationService b2bTrancsationService;
	
	@Autowired
	private B2bContractGoodsService b2bContractGoodsService;
	
	@Autowired
	private B2bContractService b2bContractService;
	
	@Autowired
	private ForeignBankService foreignBankService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 订单列表查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchOrderList", method = RequestMethod.POST)
	public String searchOrderList(HttpServletRequest req){
		Map<String, Object> map = this.paramsToMap(req);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		map.put("orderName", ServletUtils.getStringParameter(req, "orderName", "insertTime"));
		map.put("orderType", ServletUtils.getStringParameter(req, "orderType", "desc"));
		map.put("orderClassify", ServletUtils.getStringParameter(req, "orderClassify",""));//订单类型：1正常订单  3竞拍订单  4拼团订单
		String searchType = ServletUtils.getStringParameter(req, "searchType","1");
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		Sessions session = this.getSessions(req);
		PageModel<B2bOrderDtoEx> pm = new PageModel<B2bOrderDtoEx>();
		switch (searchType) {
		case "1"://采购商订单
			pm = facadeService.searchPuOrderList(map,company);
			break;
		case "2"://供应商订单
			pm = facadeService.searchSpOrderList(map,store,company);
			break;
		case "3"://业务员订单
			pm = facadeService.searchEmOrderList(map, session);
			break;
		default:
			break;
		}
		return RestCode.CODE_0000.toJson(pm);
	}
	
	/**
	 * 订单数量查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchOrderCounts", method = RequestMethod.POST)
	public String searchOrderCounts(HttpServletRequest req){
		String result="";
		try {
			Map<String, Object> map = this.paramsToMap(req);
			if ( null==map.get("searchType")||"".equals(map.get("searchType").toString())) {
				return RestCode.CODE_A001.toJson();
			}
			map.put("orderClassify", ServletUtils.getStringParameter(req, "orderClassify",""));//订单类型：1正常订单  3竞拍订单  4拼团订单
			Sessions session = this.getSessions(req);
			result= RestCode.CODE_0000.toJson(facadeService.searchOrderCounts(map, session));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchOrderCounts exception:", e);
			result= RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	/**
	 * 订单提交
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "submitOrder", method = RequestMethod.POST)
	public String submitOrder(HttpServletRequest req){
		B2bMemberDto member = this.getMemberBysessionsId(req);
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		int source = this.getSource(req);
		String orders = ServletUtils.getStringParameter(req, "orders","");
		String rest = null;
		if("".equals(orders)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			CallBackOrder callBack =  facadeService.submitOrder(orders, company, member, source);
			
			List<BackOrder> backList = new ArrayList<BackOrder>();
			if ("0000".equals(callBack.getCode().getCode())) {// 订单提交后事后处理
				for (B2bOrder order : callBack.getBorder().getOlist()) {
					backList.add(new BackOrder(order));
				}
				for (B2bBindOrder border : callBack.getBorder().getBolist()) {
					backList.add(new BackOrder(border));
				}
				facadeService.afterOrder(callBack.getBorder(), callBack.getPorder());
			}
			rest = callBack.getCode().toJson(backList);
		}
		return rest;
	}
	/**
	 * 订单支付	
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "payOrder", method = RequestMethod.POST)
	public String payOrder(HttpServletRequest req){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		String paymentType =ServletUtils.getStringParameter(req, "paymentType ", "1");
		String paymentName = ServletUtils.getStringParameter(req, "paymentName","");
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
		if(!"".equals(orderNumber)){
			rest = facadeService.payOrder(memberDto, orderNumber, Integer.parseInt(paymentType), paymentName);
		}
		if(!"".equals(contractNo)){
			rest = facadeService.payContract(memberDto, contractNo, Integer.parseInt(paymentType), paymentName);
		}
		return rest;
	}
	
	
	/**
	 * 确认收款	
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "receivablesOrder", method = RequestMethod.POST)
	public String receivablesOrder(HttpServletRequest req){
		B2bMemberDto member = this.getMemberBysessionsId(req);
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		return facadeService.receivablesOrder(member, orderNumber);
	}
	
	/**
	 * 订单发货(全部发货/部分发货)	
	 * @param req  201707251403183739849  , 
	 * @return
	 */
	@RequestMapping(value = "deliverOrder", method = RequestMethod.POST)
	public String deliverOrder(HttpServletRequest req){
		B2bMemberDto member = this.getMemberBysessionsId(req);
		String orders = ServletUtils.getStringParameter(req, "orders","");
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		String rest = null;
		if("".equals(orderNumber) && "".equals(contractNo)){
			rest = RestCode.CODE_A001.toJson();
		}else if(!"".equals(orderNumber)){
			rest = facadeService.deliverOrder(member, orderNumber, orders);
		}else{
			rest = facadeService.deliverContract(member, contractNo, orders);
		}
		return rest;
	}
	
	/**
	 * 订单完成（对应页面采购商/供应商确认收货操作）
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "completeOrder", method = RequestMethod.POST)
	public String completeOrder(HttpServletRequest req){
		B2bMemberDto member = this.getMemberBysessionsId(req);
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		RestCode code = RestCode.CODE_0000;
		if("".equals(orderNumber) && "".equals(contractNo)){
			code = RestCode.CODE_A001;
		}else if(!"".equals(orderNumber)){
			code = facadeService.completeOrder(member, orderNumber);
			//异步调用票据完成
			if(RestCode.CODE_0000.getCode().equals(code.getCode())){
				foreignBankService.completeBillOrder(orderNumber);
			}
		}else{
			code = facadeService.completeContract(member, contractNo);
			//异步调用票据完成
			if(RestCode.CODE_0000.getCode().equals(code.getCode())){
				foreignBankService.completeBillOrder(contractNo);
			}
		}
		return code.toJson();
	}
	
	/**
	 * 订单取消
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
	public String cancelOrder(HttpServletRequest req){
		B2bMemberDto member = this.getMemberBysessionsId(req);
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		String rest = null;
		if("".equals(orderNumber) && "".equals(contractNo)){
			rest = RestCode.CODE_A001.toJson();
		}else if(!"".equals(orderNumber)){
			rest = facadeService.cancelOrder(member, orderNumber,true);
		}else{
			rest = facadeService.cancelContract(member, contractNo);
		}
		return rest;
	}
	
	
	/**
	 * 订单删除
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "delOrder", method = RequestMethod.POST)
	public String delOrder(HttpServletRequest req){
		String rest = RestCode.CODE_0000.toJson();
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		Integer isDelete = ServletUtils.getIntParameterr(req, "isDelete", 0);//采购商删除
		Integer isDeleteSp = ServletUtils.getIntParameterr(req, "isDeleteSp", 0);//供应商删除
		B2bOrder order = new B2bOrder();
		order.setOrderNumber(orderNumber);
		if(isDelete>0){
			order.setIsDelete(2);
		}
		if(isDeleteSp>0){
			order.setIsDeleteSp(2);
		}
		try {
			b2bOrderService.updateOrder(order, null);
		} catch (Exception e) {
			logger.error("errordelOrder",e);
			rest = RestCode.CODE_S999.toJson();
		}
		 return rest;
	}
	
	/**
	 * 修改订单重量和价格
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "updateOrderPrice", method = RequestMethod.POST)
	public String updateOrderPrice(HttpServletRequest req){
		String rest = null;
		B2bMemberDto member = this.getMemberBysessionsId(req);
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String orders =  ServletUtils.getStringParameter(req, "orders","");
		if(("".equals(orderNumber)) || "".equals(orders)){
			rest = RestCode.CODE_A001.toJson();
		}else if(!"".equals(orderNumber)){
			rest = facadeService.updateOrderPrice(member, orderNumber, orders);
		}
		return rest;
	}
	/**
	 * 订单商品列表
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "orderGoods", method = RequestMethod.POST)
	public String orderGoods(HttpServletRequest req){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		if("".equals(orderNumber) && "".equals(contractNo)){
			rest = RestCode.CODE_A001.toJson();
		}else if(!"".equals(orderNumber)){
			rest = RestCode.CODE_0000.toJson(b2bOrderService.searchOrderGoodsList(orderNumber));
		}else{
			rest = RestCode.CODE_0000.toJson(b2bContractGoodsService.searchListByContractNo(contractNo, true));
		}
		return rest;
	}
	
	/**
	 * 订单详情
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "orderDetails", method = RequestMethod.POST)
	public String orderDetails(HttpServletRequest req){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		if("".equals(orderNumber) && "".equals(contractNo)){
			rest = RestCode.CODE_A001.toJson();
		}else if(!"".equals(orderNumber)){
			rest = facadeService.orderDetails(orderNumber);
		}else{
			rest = RestCode.CODE_0000.toJson(b2bContractService.getB2bContractDetails(contractNo));
		}
		return rest;
	}
	
	/**
	 * 订单操作流程图
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "orderRecordChart", method = RequestMethod.POST)
	public String orderRecord(HttpServletRequest req){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		if("".equals(orderNumber)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			rest = RestCode.CODE_0000.toJson(b2bOrderRecordService.searchOrderRecordList(orderNumber));
		}
		return rest;
	}
	/**
	 * 订单导出
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/exportOrder", method = RequestMethod.POST)
	public void exportOrder(HttpServletRequest req,HttpServletResponse resp){
		try {
			Map<String, Object> map = this.paramsToMap(req);
			String searchType = ServletUtils.getStringParameter(req, "searchType","1");
			Sessions session = this.getSessions(req);
			List<ExportOrderDto> list = facadeService.exportOrderList(map,session);
			fileOperationService.exportOrder(list, req, resp,session,searchType);
		} catch (Exception e) {
			logger.error("errorExportOrder",e);
		}
		return;
	}
	
	
	/**
	 * 订单合同下载
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/downloadContract", method = RequestMethod.POST)
	public void downloadContract(HttpServletRequest req,HttpServletResponse resp){
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		if(!"".equals(orderNumber)){
			fileOperationService.downContract(orderNumber, req, resp);
		}else{
			fileOperationService.downContractOrder(contractNo, req, resp);
		}
		return;
	}
	
	
	/**
	 * 支付方式
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/searchPayment", method = RequestMethod.POST)
	public String searchPayment(HttpServletRequest req,HttpServletResponse resp){
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		return RestCode.CODE_0000.toJson(b2bPaymentService.searchPayment(orderNumber,contractNo,company.getPk()));
	}
	
	/**
	 * 支付方式List
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/searchPaymentList", method = RequestMethod.POST)
	public String searchPaymentList(HttpServletRequest req,HttpServletResponse resp){
		Map<String, Object> map = new HashMap<>();
		map.put("isVisable", 1);
		return RestCode.CODE_0000.toJson(b2bPaymentService.searchPaymentList(map));
	}
	
	
	/**
	 * 订单交易数据统计
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/searchTransactionList", method = RequestMethod.POST)
	public String searchTrancsationList(HttpServletRequest req,HttpServletResponse resp){
		Map<String, Object> map = this.paramsToMap(req);
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		map.put("start", null==map.get("start")?0:map.get("start"));
		map.put("limit", null==map.get("limit")?10:map.get("limit"));
		return RestCode.CODE_0000.toJson(b2bTrancsationService.searchPageTrancsationList(map,store,company));
	}
	/**
	 * 交易数据导出
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/exportTransaction", method = RequestMethod.POST)
	public void exportTrancsation(HttpServletRequest req,HttpServletResponse resp){
		Map<String, Object> map = this.paramsToMap(req);
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		fileOperationService.exportTrancsation(b2bTrancsationService.searchTrancsationList(map,store, company), req, resp);
		return;
	}
	
	
	
	 /**
     * 合同订单
     */
    @RequestMapping(value = "contractToOrder", method = RequestMethod.POST)
    public String contractToOrder(HttpServletRequest req){
    	  B2bMemberDto member = this.getMemberBysessionsId(req);
    	String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
    	String orders = ServletUtils.getStringParameter(req, "orders","");
    	String address = ServletUtils.getStringParameter(req, "address","");
    	if("".equals(contractNo)){
		  return RestCode.CODE_A001.toJson();
		} 
		return facadeService.contractToOrder(contractNo,member,orders,address);
     }
    
}
