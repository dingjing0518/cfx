package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAuctionDto;
import cn.cf.model.B2bAuction;

public interface B2bAuctionDaoEx extends B2bAuctionDao {

	/**
	 * 部分更新
	 * @param auction
	 * @return
	 */
	int updateEx(B2bAuction auction);
	
	
	/**
	 * 存在性验证
	 * @param dto
	 * @return
	 */
	int searchEntity(B2bAuctionDto dto);

	
	List<B2bAuctionDto> getAucListSort();
	
	/**
	 * 竞拍场次管理的页签数量
	 * @param map
	 * @return
	 */
	Map<String, Object> searchAuctionListCount(Map<String, Object> map);

}
