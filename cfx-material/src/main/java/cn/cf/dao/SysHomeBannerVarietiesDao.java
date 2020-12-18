/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2017
 */

package cn.cf.dao;

import cn.cf.model.SysHomeBannerVarieties;
import cn.cf.dto.SysHomeBannerVarietiesDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHomeBannerVarietiesDao {
	int insert(SysHomeBannerVarieties model);
	int update(SysHomeBannerVarieties model);
	int delete(String id);
	List<SysHomeBannerVarietiesDto> searchGrid(Map<String, Object> map);
	List<SysHomeBannerVarietiesDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHomeBannerVarietiesDto getByPk(java.lang.String pk); 
	 SysHomeBannerVarietiesDto getByProductbannerPk(java.lang.String productbannerPk); 
	 SysHomeBannerVarietiesDto getByVarietiesName(java.lang.String varietiesName); 
	 SysHomeBannerVarietiesDto getByVarietiesPk(java.lang.String varietiesPk); 

}
