package cn.cf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.cf.dao.B2bOrderGoodsDaoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.service.B2bOrderGoodsService;

@Service
public class B2bOrderGoodsServiceImpl implements B2bOrderGoodsService {
	
	@Autowired
    private B2bOrderGoodsDaoEx b2bOrderGoodsDaoEx;
	
	@Override
	public List<B2bOrderGoodsDtoEx> getByOrderNumber(String orderNumber) {
		
		return b2bOrderGoodsDaoEx.getByOrderNumberEx(orderNumber);
	}

}
