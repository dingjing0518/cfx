package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysVersionManagementDto;
import cn.cf.dto.SysVersionManagementExtDto;

public interface SysVersionManagementExtDao extends SysVersionManagementDao{

    int searchGridExtCount(Map<String, Object> map);

    List<SysVersionManagementExtDto> searchGridExt(Map<String, Object> map);

    int isExtVerionNum(Map<String, Object> map);

    int updateExt(SysVersionManagementDto dto);

}
