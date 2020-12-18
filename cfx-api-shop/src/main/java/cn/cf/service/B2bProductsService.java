package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bProductDto;

public interface B2bProductsService {
	
	/**
	 * 查询品名
	 * @param map
	 * @return
	 */
	List<B2bProductDto> searchProductList(Map<String, Object> map);

	List<B2bProductDto> searchProductNameByProductPks(String productPks);
	
	
	

}
