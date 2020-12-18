package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysHelpsCategoryDtoEx;
import cn.cf.dto.SysHelpsDto;



public interface SysHelpService {

	List<SysHelpsCategoryDtoEx> searchAll(Map<String, Object> map) throws Exception;

	SysHelpsDto searchByPk(String pk);
	
	
	SysHelpsDto getAboutUs(Map<String, Object> map);

}
