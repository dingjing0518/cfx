package cn.cf.service.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bStoreExtDao;
import cn.cf.dto.B2bStoreDto;
import cn.cf.service.manage.OperationManageService;

/**
 * Created by zj on 2018/5/8.
 */
@Service
public class OperationServiceManageImpl implements OperationManageService {

	@Autowired
	private B2bStoreExtDao b2bStoreExtDao;

    @Override
    public B2bStoreDto getB2bStoreByCompany(String companyPk) {
        return b2bStoreExtDao.getByCompanyPk(companyPk);
    }
}
