package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bGoodsBrandDto;


public interface B2bGoodsBrandDaoEx extends B2bGoodsBrandDao {

	List<B2bGoodsBrandDto> searchBrand(Map<String, Object> map);
	
	int searchBrandCount(Map<String, Object> map);
	

}
