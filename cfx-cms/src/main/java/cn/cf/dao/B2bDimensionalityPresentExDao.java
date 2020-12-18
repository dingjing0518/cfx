package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bDimensionalityPresentExDto;

public interface B2bDimensionalityPresentExDao extends B2bDimensionalityPresentDao {



	List<B2bDimensionalityPresentExDto> exportDimensionalityPresentList(Map<String, Object> map);

	int searchGridCountExt(Map<String, Object> map);

	List<B2bDimensionalityPresentExDto> searchGridExt(Map<String, Object> map);
	
	
	int searchGridCountExtWard(Map<String, Object> map);

	List<B2bDimensionalityPresentExDto> searchGridExtWard(Map<String, Object> map);

	void deleteByDimen(Map<String, Object> map);

}
