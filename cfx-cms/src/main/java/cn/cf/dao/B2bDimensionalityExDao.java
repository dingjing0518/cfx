package cn.cf.dao;

import cn.cf.dto.B2bDimensionalityDto;

public interface B2bDimensionalityExDao extends B2bDimensionalityDao {

	B2bDimensionalityDto getByType(String type);

}
