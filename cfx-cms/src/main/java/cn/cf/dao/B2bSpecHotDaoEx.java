package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bSpecHotDto;
import cn.cf.model.B2bSpecHot;

public interface B2bSpecHotDaoEx extends B2bSpecHotDao{

	int searchSpecHotCount(Map<String,Object> map);
	
	List<B2bSpecHotDto> searchSpecHotList(Map<String,Object> map);

	int updateExt(B2bSpecHot model);

	int isExitSpecHot(Map<String, Object> map);
}
