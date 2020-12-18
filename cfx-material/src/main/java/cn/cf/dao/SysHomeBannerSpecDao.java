/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2017
 */

package cn.cf.dao;

import cn.cf.model.SysHomeBannerSpec;
import cn.cf.dto.SysHomeBannerSpecDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHomeBannerSpecDao {
	int insert(SysHomeBannerSpec model);
	int update(SysHomeBannerSpec model);
	int delete(String id);
	List<SysHomeBannerSpecDto> searchGrid(Map<String, Object> map);
	List<SysHomeBannerSpecDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHomeBannerSpecDto getByPk(java.lang.String pk); 
	 SysHomeBannerSpecDto getByProductnamePk(java.lang.String productnamePk); 
	 SysHomeBannerSpecDto getBySpecPk(java.lang.String specPk); 
	 SysHomeBannerSpecDto getBySpecName(java.lang.String specName); 

}
