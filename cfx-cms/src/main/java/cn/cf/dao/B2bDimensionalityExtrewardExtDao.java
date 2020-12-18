/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bDimensionalityExtrewardExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bDimensionalityExtrewardExtDao extends B2bDimensionalityExtrewardDao{

	int updateDimenExtreward(B2bDimensionalityExtrewardExtDto extDto);

	List<B2bDimensionalityExtrewardExtDto> searchGridExt(Map<String, Object> map);
	
	int searchGridExtCount(Map<String, Object> map);
	
	List<B2bDimensionalityExtrewardExtDto> isExistName(Map<String, Object> map);
	

}
