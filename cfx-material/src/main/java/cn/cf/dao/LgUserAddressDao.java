/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgUserAddress;
import cn.cf.dto.LgUserAddressDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgUserAddressDao {
	int insert(LgUserAddress model);
	int update(LgUserAddress model);
	int delete(String id);
	List<LgUserAddressDto> searchGrid(Map<String, Object> map);
	List<LgUserAddressDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgUserAddressDto getByPk(java.lang.String pk); 
	 LgUserAddressDto getByUserPk(java.lang.String userPk); 
	 LgUserAddressDto getByAddress(java.lang.String address); 
	 LgUserAddressDto getByProvince(java.lang.String province); 
	 LgUserAddressDto getByProvinceName(java.lang.String provinceName); 
	 LgUserAddressDto getByCity(java.lang.String city); 
	 LgUserAddressDto getByCityName(java.lang.String cityName); 
	 LgUserAddressDto getByArea(java.lang.String area); 
	 LgUserAddressDto getByAreaName(java.lang.String areaName); 
	 LgUserAddressDto getByTown(java.lang.String town); 
	 LgUserAddressDto getByTownName(java.lang.String townName); 
	 LgUserAddressDto getByContacts(java.lang.String contacts); 
	 LgUserAddressDto getByContactsTel(java.lang.String contactsTel); 
	 LgUserAddressDto getByFixedTel(java.lang.String fixedTel); 
	 LgUserAddressDto getByZipCode(java.lang.String zipCode); 
	 LgUserAddressDto getByCompanyName(java.lang.String companyName); 
	 LgUserAddressDto getByCompanyPk(java.lang.String companyPk); 
	 LgUserAddressDto getByParentCompanyPk(java.lang.String parentCompanyPk); 
	 LgUserAddressDto getByTitle(java.lang.String title); 

}
