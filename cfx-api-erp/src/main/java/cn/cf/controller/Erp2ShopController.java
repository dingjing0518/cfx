package cn.cf.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import ch.qos.logback.classic.Logger;
import cn.cf.common.KeyWordUtil;
import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.CustomerInfoDto;
import cn.cf.entity.B2bCorrespondenceInfoEx;
import cn.cf.entity.B2bOrderGoodsUpdateOrderShiped;
import cn.cf.entity.B2bOrderGoodsUpdateOrderStatus;
import cn.cf.entity.B2bOrderGoodsUpdateWeight;
import cn.cf.entity.B2bOrderUpdateOrderShiped;
import cn.cf.entity.B2bOrderUpdateOrderStatus;
import cn.cf.entity.B2bOrderUpdateWeight;
import cn.cf.entity.ContractGoodsSync;
import cn.cf.entity.ContractStatusSync;
import cn.cf.entity.ContractSync;
import cn.cf.entity.ErpCorrespondenceInfo;
import cn.cf.entity.GoodsDeliverySync;
import cn.cf.entity.LogisticsDeliverySync;
import cn.cf.entity.SubContractGoodsSync;
import cn.cf.entity.SubContractSync;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.service.B2bCompanyService;
import cn.cf.service.B2bOrderService;
import cn.cf.service.HuaxianhuiCrmService;
import cn.cf.service.HuaxianhuiService;
import cn.cf.service.LogisticsService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController

@RequestMapping(value = { "/erp", "/cf_api/erp", "/erp/erp" })
public class Erp2ShopController extends BaseController {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(Erp2ShopController.class);
	
	@Autowired
	private B2bOrderService orderService;
	
	@Autowired
	private B2bCompanyService b2bCompanyService;
	
	@Autowired
	private LogisticsService logisticsService;
	
	@Autowired
	private HuaxianhuiCrmService huaxianhuiCrmService;
	
	@Autowired
	private HuaxianhuiService huaxianhuiService;
	
	

	/**
	 * 获取客户业务员信息扩展
	 * 
	 * @param token
	 * @param jsonDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getCorrespondenceInfo", method = RequestMethod.POST)
	public String getCorrespondenceInfo(HttpServletRequest request) {
		String jsonData = ServletUtils.getStringParameter(request, "jsonData");
		B2bTokenDto b2btoken = JedisUtils.get(ServletUtils.getStringParameter(request, "token"), B2bTokenDto.class);
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("correspondenceInfo", ErpCorrespondenceInfo.class);
		B2bCorrespondenceInfoEx b2bCorrespondenceInfoEx = JsonUtils.toJSONMapBean(jsonData,B2bCorrespondenceInfoEx.class, map);
		return orderService.editCorrespondenceInfo(b2bCorrespondenceInfoEx,b2btoken);
	}

	/**
	 * 合同同步(crm -->b2b)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "contractSync", method = RequestMethod.POST)
	public String contractSync(HttpServletRequest request) throws Exception {
		String result = null;
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		logger.error("++++++++++++++++++++++++contractSync_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			Map<String, Class> map = new HashMap<String, Class>();
			map.put("items", SubContractSync.class);
			ContractSync contractSync = JsonUtils.toJSONMapBean(jsonStr, ContractSync.class, map);
			logger.error("++++++++++++++++++++++++contractSync_Obj:"+JsonUtils.convertToString(contractSync));
			try {
				result = orderService.updateContract(contractSync);
			} catch (Exception e) {
				logger.error("++++++++++++++++++++++contractSync_Exception:",e);
				result = RestCode.CODE_S999.toJson();
			}
			if(null != result && result.contains(RestCode.CODE_0000.getCode())){
				orderService.afterCmsContract(contractSync.getContractNo(), 3, Block.CF.getValue());
			}
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
	
	/**
	 * 合同审批状态(crm -->b2b)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "contractStatus", method = RequestMethod.POST)
	public String contractStatus(HttpServletRequest request) {
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		logger.error("++++++++++++++++++++++++contractStatus_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			ContractStatusSync contractStatusSync = JsonUtils.toBean(jsonStr, ContractStatusSync.class);
			logger.error("++++++++++++++++++++++++++contractStatus_Obj:"+JsonUtils.convertToString(contractStatusSync));
			try {
				result = orderService.updateContractStatus(contractStatusSync);
			} catch (Exception e) {
				logger.error("++++++++++++++++++++++++++contractStatus_Exception:"+e.toString());
				result = RestCode.CODE_S999.toJson();
			}
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
	
	/**
	 * 合同发货(crm -->b2b)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "contractSend", method = RequestMethod.POST)
	public String contractSend(HttpServletRequest request){
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		logger.error("++++++++++++++++++++++++contractSend_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			Map<String, Class> map = new HashMap<String, Class>();
			map.put("items", SubContractGoodsSync.class);
			ContractGoodsSync contractSync = JsonUtils.toJSONMapBean(jsonStr, ContractGoodsSync.class, map);
			logger.error("++++++++++++++++++++++++++++++++contractSend_Obj:"+JsonUtils.convertToString(contractSync));
			try {
				result = orderService.shipmentContractGoods(contractSync);
			} catch (Exception e) {
				logger.error("contractSend_Exception:"+e.toString());
				result = RestCode.CODE_S999.toJson();
			}
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}

	
    /**
     * 订单分货(crm->b2b)
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "orderDivision", method = RequestMethod.POST)
    public String orderDivision(HttpServletRequest request) {
        String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
        logger.error("+++++++++++++++++++++++++++++orderDivision_jsonStr"+jsonStr);
        String rest = RestCode.CODE_0000.toJson();
        try {
        	Map<String, Class> map = new HashMap<String, Class>();
        	map.put("items", B2bOrderGoodsUpdateWeight.class);
        	B2bOrderUpdateWeight b2bOrderWeightDto = JsonUtils.toJSONMapBean(jsonStr, B2bOrderUpdateWeight.class, map);
        	huaxianhuiService.updateDivision(b2bOrderWeightDto.getOrderNumber(), b2bOrderWeightDto.getItems());
		} catch (Exception e) {
			logger.error("orderDivision",e);
			rest = RestCode.CODE_S999.toJson();
		}
        return rest;
    }
    
    
    /**
     * 订单状态同步
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "orderStatus", method = RequestMethod.POST)
    public String orderStatus(HttpServletRequest request) {
        String rest = RestCode.CODE_0000.toJson();
    	try {
    		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
    		logger.error("+++++++++++++++++++++++++++++orderStatus_jsonStr"+jsonStr);
    		Map<String, Class> map = new HashMap<String, Class>();
    		map.put("items", B2bOrderGoodsUpdateOrderStatus.class);
    		B2bOrderUpdateOrderStatus b2bOrderStatusDto = JsonUtils.toJSONMapBean(jsonStr, B2bOrderUpdateOrderStatus.class, map);
    		huaxianhuiService.updateOrderStatus(b2bOrderStatusDto.getOrderNumber(), b2bOrderStatusDto.getItems());
    	} catch (Exception e) {
    		logger.error("orderStatus",e);
    		rest = RestCode.CODE_S999.toJson();
		}
        return rest;
    }
    
    
	 /**
     * 订单发货(crm->b2b)
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "orderSend", method = RequestMethod.POST)
    public String orderSend(HttpServletRequest request) throws Exception {
        String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
        String rest = RestCode.CODE_0000.toJson();
        logger.error("+++++++++++++++++++++++++++++orderSend_jsonStr"+jsonStr);
        Map<String, Class> map = new HashMap<String, Class>();
        map.put("items", B2bOrderGoodsUpdateOrderShiped.class);
        B2bOrderUpdateOrderShiped b2bOrderShipedDto = JsonUtils.toJSONMapBean(jsonStr, B2bOrderUpdateOrderShiped.class, map);
        try {
        	huaxianhuiService.updateOrderShiped(b2bOrderShipedDto.getOrderNumber(), b2bOrderShipedDto.getItems());
		} catch (Exception e) {
			logger.error("orderSend",e);
			rest = RestCode.CODE_S999.toJson();
		}
        return rest;
    }
    
    
    
    
	/**
	 * 新风鸣
	 * 更新订单状态  (crm->b2b)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "orderUpdate", method = RequestMethod.POST)
	public String orderUpdate(HttpServletRequest request) {
		String rest = RestCode.CODE_0000.toJson();
		try {
			String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
			logger.error("+++++++++++++++++++++++++++++orderUpdate_jsonStr" + jsonStr);
			jsonStr = KeyWordUtil.updateChildNumberByJsonStr(jsonStr);
			Map<String, Class> map = new HashMap<String, Class>();
			map.put("items", B2bOrderGoodsUpdateOrderStatus.class);
			B2bOrderUpdateOrderStatus b2bOrderStatusDto = JsonUtils.toJSONMapBean(jsonStr,B2bOrderUpdateOrderStatus.class, map);
			rest = huaxianhuiCrmService.updateOrders(Integer.parseInt(b2bOrderStatusDto.getItems().get(0).getStatus()), jsonStr);
		} catch (Exception e) {
			logger.error("orderStatus", e);
			rest = RestCode.CODE_S999.toJson();
		}
		return rest;
	}

    /**
	 * 客户档案
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "customerInfo", method = RequestMethod.POST)
	public String customerInfo(HttpServletRequest request)  {
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		if (jsonStr != null && !"".equals(jsonStr)) {
			logger.error("+++++++++++++++++++++++++++++customerInfo_jsonStr"+jsonStr);
			JSONObject obj = JSONObject.parseObject(jsonStr);
			String companyId = obj.getString("companyId");
			String companyName = obj.getString("companyName");
			String organizationCode = obj.getString("organizationCode");
			// 三个参数不能同时为空
			if (((companyId == null) || companyId.isEmpty()) && (companyName == null || companyName.isEmpty())
					&& (organizationCode == null || organizationCode.isEmpty())) {
				return RestCode.CODE_T000.toJson();
			}
			if ((companyId == null) || companyId.isEmpty()) {
				if (companyName != null && !companyName.isEmpty()) {
					B2bCompanyDto companyDto = b2bCompanyService.getByName(companyName);
					if (companyDto != null && !companyDto.getPk().isEmpty())
						companyId = companyDto.getPk();
					else {
						if (organizationCode != null && !organizationCode.isEmpty()) {
							companyDto = b2bCompanyService.getByCode(organizationCode);
							if (companyDto != null && !companyDto.getPk().isEmpty())
								companyId = companyDto.getPk();
						}
					}
				} else {
					B2bCompanyDto companyDto = b2bCompanyService.getByCode(organizationCode);
					if (companyDto != null && !companyDto.getPk().isEmpty())
						companyId = companyDto.getPk();
				}
			}
			System.out.println("request = [" + companyId + "]");
			if ((companyId == null) || companyId.isEmpty()) {
				return result;
			}
			B2bCompanyDto companyDto = b2bCompanyService.getByPk(companyId);
			CustomerInfoDto customerInfoDto = new CustomerInfoDto();
			if (null!=companyDto) {
				customerInfoDto.setCompanyId(companyDto.getPk());
				customerInfoDto.setCompanyName(null==companyDto.getName()?"":companyDto.getName());
				customerInfoDto.setRegPhone(null==companyDto.getContactsTel()?"":companyDto.getContactsTel());
				customerInfoDto.setRegAddress(null==companyDto.getRegAddress()?"":companyDto.getRegAddress());
				customerInfoDto.setProvinceName(null==companyDto.getProvinceName()?"":companyDto.getProvinceName());
				customerInfoDto.setCityName(null==companyDto.getCityName()?"":companyDto.getCityName());
				customerInfoDto.setAreaName(null==companyDto.getAreaName()?"":companyDto.getAreaName());
				customerInfoDto.setTaxidNumber(null==companyDto.getOrganizationCode()?"":companyDto.getOrganizationCode());
				customerInfoDto.setBankName(null==companyDto.getBankName()?"":companyDto.getBankName());
				customerInfoDto.setBankAccount(null==companyDto.getBankAccount()?"":companyDto.getBankAccount());
				customerInfoDto.setSource(1);
				customerInfoDto.setBlUrl(null==companyDto.getBlUrl()?"":companyDto.getBlUrl());
				customerInfoDto.setOrganizationCode(null==companyDto.getOrganizationCode()?"":companyDto.getOrganizationCode());
			}
			return RestCode.CODE_0000.toJson(customerInfoDto);
		} else {
			return RestCode.CODE_T000.toJson();
		}
	}
	
	
	
	
	
	/**
	 *  发货派车同步 (crm->b2b)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "syncDelivery", method = RequestMethod.POST)
	public String syncDelivery(HttpServletRequest request) {
			String rest = null;
			try {
				String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
				Map<String, Class> map = new HashMap<String, Class>();
				map.put("goods", GoodsDeliverySync.class);
				LogisticsDeliverySync logisticsDeliverySync = JsonUtils.toJSONMapBean(jsonStr, LogisticsDeliverySync.class, map);
				B2bTokenDto token = JedisUtils.get(request.getParameter("token"), B2bTokenDto.class);
				logisticsDeliverySync.setStorePk(null==token?"":token.getStorePk());
				rest = logisticsService.syncDelivery(logisticsDeliverySync);
			} catch (Exception e) {
				logger.error("syncDelivery",e);
				rest = RestCode.CODE_S999.toJson();
			}
	        return rest;
	}
	
	
	/**
	 *  运输反馈结果 (crm->b2b)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateDelivery", method = RequestMethod.POST)
	public String updateDelivery(HttpServletRequest request) throws Exception {
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		LogisticsDeliverySync logisticsDeliverySync = JsonUtils.toBean(jsonStr, LogisticsDeliverySync.class);
        return logisticsService.updateDelivery(logisticsDeliverySync);
	}
	
	
	
	
	/**
	 * 新风鸣
	 * 产品上下架同步（crm->b2b）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "goodsAll", method = RequestMethod.POST)
	public String goodsAll(HttpServletRequest request){
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		B2bTokenDto tokenDto = JedisUtils.get(request.getParameter("token"), B2bTokenDto.class);
		logger.error("++++++++++++++++++++++++goodsAll_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			try {
				result = huaxianhuiCrmService.syncGoods(tokenDto.getStorePk(), jsonStr);
			} catch (Exception e) {
				logger.error("++++++++++++++++++++++goodsAll_Exception:"+e.toString());
				result = RestCode.CODE_S999.toJson();
			}	
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
	/**
	 * 新风鸣
	 * 产品价格更新（crm->b2b）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "goodsPrice", method = RequestMethod.POST)
	public String goodsPrice(HttpServletRequest request) {
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		B2bTokenDto tokenDto = JedisUtils.get(request.getParameter("token"), B2bTokenDto.class);
		logger.error("++++++++++++++++++++++++goodsPrice_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			try {
				result = huaxianhuiCrmService.syncGoodsPrice(tokenDto.getStorePk(), jsonStr);
			} catch (Exception e) {
				logger.error("++++++++++++++++++++++goodsPrice_Exception:"+e.toString());
				result = RestCode.CODE_S999.toJson();
			}	
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
	
	/**
	 * 新风鸣
	 * 产品库存更新（crm->b2b）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "goodsStock", method = RequestMethod.POST)
	public String goodsStock(HttpServletRequest request) {
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		B2bTokenDto tokenDto = JedisUtils.get(request.getParameter("token"), B2bTokenDto.class);
		logger.error("++++++++++++++++++++++++goodsStock_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			try {
				result = huaxianhuiCrmService.syncGoodsStock(tokenDto.getStorePk(), jsonStr);
			} catch (Exception e) {
				logger.error("++++++++++++++++++++++goodsStock_Exception:"+e.toString());
				result = RestCode.CODE_S999.toJson();
			}	
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
	/**
	 * 
	 * 同步地址（crm->b2b）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "syncAddress", method = RequestMethod.POST)
	public String syncAddress(HttpServletRequest request) {
		String result = RestCode.CODE_0000.toJson();
		String jsonStr = ServletUtils.getStringParameter(request, "jsonData");
		logger.error("++++++++++++++++++++++++syncAddress_jsonStr:"+jsonStr);
		if (jsonStr != null && !"".equals(jsonStr)) {
			try {
				result = huaxianhuiCrmService.syncAddressOne(jsonStr);
			} catch (Exception e) {
				logger.error("++++++++++++++++++++++syncAddress_Exception:"+e.toString());
				result = RestCode.CODE_S999.toJson();
			}	
		}else{
			result = RestCode.CODE_T000.toJson();
		}
		return result;
	}
	
}
