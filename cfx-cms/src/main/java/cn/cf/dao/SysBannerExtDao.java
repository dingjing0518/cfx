/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysBannerExtDto;
import cn.cf.model.SysBanner;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysBannerExtDao extends SysBannerDao{
	
	int updateBanner(SysBannerExtDto dto);
	
	List<SysBannerExtDto> searchGridExt(Map<String, Object> map);
	
	int searchGridExtCount(Map<String, Object> map);

    int searchGridCount(Map<String, Object> map);

	int updateModel(SysBanner banner);
	
}
