package cn.cf.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import cn.cf.dao.LgMemberDeliveryOrderDaoEx;
import cn.cf.service.LgMemberDeliveryOrderService;

public class LgMemberDeliveryOrderServiceImpl implements LgMemberDeliveryOrderService{

	@Autowired
	private LgMemberDeliveryOrderDaoEx lgMemberDeliveryOrderDaoEx;
	
	/**
	 * 根据业务员pk查询该业务员有哪些订单可见
	 */
	@Override
	public List<String> getVisibleDeliveryOrderByMemberPk(String memberPk) {
		return lgMemberDeliveryOrderDaoEx.getVisibleDeliveryOrderByMemberPk(memberPk);
	}

}
