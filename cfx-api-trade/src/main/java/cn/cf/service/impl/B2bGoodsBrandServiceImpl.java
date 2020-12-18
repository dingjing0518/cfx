package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bGoodsBrandDaoEx;
import cn.cf.dto.B2bBrandDto;
import cn.cf.service.B2bGoodsBrandService;

@Service
public class B2bGoodsBrandServiceImpl implements B2bGoodsBrandService {
	
	@Autowired
	private B2bGoodsBrandDaoEx   b2bGoodsBrandDaoExt;

	@Override
	public List<B2bBrandDto> searchBrand(Map<String, Object> map) {
		return b2bGoodsBrandDaoExt.searchBrand(map);
	}

}
