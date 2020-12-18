/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgLine;
import cn.cf.dto.LgLineDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgLineDao {
	int insert(LgLine model);
	int update(LgLine model);
	int delete(String id);
	List<LgLineDto> searchGrid(Map<String, Object> map);
	List<LgLineDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgLineDto getByPk(java.lang.String pk); 
	 LgLineDto getByName(java.lang.String name); 
	 LgLineDto getByCompanyPk(java.lang.String companyPk); 
	 LgLineDto getByProductPk(java.lang.String productPk); 
	 LgLineDto getByProductName(java.lang.String productName); 
	 LgLineDto getByGradePk(java.lang.String gradePk); 
	 LgLineDto getByGradeName(java.lang.String gradeName); 
	 LgLineDto getByFromProvicePk(java.lang.String fromProvicePk); 
	 LgLineDto getByFromProviceName(java.lang.String fromProviceName); 
	 LgLineDto getByFromCityPk(java.lang.String fromCityPk); 
	 LgLineDto getByFromCityName(java.lang.String fromCityName); 
	 LgLineDto getByFromAreaPk(java.lang.String fromAreaPk); 
	 LgLineDto getByFromAreaName(java.lang.String fromAreaName); 
	 LgLineDto getByToProvicePk(java.lang.String toProvicePk); 
	 LgLineDto getByToProviceName(java.lang.String toProviceName); 
	 LgLineDto getByToCityPk(java.lang.String toCityPk); 
	 LgLineDto getByToCityName(java.lang.String toCityName); 
	 LgLineDto getByToAreaPk(java.lang.String toAreaPk); 
	 LgLineDto getByToAreaName(java.lang.String toAreaName); 
	 LgLineDto getByFromTownPk(java.lang.String fromTownPk); 
	 LgLineDto getByFromTownName(java.lang.String fromTownName); 
	 LgLineDto getByToTownPk(java.lang.String toTownPk); 
	 LgLineDto getByToTownName(java.lang.String toTownName); 

}
