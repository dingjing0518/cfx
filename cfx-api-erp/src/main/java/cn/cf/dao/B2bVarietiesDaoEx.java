package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bVarietiesDto;

public interface B2bVarietiesDaoEx extends B2bVarietiesDao{

	public List<B2bVarietiesDto> getByParentAndName(Map<String, Object> map);

}
