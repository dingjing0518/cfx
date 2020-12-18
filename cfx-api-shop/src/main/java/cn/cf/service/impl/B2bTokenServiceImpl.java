package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bTokenDao;
import cn.cf.dto.B2bTokenDto;
import cn.cf.service.B2bTokenService;

@Service
public class B2bTokenServiceImpl implements B2bTokenService {
	
	@Autowired
	private B2bTokenDao b2bTokenDao;

	@Override
	public B2bTokenDto getByStorePk(String storePk) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		B2bTokenDto token = null;
		map.put("storePk", storePk);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		List<B2bTokenDto> list = b2bTokenDao.searchGrid(map);
		if(null != list &&list.size() >0){
			token = list.get(0);
		}
		return token;
	}

}
