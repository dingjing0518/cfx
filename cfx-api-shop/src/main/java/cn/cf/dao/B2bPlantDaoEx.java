package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bPlantDtoEx;

public interface B2bPlantDaoEx extends B2bPlantDao {

	B2bPlantDto isPlantRepeated(Map<String, Object> map);

	List<B2bPlantDtoEx> searchPlantGrid(Map<String, Object> map);
	

}
