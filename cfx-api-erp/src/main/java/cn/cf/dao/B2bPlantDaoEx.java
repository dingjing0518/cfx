package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bPlantDto;

public interface B2bPlantDaoEx extends B2bPlantDao{

	public List<B2bPlantDto> getByName(Map<String, Object> map);
}
