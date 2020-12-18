/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bAddress;
import cn.cf.dto.B2bAddressDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bAddressDao {
	int insert(B2bAddress model);
	int update(B2bAddress model);
	int delete(String id);
	List<B2bAddressDto> searchGrid(Map<String, Object> map);
	List<B2bAddressDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bAddressDto getByPk(java.lang.String pk); 
	 B2bAddressDto getByCompanyPk(java.lang.String companyPk); 
	 B2bAddressDto getByAddress(java.lang.String address); 
	 B2bAddressDto getByProvinceName(java.lang.String provinceName); 
	 B2bAddressDto getByProvince(java.lang.String province); 
	 B2bAddressDto getByCityName(java.lang.String cityName); 
	 B2bAddressDto getByCity(java.lang.String city); 
	 B2bAddressDto getByAreaName(java.lang.String areaName); 
	 B2bAddressDto getByArea(java.lang.String area); 
	 B2bAddressDto getByTown(java.lang.String town); 
	 B2bAddressDto getByTownName(java.lang.String townName); 
	 B2bAddressDto getByContacts(java.lang.String contacts); 
	 B2bAddressDto getByContactsTel(java.lang.String contactsTel); 

}
