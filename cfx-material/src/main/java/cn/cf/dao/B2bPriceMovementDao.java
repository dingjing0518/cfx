/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bPriceMovement;
import cn.cf.dto.B2bPriceMovementDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bPriceMovementDao {
	int insert(B2bPriceMovement model);
	int update(B2bPriceMovement model);
	int delete(String id);
	List<B2bPriceMovementDto> searchGrid(Map<String, Object> map);
	List<B2bPriceMovementDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bPriceMovementDto getByPk(java.lang.String pk); 
	 B2bPriceMovementDto getByGoodsPk(java.lang.String goodsPk); 

}
