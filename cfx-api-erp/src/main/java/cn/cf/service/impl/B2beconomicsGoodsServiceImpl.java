package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bEconomicsGoodsDao;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.service.B2beconomicsGoodsService;

@Service
public class B2beconomicsGoodsServiceImpl implements B2beconomicsGoodsService {
	
	@Autowired
	private B2bEconomicsGoodsDao b2bEconomicsGoodsDao;
	
	@Override
	public B2bEconomicsGoodsDto getEconomicsGoods(String pk) {
		// TODO Auto-generated method stub
		return b2bEconomicsGoodsDao.getByPk(pk);
	}
 
}
