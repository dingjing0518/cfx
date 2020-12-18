/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SxMaterial;
import cn.cf.dto.SxMaterialDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SxMaterialDao {
	int insert(SxMaterial model);
	int update(SxMaterial model);
	int delete(String id);
	List<SxMaterialDto> searchGrid(Map<String, Object> map);
	List<SxMaterialDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SxMaterialDto getByPk(java.lang.String pk); 
	 SxMaterialDto getByName(java.lang.String name); 
	 SxMaterialDto getByDetils(java.lang.String detils); 

}
