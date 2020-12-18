package cn.cf.service.logistics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.LgDeliveryOrderExtDao;
import cn.cf.dto.SettleAccountDto;
import cn.cf.model.SearchInvoice;
import cn.cf.service.logistics.SupplierSettleAccountService;

/**
 * 物流供应商结算
 * 
 * @author xht
 *
 */
@Service
public class SupplierSettleAccountServiceImpl implements SupplierSettleAccountService {

	@Autowired
	private LgDeliveryOrderExtDao lgOrderExtDao;

	@Override
	public PageModel<SettleAccountDto> searchSupplierSettleAccountList(QueryModel<SearchInvoice> qm, int i) {
		//String allStatus = "2,3,4,5,6,10";// 全部订单状态
		String allStatus = "3,4,5,6";// 全部订单状态
		String str = "-1";
		PageModel<SettleAccountDto> pm = new PageModel<SettleAccountDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 是否显示分页字段
		if (i == 1) {
			map.put("start", qm.getStart());
			map.put("limit", qm.getLimit());
		}
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isMatched", Constants.ONE);
		map.put("logisticsCompanyName", qm.getEntity().getLogisticsCompanyName());
		map.put("logisticsCompanyContactsTel", qm.getEntity().getLogisticsCompanyContactsTel());
		map.put("orderPk", qm.getEntity().getOrderPk());
		map.put("placeOrderStartTime", qm.getEntity().getPlaceOrderStartTime());
		map.put("placeOrderEndTime", qm.getEntity().getPlaceOrderEndTime());
		// 订单状态
		Integer orderTemp = qm.getEntity().getOrderStatus();
		if (orderTemp == null) {
			map.put("orderStatus", allStatus.split(","));
		}else{
			if (orderTemp == Constants.ORDER_STATUS_002 || orderTemp == Constants.ORDER_STATUS_003
					|| orderTemp == Constants.ORDER_STATUS_004 || orderTemp == Constants.ORDER_STATUS_005
					|| orderTemp == Constants.ORDER_STATUS_006||orderTemp == Constants.ORDER_STATUS_010) {
				map.put("orderStatus", String.valueOf(orderTemp).split(","));
			} else {
				map.put("orderStatus", String.valueOf(str).split(","));
			}
		}
		
		//结算状态
		map.put("isSettleFreight", qm.getEntity().getIsSettleFreight());
		map.put("isAbnormal", qm.getEntity().getIsAbnormal());
	    map.put("colComName",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_SETTLEMENT_COL_COM_NAME));
        map.put("colContacts", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_SETTLEMENT_COL_CONTACTS));
        map.put("colContactsTel",CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(), ColAuthConstants.LG_FC_SETTLEMENT_COL_CONTACTSTEL));

		int totalCount = lgOrderExtDao.searchSupplierSettleAccountCount(map);
		List<SettleAccountDto> list = lgOrderExtDao.searchSupplierSettleAccountList(map);
		  for (SettleAccountDto  dto :list ) {//物流供应商结算金额
				if (null!=dto.getIsAbnormal()&&dto.getIsAbnormal()==2&&null!=dto.getIsConfirmed()&&dto.getIsConfirmed()==1) {
					dto.setOrderStatusName("异常-已确认");
				}
				if (null!=dto.getIsAbnormal()&&dto.getIsAbnormal()==2&&null!=dto.getIsConfirmed()&&dto.getIsConfirmed()==2) {
					dto.setOrderStatusName("异常-未确认");
				}
				
		  }
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateSettleFreight(Map<String, Object> map) {
		return lgOrderExtDao.updateSettleFreight(map);
	}

	public Double  checkDouble (Double num) {
		if(num==null || num.equals("")){
			return 0.0; 
		}else {
			return num ;
		}		
	}
}
