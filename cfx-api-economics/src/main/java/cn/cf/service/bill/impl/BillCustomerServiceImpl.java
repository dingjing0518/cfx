package cn.cf.service.bill.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBillCustomerDao;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.service.bill.BillCustomerService;

@Service
public class BillCustomerServiceImpl implements BillCustomerService {

	@Autowired
	private B2bBillCustomerDao b2bBillCustomerDao;
	
	@Override
	public B2bBillCustomerDto getByPk(String pk) {
		// TODO Auto-generated method stub
		return b2bBillCustomerDao.getByPk(pk);
	}

	@Override
	public B2bBillCustomerDto getByCompanyPk(String companyPk) {
		// TODO Auto-generated method stub
		return b2bBillCustomerDao.getByCompanyPk(companyPk);
	}

}
