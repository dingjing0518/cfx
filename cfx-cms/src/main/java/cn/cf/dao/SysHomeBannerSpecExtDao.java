package cn.cf.dao;

import cn.cf.dto.SysHomeBannerSpecDto;
import cn.cf.dto.SysHomeBannerSpecExtDto;

import java.util.List;
import java.util.Map;

public interface SysHomeBannerSpecExtDao  extends SysHomeBannerSpecDao{
    int updateBannerByProductnamePk(java.lang.String productnamePk);
    List<SysHomeBannerSpecExtDto> getByProductnamePkExt(java.lang.String productnamePk);
    int updateObj(SysHomeBannerSpecDto dto);
    int updateBannerBySpecPk(java.lang.String productnamePk);
    List<SysHomeBannerSpecExtDto> searchGridExt(Map<String, Object> map);
    List<SysHomeBannerSpecDto> getBySpecPkExt(String specPk);
    int searchGridExtCount(Map<String, Object> map);
}
