package cn.cf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.B2bAuctionDaoEx;
import cn.cf.dto.B2bAuctionDto;
import cn.cf.service.B2bAuctionService;


@Service
public class B2bAuctionServiceImpl implements B2bAuctionService{
	
	@Autowired
	private B2bAuctionDaoEx auctionDaoEx;
	
	/**
	 * 根据pk查询场次
	 */
	@Override
	public B2bAuctionDto getByPk(String pk) {
		return auctionDaoEx.getByPk(pk);
	}





}
