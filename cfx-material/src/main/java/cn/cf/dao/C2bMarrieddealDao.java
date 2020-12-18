/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.C2bMarrieddeal;
import cn.cf.dto.C2bMarrieddealDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface C2bMarrieddealDao {
	int insert(C2bMarrieddeal model);
	int update(C2bMarrieddeal model);
	int delete(String id);
	List<C2bMarrieddealDto> searchGrid(Map<String, Object> map);
	List<C2bMarrieddealDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 C2bMarrieddealDto getByPk(java.lang.String pk); 
	 C2bMarrieddealDto getByMemberPk(java.lang.String memberPk); 
	 C2bMarrieddealDto getByMemberName(java.lang.String memberName); 
	 C2bMarrieddealDto getByAuditPk(java.lang.String auditPk); 
	 C2bMarrieddealDto getByContactsTel(java.lang.String contactsTel); 
	 C2bMarrieddealDto getByContacts(java.lang.String contacts); 
	 C2bMarrieddealDto getByBrandPk(java.lang.String brandPk); 
	 C2bMarrieddealDto getByBrandName(java.lang.String brandName); 
	 C2bMarrieddealDto getByProductPk(java.lang.String productPk); 
	 C2bMarrieddealDto getByProductName(java.lang.String productName); 
	 C2bMarrieddealDto getBySpecPk(java.lang.String specPk); 
	 C2bMarrieddealDto getBySpecName(java.lang.String specName); 
	 C2bMarrieddealDto getBySpecifications(java.lang.String specifications); 
	 C2bMarrieddealDto getByGradePk(java.lang.String gradePk); 
	 C2bMarrieddealDto getByGradeName(java.lang.String gradeName); 
	 C2bMarrieddealDto getBySeriesPk(java.lang.String seriesPk); 
	 C2bMarrieddealDto getBySeriesName(java.lang.String seriesName); 
	 C2bMarrieddealDto getByVarietiesPk(java.lang.String varietiesPk); 
	 C2bMarrieddealDto getByVarietiesName(java.lang.String varietiesName); 
	 C2bMarrieddealDto getBySeedvarietyPk(java.lang.String seedvarietyPk); 
	 C2bMarrieddealDto getBySeedvarietyName(java.lang.String seedvarietyName); 
	 C2bMarrieddealDto getByRefuseReason(java.lang.String refuseReason); 
	 C2bMarrieddealDto getByProvince(java.lang.String province); 
	 C2bMarrieddealDto getByProvinceName(java.lang.String provinceName); 
	 C2bMarrieddealDto getByCity(java.lang.String city); 
	 C2bMarrieddealDto getByCityName(java.lang.String cityName); 
	 C2bMarrieddealDto getByArea(java.lang.String area); 
	 C2bMarrieddealDto getByAreaName(java.lang.String areaName); 
	 C2bMarrieddealDto getByAddress(java.lang.String address); 
	 C2bMarrieddealDto getByUsePk(java.lang.String usePk); 
	 C2bMarrieddealDto getByUseName(java.lang.String useName); 
	 C2bMarrieddealDto getByRemarks(java.lang.String remarks); 
	 C2bMarrieddealDto getByPurchaserPk(java.lang.String purchaserPk); 
	 C2bMarrieddealDto getByPurchaserName(java.lang.String purchaserName); 
	 C2bMarrieddealDto getBySupplierPk(java.lang.String supplierPk); 
	 C2bMarrieddealDto getBySupplierName(java.lang.String supplierName); 

}
