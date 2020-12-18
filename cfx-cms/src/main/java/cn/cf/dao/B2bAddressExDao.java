package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bAddressDto;
import cn.cf.model.B2bAddress;

public interface B2bAddressExDao extends B2bAddressDao {

	List<B2bAddressDto> getByCompanyPkEx(java.lang.String companyPk);

	List<B2bAddressDto> getAddressByMap(Map<String, Object> map);

	List<B2bAddressDto> getAddressisDefaultByCompanyPk(String companyPk);

	Integer updateAddress(B2bAddress b2bAddress);

	B2bAddressDto getExtByMap(Map<String, Object> map);

}
