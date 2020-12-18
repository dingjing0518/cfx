package cn.cf.dao;

import java.util.Map;

import cn.cf.dto.SysRegionsDto;

public interface SysRegionsDaoEx {

	SysRegionsDto getRegionByNames(Map<String,Object> map);
}
