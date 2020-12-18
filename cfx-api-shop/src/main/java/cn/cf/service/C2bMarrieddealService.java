package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.C2bMarrieddealDto;
import cn.cf.dto.C2bMarrieddealDtoEx;
import cn.cf.model.C2bMarrieddeal;

public interface C2bMarrieddealService {

	int exist(C2bMarrieddealDto marrieddeal);

	void addMarrieddeal(C2bMarrieddealDto marrieddeal);

	void update(C2bMarrieddeal model);

	C2bMarrieddealDto getByMarrieddealId(String marrieddealPk);

	void insert(C2bMarrieddeal model);

	List<C2bMarrieddealDtoEx> searchMyGrid(Map<String, Object> map);

	int searchMyGridCount(Map<String, Object> map);

}
