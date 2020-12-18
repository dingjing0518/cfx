/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgLinePrice;
import cn.cf.dto.LgLinePriceDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgLinePriceDao {
	int insert(LgLinePrice model);
	int update(LgLinePrice model);
	int delete(String id);
	List<LgLinePriceDto> searchGrid(Map<String, Object> map);
	List<LgLinePriceDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgLinePriceDto getByPk(java.lang.String pk); 
	 LgLinePriceDto getByLinePk(java.lang.String linePk); 

}
