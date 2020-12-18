package cn.cf.service.platform.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bEconomicsCustomerDao;
import cn.cf.dto.B2bEconomicsCustomerDto;
import cn.cf.model.B2bEconomicsCustomer;
import cn.cf.service.platform.B2bEconomicsCustomerService;

@Service
public class B2bEconomicsCustomerServiceImpl implements
		B2bEconomicsCustomerService {
	
	@Autowired
	private B2bEconomicsCustomerDao b2bEconomicsCustomerDao;
	
	@Override
	public B2bEconomicsCustomerDto getEconomicsCustomer(String companyPk) {
		// TODO Auto-generated method stub
		return b2bEconomicsCustomerDao.getByCompanyPk(companyPk);
	}

	@Override
	public void updateEconomicsCustomer(B2bEconomicsCustomerDto customerDto) {
		B2bEconomicsCustomer customer = new B2bEconomicsCustomer();
		customer.UpdateDTO(customerDto);
		b2bEconomicsCustomerDao.update(customer);
	}

}
