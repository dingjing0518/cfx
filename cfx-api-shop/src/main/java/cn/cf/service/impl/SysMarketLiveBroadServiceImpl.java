package cn.cf.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.dao.SysLivebroadCategoryDao;
import cn.cf.dao.SysMarketLivebroadDaoEx;
import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.dto.SysMarketLivebroadDto;
import cn.cf.service.SysMarketLiveBroadService;

@Service
public class SysMarketLiveBroadServiceImpl implements SysMarketLiveBroadService {

	@Autowired
	private SysMarketLivebroadDaoEx sysMarketLivebroadDaoEx;
	
	@Autowired
	private SysLivebroadCategoryDao livebroadCategoryDao;
	
	@Override
	public List<SysLivebroadCategoryDto> searchLiveBroadCateGoryList(Map<String,Object> map) {
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("orderName", "sort");
		map.put("orderType", "asc");
		return livebroadCategoryDao.searchGrid(map);
	}

	@Override
	public PageModel<SysMarketLivebroadDto> searchsMarketLivebroadList(Map<String, Object> map) {
		PageModel<SysMarketLivebroadDto> pm = new PageModel<SysMarketLivebroadDto>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("orderName", "updateTime");
		map.put("orderType", "desc");
		List<SysMarketLivebroadDto> list = sysMarketLivebroadDaoEx.searchGridEx(map);
		int count =  sysMarketLivebroadDaoEx.searchGridCountEx(map);
		pm.setDataList(list);
		pm.setTotalCount(count);
		if(null != map.get("start")){
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

}
