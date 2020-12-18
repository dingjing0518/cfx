package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillCustomerApplyDto;
import cn.cf.dto.B2bBillCustomerApplyExtDto;

public interface B2bBillCustomerApplyExtDao  extends B2bBillCustomerApplyDao{

	int searchGridExtCount(Map<String, Object> map);

	List<B2bBillCustomerApplyExtDto> searchGridExt(Map<String, Object> map);

	int updateExt(B2bBillCustomerApplyDto dto);

	B2bBillCustomerApplyExtDto getByMap(Map<String, Object> map);


}
