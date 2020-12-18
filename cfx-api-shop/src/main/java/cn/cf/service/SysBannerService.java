package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysBannerDto;

public interface SysBannerService {
	List<SysBannerDto> searchBanners(Map<String,Object> map) throws Exception;

	
}
