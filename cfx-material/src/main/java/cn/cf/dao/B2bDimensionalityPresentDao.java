/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bDimensionalityPresent;
import cn.cf.dto.B2bDimensionalityPresentDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityPresentDao {
	int insert(B2bDimensionalityPresent model);
	int update(B2bDimensionalityPresent model);
	int delete(String id);
	List<B2bDimensionalityPresentDto> searchGrid(Map<String, Object> map);
	List<B2bDimensionalityPresentDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bDimensionalityPresentDto getByPk(java.lang.String pk); 
	 B2bDimensionalityPresentDto getByRewardPk(java.lang.String rewardPk); 
	 B2bDimensionalityPresentDto getByCompanyPk(java.lang.String companyPk); 
	 B2bDimensionalityPresentDto getByCompanyName(java.lang.String companyName); 
	 B2bDimensionalityPresentDto getByContactsTel(java.lang.String contactsTel); 
	 B2bDimensionalityPresentDto getByDimenName(java.lang.String dimenName); 
	 B2bDimensionalityPresentDto getByDimenTypeName(java.lang.String dimenTypeName); 

}
