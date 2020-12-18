package cn.cf.service.operation;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysVersionManagementDto;
import cn.cf.dto.SysVersionManagementExtDto;

public interface VersionService {
    /**
     * 版本管理列表
     * 
     * @param qm
     * @return
     */
    PageModel<SysVersionManagementExtDto> searchVersionList(QueryModel<SysVersionManagementExtDto> qm);

    /**
     * 编辑app版本
     * @param dto
     * @return
     */
    int updateVersion(SysVersionManagementDto dto);
    
    
}
