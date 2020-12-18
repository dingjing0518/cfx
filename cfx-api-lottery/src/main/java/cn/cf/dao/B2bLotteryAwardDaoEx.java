package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bLotteryAwardDto;

public interface B2bLotteryAwardDaoEx extends B2bLotteryAwardDao{

	List<B2bLotteryAwardDto> searchAwardList(Map<String, Object> awardMap);
	
}
