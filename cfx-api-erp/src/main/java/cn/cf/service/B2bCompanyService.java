package cn.cf.service;

import java.util.Map;

import cn.cf.dto.B2bCompanyDto;

public interface B2bCompanyService {

	B2bCompanyDto getByName(String companyName);

	B2bCompanyDto getByCode(String companyCode);

	String createNewCompany(String companyName, String currentCompanyPk);

	B2bCompanyDto getByPk(String purchaserPk);

	B2bCompanyDto getCompanyInfoByMap(Map<String, Object> param);



}
