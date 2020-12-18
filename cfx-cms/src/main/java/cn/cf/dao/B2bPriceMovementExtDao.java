/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPriceMovementExtDto;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bPriceMovementExtDao extends B2bPriceMovementDao{

	List<B2bPriceMovementExtDto> searchGridExt(Map<String, Object> map);
	
	int searchGridCountExt(Map<String, Object> map);
	
	int updatePriceMovement(B2bPriceMovementExtDto dto);


}
