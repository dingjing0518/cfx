package cn.cf.service.logistics.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.LgLogisticsTrackingExtDao;
import cn.cf.dto.LgLogisticsTrackingDto;
import cn.cf.model.LgLogisticsTracking;
import cn.cf.service.logistics.LgLogisticsTrackingService;

@Service
public class LgLogisticsTrackingServiceImpl implements LgLogisticsTrackingService{
	
	@Autowired
	private LgLogisticsTrackingExtDao trackingExtDao;

	@Override
	public PageModel<LgLogisticsTrackingDto> getLogisticsTracking(QueryModel<LgLogisticsTrackingDto> qm) {
		PageModel<LgLogisticsTrackingDto> pm = new PageModel<LgLogisticsTrackingDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("orderPk", qm.getEntity().getOrderPk());
        int totalCount = trackingExtDao.countLgLogisticsTracking(map);
        List<LgLogisticsTrackingDto> list = trackingExtDao.getLgLogisticsTracking(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
	}

	@Override
	public int saveLgLogisticsTrack(LgLogisticsTrackingDto dto) {
		int retVal = 0 ;
		LgLogisticsTracking model = new LgLogisticsTracking();
		model.UpdateDTO(dto);
		//更新
		if (model.getPk()!=null&& !model.getPk().equals("")) {
			retVal =	trackingExtDao.updateExt(model);
		}else {//添加
			retVal =	trackingExtDao.insertExt(model);
		}
		return retVal;
	}

	@Override
	public int delLgLogisticsTrack(LgLogisticsTrackingDto dto) {
		
		return trackingExtDao.delete(dto.getPk());
	}
	
}
