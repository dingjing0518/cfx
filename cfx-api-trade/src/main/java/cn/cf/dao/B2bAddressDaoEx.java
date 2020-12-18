package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAddressDto;
import cn.cf.model.B2bAddress;

public interface B2bAddressDaoEx extends B2bAddressDao{

	int searchExistence(B2bAddressDto addressDto);
	
	int updateNoDefault(String companyPk);
	
	B2bAddress searchDTO(B2bAddress addressM);
	
	int updateEx(B2bAddress model);
	
	/**
	 * 根据companyPk查询默认收货地址
	 * @param companyPk
	 * @return
	 */
	B2bAddress getDefaultAddressByCompanyPk(String companyPk);
	
	/**
	 * 根据companyPk查询该公司的第一条收货地址
	 * @param companyPk
	 * @return
	 */
	B2bAddress getFirstAddressByCompanyPk(String companyPk);
	
	
	List<B2bAddressDto> searchListEx(Map<String, Object> map);
	/**
	 * 根据默认地址/时间依次排序
	 * @param companyPk
	 * @return
	 */
	List<B2bAddressDto> searchAddressByOrder(String companyPk);
	
	/**
	 * 根据pk查询地址
	 */
	B2bAddressDto getByPkEx(String pk);

	B2bAddressDto searchOne(B2bAddressDto address); 
	
}
