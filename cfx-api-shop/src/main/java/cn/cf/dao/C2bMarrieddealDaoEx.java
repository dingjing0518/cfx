package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.C2bMarrieddealDtoEx;
import cn.cf.model.C2bMarrieddeal;

public interface C2bMarrieddealDaoEx extends C2bMarrieddealDao{

	int exist(C2bMarrieddeal model);

	List<C2bMarrieddealDtoEx> searchMyGrid(Map<String, Object> map);

	int searchMyGridCount(Map<String, Object> map);

}
