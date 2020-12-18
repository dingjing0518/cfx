package cn.cf.service.platform;

import java.util.List;

import cn.cf.dto.SysCompanyBankcardDto;

public interface B2bCompanyBankcardService {

	SysCompanyBankcardDto getCompanyBankCard(String supplierPk,String bankPk);
	
	List<SysCompanyBankcardDto> getCompanyBankCardList(String companyPk);
}
