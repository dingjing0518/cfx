package cn.cf.service.impl;

import cn.cf.dao.B2bLoanNumberDaoEx;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.service.B2bLoanNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class B2bLoanNumberServiceImpl implements B2bLoanNumberService {
	
	@Autowired
	private B2bLoanNumberDaoEx b2bLoanNumberDao;

	@Override
	public B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber) {
		return b2bLoanNumberDao.getByOrderNumber(orderNumber);
	}

}
