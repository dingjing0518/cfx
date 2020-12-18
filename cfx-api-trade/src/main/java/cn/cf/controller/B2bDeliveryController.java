package cn.cf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.FileOperationService;
import cn.cf.util.ServletUtils;

@RequestMapping("trade")
@RestController
public class B2bDeliveryController {
	
	@Autowired
	private B2bFacadeService b2bFacadeService;
	
	@Autowired
	private FileOperationService fileOperationService;
	
    /**
     * 提货详情
     * @param request
     * @return
     */
    @RequestMapping(value = "deliveryDetails", method = RequestMethod.POST)
    public String searchContractList(HttpServletRequest req) {
    	String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		Integer status = ServletUtils.getIntParameterr(req, "status", 1);
        return RestCode.CODE_0000.toJson(b2bFacadeService.searchDeliveryDto(orderNumber, status));

    }
    
    /**
     * 提货申请
     * @param request
     * @return
     */
    @RequestMapping(value = "createDeliveryOrder", method = RequestMethod.POST)
    public String createDeliveryOrder(HttpServletRequest req) {
    	String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
    	String json = ServletUtils.getStringParameter(req, "order","");
    	String rest = null;
    	if("".equals(orderNumber) || "".equals(json)){
    		rest = RestCode.CODE_A001.toJson();
    	}else{
    		rest = b2bFacadeService.createDeliveryOrder(orderNumber, json);
    	}
        return rest;
    }
    
    /**
     * 提货申请
     * @param request
     * @return
     */
    @RequestMapping(value = "downloadDelivery", method = RequestMethod.POST)
    public void downloadDelivery(HttpServletRequest req,HttpServletResponse resp) {
    	String deliveryNumber = ServletUtils.getStringParameter(req, "deliveryNumber","");
    	Integer type = ServletUtils.getIntParameterr(req, "type", 1);
    	fileOperationService.downloadDelivery(deliveryNumber, type, resp);
    	return;
    }
}
