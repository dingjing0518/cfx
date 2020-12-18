package cn.cf.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bCompanyDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.model.B2bCompany;

import cn.cf.service.B2bCompanyService;
import cn.cf.util.KeyUtils;

@Service
public class B2bCompanyServiceImpl implements B2bCompanyService {

    @Autowired
    private B2bCompanyDaoEx b2bCompanyDaoEx;

    @Override
    public B2bCompanyDto getByName(String companyName) {

        return b2bCompanyDaoEx.getByName(companyName);
    }

    @Override
    public B2bCompanyDto getByCode(String companyCode) {
        return b2bCompanyDaoEx.getByOrganizationCode(companyCode);
    }

    @Override
    @Transactional
    public String createNewCompany(String companyName, String currentCompanyPk) {

        B2bCompany b2bCompany = new B2bCompany();
        String pk = KeyUtils.getUUID();
        b2bCompany.setPk(pk);
        b2bCompany.setName(companyName);
        b2bCompany.setIsDelete(1);
        b2bCompany.setAuditStatus(1);
        b2bCompany.setAuditSpStatus(0);
        b2bCompany.setInsertTime(new Date());
        b2bCompany.setUpdateTime(new Date());
        b2bCompany.setParentPk(currentCompanyPk);// 总公司Pk
        b2bCompany.setCompanyType(1);
        b2bCompany.setIsVisable(1);
        b2bCompany.setIsPerfect(1);
        b2bCompanyDaoEx.insert(b2bCompany);
        return pk;
    }

    @Override
    public B2bCompanyDto getByPk(String purchaserPk) {

        return b2bCompanyDaoEx.getByPk(purchaserPk);
    }

	@Override
	public B2bCompanyDto getCompanyInfoByMap(Map<String, Object> param) {
		return b2bCompanyDaoEx.getCompanyInfoByMap(param);
	}

}
