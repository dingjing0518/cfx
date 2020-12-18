package cn.cf.dao;

import cn.cf.dto.LgCarDto;
import cn.cf.model.LgCar;


public interface LgCarDaoEx  extends LgCarDao{
	
	 int searchEntity(LgCarDto dto);
	
	 int updatePartField(LgCar model);
}
