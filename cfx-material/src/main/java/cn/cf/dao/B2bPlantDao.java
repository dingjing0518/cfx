/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bPlant;
import cn.cf.dto.B2bPlantDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bPlantDao {
	int insert(B2bPlant model);
	int update(B2bPlant model);
	int delete(String id);
	List<B2bPlantDto> searchGrid(Map<String, Object> map);
	List<B2bPlantDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bPlantDto getByPk(java.lang.String pk); 
	 B2bPlantDto getByName(java.lang.String name); 
	 B2bPlantDto getByStorePk(java.lang.String storePk); 
	 B2bPlantDto getByAddress(java.lang.String address); 
	 B2bPlantDto getByProvince(java.lang.String province); 
	 B2bPlantDto getByProvinceName(java.lang.String provinceName); 
	 B2bPlantDto getByCity(java.lang.String city); 
	 B2bPlantDto getByCityName(java.lang.String cityName); 
	 B2bPlantDto getByArea(java.lang.String area); 
	 B2bPlantDto getByAreaName(java.lang.String areaName); 
	 B2bPlantDto getByTown(java.lang.String town); 
	 B2bPlantDto getByTownName(java.lang.String townName); 
	 B2bPlantDto getByPlantCode(java.lang.String plantCode); 
	 B2bPlantDto getByStoreName(java.lang.String storeName); 
	 B2bPlantDto getByContactsTel(java.lang.String contactsTel); 
	 B2bPlantDto getByContacts(java.lang.String contacts); 
	 B2bPlantDto getByLandline(java.lang.String landline); 

}
