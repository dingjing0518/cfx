package cn.cf.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bBindOrderDtoEx;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bBindOrderService;
import cn.cf.service.B2bFacadeService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * @description:拼团购物车
 * @date 2018-8-17 下午3:13:34
 */
@RestController
@RequestMapping("trade")
public class B2bBindOrderController extends BaseController {

    
    @Autowired
    private B2bFacadeService b2bFacadeService;
    @Autowired
    private B2bBindOrderService bindOrderService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 拼团转化订单
     */
    @RequestMapping(value = "bindToOrder", method = RequestMethod.POST)
    public String bindToOrder(HttpServletRequest req){
        B2bMemberDto member = this.getMemberBysessionsId(req);
        String orderNumber = ServletUtils.getStringParameter(req, "orderNumber");
        return b2bFacadeService.bindToOrder(orderNumber, member);
    }
    /**
     * 需求单列表
     * @param req
     * @return
     */
    @RequestMapping(value = "searchBindOrderList", method = RequestMethod.POST)
	public String searchBindOrderList(HttpServletRequest req){
		Map<String, Object> map = this.paramsToMap(req);
		map.put("start", ServletUtils.getIntParameterr(req, "start", 0));
		map.put("limit", ServletUtils.getIntParameterr(req, "limit", 10));
		map.put("orderType", ServletUtils.getStringParameter(req, "orderType", "desc"));
		map.put("bindPk", ServletUtils.getStringParameter(req, "bindPk"));
		map.put("orderNumber", ServletUtils.getStringParameter(req, "orderNumber"));
		B2bStoreDto store = this.getStoreBysessionsId(req);
		Sessions session = this.getSessions(req);
		B2bMemberDto memberDto = this.getMemberBysessionsId(req);
		PageModel<B2bBindOrderDtoEx> pm = new PageModel<B2bBindOrderDtoEx>();
		pm = bindOrderService.searchBindOrderList(map, store, memberDto, session.getIsAdmin());
		return RestCode.CODE_0000.toJson(pm);
	}
    
    /**
     * 需求列表标签数量
     * 		
     */
	@RequestMapping(value = "searchBindOrderCounts", method = RequestMethod.POST)
	public String searchBindOrderCounts(HttpServletRequest req){
		String result="";
		try {
			Map<String, Object> map = this.paramsToMap(req);
			B2bStoreDto store = this.getStoreBysessionsId(req);
			Sessions session = this.getSessions(req);
			B2bMemberDto memberDto = this.getMemberBysessionsId(req);
			result= RestCode.CODE_0000.toJson(bindOrderService.searchBindOrderCounts(map, store, memberDto, session.getIsAdmin()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("searchBindOrderCounts exception:", e);
			result= RestCode.CODE_S999.toJson();
		}
		return result;
	}
     
}
