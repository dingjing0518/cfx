package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBrandDto;

public interface B2bBrandService {

	List<B2bBrandDto> searchGrid(Map<String,Object> map);

	String createNewBrand(String brandName);

}
