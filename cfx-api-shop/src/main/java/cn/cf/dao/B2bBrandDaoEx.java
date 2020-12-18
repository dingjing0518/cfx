package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBrandDto;


public interface B2bBrandDaoEx  extends B2bBrandDao{

	List<B2bBrandDto> searchbrandNameBybrandPks(Map<String, Object> map);

	List<B2bBrandDto> searchBrand(Map<String, Object> map);

	int searchBrandCount(Map<String, Object> map);

	

}
