package cn.cf.dao;

import cn.cf.dto.SysHomeBannerVarietiesDto;
import cn.cf.dto.SysHomeBannerVarietiesExtDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2018/5/10.
 */
public interface SysHomeBannerVarietiesExtDao {
    int updateObj(SysHomeBannerVarietiesDto dto);
    List<SysHomeBannerVarietiesExtDto> searchGrid(Map<String, Object> map);
    List<SysHomeBannerVarietiesExtDto> searchList(Map<String, Object> map);
    int searchGridCount(Map<String, Object> map);
    int updateBannerByProductnamePk(java.lang.String productnamePk);
}
