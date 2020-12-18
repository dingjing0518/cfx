package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.dto.B2bPlantDto;
import cn.cf.dto.B2bPlantDtoEx;
import cn.cf.model.B2bPlant;

public interface B2bPlantService {

	List<B2bPlantDto> searchList(Map<String, Object> map);
	/**
	 * 产区是否已存在
	 * @param map
	 * @return
	 */
	B2bPlantDto isPlantRepeated(Map<String, Object> map);
	
	int insert(B2bPlant plantModel);
	
	int update(B2bPlant plantModel);
	
	B2bPlantDto getByPk(String pk);
	
	PageModel<B2bPlantDtoEx> searchPlantList(Map<String, Object> map);
	
	List<B2bPlantDto> searchAllPlantList(Map<String, Object> map);

}
