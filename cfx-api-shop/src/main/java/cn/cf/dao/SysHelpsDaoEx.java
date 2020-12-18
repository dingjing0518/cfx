package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysHelpsDto;

public interface SysHelpsDaoEx extends SysHelpsDao{
	
	List<SysHelpsDto> searchHelpMenu(Map<String, Object> map);
	
}
