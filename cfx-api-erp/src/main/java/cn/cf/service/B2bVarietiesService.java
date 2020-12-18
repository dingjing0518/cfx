package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bVarietiesDto;

public interface B2bVarietiesService {


	public B2bVarietiesDto getByParentAndName(Map<String, Object> map);
	public B2bVarietiesDto getByPk(String pk);
	public String createNewVarieties(Map<String, Object> map);

}
