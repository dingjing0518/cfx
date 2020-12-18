package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.common.RestCode;
import cn.cf.dao.LgDeliveryOrderDaoEx;
import cn.cf.dao.LgLogisticsTrackingDao;
import cn.cf.dao.LgTrackDetailDaoEx;
import cn.cf.dto.LgDeliveryOrderDto;
import cn.cf.dto.LgLogisticsTrackingDto;
import cn.cf.dto.LgLogisticsTrackingDtoEx;
import cn.cf.model.LgLogisticsTracking;
import cn.cf.model.LgTrackDetail;
import cn.cf.service.LgLogisticsTrackingService;
import cn.cf.util.KeyUtils;

@Service
public class LgLogisticsTrackingServiceImpl implements LgLogisticsTrackingService {
	
	@Autowired
	private LgLogisticsTrackingDao trackingDao;
	
	@Autowired
	private LgDeliveryOrderDaoEx deliveryOrderDao;
	
	@Autowired
	private LgTrackDetailDaoEx lgTrackDetailDaoEx;

	@Override
	public RestCode updateTracking(LgLogisticsTrackingDtoEx dto) {
		RestCode code = RestCode.CODE_0000;
		LgLogisticsTracking track=new LgLogisticsTracking();
		track.UpdateDTO(dto);
		if(null!=dto.getIsDelete()&&dto.getIsDelete()==1){
			trackingDao.delete(dto.getPk());
		}else if(null==dto.getPk()||"".equals(dto.getPk())){
			addTrackDeatil(track);
			trackingDao.insert(track);
			
		}else if(null!=dto.getPk()&&!"".equals(dto.getPk())){
			addTrackDeatil(track);
			trackingDao.update(track);
		
		}
		return code;
	}
	
	private void addTrackDeatil(LgLogisticsTracking logisticsTracking) {
		LgDeliveryOrderDto order=deliveryOrderDao.getByPk(logisticsTracking.getOrderPk());
		if(null!=order){
			String provinceName=logisticsTracking.getProvinceName()==null?"":logisticsTracking.getProvinceName();
			String CityName=logisticsTracking.getCityName()==null?"":logisticsTracking.getCityName();
			String AreaName=logisticsTracking.getAreaName()==null?"":logisticsTracking.getAreaName();
			String Meno=logisticsTracking.getMeno()==null?"": "".equals(logisticsTracking.getMeno())?"":"    备注："+logisticsTracking.getMeno();
			LgTrackDetail model=new LgTrackDetail();
			model.setPk(KeyUtils.getUUID());
			model.setDeliveryPk(order.getPk());
			model.setOrderStatus(order.getOrderStatus());
			model.setStepDetail("货物所在地在"+ provinceName+CityName+AreaName+Meno);
			model.setInsertTime(new Date());
			model.setUpdateTime(new Date());
			model.setFinishedTime(new Date());
			lgTrackDetailDaoEx.insert(model); 
		}
	}
	
	
	@Override
	public List<LgLogisticsTrackingDto>  getLgTrackingsByPk(String orderPk) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("orderPk", orderPk);
		map.put("orderName", "updateTime");
		map.put("orderType", "desc"); 
		List<LgLogisticsTrackingDto> list=trackingDao.searchList(map);
		return list;
	}
	
	
	@Override
	public LgLogisticsTrackingDto searchLgTrackingsByPk(String pk) {
		return 	trackingDao.getByPk(pk);
	}
	

}
