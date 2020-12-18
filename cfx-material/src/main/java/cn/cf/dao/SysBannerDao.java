/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.SysBanner;
import cn.cf.dto.SysBannerDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysBannerDao {
	int insert(SysBanner model);
	int update(SysBanner model);
	int delete(String id);
	List<SysBannerDto> searchGrid(Map<String, Object> map);
	List<SysBannerDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysBannerDto getByPk(java.lang.String pk); 
	 SysBannerDto getByName(java.lang.String name); 
	 SysBannerDto getByUrl(java.lang.String url); 
	 SysBannerDto getByDetails(java.lang.String details); 
	 SysBannerDto getByLinkUrl(java.lang.String linkUrl); 

}
