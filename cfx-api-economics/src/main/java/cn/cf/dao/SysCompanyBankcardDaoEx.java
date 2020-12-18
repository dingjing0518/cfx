package cn.cf.dao;

import java.util.List;

import cn.cf.dto.SysCompanyBankcardDto;

public interface SysCompanyBankcardDaoEx extends SysCompanyBankcardDao{
	
	List<SysCompanyBankcardDto> getCompanyBankcard(String companyPk);
}	
