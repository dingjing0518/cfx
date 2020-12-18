/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SxSpec;
import cn.cf.dto.SxSpecDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SxSpecDao {
	int insert(SxSpec model);
	int update(SxSpec model);
	int delete(String id);
	List<SxSpecDto> searchGrid(Map<String, Object> map);
	List<SxSpecDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SxSpecDto getByPk(String pk);
	 SxSpecDto getByName(String name);

}
