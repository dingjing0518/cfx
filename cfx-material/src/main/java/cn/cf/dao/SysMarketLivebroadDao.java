/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysMarketLivebroad;
import cn.cf.dto.SysMarketLivebroadDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysMarketLivebroadDao {
	int insert(SysMarketLivebroad model);
	int update(SysMarketLivebroad model);
	int delete(String id);
	List<SysMarketLivebroadDto> searchGrid(Map<String, Object> map);
	List<SysMarketLivebroadDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysMarketLivebroadDto getByPk(java.lang.String pk); 
	 SysMarketLivebroadDto getByLivebroadcategoryName(java.lang.String livebroadcategoryName); 
	 SysMarketLivebroadDto getByLivebroadcategoryPk(java.lang.String livebroadcategoryPk); 
	 SysMarketLivebroadDto getByContent(java.lang.String content); 
	 SysMarketLivebroadDto getByKeyword(java.lang.String keyword); 

}
