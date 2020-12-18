package cn.cf.controller;

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
import cn.cf.dto.ForB2BLgPriceDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgDeliveryOrderForB2BDto;
import cn.cf.dto.LgDeliveryOrderForB2BPTDto;
import cn.cf.dto.LgLogisticsNameDto;
import cn.cf.dto.LgSearchPriceForB2BDto;
import cn.cf.json.JsonUtils;
import cn.cf.service.LgCompanyService;
import cn.cf.service.LgDeliveryOrderService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("/logistics")
public class B2bController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(B2bController.class);
	
	@Autowired
	private LgCompanyService lgCompanyService;
	
	@Autowired
	private LgDeliveryOrderService lgDeliveryOrderService;

    /**
     * 根据名字查询物流承运商
     * @author wangc
     * 
     * @param req 请求参数
     * @return
     */
    @RequestMapping(value = "/getLogisticsByName", method = RequestMethod.POST)
    public String getLogisticsByName(String jsonData) {
        String result="";
    	try {
        	LgLogisticsNameDto lgLogisticsNameDto = JsonUtils.toBean(jsonData, LgLogisticsNameDto.class);
        	if (null==lgLogisticsNameDto||null==lgLogisticsNameDto.getLogisticsName()||"".equals(lgLogisticsNameDto.getLogisticsName())) {
				return RestCode.CODE_A007.toJson();
			}
        	LgCompanyDto lgCompanyDto=lgCompanyService.getLogisticsByName(lgLogisticsNameDto.getLogisticsName().toString().trim());
        	result = RestCode.CODE_0000.toJson(lgCompanyDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getLogisticsByName exception:", e);
            result = RestCode.CODE_S999.toJson();
        }
    	logger.info("getLogisticsByName result:"+result);
    	return result;
    }
    
    /**
     * 商城确认发货(商家承运)
     * @return 返回值
     */
	@RequestMapping(value = "/confirmFaHuoForB2BSJ", method = RequestMethod.POST)
	public String confirmFaHuoForB2BSJ(HttpServletRequest request) {
		String jsonData = ServletUtils.getStringParameter(request, "jsonData");
		LgDeliveryOrderForB2BDto lgDeliveryOrderForB2BDto = JsonUtils.toBean(jsonData,LgDeliveryOrderForB2BDto.class);
		boolean b = lgCompanyService.confirmFaHuoForB2BSJ(lgDeliveryOrderForB2BDto);
		if (b) {
			return RestCode.CODE_0000.toJson();
		} else {
			return RestCode.CODE_S999.toJson();
		}
	}
	
    /**
     * 商城确认发货(平台承运)
     * @return
     */
	@RequestMapping(value = "/confirmFaHuoForB2BPT", method = RequestMethod.POST)
	public String confirmFaHuoForB2BPT(HttpServletRequest request) {
		String jsonData = ServletUtils.getStringParameter(request, "jsonData");
		LgDeliveryOrderForB2BPTDto dto = JsonUtils.toBean(jsonData,LgDeliveryOrderForB2BPTDto.class);
		boolean b = lgCompanyService.confirmFaHuoForB2BPT(dto);
		if (b) {
			return RestCode.CODE_0000.toJson();
		} else {
			return RestCode.CODE_S999.toJson();
		}
	}
	
	
	 /**
	  * 供商城查询线路价格
	  * @param request
	  * @return
	  */
	@RequestMapping(value = "/searchLgPriceForB2B", method = RequestMethod.POST)
	public String searchLgPriceForB2B(HttpServletRequest request) {
		String result="";
		String jsonData = ServletUtils.getStringParameter(request, "jsonData");
		LgSearchPriceForB2BDto dto = JsonUtils.toBean(jsonData,LgSearchPriceForB2BDto.class);
		ForB2BLgPriceDto returnDto = lgCompanyService.searchLgPriceForB2B(dto);
		if (null!=returnDto && null!=returnDto.getLinePk() ) {
			result=RestCode.CODE_0000.toJson(returnDto);
		}else {
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
	
	
    /**
     * 商城那边确认发货，调用该接口，把该记录insert到lg_delivery_order表，lg_order_goods表
     * @author wangc
     * @param req orderDelivery
     * @return
     */
    @RequestMapping(value = "/confirmFaHuoForB2B", method = RequestMethod.POST)
    public String confirmFaHuoForB2B(String jsonData) {
        try {
        	LgDeliveryOrderForB2BDto lgDeliveryOrderForB2BDto = JsonUtils.toBean(jsonData, LgDeliveryOrderForB2BDto.class);
        	if (lgDeliveryOrderForB2BDto.getOrderPk()==null||lgDeliveryOrderForB2BDto.getOrderPk().equals("")
        		||lgDeliveryOrderForB2BDto.getOriginalTotalFreight()==null
        		||lgDeliveryOrderForB2BDto.getPresentTotalFreight()==null
        		||lgDeliveryOrderForB2BDto.getLogisticsCompanyPk()==null||lgDeliveryOrderForB2BDto.getLogisticsCompanyPk().equals("")
        		||lgDeliveryOrderForB2BDto.getLogisticsCompanyName()==null||lgDeliveryOrderForB2BDto.getLogisticsCompanyName().equals("")
        		){
        		return RestCode.CODE_A007.toJson();
			}
        	boolean b=lgDeliveryOrderService.confirmFaHuoForB2B(lgDeliveryOrderForB2BDto);
            if (b) {
            	return RestCode.CODE_0000.toJson();
			}else {
				 return RestCode.CODE_S999.toJson();
			}
        } catch (Exception e) {
            e.printStackTrace();
            return RestCode.CODE_S999.toJson();
        }
    }
    
    /****
     * 查询所有承运商
     */
    @RequestMapping(value = "/searchLgCompanyList", method = RequestMethod.POST)
    public String searchLgCompanyList(HttpServletRequest request) {
        String result = "";
        try {
            Map<String, Object> map = this.paramsToMap(request);
            map.put("start", ServletUtils.getIntParameterr(request, "start", 0));
            map.put("limit", ServletUtils.getIntParameterr(request, "limit", 10));
            List<LgCompanyDto> list = lgCompanyService.searchLgCompanyList(map);
            result = RestCode.CODE_0000.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("searchLgCompanyList exception:" + e);
            result = RestCode.CODE_S999.toJson();
        }
        return result;
    }

	 /**
	  * 商城下单时，平台承运查询运费总价
	  * @param request
	  * @return
	  */
	@RequestMapping(value = "/searchLgPriceForB2BByLinePk", method = RequestMethod.POST)
	public String searchLgPriceForB2BByLinePk(HttpServletRequest request) {
		String result="";
		String lgLinePk = ServletUtils.getStringParameter(request, "lgLinePk");
		String lgLineStepPk = ServletUtils.getStringParameter(request, "lgLineStepPk");
		if (null==lgLinePk || "".equals(lgLinePk)) {
			return RestCode.CODE_A001.toJson();
		}
		ForB2BLgPriceDto returnDto = lgCompanyService.searchLgPriceForB2BByLinePk(lgLinePk,lgLineStepPk);
		if (null!=returnDto && null!=returnDto.getLinePk()) {
			result = RestCode.CODE_0000.toJson(returnDto);
		}else{
			result = RestCode.CODE_S999.toJson();
		}
		return result;
	}
    
    
}
