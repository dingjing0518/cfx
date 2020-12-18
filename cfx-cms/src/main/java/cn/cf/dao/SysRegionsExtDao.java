package cn.cf.dao;

import cn.cf.dto.SysRegionsDto;
import cn.cf.model.SysRegions;

import java.util.List;
import java.util.Map;

public interface SysRegionsExtDao {
    List<SysRegionsDto> getByParentPkList(java.lang.String parentPk);

    List<SysRegionsDto> getAllSubRegionsDesc(java.lang.String parentPk);

    List<SysRegionsDto> getAllRegions();

    int  getMaxPk();


    int insert(SysRegions model);
    int update(SysRegions model);
    int delete(String id);
    List<SysRegionsDto> searchGrid(Map<String, Object> map);
    List<SysRegionsDto> searchList(Map<String, Object> map);
    int searchGridCount(Map<String, Object> map);
    SysRegionsDto getByPk(java.lang.String pk);
    SysRegionsDto getByName(java.lang.String name);
    SysRegionsDto getByParentPk(java.lang.String parentPk);
    SysRegionsDto getByIsVisable(java.lang.String isVisable);
    String selectRegionsNameByName(Map<String, Object> map); ;
}
