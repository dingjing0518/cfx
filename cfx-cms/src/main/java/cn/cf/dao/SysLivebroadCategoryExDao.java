package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bLiveBroadCastCategoryExDto;
import cn.cf.dto.SysLivebroadCategoryDto;

public interface SysLivebroadCategoryExDao extends SysLivebroadCategoryDao {

	int searchGridExCount(Map<String, Object> map);

	List<B2bLiveBroadCastCategoryExDto> searchGridEx(Map<String, Object> map);

	int valiDateCounts(B2bLiveBroadCastCategoryExDto b2bLiveBraodCastCategoryExDto);

	int updateEx(SysLivebroadCategoryDto sysLivebroadCategoryDto);

	List<B2bLiveBroadCastCategoryExDto> getAllBroadCastCategory();

}
