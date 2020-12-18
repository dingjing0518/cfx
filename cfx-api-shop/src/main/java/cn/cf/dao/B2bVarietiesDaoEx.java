package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bVarietiesDto;

public interface B2bVarietiesDaoEx extends B2bVarietiesDao {

	List<B2bVarietiesDto> searchChList(Map<String, Object> map);
	
	int searchChCount(Map<String, Object> map);

	List<B2bVarietiesDto> searchVarietiesNameByVarietiesPks(
			Map<String, Object> map);
}
