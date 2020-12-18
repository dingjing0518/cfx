package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bGoodsBrandDto;

public interface B2bGoodsBrandDaoEx extends B2bGoodsBrandDao{

	public List<B2bGoodsBrandDto> getByBrandName(Map<String, Object> map);
}
