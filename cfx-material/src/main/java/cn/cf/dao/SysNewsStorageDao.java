/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysNewsStorage;
import cn.cf.dto.SysNewsStorageDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysNewsStorageDao {
	int insert(SysNewsStorage model);
	int update(SysNewsStorage model);
	int delete(String id);
	List<SysNewsStorageDto> searchGrid(Map<String, Object> map);
	List<SysNewsStorageDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysNewsStorageDto getByPk(java.lang.String pk); 
	 SysNewsStorageDto getByCategoryPk(java.lang.String categoryPk); 
	 SysNewsStorageDto getByCategoryName(java.lang.String categoryName); 
	 SysNewsStorageDto getByNewsPk(java.lang.String newsPk); 
	 SysNewsStorageDto getByTitle(java.lang.String title); 
	 SysNewsStorageDto getByContent(java.lang.String content); 
	 SysNewsStorageDto getByKeyword(java.lang.String keyword); 
	 SysNewsStorageDto getByNewAbstrsct(java.lang.String newAbstrsct); 
	 SysNewsStorageDto getByUrl(java.lang.String url); 
	 SysNewsStorageDto getByNewSource(java.lang.String newSource); 

}
