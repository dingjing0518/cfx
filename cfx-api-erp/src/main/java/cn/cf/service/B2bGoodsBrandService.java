package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bGoodsBrandDto;

public interface B2bGoodsBrandService {

	B2bGoodsBrandDto getByBrandName(Map<String, Object> map);

	String createNewGoodsBrand(String brandName,String brandPk, String storePk,String storeName);

}
