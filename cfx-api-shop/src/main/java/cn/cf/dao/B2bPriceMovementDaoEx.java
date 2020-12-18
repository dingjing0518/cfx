package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPriceMovementDtoEx;

public interface B2bPriceMovementDaoEx extends B2bPriceMovementDao {
	
	List<B2bPriceMovementDtoEx> searchB2bPriceMovementList(Map<String,Object> map);
	
	int  searchB2bPriceMovementCount(Map<String,Object> map);
	
}
