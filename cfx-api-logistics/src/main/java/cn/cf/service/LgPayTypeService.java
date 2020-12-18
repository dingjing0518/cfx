package cn.cf.service;

import java.util.List;
import cn.cf.model.LgPayType;

public interface LgPayTypeService {
	
	/**
	 * 查询启用的支付方式
	 * @return
	 */
	public List<LgPayType> getPayTypes();
	
	/**
	 * 根据pk查询payTypeName
	 * @param paymentPk 支付方式pk
	 * @return
	 */
	public String getPayTypeNameByPK(String paymentPk);
	
	
}
