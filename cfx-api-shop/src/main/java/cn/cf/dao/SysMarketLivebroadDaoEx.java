/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.dto.SysMarketLivebroadDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysMarketLivebroadDaoEx extends SysMarketLivebroadDao{
	
	List<SysMarketLivebroadDto> searchGridEx(Map<String, Object> map);
	
	int searchGridCountEx(Map<String, Object> map);
	

}
