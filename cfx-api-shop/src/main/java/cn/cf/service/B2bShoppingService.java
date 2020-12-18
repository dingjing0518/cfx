package cn.cf.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dto.*;


public interface B2bShoppingService {

	List<SysHomeBannerProductnameDtoEx> homeProductList(Map<String, Object>map);
	
	
	List<SysHomeBannerProductnameDtoEx> nagigationProductList(Map<String, Object>map);
	
	
	List<SysHomeBannerVarietiesDtoEx> nagigationVarietiesList(Map<String,Object> map);
	
	List<SysSupplierRecommedDto> nagigationSupplierRecommedList(Map<String,Object> map);

	LinkedHashMap<Integer, Object> getLeftAllList();

	
}
