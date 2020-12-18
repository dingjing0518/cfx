/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bDimensionalityRelevancy;
import cn.cf.dto.B2bDimensionalityRelevancyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityRelevancyDao {
	int insert(B2bDimensionalityRelevancy model);
	int update(B2bDimensionalityRelevancy model);
	int delete(String id);
	List<B2bDimensionalityRelevancyDto> searchGrid(Map<String, Object> map);
	List<B2bDimensionalityRelevancyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bDimensionalityRelevancyDto getByPk(java.lang.String pk); 
	 B2bDimensionalityRelevancyDto getByDimenName(java.lang.String dimenName); 
	 B2bDimensionalityRelevancyDto getByDimenTypeName(java.lang.String dimenTypeName); 
	 B2bDimensionalityRelevancyDto getByLinkUrl(java.lang.String linkUrl); 

}
