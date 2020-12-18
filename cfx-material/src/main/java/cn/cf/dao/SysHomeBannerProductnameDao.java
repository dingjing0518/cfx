/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2017
 */

package cn.cf.dao;

import cn.cf.model.SysHomeBannerProductname;
import cn.cf.dto.SysHomeBannerProductnameDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHomeBannerProductnameDao {
	int insert(SysHomeBannerProductname model);
	int update(SysHomeBannerProductname model);
	int updateObj(SysHomeBannerProductnameDto dto);
	int delete(String id);
	List<SysHomeBannerProductnameDto> searchGrid(Map<String, Object> map);
	List<SysHomeBannerProductnameDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHomeBannerProductnameDto getByPk(java.lang.String pk); 
	 SysHomeBannerProductnameDto getByProductPk(java.lang.String productPk); 
	 SysHomeBannerProductnameDto getByProductName(java.lang.String productName); 

}
