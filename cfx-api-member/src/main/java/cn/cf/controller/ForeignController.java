package cn.cf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCustomerManagementDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bMemberDtoEx;
import cn.cf.entity.MangoMemberPoint;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bMemberService;
import cn.cf.service.CuccSmsService;
import cn.cf.service.MemberPointService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

@RestController
@RequestMapping("member")
public class ForeignController extends BaseController{
	
	@Autowired
	private CuccSmsService commonSmsService;
	
	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	@Autowired
	private MemberPointService memberPointService;
	
	/**
	 * 新增客户关系 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/addCustomerByManagement", method = RequestMethod.POST)
	public String addCustomerByManagement(HttpServletRequest req,HttpServletResponse resp){
		String purchaserPk = ServletUtils.getStringParameter(req,"purchaserPk","");
		String storePk = ServletUtils.getStringParameter(req,"storePk","");
		B2bCustomerManagementDto dto = new B2bCustomerManagementDto();
		dto.setPurchaserPk(purchaserPk);
		dto.setStorePk(storePk);
		return b2bCustomerSaleManService.addCustomerManagement(dto).toJson();
	}
	
	/**
	 * 查询业务员(前端暂未用 2019-5-22)
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getSaleMan", method = RequestMethod.POST)
	public String getSaleMan(HttpServletRequest req,HttpServletResponse resp){
		String purchaserPk = ServletUtils.getStringParameter(req,"purchaserPk","");
		String storePk = ServletUtils.getStringParameter(req,"storePk","");
		String companyPk = ServletUtils.getStringParameter(req,"companyPk","");
		String productPk = ServletUtils.getStringParameter(req,"productPk","");
		return RestCode.CODE_0000.toJson(b2bCustomerSaleManService.getSaleMan(companyPk, purchaserPk, productPk, storePk));
	}
	
	/**
	 * 根据业务员查询商品条件
	 * @param req
	 * @param resp
	 * @return
	 * 前端暂未用2019-5-22
	 */
	@RequestMapping(value = "/getGoodsByMember", method = RequestMethod.POST)
	public String getGoodsByMember(HttpServletRequest req,HttpServletResponse resp){
		Map<String,Object> map = paramsToMap(req);
		return RestCode.CODE_0000.toJson(b2bCustomerSaleManService.getGoodsByMember(map));
	}
	
	/**
	 * 查询会员信息
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getByMember", method = RequestMethod.POST)
	public String getMemberBy(HttpServletRequest req,HttpServletResponse resp){
		Map<String,Object> map = paramsToMap(req);
		List<B2bMemberDto> list = b2bMemberService.searchList(map);
		if(null!=list && list.size()>0){
			return RestCode.CODE_0000.toJson(list);
		}else{
			return RestCode.CODE_0000.toJson();
		}
	}
	
	/**
	 * 发送短信
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/sendMessageContent", method = RequestMethod.POST)
	public String sendMessageContent(HttpServletRequest req,HttpServletResponse resp){
		String isAdmin = ServletUtils.getStringParameter(req, "isAdmin");
		String companyPk = ServletUtils.getStringParameter(req, "companyPk","");
		String mobile = ServletUtils.getStringParameter(req, "mobile","");
		String memberPk = ServletUtils.getStringParameter(req, "memberPk","");
		String smsCode = ServletUtils.getStringParameter(req, "smsCode","");
		B2bMemberDtoEx member = b2bMemberService.getByPk(memberPk);
		
		Map<String,Object> map = paramsToMap(req);
		map.remove("isAdmin");
		map.remove("companyPk");
		map.remove("mobile");
		map.remove("memberPk");
		map.remove("smsCode");
		Map<String,String> sendMap = new HashMap<String,String>();
		for (String string : map.keySet()) {
			if(null != map.get(string)){
			sendMap.put(string, map.get(string).toString());
			}
		}	
		if(!"".equals(companyPk)){
			commonSmsService.sendMessage(member.getPk(), companyPk, Boolean.valueOf(isAdmin).booleanValue(), smsCode, sendMap);
		}else{
			commonSmsService.sendMSM(member, mobile, smsCode, sendMap);
		}
		return RestCode.CODE_0000.toJson();
	}
	/**
	 * 发送短信(新接口)
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
	public String sendMsg(HttpServletRequest req,HttpServletResponse resp){
		String content = ServletUtils.getStringParameter(req, "content");
		String companyPk = ServletUtils.getStringParameter(req, "companyPk","");
		String mobile = ServletUtils.getStringParameter(req, "mobile","");
		String smsCode = ServletUtils.getStringParameter(req, "smsCode","");
		String companyName = ServletUtils.getStringParameter(req, "companyName","");
		commonSmsService.sendCUCCMsg(mobile, smsCode, content, companyPk, companyName);
		return RestCode.CODE_0000.toJson();
	}

	
	/**
	 * 查询会员基础值
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "searchPointList", method = RequestMethod.POST)
	public String searchPointList(HttpServletRequest request) {
		String dimenType = ServletUtils.getStringParameter(request, "dimenType");
		String companyPk = ServletUtils.getStringParameter(request, "companyPk");
		Map<String, Object> map=new HashMap<>();
		map.put("dimenType", dimenType);
		map.put("companyPk", companyPk);
		List<MangoMemberPoint> list = memberPointService.searchPointList(map);
		return RestCode.CODE_0000.toJson(list);
	}
	
	
	/**
	 * 插入会员基础值
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addPoint", method = RequestMethod.POST)
	public String addPoint(HttpServletRequest request) {
		String memberPk = ServletUtils.getStringParameter(request, "memberPk");
		String companyPk=ServletUtils.getStringParameter(request, "companyPk");
		Integer pointType=ServletUtils.getIntParameter(request, "pointType",1);
		String active=ServletUtils.getStringParameter(request, "active");
		RestCode restCode = memberPointService.addPoint(memberPk, companyPk, pointType, active);
		return restCode.toJson();
	}

	
	/**
	 * 插入会员基础值(重载方法 勿删)
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addPoint2", method = RequestMethod.POST)
	public String addPoint2(HttpServletRequest request) {
		String memberPk = ServletUtils.getStringParameter(request, "memberPk");
		String companyPk = ServletUtils.getStringParameter(request, "companyPk");
		Integer pointType = ServletUtils.getIntParameter(request, "pointType",1);
		String active = ServletUtils.getStringParameter(request, "active");
		boolean flag = ServletUtils.getBooleanParameter(request, "flag",true);
		RestCode restCode = memberPointService.addPoint2(memberPk, companyPk, pointType, active,flag);
		return restCode.toJson();
	}
	

	/**
	 * 收藏商品/店铺
	 * @param memberPk
	 * @param companyPk
	 * @param thing
	 * @param active
	 * @return
	 */
	@RequestMapping(value = "addPointForThing", method = RequestMethod.POST)
	public String addPointForThing(HttpServletRequest request) {
		String memberPk = ServletUtils.getStringParameter(request, "memberPk");
		String companyPk = ServletUtils.getStringParameter(request, "companyPk");
		String thing  = ServletUtils.getStringParameter(request, "thing");
		String active = ServletUtils.getStringParameter(request, "active");
		RestCode restCode = memberPointService.addPointForThing(memberPk, companyPk, thing, active);
		return restCode.toJson();
	}
	
	
	/**
	 * 取消收藏商品
	 * @param thingPk
	 * @param member
	 */
	@RequestMapping(value = "cancelThing", method = RequestMethod.POST)
	public String cancelThing(HttpServletRequest request) {
		String thingPk = ServletUtils.getStringParameter(request, "thingPk");
		String member = ServletUtils.getStringParameter(request, "member");
		memberPointService.cancelThing(thingPk, member);
		return RestCode.CODE_0000.toJson();
	}
	
	
	/**
	 * 取消订单
	 * @param orderNumber
	 */
	@RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
	public String cancelOrder(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber");
		memberPointService.cancelOrder(orderNumber);
		return RestCode.CODE_0000.toJson();
	}

	
	/**
	 * 插入交易维度
	 * @param memberPk
	 *  @param companyPk
	 * @param pointType 1增加分值；2减少分值
	 * @param active 动作
	 * @param total 总金额
	 * @return
	 */
	@RequestMapping(value = "addPointForOrder", method = RequestMethod.POST)
	public String addPointForOrder(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber");
		int pointType = ServletUtils.getIntParameter(request, "pointType", 1);
		RestCode restCode= memberPointService.addPointForOrder(orderNumber,pointType);
		return restCode.toJson();
	}
	
	
	/**
	 * 给会员加额外奖励积分
	 * @param orderNumber  订单编号
	 * @param pointType  1增加分值；2减少分值
	 * @return
	 */
	@RequestMapping(value = "addExtPointForOrder", method = RequestMethod.POST)
	public String addExtPointForOrder(HttpServletRequest request) {
		String orderNumber = ServletUtils.getStringParameter(request, "orderNumber");
		int pointType = ServletUtils.getIntParameter(request, "pointType", 1);
		RestCode restCode= memberPointService.addExtPointForOrder(orderNumber,pointType);
		return restCode.toJson();
	}
	
}
