package cn.cf.service.platform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bEconomicsCreditcustomerDaoEx;
import cn.cf.dto.B2bEconomicsCreditcustomerDto;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.service.platform.B2bEconomicsCreditCustomerService;
import cn.cf.util.DateUtil;

@Service
public class B2bEconomicsCreditCustomerServiceImpl implements
		B2bEconomicsCreditCustomerService {
	@Autowired
	private B2bEconomicsCreditcustomerDaoEx b2bEconomicsCreditcustomerDao;
	
	
	@Override
	public List<B2bEconomicsCreditcustomerDto> searchCustomerList(
			String companyPk, Integer status,B2bEconomicsCustomerDto customer) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		map.put("status", status);
		if(null != customer && null != customer.getUpdateTime()){
			map.put("updateTime", DateUtil.formatDateAndTime(customer.getUpdateTime()));
		}
		return b2bEconomicsCreditcustomerDao.searchGrid(map);
	}

}
