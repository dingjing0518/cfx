/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysContrast;
import cn.cf.dto.SysContrastDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysContrastDao {
	int insert(SysContrast model);
	int update(SysContrast model);
	int delete(String id);
	List<SysContrastDto> searchGrid(Map<String, Object> map);
	List<SysContrastDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysContrastDto getByPk(java.lang.String pk); 
	 SysContrastDto getByName(java.lang.String name); 
	 SysContrastDto getByUrl(java.lang.String url); 

}
