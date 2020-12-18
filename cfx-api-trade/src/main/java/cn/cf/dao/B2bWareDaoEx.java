package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bWareDto;
import cn.cf.dto.B2bWareDtoEx;

public interface B2bWareDaoEx extends B2bWareDao{

	B2bWareDto isWareRepeated(Map<String, Object> map);

	List<B2bWareDtoEx> searchWareGrid(Map<String, Object> map);

}
