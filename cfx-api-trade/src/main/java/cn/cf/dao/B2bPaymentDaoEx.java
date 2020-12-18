package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPaymentDtoEx;

public interface B2bPaymentDaoEx extends B2bPaymentDao{
	
	List<B2bPaymentDtoEx> searchPayment(Map<String,Object> map);
}
