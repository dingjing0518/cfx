package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysRegionsDto;

public interface SysRegionsService {

	List<SysRegionsDto> getSysregisList(String parentPk);

	List<SysRegionsDto> findall();
	
	SysRegionsDto getRegionsPk(Map<String,Object> map);
}
