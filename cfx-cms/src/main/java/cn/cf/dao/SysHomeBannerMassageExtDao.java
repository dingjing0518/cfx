package cn.cf.dao;

import cn.cf.dto.SysHomeBannerMassageDto;
import cn.cf.dto.SysHomeBannerMassageExtDto;

import java.util.List;
import java.util.Map;

public interface SysHomeBannerMassageExtDao {
    int updateBannerByProductnamePk(java.lang.String productnamePk);
    int updateObj(SysHomeBannerMassageDto dto);
    List<SysHomeBannerMassageExtDto> searchGrid(Map<String, Object> map);
    int searchGridCount(Map<String, Object> map);
}
