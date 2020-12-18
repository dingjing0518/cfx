package cn.cf.dao;

import cn.cf.dto.SysNewsExtDto;

import java.util.Map;

public interface SysNewsExtDao extends SysNewsDao{

	SysNewsExtDto getNewsByPk(Map<String,Object> map);

}
