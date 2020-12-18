package cn.cf.service;

import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.SysRegionsDto;

public interface LogisticsSyncService {
	
	public SysRegionsDto getRegionsByName(String name);
	
	public B2bProductDto getByProductName(String name);
	
	public B2bPlantDto getByPlantName(String name);
	
	
}
