package cn.cf.service;

import java.util.List;


public interface LgMemberDeliveryOrderService {
	
	/**
	 * 根据业务员pk查询该业务员可见的订单
	 * @param searchLgPrice
	 * @return
	 */
	List<String> getVisibleDeliveryOrderByMemberPk(String memberPk);
	
	
}
