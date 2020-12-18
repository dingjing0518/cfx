package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bBrandDaoEx;
import cn.cf.dto.B2bBrandDto;
import cn.cf.model.B2bBrand;
import cn.cf.service.B2bBrandService;
import cn.cf.util.KeyUtils;

@Service
public class B2bBrandServiceImpl implements B2bBrandService {

	@Autowired
	private B2bBrandDaoEx b2bBrandDaoEx;
	@Override
	public List<B2bBrandDto> searchGrid(Map<String,Object> map) {
		return b2bBrandDaoEx.searchGrid(map);
	}

	@Override
	@Transactional
	public String createNewBrand(String brandName){
		
		B2bBrand b2bBrand=new B2bBrand();
		String pk=KeyUtils.getUUID();
		b2bBrand.setPk(pk);
		b2bBrand.setName(brandName);
		b2bBrand.setIsDelete(1);
		b2bBrand.setIsVisable(1);
		b2bBrand.setSort(0);
		b2bBrand.setInsertTime(new Date());
		b2bBrandDaoEx.insert(b2bBrand);
		return pk;
	}

}
