package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bPlantDto;

public interface B2bPlantService {

	B2bPlantDto getByName(Map<String, Object> map);

	String createNewPlant(String plantName, String plantCode, String plantAddress, String storePk);

}
