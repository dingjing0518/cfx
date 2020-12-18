package cn.cf.service;

import cn.cf.dto.B2bPaymentDto;

public interface B2bPaymentService {

	B2bPaymentDto getByPk(String paymentPk);
	
	B2bPaymentDto getByType(Integer type);

}
