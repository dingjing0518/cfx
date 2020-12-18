/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.LgPayType;
import cn.cf.dto.LgPayTypeDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface LgPayTypeDao {
	int insert(LgPayType model);
	int update(LgPayType model);
	int delete(String id);
	List<LgPayTypeDto> searchGrid(Map<String, Object> map);
	List<LgPayTypeDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 LgPayTypeDto getByPk(java.lang.String pk); 
	 LgPayTypeDto getByPayTypeName(java.lang.String payTypeName); 

}
