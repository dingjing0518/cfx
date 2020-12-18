package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bLoanNumberDtoEx;

public interface B2bLoanNumberDaoEx extends B2bLoanNumberDao{

	List<B2bLoanNumberDtoEx> searchOldUsersLoanByEconomicsGoodsTypeIsOne(
			Map<String, Object> orderMap);

	List<B2bLoanNumberDtoEx> searchNewUsersLoanByEconomicsGoodsTypeIsOne(
			Map<String, Object> orderMap);
	
	 
}
