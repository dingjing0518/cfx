package cn.cf.service.logistics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.LgLogisticsTrackingDto;

public interface LgLogisticsTrackingService {
    /**
     * 货物所在地列表
     * 
     * @param qm
     * @return
     */
    PageModel<LgLogisticsTrackingDto> getLogisticsTracking(QueryModel<LgLogisticsTrackingDto> qm);

    /**
     * 编辑/保存货物所在地
     * 
     * @param dto
     * @return
     */
    int saveLgLogisticsTrack(LgLogisticsTrackingDto dto);

    /**
     * 删除货物所在地
     * 
     * @param dto
     * @return
     */
    int delLgLogisticsTrack(LgLogisticsTrackingDto dto);

}
