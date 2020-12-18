package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bLotteryRecordDto;
import cn.cf.model.B2bLotteryRecord;

public interface B2bLotteryRecordDaoEx extends B2bLotteryRecordDao{
	
	List<B2bLotteryRecord> searchBeforeRecords(String memberPk);
	
	int updateEx(B2bLotteryRecord model);
	
	

	int searchToDayByActivityPk(Map<String, Object> map);

	List<B2bLotteryRecordDto> searchGridEx(Map<String, Object> map);

	int searchGridCountEx(Map<String, Object> map);
	
}
