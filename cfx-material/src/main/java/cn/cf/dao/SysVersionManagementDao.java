/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SysVersionManagement;
import cn.cf.dto.SysVersionManagementDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysVersionManagementDao {
	int insert(SysVersionManagement model);
	int update(SysVersionManagement model);
	int delete(String id);
	List<SysVersionManagementDto> searchGrid(Map<String, Object> map);
	List<SysVersionManagementDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysVersionManagementDto getByPk(java.lang.String pk); 
	 SysVersionManagementDto getByVersion(java.lang.String version); 
	 SysVersionManagementDto getByDownloadUrl(java.lang.String downloadUrl); 
	 SysVersionManagementDto getByPublisher(java.lang.String publisher); 
	 SysVersionManagementDto getByRemark(java.lang.String remark); 
	 SysVersionManagementDto getByVersionName(java.lang.String versionName); 
	 SysVersionManagementDto searchOne(Map<String, Object> map);
}
