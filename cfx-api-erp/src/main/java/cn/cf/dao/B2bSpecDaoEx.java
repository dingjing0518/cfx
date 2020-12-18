package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bSpecDto;

public interface B2bSpecDaoEx extends B2bSpecDao {

	List<B2bSpecDto> getByNameParent(String name);
	
	List<B2bSpecDto> getBySeriesName(String name);

}
