package cn.cf.service;

import java.util.List;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bStoreDtoEx;
import cn.cf.model.B2bStore;

public interface B2bStoreService {
	
	/**
	 * 查询店铺信息
	 * @param storePk
	 * @return
	 */
	B2bStoreDtoEx getCompStoreByStorePk(String storePk);

	RestCode updateIsOpen(B2bStore store);
	List<B2bStoreDto> searchStoreByBrandPk(String brandPk);
}
