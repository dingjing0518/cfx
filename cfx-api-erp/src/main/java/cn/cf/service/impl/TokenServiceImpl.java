package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import cn.cf.dao.B2bStoreDao;
import cn.cf.dao.B2bTokenDao;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.service.TokenService;



@Service
public class TokenServiceImpl  implements TokenService {
	
	@Autowired
	private B2bTokenDao b2bTokenDao;
	
	@Autowired
	private B2bStoreDao b2bStoreDao;

	@Override
	public B2bTokenDto searchToken(Map<String, Object> map) {
		List<B2bTokenDto> list = b2bTokenDao.searchGrid(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public B2bStoreDto getByStorePk(String storePk) {
		// TODO Auto-generated method stub
		return b2bStoreDao.getByPk(storePk);
	}

	@Override
	public B2bTokenDto getB2bTokenByStorePk(String storePk) {
		// TODO Auto-generated method stub
		return b2bTokenDao.getByStorePk(storePk);
	}

	
	
	
}
