package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bProductDto;

public interface B2bProductDaoEx  extends B2bProductDao{

	List<B2bProductDto> searchProductNameByProductPks(Map<String, Object> map);
	

}
