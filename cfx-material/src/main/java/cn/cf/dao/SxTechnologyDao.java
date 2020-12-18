/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SxTechnology;
import cn.cf.dto.SxTechnologyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SxTechnologyDao {
	int insert(SxTechnology model);
	int update(SxTechnology model);
	int delete(String id);
	List<SxTechnologyDto> searchGrid(Map<String, Object> map);
	List<SxTechnologyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SxTechnologyDto getByPk(java.lang.String pk); 
	 SxTechnologyDto getByName(java.lang.String name); 

}
