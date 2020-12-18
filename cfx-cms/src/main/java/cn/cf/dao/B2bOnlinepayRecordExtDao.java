package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bOnlinepayRecordExtDto;

public interface B2bOnlinepayRecordExtDao  extends B2bOnlinepayRecordDao{

	int searchGridExtCount(Map<String, Object> map);

	List<B2bOnlinepayRecordExtDto> searchExtList(Map<String, Object> map);

}
