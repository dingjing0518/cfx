package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SxTechnologyExtDto;

public interface SxTechnologyExtDao extends SxTechnologyDao{

    int searchGridExtCount(Map<String, Object> map);

    List<SxTechnologyExtDto> searchGridExt(Map<String, Object> map);

    int updateTechnology(SxTechnologyExtDto sxTechnologyExtDto);

    int check(Map<String, Object> m);

}
