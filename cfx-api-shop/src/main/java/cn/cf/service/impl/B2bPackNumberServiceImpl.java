package cn.cf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bPackNumberDaoEx;
import cn.cf.dto.B2bPackNumberDto;
import cn.cf.model.B2bPackNumber;
import cn.cf.service.B2bPackNumberService;

@Service
public class B2bPackNumberServiceImpl implements B2bPackNumberService {
	
	@Autowired
	private B2bPackNumberDaoEx packNumberDao;

	@Override
	public Integer update(B2bPackNumber b2bPackNumber) {
		return packNumberDao.update(b2bPackNumber);
		
	}

	@Override
	public Integer insert(B2bPackNumber b2bPackNumber) {
		return packNumberDao.insert(b2bPackNumber);
		
	}

	@Override
	public PageModel<B2bPackNumberDto> searchPackNumberList(Map<String, Object> map) {
		PageModel<B2bPackNumberDto> pm = new PageModel<B2bPackNumberDto>();
		int totalCount = packNumberDao.searchGridCount(map);
		List<B2bPackNumberDto> list = packNumberDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		if (map.get("start") != null) {
			pm.setStartIndex(Integer.valueOf(String.valueOf(map.get("start"))));
			pm.setPageSize(Integer.valueOf(String.valueOf(map.get("limit"))));
		}
		return pm;
	}
	

}
