package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.dto.SysMarketLivebroadDto;

public interface SysMarketLiveBroadService {
	/**
	 * 行情直播分类
	 * @return
	 */
	List<SysLivebroadCategoryDto> searchLiveBroadCateGoryList(Map<String,Object> map);
	
	/**
	 * 行情直播列表
	 * @param map
	 * @return
	 */
	PageModel<SysMarketLivebroadDto> searchsMarketLivebroadList(Map<String,Object> map);
}
