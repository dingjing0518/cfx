/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2017
 */

package cn.cf.dao;

import cn.cf.model.SysHomeBannerMassage;
import cn.cf.dto.SysHomeBannerMassageDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHomeBannerMassageDao {
	int insert(SysHomeBannerMassage model);
	int update(SysHomeBannerMassage model);
	int delete(String id);
	List<SysHomeBannerMassageDto> searchGrid(Map<String, Object> map);
	List<SysHomeBannerMassageDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHomeBannerMassageDto getByPk(java.lang.String pk); 
	 SysHomeBannerMassageDto getByUrl(java.lang.String url); 
	 SysHomeBannerMassageDto getByProductnamePk(java.lang.String productnamePk); 

}
