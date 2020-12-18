package cn.cf.service;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bKeywordHotDto;

public interface B2bKeywordHotService {
	
	List<B2bKeywordHotDto> searchKeyWordList(Map<String, Object> map);
}
