package cn.cf.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.B2bLotteryActivityDtoEx;

public interface B2bLotteryActivityDaoEx extends B2bLotteryActivityDao{
	
	B2bLotteryActivityDtoEx searchOnlineEntity(String activityPk);

	List<B2bLotteryActivityDtoEx> searchActivityList(
			Map<String, Object> map);

	Integer searchCreditStatus(@Param("creditPk") String creditPk, @Param("activityPk") String activityPk  );

 
}
