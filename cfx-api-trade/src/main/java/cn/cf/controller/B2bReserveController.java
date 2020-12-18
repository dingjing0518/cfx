package cn.cf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.BackOrder;
import cn.cf.entity.CallBackOrder;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bReserveOrder;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bReserveService;
import cn.cf.util.DateUtil;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/***
 * 
* @description:预约单管理控制层
 * @author ZLB
 * @date 2019-5-30 上午08:39:00
 *
 */
@RestController
@RequestMapping("trade")
public class B2bReserveController extends BaseController {
	
	@Autowired
	private B2bFacadeService facadeService;
	
	
	@Autowired
	private B2bReserveService reserveService;
	
	private final static Logger logger = LoggerFactory.getLogger(B2bReserveController.class);
	/**
	 * 预约单提交
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "submitReserve", method = RequestMethod.POST)
	public String submitReserve(HttpServletRequest req){
		B2bMemberDto member = this.getMemberBysessionsId(req);
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		int source = this.getSource(req);
		String orders = ServletUtils.getStringParameter(req, "orders","");
		String rest = null;
		if("".equals(orders)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			CallBackOrder callBack =  facadeService.submitReserve(orders, company, member, source);
			
			List<BackOrder> backList = new ArrayList<BackOrder>();
			if ("0000".equals(callBack.getCode().getCode())) {// 订单提交后事后处理
				for (B2bReserveOrder order : callBack.getBorder().getRolist()) {
					backList.add(new BackOrder(order));
					if(null==order.getAppointmentTime()||DateUtil.sameDate(new Date(), order.getAppointmentTime())==true){
						facadeService.reservesToOrder(order.getOrderNumber(), member);
					}
					
				}
			}
			rest = callBack.getCode().toJson(backList);
		}
		return rest;
	}
	
	
	 /**
     * 预约单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "searchReserveOrderList", method = RequestMethod.POST)
    public String searchReserveOrderList(HttpServletRequest req) {
    	Map<String, Object> map = this.paramsToMap(req);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		map.put("orderName", ServletUtils.getStringParameter(req, "orderName", "insertTime"));
		map.put("orderType", ServletUtils.getStringParameter(req, "orderType", "desc"));
		String searchType = ServletUtils.getStringParameter(req, "searchType","2");
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		Sessions session = this.getSessions(req);
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
        return RestCode.CODE_0000.toJson(reserveService.searchReserveOrderList(searchType, store, company, map, session, memberDto));

    }
    
    
    /**
     * 预约单数量
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "searchReserveStatusCount", method = RequestMethod.POST)
    public String searchReserveStatusCount(HttpServletRequest req) {
    	Map<String, Object> map = this.paramsToMap(req);
		String searchType = ServletUtils.getStringParameter(req, "searchType","2");
		B2bCompanyDto company = this.getCompanyBysessionsId(req);
		B2bStoreDto store = this.getStoreBysessionsId(req);
		Sessions session = this.getSessions(req);
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
        return RestCode.CODE_0000.toJson(reserveService.searchReserveOrderCount(searchType,store, company, map,session,memberDto));

    }
    
    /**
     * 预约详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getReserveDetails", method = RequestMethod.POST)
    public String getReserveDetails(HttpServletRequest req) {
    	String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
    	String rest = null;
    	if("".equals(orderNumber)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		rest = RestCode.CODE_0000.toJson(reserveService.getReserveDetails(orderNumber));
    	}
        return rest;

    }
    
    
    /**
     * 转化订单
     */
    @RequestMapping(value = "reservesToOrder", method = RequestMethod.POST)
    public String reservesToOrder(HttpServletRequest req){
        B2bMemberDto member = this.getMemberBysessionsId(req);
        String orderNumber = ServletUtils.getStringParameter(req, "orderNumber");
        return facadeService.reservesToOrder(orderNumber, member);
    }
 
    
    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateReserveStatus", method = RequestMethod.POST)
    public String updateReserveStatus(HttpServletRequest req) {
    	Map<String, Object> map = this.paramsToMap(req);
    	 String orderNumber = ServletUtils.getStringParameter(req, "orderNumber");
    	String rest = null;
    	if("".equals(orderNumber)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		try {
    		  int result=	reserveService.updateReserveStatus(map);
    		  if(result==1){
    			  rest = RestCode.CODE_0000.toJson();
    		  }
     			
			} catch (Exception e) {
				logger.error("updateReserveStatus",e);
				rest = RestCode.CODE_S999.toJson();
			} 
    	}
    	return rest;
    }
    
    
 
}
