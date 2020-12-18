package cn.cf.dao;

import cn.cf.dto.LgDriverDto;
import cn.cf.model.LgDriver;

public interface LgDriverDaoEx extends LgDriverDao{
	
	int searchEntity(LgDriverDto dto);
	
	int updatePartField(LgDriver model);
	

}
