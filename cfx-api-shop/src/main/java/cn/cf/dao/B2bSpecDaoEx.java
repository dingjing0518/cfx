package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bSpecDto;

public interface B2bSpecDaoEx  extends B2bSpecDao{

	List<B2bSpecDto> searchChList(Map<String, Object> map);
	
	int searchChCount(Map<String, Object> map);

	List<B2bSpecDto> searchSeriesNameBySeriesPks(Map<String, Object> map);
	
	

}
