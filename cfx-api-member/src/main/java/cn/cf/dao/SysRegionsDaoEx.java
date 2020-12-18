package cn.cf.dao;

import java.util.Map;

import cn.cf.dto.SysRegionsDto;

public interface SysRegionsDaoEx extends SysRegionsDao{

	SysRegionsDto getRegionByNames(Map<String,Object> map);
}
