package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bLoanNumberDtoEx;
import cn.cf.model.B2bLoanNumber;

public interface B2bLoanNumberDaoEx extends B2bLoanNumberDao{
	
	
	List<B2bLoanNumberDtoEx> searchLoanNumberDtoByRepayment(Map<String,String> map);
	
	void updateByOrderNumber(B2bLoanNumber b2bLoanNumber);
	
	Double  searchSumLoan(Map<String,Object> map);
}
