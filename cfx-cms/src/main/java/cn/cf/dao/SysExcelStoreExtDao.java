/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.model.SysExcelStore;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysExcelStoreExtDao extends SysExcelStoreDao {

	List<SysExcelStoreExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);

	List<SysExcelStoreExtDto> getFirstTimeExcelStore(Map<String, Object> map);

	SysExcelStoreExtDto getIsFirstTimeExcelStore();
}
