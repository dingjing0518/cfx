package cn.cf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bCustomerSalesmanDaoEx;
import cn.cf.dto.B2bCustomerSalesmanDto;
import cn.cf.service.B2bCustomerSalesmanService;

@Service
public class B2bCustomerSalesmanServiceImpl implements B2bCustomerSalesmanService {

	@Autowired
	private B2bCustomerSalesmanDaoEx b2bCustomerSalesmanExDao;
	
	@Override
	@Transactional
	public void deleteByCustomerPk(String customerPk) {
		
		b2bCustomerSalesmanExDao.deleteByCustomerPk(customerPk);
	}

	@Override
	@Transactional
	public void insertBatch(List<B2bCustomerSalesmanDto> customerSalesmanLst) {
		
		b2bCustomerSalesmanExDao.insertBatch(customerSalesmanLst);
	}

}
