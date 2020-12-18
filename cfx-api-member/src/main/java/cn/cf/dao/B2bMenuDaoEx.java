package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMenuDto;

public interface B2bMenuDaoEx extends B2bMenuDao{

	List<B2bMenuDto> searchMenuList(Map<String,Object> map);
}
