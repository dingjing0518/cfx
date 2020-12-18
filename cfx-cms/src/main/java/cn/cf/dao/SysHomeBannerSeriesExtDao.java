package cn.cf.dao;


import cn.cf.dto.SysHomeBannerSeriesDto;
import cn.cf.dto.SysHomeBannerSeriesExtDto;

import java.util.List;
import java.util.Map;

public interface SysHomeBannerSeriesExtDao {
    int updateBannerByProductnamePk(java.lang.String productnamePk);
    int updateObj(SysHomeBannerSeriesDto dto);
    List<SysHomeBannerSeriesExtDto> searchGrid(Map<String, Object> map);
    int searchCount(Map<String, Object> map);
}
