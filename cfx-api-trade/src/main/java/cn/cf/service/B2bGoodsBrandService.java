package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBrandDto;

public interface B2bGoodsBrandService {
	
	/**
	 * 查询品牌
	 * @param map
	 * @return
	 */
	List<B2bBrandDto> searchBrand(Map<String, Object> map);

}
