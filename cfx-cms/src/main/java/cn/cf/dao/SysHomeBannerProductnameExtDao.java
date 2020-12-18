package cn.cf.dao;

import cn.cf.dto.SysHomeBannerProductnameDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2018/5/10.
 */
public interface SysHomeBannerProductnameExtDao  extends  SysHomeBannerProductnameDao{
    List<SysHomeBannerProductnameDto> getSysBannerProductNameToOtherList();

    List<SysHomeBannerProductnameDto> getAllSysBannerProductNameList();

    List<SysHomeBannerProductnameDto> searchExtList(Map<String, Object> map);
    
}
