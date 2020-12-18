package cn.cf.service.platform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bEconomicsGoodsDao;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.service.platform.B2bEconomicsGoodsService;

@Service
public class EconomicsGoodsServiceImpl implements B2bEconomicsGoodsService {

	@Autowired
	private B2bEconomicsGoodsDao b2bEconomicsGoodsDao;
	
	@Override
	public List<B2bEconomicsGoodsDto> searchList() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("status", 1);
		return b2bEconomicsGoodsDao.searchList(map);
	}

	@Override
	public B2bEconomicsGoodsDto getByPk(String pk) {
		return b2bEconomicsGoodsDao.getByPk(pk);
	}

	@Override
	public B2bEconomicsGoodsDto searchOne(Map<String, Object> map) {
		map.put("isDelete", 1);
		map.put("status", 1);
		List<B2bEconomicsGoodsDto> list= b2bEconomicsGoodsDao.searchList(map);
		if (null!=list && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
