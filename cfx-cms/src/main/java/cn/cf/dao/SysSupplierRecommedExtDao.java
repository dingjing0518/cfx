/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysSupplierRecommedExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysSupplierRecommedExtDao extends SysSupplierRecommedDao{
	
	
	int updateSupplierRecommed(SysSupplierRecommedExtDto dto);
	
	
	List<SysSupplierRecommedExtDto> searchGridExt(Map<String, Object> map);
	
	
	int searchGridExtCount(Map<String, Object> map);
	
	int searchRecommendCounts(Map<String,Object> map);
	
//	int searchSupplierRecommedCount(Map<String, Object> map);
//    List<SysSupplierRecommedExtDto> searchSupplierRecommedList(Map<String, Object> map);
	
}
