package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCompanyDtoEx;

public interface B2bCompanyService {

	/**
	 * 获取某一个公司
	 * @param pk
	 * @return
	 */
	B2bCompanyDtoEx getCompany(String pk);
	/**
	 * 根据公司pk返回所有子母公司
	 * @param companyType(1:采购商 2:供应商)
	 * @param companyPk(公司pk)
	 * @param returnType(1:返回子母所有 2:返回子公司)
	 * @param map(附加参数)
	 * @return
	 */
	List<B2bCompanyDtoEx> searchCompanyList(Integer companyType,String companyPk,Integer returnType,Map<String,Object> map);
	/**
	 *根据条件获取公司
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map);

	/**
	 * 根据公司名称获取查询公司
	 * @param name
	 * @return
	 */
	B2bCompanyDto getCompanyByName(String name);
	
}
