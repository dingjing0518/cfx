package cn.cf.service.manage;

import cn.cf.dto.B2bStoreDto;

/**
 * Created by zj on 2018/5/8.
 */
public interface OperationManageService {
	
    B2bStoreDto getB2bStoreByCompany(String companyPk);
}
