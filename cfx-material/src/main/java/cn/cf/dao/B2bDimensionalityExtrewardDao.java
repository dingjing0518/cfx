/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bDimensionalityExtreward;
import cn.cf.dto.B2bDimensionalityExtrewardDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityExtrewardDao {
	int insert(B2bDimensionalityExtreward model);
	int update(B2bDimensionalityExtreward model);
	int delete(String id);
	List<B2bDimensionalityExtrewardDto> searchGrid(Map<String, Object> map);
	List<B2bDimensionalityExtrewardDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bDimensionalityExtrewardDto getByPk(java.lang.String pk); 
	 B2bDimensionalityExtrewardDto getByDimenName(java.lang.String dimenName); 
	 B2bDimensionalityExtrewardDto getByDimenTypeName(java.lang.String dimenTypeName); 
	 B2bDimensionalityExtrewardDto getByDetail(java.lang.String detail); 
	 B2bDimensionalityExtrewardDto getByTimesDetail(java.lang.String timesDetail); 

}
