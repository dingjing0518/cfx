package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bProductDaoEx;
import cn.cf.dto.B2bProductDto;
import cn.cf.model.B2bProduct;
import cn.cf.service.B2bProductService;
import cn.cf.util.KeyUtils;


@Service
public class B2bProductServiceImpl implements B2bProductService{

	@Autowired
	private B2bProductDaoEx b2bProductDaoEx;
	
	 
	@Override
	public B2bProductDto getByName(String productName) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", productName);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		B2bProductDto b2bProductDto = null;
		List<B2bProductDto> list = b2bProductDaoEx.searchList(map);
		if (null!=list && list.size()>0) {
			b2bProductDto = list.get(0);
		}
		return b2bProductDto;
	}

	@Override
	@Transactional
	public String createNewProduct(String productName ){
		B2bProduct b2bProduct = new B2bProduct();
		String pk = KeyUtils.getUUID();
		b2bProduct.setName(productName);
		b2bProduct.setPk(pk);
		b2bProduct.setIsDelete(1);
		b2bProduct.setIsVisable(1);
		b2bProduct.setInsertTime(new Date());
		b2bProduct.setSort(1);
		b2bProductDaoEx.insert(b2bProduct);
		return pk;
	}



}
