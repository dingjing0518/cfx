package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bProductDaoEx;
import cn.cf.dto.B2bProductDto;
import cn.cf.service.B2bProductsService;

@Service
public class B2bProductsServiceImpl implements B2bProductsService {
	
	@Autowired
	private B2bProductDaoEx productDao;
	/**
	 * 品名列表
	 */
	@Override
	public List<B2bProductDto> searchProductList(Map<String, Object> map) {
		return productDao.searchList(map);
	}
	@Override
	public List<B2bProductDto> searchProductNameByProductPks(String productPks) {
		Map<String, Object> map=new HashMap<String, Object>();
		if(!"-1".equals(productPks)){
			String [] productPk=productPks.split(",");
			if(productPk.length>0){
				map.put("productPks", productPk);
			} 
		} 
		return productDao.searchProductNameByProductPks(map);
	}

}
