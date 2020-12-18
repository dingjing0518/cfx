package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAuctionGoodsDto;
import cn.cf.dto.B2bAuctionGoodsDtoEx;


public interface B2bAuctionGoodsDaoEx extends B2bAuctionGoodsDao{

	
	/**
	 * 查询商品正在进行中的场次
	 * @param auctionMap
	 * @return
	 */


	String searchOne(Map<String, Object> map);

 

	 
	
	
 
	
	
	 
	
	
	
 
 
	
	
	
	
	
	B2bAuctionGoodsDtoEx checkGoodsAuctionStatus(String goodsPk);
	
	int deleteByPk(String pk);
	
	
	
	
	
	
	
 	
 	List<B2bAuctionGoodsDto> searchAuctingGoods(Map<String, Object> map);
	

 	int deleteByGoodsPk(String goodsPk);
}
