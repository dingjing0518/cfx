package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bStoreDtoEx;


public interface B2bStoreDaoEx extends B2bStoreDao{
	
	/*
	 * 根据店铺pk获取公司pk
	 */
	B2bStoreDtoEx getCompStoreByStorePk(String pk);
	/**
	 * 根据商品pk查询店铺开启状态
	 * @param goodsPk
	 * @return
	 */
	int isOpenByGoodsPk(String goodsPk);
	
	B2bStoreDto getByGoodsPk(String goodsPk);
	List<B2bStoreDto> searchStoreByBrandPk(Map<String, Object> map);

	
	/**
	 * 检查店铺是否开店
	 * @param pk
	 * @return store
	 */
	B2bStoreDto storeIsOpen(String pk);
	
	
}
