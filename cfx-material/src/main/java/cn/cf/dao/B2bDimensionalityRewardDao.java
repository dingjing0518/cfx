/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bDimensionalityReward;
import cn.cf.dto.B2bDimensionalityRewardDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityRewardDao {
	int insert(B2bDimensionalityReward model);
	int update(B2bDimensionalityReward model);
	int delete(String id);
	List<B2bDimensionalityRewardDto> searchGrid(Map<String, Object> map);
	List<B2bDimensionalityRewardDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bDimensionalityRewardDto getByPk(java.lang.String pk); 
	 B2bDimensionalityRewardDto getByDimenName(java.lang.String dimenName); 
	 B2bDimensionalityRewardDto getByDimenTypeName(java.lang.String dimenTypeName); 

}
