package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bLotteryActivityExDto;

public interface B2bLotteryActivityExDao extends B2bLotteryActivityDao{
	
	B2bLotteryActivityExDto getByActivityType(Integer activityType);
	 
	int updateByDto(B2bLotteryActivityExDto dto);
	 
	int searchGridCountEx(Map<String, Object> map);
	
	List<B2bLotteryActivityExDto> searchGridEx(Map<String, Object> map);
	
	List<B2bLotteryActivityExDto> getAllLotteryActivity(Integer activityType);
	
	B2bLotteryActivityExDto getByPkEx(java.lang.String pk); 
	
	int deleteByType(Integer type);
	 
}
