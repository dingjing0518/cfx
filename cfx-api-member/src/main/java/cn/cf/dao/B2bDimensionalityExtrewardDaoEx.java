package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.B2bDimensionalityExtrewardDto;

public interface B2bDimensionalityExtrewardDaoEx extends B2bDimensionalityExtrewardDao{
	
	List<B2bDimensionalityExtrewardDto> searchListEx(Map<String, Object> map);
	
	/**
	 * 根据维度类别查询所有维度
	 * @param dimenCategory 维度类别
	 * @return
	 */
	List<B2bDimensionalityExtrewardDto> getExtrewardByDimenCategory(String dimenCategory);
	
}
