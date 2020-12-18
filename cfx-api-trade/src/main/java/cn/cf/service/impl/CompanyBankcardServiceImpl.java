package cn.cf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.service.CommonService;
import cn.cf.service.CompanyBankcardService;

@Service
public class CompanyBankcardServiceImpl implements CompanyBankcardService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonService commonService;
	
	@Override
	public SysCompanyBankcardDto getCompanyBankCard(
			String supplierPk) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("supplierPk", supplierPk);
		SysCompanyBankcardDto sysCompanyBankcardDto = null;
		try {
			sysCompanyBankcardDto =	commonService.getCompanyBankCard(supplierPk);
		} catch (Exception e) {
			logger.error("getCompanyBankCard",e);
		}
		return sysCompanyBankcardDto;
	}

}
