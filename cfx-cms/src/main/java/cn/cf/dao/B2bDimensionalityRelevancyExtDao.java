/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bDimensionalityRelevancyDto;
import cn.cf.dto.B2bDimensionalityRelevancyExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityRelevancyExtDao extends B2bDimensionalityRelevancyDao{

	int updateDimenRe(B2bDimensionalityRelevancyExtDto extDto);

	List<B2bDimensionalityRelevancyExtDto> searchGridExt(Map<String, Object> map);
	
	int searchGridExtCount(Map<String, Object> map);
	
	List<B2bDimensionalityRelevancyExtDto> isExistName(Map<String, Object> map);

	B2bDimensionalityRelevancyDto getCategoryNamePk(Map<String, String> map);
	

}
