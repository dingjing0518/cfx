package cn.cf.dao;

import java.util.List;

import cn.cf.dto.B2bPaymentDto;

public interface B2bPaymentExDao extends B2bPaymentDao{

	 B2bPaymentDto getByType(Integer type);
	 
	 List<B2bPaymentDto> getPaymentByInType(Integer... typeList);
}
