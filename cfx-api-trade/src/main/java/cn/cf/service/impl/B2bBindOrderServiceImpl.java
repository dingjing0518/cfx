package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dto.*;
import cn.cf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bBindDaoEx;
import cn.cf.dao.B2bBindGoodsDao;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dao.B2bBindOrderDao;
import cn.cf.dao.B2bBindOrderDaoEx;
import cn.cf.dao.B2bCartDaoEx;
import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dao.B2bGoodsDao;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dao.LogisticsModelDao;
import cn.cf.dao.SysRegionsDao;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.service.B2bBindOrderService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
public class B2bBindOrderServiceImpl implements B2bBindOrderService {


	@Autowired
	private LogisticsModelDao logisticsModelDao;
	@Autowired
	private B2bGoodsDao b2bGoodsDao;

	@Autowired
	private B2bCompanyDaoEx companyDaoEx;

	@Autowired
	private B2bStoreDaoEx storeDao;

 

	@Autowired
	private B2bBindOrderDaoEx b2bBindOrderDaoEx;

	@Autowired
	private B2bBindOrderDao b2bBindOrderDao;

	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;
	@Autowired
	private B2bBindGoodsDao b2bBindGoodsDao;

	@Autowired
	private B2bBindDaoEx b2bBindDaoEx;

	@Autowired
	private SysRegionsDao sysRegionsDao;
	@Autowired
	private B2bCartDaoEx cartdao;
	
 

	public void checkBindBoxes(String bindPk) {
		int result = b2bBindGoodsDaoEx.checkBind(bindPk);
		if (result == 0) {
			b2bBindDaoEx.updateStatus(bindPk);// 该团成团
			cartdao.deleteByBindPk(bindPk);// 成团后 该团清空购物车
			b2bBindGoodsDaoEx.cleanBindGoods(bindPk);

		}
	}

	@Override
	public B2bBindOrderDto searchBindOrder(String orderNumber) {
		return b2bBindOrderDaoEx.getByOrderNumber(orderNumber);
	}

	@Override
	public void updateB2bBindOrderStatus(String orderNumber, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNumber", orderNumber);
		map.put("status", status);
		b2bBindOrderDaoEx.updateBindOrder(map);

	}

	@Override
	public RestCode submitBindOrder(Porder o, Map<String, List<Pgoods>> map,
			BatchOrder bor) {
		RestCode code = RestCode.CODE_0000;
		B2bBindOrderEx bo = null;
		List<B2bBindOrder> boList = new ArrayList<B2bBindOrder>();
		List<B2bBindOrderGoodsEx> oglist = new ArrayList<B2bBindOrderGoodsEx>();
		for (String key : map.keySet()) {
			String orderNumber = "B" + KeyUtils.getOrderNumber();
			double orderAmount = 0;
			String keys[] = key.split(",");
			String bindPk = keys[1];
			B2bBindDto bind = this.b2bBindDaoEx.getByPk(bindPk);// 活動詳情
			// 团购产品詳情
			List<Pgoods> list = map.get(key);

			B2bBindOrderGoodsEx bog = null;
			for (Pgoods pg : list) {
				if (null != o.getBindPk() && !"".equals(o.getBindPk())) {
					pg.setBoxes(pg.getBindGood().getTotalBoxes());
				} else {
					int sBoxes = (int) ArithUtil.sub(pg.getBindGood()
							.getBoxes(), pg.getBoxes());
					if (sBoxes < 0) {
						return RestCode.CODE_G004;
					}
				}
				bog = new B2bBindOrderGoodsEx(pg, orderNumber, bind, o
						.getLmdto().getType());
				orderAmount = ArithUtil.add(orderAmount,
						ArithUtil.add(bog.getTotalPrice(),ArithUtil.round(bog.getTotalFreight(),  2)));
		
				oglist.add(bog);
			}
			if(ArithUtil.round(orderAmount, 2)==0){
				return RestCode.CODE_G015;
			}
			bo = new B2bBindOrderEx(o, orderNumber, ArithUtil.round(
					orderAmount, 2), bind,oglist);
			boList.add(bo);
		}
		bor.setBolist(boList);
		return code;
	}

	@Override
	public PageModel<B2bBindOrderDtoEx> searchBindOrderList(
			Map<String, Object> map, B2bStoreDto store,
			B2bMemberDto memberDto, Integer isAdmin) {
		map.put("storePk",
				store.getPk() == null ? "" : store.getPk());
		// 超级管理员查看所有,业务员查看自己的订单
		/*
		 * if (isAdmin != 1) { map.put("memberPk", memberDto.getPk()); }
		 */
		return searchB2bBindOrderList(map);
	}

	public PageModel<B2bBindOrderDtoEx> searchB2bBindOrderList(
			Map<String, Object> map) {
		PageModel<B2bBindOrderDtoEx> pm = new PageModel<B2bBindOrderDtoEx>();
		List<B2bBindOrderDtoEx> list = b2bBindOrderDaoEx
				.searchB2bBindOrderList(map);
		int count = b2bBindOrderDaoEx.searchB2bBindOrderCounts(map);

		pm.setDataList(list);
		pm.setTotalCount(count);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));

		return pm;
	}

	@Override
	public List<B2bBindOrderDtoEx> searchBindToOrder() {
		return b2bBindOrderDaoEx.searchBindToOrder();
	}

	/**
	 * 根据bindPk取消需求单
	 */
	@Override
	public void cancelBindOrderByBindPk(String bindPk) {
		b2bBindOrderDaoEx.updateStatus(bindPk);
	}

	@Override
	public Map<String, Object> searchBindOrderCounts(Map<String, Object> map,
			B2bStoreDto store, B2bMemberDto memberDto, Integer isAdmin) {
		Map<String, Object> m = new HashMap<String, Object>();
		map.put("storePk",
				store.getPk() == null ? "" : store.getPk());
		// 超级管理员查看所有,业务员查看自己的订单
		/*
		 * if (isAdmin != 1) { map.put("memberPk", memberDto.getPk()); }
		 */
		m.put("allNum", b2bBindOrderDaoEx.searchB2bBindOrderCounts(map)); 
		map.put("status", 1);// 未转换数量
		m.put("noBindOrderNum", b2bBindOrderDaoEx.searchB2bBindOrderCounts(map)); 
		map.put("status", 2);// 已转化数量
		m.put("BindOrderNum", b2bBindOrderDaoEx.searchB2bBindOrderCounts(map)); 
		map.put("status", -1);// 已取消数量
		m.put("cBindOrderNum", b2bBindOrderDaoEx.searchB2bBindOrderCounts(map)); 
		return m;
	}

}
