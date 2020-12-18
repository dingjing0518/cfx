package cn.cf.service.foreign;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCompanyDto;

public interface ForeignCompanyService {

	/**
	 * 获取某一个公司
	 * @param pk
	 * @return
	 */
	B2bCompanyDto getCompany(String pk);
//	
	
	/**
	 * 根据名称获取某一个公司
	 * @param pk
	 * @return
	 */
	B2bCompanyDto getCompanyByName(String companyName);

	/**
	 *根据条件获取公司
	 * @param map
	 * @return
	 */
	List<B2bCompanyDto> getCompanyDtoByMap(Map<String, Object> map);
}
