package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bReserveOrderDaoEx;
import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bReserveOrderDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bReserveGoods;
import cn.cf.model.B2bReserveOrder;
import cn.cf.model.B2bReserveOrderEx;
import cn.cf.service.B2bCustomerSaleManService;
import cn.cf.service.B2bReserveService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;
@Service
public class B2bReserveServiceImpl implements B2bReserveService {
	@Autowired
	private B2bStoreDaoEx storeDao;
	@Autowired
	private B2bCustomerSaleManService b2bCustomerSaleManService;
	
	@Autowired
	private B2bReserveOrderDaoEx reserveOrderDaoEx;
	
	@Override
	public RestCode submitReserveOrder(Porder o, Map<String, List<Pgoods>> map,
			BatchOrder bo) {
		List<Pgoods> list = null;
		B2bReserveOrder model = null;
		String orderNumber = null;
		B2bReserveGoods og = null;
		List<B2bReserveGoods> rglist = new ArrayList<B2bReserveGoods>();
		List<B2bReserveOrder> rlist = new ArrayList<B2bReserveOrder>();
		B2bStoreDto sdto = null;
		// 根据店铺对订单商品进行拆分
		for (String key : map.keySet()) {
			sdto = storeDao.getByPk(key) ;
			list = map.get(key);
			// 先绑定所有业务员 
			list = openGoodsBySalesman(list, o.getCompany().getPk());
			 double orderAmount = 0.00;
			 orderNumber = "YE"+KeyUtils.getOrderNumber();
		 for (Pgoods pg:list) {
			 
			 
			 og = new B2bReserveGoods(pg ,o.getLmdto().getType());// 该预约单为待提交
				rglist.add(og);
				orderAmount = ArithUtil.add(orderAmount,og.getGoodAmount());
		}
		 if(ArithUtil.round(orderAmount, 2)==0){
				return RestCode.CODE_G015;
			}
		model = new B2bReserveOrderEx(o,  
							sdto.getPk(), sdto.getName(), orderNumber,  
							orderAmount, list.get(0).getMdto(),rglist);
				rlist.add(model);
		 
		}
		 
		bo.setRolist(rlist);
		return RestCode.CODE_0000;
	}

	/**
	 * 订单商品根据业务员为最小单位划分
	 * 
	 * @param list
	 * @param purchaserPk
	 *            采购商
	 * @return
	 */
	public List<Pgoods> openGoodsBySalesman(List<Pgoods> list,
			String purchaserPk ) {
		List<Pgoods> newList = new ArrayList<Pgoods>();
		if (null != list && list.size() > 0) {
			// 先根据业务员拆单
			for (int i = 0; i < list.size(); i++) {
				// 此处调用member系统返回业务员信息
				B2bGoodsDtoMa gma = list.get(i).getGdto();
				B2bMemberDto memberDto=null;
				if("cf".equals(gma.getBlock())){
					  memberDto = b2bCustomerSaleManService.getSaleMan(
							  gma.getCompanyPk(), purchaserPk, gma.getCfGoods().getProductPk(),
							  gma.getStorePk());
					
				}else{
					 memberDto = b2bCustomerSaleManService.getSaleMan(
							  gma.getCompanyPk(), purchaserPk, "",
							  gma.getStorePk());
				}
		
				if (null != memberDto) {
					list.get(i).setMdto(memberDto);
				} 
				newList.add(list.get(i));

			}
		}
		return newList;
	}
	
	@Override
	public PageModel<B2bReserveOrderEx> searchReserveOrderList(String searchType,
			B2bStoreDto store,B2bCompanyDto company,Map<String,Object> map,Sessions session,B2bMemberDto memberDto) {
		if(null == map){
			map = new HashMap<String,Object>();
		}
		if (session.getIsAdmin() != 1) {
			map.put("memberPk", memberDto.getPk());
		} else{
			if(null !=searchType && "1".equals(searchType)){
				map.put("purchaserPk", company.getPk());
			}
			if(null != searchType && "2".equals(searchType)){
				map.put("storePk", store.getPk());
			}
		}
		PageModel<B2bReserveOrderEx> pm = new PageModel<B2bReserveOrderEx>();
		List<B2bReserveOrderEx> list = reserveOrderDaoEx.searchReserveOrderList(map);
		int count = reserveOrderDaoEx.searchReserveOrderCount(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if(null != map.get("start")){
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		}
		if(null != map.get("limit")){
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	@Override
	public Map<String, Object> searchReserveOrderCount(String searchType,
			B2bStoreDto store, B2bCompanyDto company, Map<String, Object> map,
			Sessions session, B2bMemberDto memberDto) {
		if(null == map){
			map = new HashMap<String,Object>();
		}
		if (session.getIsAdmin() != 1) {
			map.put("memberPk", memberDto.getPk());
		} else{
			if(null !=searchType && "1".equals(searchType)){
				map.put("purchaserPk", company.getPk());
			}
			if(null != searchType && "2".equals(searchType)){
				map.put("storePk", store.getPk());
			}
		}
		return reserveOrderDaoEx.searchReserveStatusCounts(map);
	}

	@Override
	public B2bReserveOrderDto getReserveDetails(String orderNumber) {
		return reserveOrderDaoEx.getByOrderNumber(orderNumber);
	}

	@Override
	public int updateReserveStatus(Map<String, Object> map) {
		int result=reserveOrderDaoEx.updateReserve(map);
		return result;
	}

	@Override
	public List<B2bReserveOrderDto> searchReserveOrderListForToday() {
		return reserveOrderDaoEx.searchReserveOrderListForToday();
	}

	 
}
