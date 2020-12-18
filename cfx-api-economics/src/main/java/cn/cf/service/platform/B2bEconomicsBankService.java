package cn.cf.service.platform;

import cn.cf.dto.B2bEconomicsBankDto;

public interface B2bEconomicsBankService {
	
	B2bEconomicsBankDto getEconomicsBank(String pk);
	
	B2bEconomicsBankDto getEconomicsBankByName(String name);
}
