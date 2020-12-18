package cn.cf.service;

import java.util.List;

import cn.cf.entity.B2bPriceMovementEntity;

public interface B2bPriceHistoryService {
	
	List<B2bPriceMovementEntity> searchB2bPriceHistoryList(String pk,Integer days);
}
