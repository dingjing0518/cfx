package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bProductDao;
import cn.cf.dto.B2bProductDto;
import cn.cf.service.B2bProductService;
@Service
public class B2bProductServiceImpl implements B2bProductService{

	@Autowired
	private B2bProductDao b2bProductDao;
	
	@Override
	public B2bProductDto getProductByName(String productName) {
		return b2bProductDao.getByName(productName);
	}

}
