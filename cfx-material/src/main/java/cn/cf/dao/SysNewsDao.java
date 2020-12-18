/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysNews;
import cn.cf.dto.SysNewsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysNewsDao {
	int insert(SysNews model);
	int update(SysNews model);
	int delete(String id);
	List<SysNewsDto> searchGrid(Map<String, Object> map);
	List<SysNewsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysNewsDto getByPk(java.lang.String pk); 
	 SysNewsDto getByTitle(java.lang.String title); 
	 SysNewsDto getByContent(java.lang.String content); 
	 SysNewsDto getByKeyword(java.lang.String keyword); 
	 SysNewsDto getByNewAbstrsct(java.lang.String newAbstrsct); 
	 SysNewsDto getByUrl(java.lang.String url); 
	 SysNewsDto getByNewSource(java.lang.String newSource); 

}
