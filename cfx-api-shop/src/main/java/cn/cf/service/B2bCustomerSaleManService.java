package cn.cf.service;

import java.util.Map;

public interface B2bCustomerSaleManService {

	/**
	 * 业务员商品列表：业务员匹配的商品
	 * @param memberPk
	 * @return
	 */
	Map<String, Object> getGoodsByMember(Map<String, Object> map);
}
