package cn.cf.service;

import java.util.List;
import cn.cf.common.RestCode;
import cn.cf.dto.LgLogisticsTrackingDto;
import cn.cf.dto.LgLogisticsTrackingDtoEx;

public interface LgLogisticsTrackingService {


	/**
	 * 更新货物所在地
	 * @param dto
	 * @return
	 */
	RestCode updateTracking(LgLogisticsTrackingDtoEx dto);

	
	/**
	 * 查询货物的所有货物所在地
	 * @param orderPk 运货单pk
	 * @return
	 */
	List<LgLogisticsTrackingDto>  getLgTrackingsByPk(String orderPk);

	
	/**
	 * 根据pk查询货物所在地
	 * @param pk pk  
	 * @return
	 */
	LgLogisticsTrackingDto searchLgTrackingsByPk(String pk);

}
