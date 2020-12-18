package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysCategoryDto;

public interface SysCategoryService {

	List<SysCategoryDto> searchCategorys(Map<String, Object> map) throws Exception;

}
