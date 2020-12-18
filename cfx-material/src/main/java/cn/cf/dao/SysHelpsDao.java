/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysHelps;
import cn.cf.dto.SysHelpsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHelpsDao {
	int insert(SysHelps model);
	int update(SysHelps model);
	int delete(String id);
	List<SysHelpsDto> searchGrid(Map<String, Object> map);
	List<SysHelpsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHelpsDto getByPk(java.lang.String pk); 
	 SysHelpsDto getByTitle(java.lang.String title); 
	 SysHelpsDto getByContent(java.lang.String content); 
	 SysHelpsDto getByHelpCategoryPk(java.lang.String helpCategoryPk); 

}
