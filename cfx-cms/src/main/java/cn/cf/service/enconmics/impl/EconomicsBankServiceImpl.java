package cn.cf.service.enconmics.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.service.enconmics.EconomicsBankService;

@Service
public class EconomicsBankServiceImpl implements EconomicsBankService {
	
	@Autowired
	private B2bEconomicsBankExtDao economicsBankDao;

	@Override
	public List<B2bEconomicsBankDto> searchBankList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);
		return economicsBankDao.searchList(map);
	}

	@Override
	public List<B2bEconomicsBankDto> searchAllList() {
		Map<String, Object> map = new HashMap<String, Object>();
		return economicsBankDao.searchList(map);
	}



}
