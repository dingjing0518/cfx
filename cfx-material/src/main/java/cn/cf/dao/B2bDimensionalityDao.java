/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bDimensionality;
import cn.cf.dto.B2bDimensionalityDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityDao {
	int insert(B2bDimensionality model);
	int update(B2bDimensionality model);
	int delete(String id);
	List<B2bDimensionalityDto> searchGrid(Map<String, Object> map);
	List<B2bDimensionalityDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bDimensionalityDto getByPk(java.lang.String pk); 
	 B2bDimensionalityDto getByName(java.lang.String name);
	 int updateDimensionality(B2bDimensionalityDto dto);
	 List<B2bDimensionalityDto> isExistName(Map<String, Object> map);

}
