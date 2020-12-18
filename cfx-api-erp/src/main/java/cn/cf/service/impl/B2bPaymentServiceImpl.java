package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bPaymentDao;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.service.B2bPaymentService;

@Service
public class B2bPaymentServiceImpl implements B2bPaymentService {

	@Autowired
	private B2bPaymentDao b2bPaymentDao;
	@Override
	public B2bPaymentDto getByPk(String paymentPk) {
		
		return b2bPaymentDao.getByPk(paymentPk);
	}
	@Override
	public B2bPaymentDto getByType(Integer type) {
		B2bPaymentDto dto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("isVisable", 1);
		List<B2bPaymentDto> list = b2bPaymentDao.searchList(map);
		if(null != list && list.size()>0){
			dto = list.get(0);
		}
		return dto;
	}

}
