package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBrandDto;


public interface B2bGoodsBrandDaoEx extends B2bGoodsBrandDao {

	List<B2bBrandDto> searchBrand(Map<String, Object> map);
	

}
