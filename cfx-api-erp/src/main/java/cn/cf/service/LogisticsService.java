package cn.cf.service;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;

import cn.cf.entity.LogisticsDeliverySync;

public interface LogisticsService {

	@Transactional
	void updateAllLogistics(String storePk,JSONArray array) throws Exception;

	String syncDelivery(LogisticsDeliverySync logisticsDeliverySync);

	String updateDelivery(LogisticsDeliverySync logisticsDeliverySync);

}
