package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entity.LoanDetail;
import cn.cf.model.B2bLoanNumber;

public interface  B2bLoanNumberExtDao extends B2bLoanNumberDao {

	String searchBankAmount(Map<String, Object> map);

	List<LoanDetail> searchLoanDetail(Map<String, Object> map);


    int updateExt(B2bLoanNumber loanNumber);
}
