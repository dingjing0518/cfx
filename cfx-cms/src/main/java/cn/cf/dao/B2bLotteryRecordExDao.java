package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bLotteryRecordExDto;

public interface B2bLotteryRecordExDao extends B2bLotteryRecordDao{
	
	int searchLotteryAwardRosterCount(Map<String, Object> map);
	
	List<B2bLotteryRecordExDto> searchLotteryAwardRosterList(Map<String, Object> map);
	
	B2bLotteryRecordExDto getExDtoByPk(String pk);
	
	int updateByLotteryRecordDto(B2bLotteryRecordExDto dto);
	
}
