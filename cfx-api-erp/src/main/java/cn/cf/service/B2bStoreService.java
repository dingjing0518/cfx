package cn.cf.service;

import cn.cf.dto.B2bStoreDto;

public interface B2bStoreService {

	B2bStoreDto getByPk(String pk);

	B2bStoreDto storeIsOpen(String pk);

	B2bStoreDto getByCompanyPk(String companyPk);
}
