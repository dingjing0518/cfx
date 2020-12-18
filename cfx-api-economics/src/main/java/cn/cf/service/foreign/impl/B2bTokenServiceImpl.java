package cn.cf.service.foreign.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bTokenDao;
import cn.cf.dto.B2bTokenDto;
import cn.cf.service.foreign.B2bTokenService;

@Service
public class B2bTokenServiceImpl implements B2bTokenService {
	
	@Autowired
	private B2bTokenDao b2bTokenDao;

	@Override
	public B2bTokenDto getByCompanyPk(String companyPk) {
		// TODO Auto-generated method stub
		return b2bTokenDao.getByStorePk(companyPk);
	}

	@Override
	public B2bTokenDto searchToken(Map<String, Object> map) {
		List<B2bTokenDto> list = b2bTokenDao.searchGrid(map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
