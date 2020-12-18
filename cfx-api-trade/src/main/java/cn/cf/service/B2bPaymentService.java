package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.B2bPaymentDtoEx;

public interface B2bPaymentService {
	/**
	 * 根据类型获取支付方式
	 * @return
	 */
	B2bPaymentDtoEx getPaymentByType(int type);
	
	B2bPaymentDto getPaymentPyPk(String pk);
	
	List<B2bPaymentDtoEx> searchPayment(String orderNumber,String contractNo,String companyPk);
	
	/**
	 * 查询支付方式
	 * @param map
	 * @return
	 */
	List<B2bPaymentDto> searchPaymentList(Map<String, Object> map);
}
