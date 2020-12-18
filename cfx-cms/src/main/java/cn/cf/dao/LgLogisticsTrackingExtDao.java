package cn.cf.dao;


import java.util.List;
import java.util.Map;

import cn.cf.dto.LgLogisticsTrackingDto;
import cn.cf.model.LgLogisticsTracking;

public interface LgLogisticsTrackingExtDao  extends LgLogisticsTrackingDao{

    int countLgLogisticsTracking (Map<String, Object> map);

    List<LgLogisticsTrackingDto> getLgLogisticsTracking(Map<String, Object> map);

	int updateExt(LgLogisticsTracking model);

	int insertExt(LgLogisticsTracking model);

   

}