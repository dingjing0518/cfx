package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bFinanceRecordsDtoEx;

public interface B2bFinanceRecordsDaoEx extends B2bFinanceRecordsDao{
	
	List<B2bFinanceRecordsDtoEx> searchFinanceRecordsList(Map<String,Object> map);

	int  searchFinanceRecordsCounts(Map<String,Object> map);
	
	void updateStatus(Map<String,Object> map);
	
	List<B2bFinanceRecordsDtoEx> searchUnsuccessRecords();
}
