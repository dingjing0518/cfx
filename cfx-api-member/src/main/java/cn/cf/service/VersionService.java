package cn.cf.service;

import cn.cf.dto.SysVersionManagementDto;


public interface VersionService {

    SysVersionManagementDto searchVersionRecent(String type);

}
