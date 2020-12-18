/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bManageRegion;
import cn.cf.dto.B2bManageRegionDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bManageRegionDao {
	int insert(B2bManageRegion model);
	int update(B2bManageRegion model);
	int delete(String id);
	List<B2bManageRegionDto> searchGrid(Map<String, Object> map);
	List<B2bManageRegionDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bManageRegionDto getByPk(java.lang.String pk); 
	 B2bManageRegionDto getByName(java.lang.String name); 
	 B2bManageRegionDto getByArea(java.lang.String area); 

}
