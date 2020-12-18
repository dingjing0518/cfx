package cn.cf.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.SysBannerDao;
import cn.cf.dto.SysBannerDto;
import cn.cf.service.SysBannerService;
@Service
public class SysBannerServiceImpl implements SysBannerService{
	
	
	@Autowired
	private SysBannerDao bannerDao;
 

	/*
	 * CMS系统修改任何banner，都要即时更新redis
	 * 查找未删除，未禁用的符合条件的banner集合
	 */
	@Override
	public List<SysBannerDto> searchBanners(Map<String, Object> map) throws Exception {
				map.put("onlineTime", new Date());
				map.put("endTime", new Date());
			    List<SysBannerDto>	list=bannerDao.searchList(map);
		return list;
	}


 
}
