package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bFriendLinkDao;
import cn.cf.dto.B2bFriendLinkDto;
import cn.cf.service.B2bFriendLinkService;

@Service
public class B2bFriendLinkServiceImpl implements B2bFriendLinkService {
	
	@Autowired
	private B2bFriendLinkDao b2bFriendLinkDao;
	
	@Override
	public List<B2bFriendLinkDto> searchFriendLinkList(Integer start,
			Integer limit) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		map.put("isDelete", 1);
		map.put("start", start);
		map.put("limit", limit);
		return b2bFriendLinkDao.searchGrid(map);
	}

}
