package cn.cf.service.platform;

import cn.cf.dto.B2bEconomicsCustomerDto;

public interface B2bEconomicsCustomerService {
	
	B2bEconomicsCustomerDto getEconomicsCustomer(String companyPk);
	
	void updateEconomicsCustomer(B2bEconomicsCustomerDto customerDto);
}
