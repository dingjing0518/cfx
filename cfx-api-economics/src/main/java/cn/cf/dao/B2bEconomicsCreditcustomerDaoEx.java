package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsCreditcustomerDto;

public interface B2bEconomicsCreditcustomerDaoEx extends
		B2bEconomicsCreditcustomerDao {
	
	List<B2bEconomicsCreditcustomerDto> searchCustomerList(Map<String,Object> map);
}
