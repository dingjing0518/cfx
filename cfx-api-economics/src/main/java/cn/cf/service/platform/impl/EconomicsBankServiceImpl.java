package cn.cf.service.platform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bEconomicsBankDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.service.platform.B2bEconomicsBankService;

@Service
public class EconomicsBankServiceImpl implements B2bEconomicsBankService {
	
	@Autowired
	private B2bEconomicsBankDao economicsBankDao;
	
	@Override
	public B2bEconomicsBankDto getEconomicsBank(String pk) {
		return economicsBankDao.getByPk(pk);
	}

	@Override
	public B2bEconomicsBankDto getEconomicsBankByName(String name) {
		// TODO Auto-generated method stub
		return economicsBankDao.getByBankName(name);
	}

}
