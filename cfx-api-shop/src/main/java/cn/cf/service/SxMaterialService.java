package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SxMaterialDto;
import cn.cf.dto.SxSpecDto;
import cn.cf.dto.SxTechnologyDto;

public interface SxMaterialService {


	/**
	 * 获取工艺 字典表数据
	 * @param map
	 * @return
	 */
	List<SxTechnologyDto> searchTechnologyList(Map<String, Object> map);

	List<SxMaterialDto> searchSxMaterialList(Map<String, Object> map);
 

    List<SxSpecDto> getSxSpecList(Map<String, Object> map);

}
