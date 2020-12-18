package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bStoreDaoEx;
import cn.cf.dto.B2bStoreDto;
import cn.cf.service.B2bStoreService;
@Service
public class B2bStoreServiceImpl implements B2bStoreService{

	@Autowired
	private B2bStoreDaoEx b2bStoreDaoEx;
	@Override
	public B2bStoreDto getByPk(String pk) {
		
		return b2bStoreDaoEx.getByPk(pk);
	}
	@Override
	public B2bStoreDto storeIsOpen(String pk) {
		
		return b2bStoreDaoEx.storeIsOpen(pk);
	}
	@Override
	public B2bStoreDto getByCompanyPk(String companyPk) {
		
		return b2bStoreDaoEx.getByCompanyPk(companyPk);
	}

}
