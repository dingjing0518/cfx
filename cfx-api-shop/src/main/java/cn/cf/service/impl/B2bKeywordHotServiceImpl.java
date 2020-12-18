package cn.cf.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bKeywordHotDao;
import cn.cf.dto.B2bKeywordHotDto;
import cn.cf.service.B2bKeywordHotService;

@Service
public class B2bKeywordHotServiceImpl implements B2bKeywordHotService {
	
	@Autowired
	private B2bKeywordHotDao b2bKeywordHotDao;
	
	@Override
	public List<B2bKeywordHotDto> searchKeyWordList(Map<String, Object> map) {
		map.put("isDelete", 1);
		map.put("status", 1);
		return b2bKeywordHotDao.searchGrid(map);
	}

}
