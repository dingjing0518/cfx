/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2017
 */

package cn.cf.dao;

import cn.cf.model.SysHomeBannerSeries;
import cn.cf.dto.SysHomeBannerSeriesDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface SysHomeBannerSeriesDao {
	int insert(SysHomeBannerSeries model);
	int update(SysHomeBannerSeries model);
	int delete(String id);
	List<SysHomeBannerSeriesDto> searchGrid(Map<String, Object> map);
	List<SysHomeBannerSeriesDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 SysHomeBannerSeriesDto getByPk(java.lang.String pk); 
	 SysHomeBannerSeriesDto getBySeriesPk(java.lang.String seriesPk); 
	 SysHomeBannerSeriesDto getBySeriesName(java.lang.String seriesName); 
	 SysHomeBannerSeriesDto getBySpecPk(java.lang.String specPk); 
}
