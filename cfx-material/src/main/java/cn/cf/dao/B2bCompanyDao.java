/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bCompany;
import cn.cf.dto.B2bCompanyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCompanyDao {
	int insert(B2bCompany model);
	int update(B2bCompany model);
	int delete(String id);
	List<B2bCompanyDto> searchGrid(Map<String, Object> map);
	List<B2bCompanyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCompanyDto getByPk(java.lang.String pk); 
	 B2bCompanyDto getByName(java.lang.String name);
	 B2bCompanyDto getCompanyByNameAndIsDelete(String name);
	 B2bCompanyDto getByParentPk(java.lang.String parentPk); 
	 B2bCompanyDto getByProvinceName(java.lang.String provinceName); 
	 B2bCompanyDto getByProvince(java.lang.String province); 
	 B2bCompanyDto getByCityName(java.lang.String cityName); 
	 B2bCompanyDto getByCity(java.lang.String city); 
	 B2bCompanyDto getByAreaName(java.lang.String areaName); 
	 B2bCompanyDto getByArea(java.lang.String area); 
	 B2bCompanyDto getByContactsTel(java.lang.String contactsTel); 
	 B2bCompanyDto getByContacts(java.lang.String contacts); 
	 B2bCompanyDto getByBlUrl(java.lang.String blUrl); 
	 B2bCompanyDto getByOrganizationCode(java.lang.String organizationCode); 
	 B2bCompanyDto getByEcUrl(java.lang.String ecUrl); 
	 B2bCompanyDto getByAuditPk(java.lang.String auditPk); 
	 B2bCompanyDto getByAuditSpPk(java.lang.String auditSpPk); 
	 B2bCompanyDto getByRefuseReason(java.lang.String refuseReason); 
	 B2bCompanyDto getByHeadPortrait(java.lang.String headPortrait); 
	 B2bCompanyDto getByRemarks(java.lang.String remarks); 

}
