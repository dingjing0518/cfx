package cn.cf.service;

import cn.cf.dto.B2bAuctionDto;

public interface B2bAuctionService {
	
	/**
	 * 根据pk查询场次
	 * @param pk
	 * @return
	 */
	B2bAuctionDto getByPk(String pk);



	
}
