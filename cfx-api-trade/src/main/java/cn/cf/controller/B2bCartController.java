package cn.cf.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCartDtoEx;
import cn.cf.entity.Sessions;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.service.B2bCartService;
import cn.cf.service.B2bGoodsService;
import cn.cf.util.ServletUtils;
import cn.cf.utils.BaseController;

/**
 * 
 * @description:购物车管理
 * @author FJM
 * @date 2018-4-16 下午4:56:03
 */
@RestController
@RequestMapping("trade")
public class B2bCartController extends BaseController {

	@Autowired
	private B2bCartService b2bCartService;

	@Autowired
	private B2bGoodsService b2bGoodsService;
	@Autowired
	private B2bBindGoodsService b2bBindGoodsService;

	/**
	 * 购物车列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchCartList", method = RequestMethod.POST)
	public String cartList(HttpServletRequest request) {
		Integer cartType = ServletUtils
				.getIntParameterr(request, "cartType", 1);
		Sessions session = this.getSessions(request);
		List<B2bCartDtoEx> list = null;
		// 自采
		if (cartType == 1) {
			list = b2bCartService.searchCartList(session.getMemberPk());
			// 代采
		} else {
			list = b2bCartService.searchCartSpList(session.getMemberPk());
		}
		return RestCode.CODE_0000.toJson(list);

	}

	/**
	 * 购物车数量
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchCartCounts", method = RequestMethod.POST)
	public String searchCartCounts(HttpServletRequest request) {

		// 1 自采 2代采
		Integer cartType = ServletUtils
				.getIntParameterr(request, "cartType", 1);
		Sessions session = this.getSessions(request);
		int counts = b2bCartService.searchCartCounts(session.getMemberPk(),
				cartType);
		HashMap<Object, Object> map = new HashMap<>();
		map.put("count", counts);
		return RestCode.CODE_0000.toJson(map);
	}

	/**
	 * 加入购物车
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addCart", method = RequestMethod.POST)
	public String addCart(HttpServletRequest request) {
		B2bCartDtoEx dto = new B2bCartDtoEx();
		dto.bind(request);
		Sessions session = this.getSessions(request);
		return b2bCartService.addCart(dto, session);
	}

	/**
	 * 编辑购物车
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateCart", method = RequestMethod.POST)
	public String updateCart(HttpServletRequest request) {
		String pk = ServletUtils.getStringParameter(request, "pk", "");
		Integer boxes = ServletUtils.getIntParameterr(request, "boxes", 0);
		Double weight = ServletUtils.getDoubleParameter(request, "weight",0d);
		return b2bCartService.updateCart(pk, boxes,weight);
	}

	/**
	 * 删除购物车
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delCart", method = RequestMethod.POST)
	public String delCart(HttpServletRequest request) {
		String pks = ServletUtils.getStringParameter(request, "pks", "");
		return b2bCartService.delCart(pks);
	}

	/**
	 * 根据多个购物车pk查询购物车信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "searchCartListByPks", method = RequestMethod.POST)
	public String searchCartListByPks(HttpServletRequest request) {
		String pks = ServletUtils.getStringParameter(request, "pks", "");
		String goodsPk = ServletUtils
				.getStringParameter(request, "goodsPk", "");
		String bindPk = ServletUtils.getStringParameter(request, "bindPk", "");
		String offerPk = ServletUtils.getStringParameter(request, "offerPk", "");
		String rest = null;
		if (!"".equals(pks)) {
			rest = RestCode.CODE_0000.toJson(b2bCartService
					.searchFirmOrder(pks));
		} else if (!"".equals(goodsPk)) {
			int boxes = ServletUtils.getIntParameter(request, "boxes", 0);
			double weight = ServletUtils.getDoubleParameter(request, "weight", 0d);
			if (boxes > 0) {
				rest = RestCode.CODE_0000.toJson(b2bGoodsService
						.searchFirmOrder(goodsPk, boxes,weight,offerPk));
			} else {
				rest = RestCode.CODE_G008.toJson();
			}
		} else if (!"".equals(bindPk)) {
			double price = ServletUtils.getDoubleParameter(request, "price", 0);
			rest = RestCode.CODE_0000.toJson(b2bBindGoodsService
					.searchFirmOrder(bindPk, price));
		} else {
			rest = RestCode.CODE_A001.toJson();
		}
		return rest;
	}
}
