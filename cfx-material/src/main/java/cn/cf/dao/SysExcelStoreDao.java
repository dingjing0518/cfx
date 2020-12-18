/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SysExcelStore;
import cn.cf.dto.SysExcelStoreDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysExcelStoreDao {
	int insert(SysExcelStore model);
	int update(SysExcelStore model);
	int delete(String id);
	List<SysExcelStoreDto> searchGrid(Map<String, Object> map);
	List<SysExcelStoreDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysExcelStoreDto getByPk(java.lang.String pk); 
	 SysExcelStoreDto getByName(java.lang.String name); 
	 SysExcelStoreDto getByParams(java.lang.String params); 
	 SysExcelStoreDto getByMethodName(java.lang.String methodName); 
	 SysExcelStoreDto getByUrl(java.lang.String url); 

}
