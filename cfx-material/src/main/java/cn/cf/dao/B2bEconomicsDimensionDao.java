/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bEconomicsDimension;
import cn.cf.dto.B2bEconomicsDimensionDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsDimensionDao {
	int insert(B2bEconomicsDimension model);
	int update(B2bEconomicsDimension model);
	int delete(String id);
	List<B2bEconomicsDimensionDto> searchGrid(Map<String, Object> map);
	List<B2bEconomicsDimensionDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bEconomicsDimensionDto getByPk(java.lang.String pk); 
	 B2bEconomicsDimensionDto getByCategory(java.lang.String category); 
	 B2bEconomicsDimensionDto getByItem(java.lang.String item); 
	 B2bEconomicsDimensionDto getByContent(java.lang.String content); 

}
