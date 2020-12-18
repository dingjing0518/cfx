package cn.cf.service.platform;

import java.util.List;

import cn.cf.dto.B2bEconomicsCreditcustomerDto;
import cn.cf.dto.B2bEconomicsCustomerDto;

public interface B2bEconomicsCreditCustomerService {
	
	List<B2bEconomicsCreditcustomerDto> searchCustomerList(String companyPk,Integer status,B2bEconomicsCustomerDto customer);
}
