/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgCompany;
import cn.cf.dto.LgCompanyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgCompanyDao {
	int insert(LgCompany model);
	int update(LgCompany model);
	int delete(String id);
	List<LgCompanyDto> searchGrid(Map<String, Object> map);
	List<LgCompanyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgCompanyDto getByPk(java.lang.String pk); 
	 LgCompanyDto getByName(java.lang.String name); 
	 LgCompanyDto getByProvince(java.lang.String province); 
	 LgCompanyDto getByProvinceName(java.lang.String provinceName); 
	 LgCompanyDto getByCity(java.lang.String city); 
	 LgCompanyDto getByCityName(java.lang.String cityName); 
	 LgCompanyDto getByArea(java.lang.String area); 
	 LgCompanyDto getByAreaName(java.lang.String areaName); 
	 LgCompanyDto getByContacts(java.lang.String contacts); 
	 LgCompanyDto getByContactsTel(java.lang.String contactsTel); 
	 LgCompanyDto getByBusinessLicense(java.lang.String businessLicense); 
	 LgCompanyDto getByBlUrl(java.lang.String blUrl); 
	 LgCompanyDto getByHeadPortrait(java.lang.String headPortrait);




}
