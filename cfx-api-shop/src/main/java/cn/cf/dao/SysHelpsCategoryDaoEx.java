/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;
import cn.cf.dto.SysHelpsCategoryDtoEx;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHelpsCategoryDaoEx extends SysHelpsCategoryDao{
	
	List<SysHelpsCategoryDtoEx> searchGridEx(Map<String, Object> map);
	
}
