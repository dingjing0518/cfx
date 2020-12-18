package cn.cf.dao;

import cn.cf.dto.B2bStoreDto;

public interface B2bStoreDaoEx extends B2bStoreDao{
	
	B2bStoreDto storeIsOpen(String pk);
}
