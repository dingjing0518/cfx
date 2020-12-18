package cn.cf.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bFacadeService;
import cn.cf.service.B2bPayVoucherService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;
/**
 * 付款凭证管理
 * @description:
 * @author FJM
 * @date 2018-5-15 下午2:14:30
 */
@RestController
@RequestMapping("trade")
public class B2bPayVoucherController extends BaseController{
	
	@Autowired
	private B2bFacadeService facadeService;
	
	@Autowired
	private B2bPayVoucherService b2bPayVoucherService;
	
	/**
	 * 上传付款凭证
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/uploadVoucher", method = RequestMethod.POST)
	public String uploadVoucher(HttpServletRequest req,HttpServletResponse resp){
		String rest = null;
		String orderNumber = ServletUtils.getStringParameter(req, "orderNumber","");
		String contractNo = ServletUtils.getStringParameter(req, "contractNo","");
		String imgUrl = ServletUtils.getStringParameter(req, "imgUrl","");
		Integer type = ServletUtils.getIntParameterr(req, "type", 1);
		B2bMemberDto member = this.getMemberBysessionsId(req);
		if(("".equals(orderNumber) && "".equals(contractNo)) || "".equals(imgUrl)){
			rest = RestCode.CODE_A001.toJson();
		}else if(!"".equals(orderNumber)){
			rest = facadeService.uploadVoucher(member, orderNumber, imgUrl,type);
		}else{
			rest = facadeService.uploadVoucherByContract(member, contractNo, imgUrl,type);
		}
		return rest;
	}
	
	/**
	 * 删除付款凭证
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/delVoucher", method = RequestMethod.POST)
	public String delVoucher(HttpServletRequest req,HttpServletResponse resp){
		String rest = null;
		String id = ServletUtils.getStringParameter(req, "id","");
		B2bMemberDto member = this.getMemberBysessionsId(req);
		if("".equals(id) || "".equals(id)){
			rest = RestCode.CODE_A001.toJson();
		}else{
			rest = facadeService.delVoucher(member, id);
		}
		return rest;
	}
	
	/**
	 * 操作付款凭证
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/updateVoucher", method = RequestMethod.POST)
	public String updateVoucher(HttpServletRequest req,HttpServletResponse resp){
		String rest = null;
		String id = ServletUtils.getStringParameter(req, "id","");
		Integer status = ServletUtils.getIntParameterr(req, "status", 0);
		if("".equals(id) || status == 0){
			rest = RestCode.CODE_A001.toJson();
		}else{
			rest = b2bPayVoucherService.updatePayVoucher(id, status);
		}
		return rest;
	}
	
	/**
	 * 查询付款凭证列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/searchVoucherList", method = RequestMethod.POST)
	public String searchVoucherList(HttpServletRequest req,HttpServletResponse resp){
		Map<String,Object> map = paramsToMap(req);
		map.put("type", ServletUtils.getIntParameterr(req, "type", 1));
		Sessions session = this.getSessions(req);
		return RestCode.CODE_0000.toJson(b2bPayVoucherService.searchB2bPayVoucherList(map,session));
	}
	
	/**
	 * 查询付款凭证数据
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/searchVoucherCounts", method = RequestMethod.POST)
	public String searchVoucherCounts(HttpServletRequest req,HttpServletResponse resp){
		Map<String,Object> map = paramsToMap(req);
		map.put("type", ServletUtils.getIntParameterr(req, "type", 1));
		Sessions session = this.getSessions(req);
		return RestCode.CODE_0000.toJson(b2bPayVoucherService.searchB2bPayVoucherCounts(map,session));
	}
}
