/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SysProperty;
import cn.cf.dto.SysPropertyDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysPropertyDao {
	int insert(SysProperty model);
	int update(SysProperty model);
	int delete(String id);
	List<SysPropertyDto> searchGrid(Map<String, Object> map);
	List<SysPropertyDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysPropertyDto getByPk(java.lang.String pk); 
	 SysPropertyDto getByProductPk(java.lang.String productPk); 
	 SysPropertyDto getByProductName(java.lang.String productName); 
	 SysPropertyDto getByProductShotName(java.lang.String productShotName); 
	 SysPropertyDto getByContent(java.lang.String content); 

}
