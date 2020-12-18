package cn.cf.service;

import java.util.Map;

import cn.cf.common.RestCode;

public interface B2bApiToLogisticsService {
	/**
	 * 物流系统同步承运商
	 * @param map ：carrierPk,carrierName,isDelete,isVisable
	 * @return
	 */
	RestCode updateLogisticsCarrier(Map<String, Object> map);

}
